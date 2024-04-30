package org.example.bank_application.config;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.constraints.NotNull;
import org.example.bank_application.service.JWTService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class BankApplicationFilter extends OncePerRequestFilter {
    @Autowired
    private JWTService jwtService;
    @Autowired
    private UserDetailsService userDetailsService;

    @Override
    protected void doFilterInternal(@NotNull HttpServletRequest request,
                                    @NotNull HttpServletResponse response,
                                    @NotNull FilterChain filterChain) throws ServletException, IOException {

        final String authorization = request.getHeader("Authorization");
        final String jwtToken;
        final String username;
        if(authorization == null || ! authorization.startsWith("Bearer ")){
            filterChain.doFilter(request, response);
            return;
        }

        jwtToken = authorization.substring(7);
        System.out.println(jwtToken);
        username = jwtService.extractUsername(jwtToken);
        System.out.println(username);

        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null){
            UserDetails userDetails = userDetailsService.loadUserByUsername(username);

            if (jwtService.isTokenValid(jwtToken,userDetails)){
                UsernamePasswordAuthenticationToken authenticationToken =new UsernamePasswordAuthenticationToken(userDetails,
                        null, userDetails.getAuthorities());
                authenticationToken.setDetails(new WebAuthenticationDetailsSource()
                        .buildDetails(request));

                SecurityContextHolder.getContext().setAuthentication(authenticationToken);
            }
        }
        filterChain.doFilter(request, response);
    }
}
