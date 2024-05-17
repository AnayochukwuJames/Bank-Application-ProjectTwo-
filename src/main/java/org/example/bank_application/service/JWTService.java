package org.example.bank_application.service;


import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;


@Service
public class JWTService {
    @Value("${application.secretKey}")
    private String SECRET_KEY;

private Key getSignIngKey(){
    byte[] keyBytes = Decoders.BASE64.decode(SECRET_KEY);
    return Keys.hmacShaKeyFor(keyBytes);
}

public String createToken(UserDetails userDetails){
    return createFreshToken(new HashMap<>(),userDetails );
}

private String createFreshToken (Map<String, Object> mapOfClaims, UserDetails userDetails){
    return Jwts.builder()
            .addClaims(mapOfClaims)
            .setSubject(userDetails.getUsername())
            .setIssuedAt(new Date())
            .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 10))
            .setIssuer("Bank App 1.0")
            .signWith(getSignIngKey(), SignatureAlgorithm.HS256)
            .compact();
}

//    String generateRefreshToken(Map<String, Object> extraClaim, UserDetails userDetails) {
//        return null;
//    }

    private Claims extractAllClaims(String token){
    return Jwts.parserBuilder()
            .setSigningKey(getSignIngKey())
            .build()
            .parseClaimsJws(token)
            .getBody();
}

public <T> T extractClaims(String token, Function<Claims, T> claimsTFunction){
    Claims claims = extractAllClaims(token);
    return claimsTFunction.apply(claims);
}

public String extractUsername(String token){
    return extractClaims(token, Claims::getSubject);
}

public Date extractExpiration(String token){
    return extractClaims(token, Claims::getExpiration);
}

public Date extractDateIssued(String token){
    return extractClaims(token, Claims::getIssuedAt);
}
private boolean isTokenExpired(String token){
    return extractExpiration(token).before(new Date(System.currentTimeMillis()));
}

private boolean isTokenGeneratedFromServer(String token){
    return extractClaims(token, Claims::getIssuedAt).equals("Bank App 1.0");
}

public boolean isTokenValid(String token, UserDetails userDetails){
    String username = extractClaims(token, Claims::getSubject);
    return  username.equalsIgnoreCase(userDetails.getUsername()) && !isTokenExpired(token);

}




}
