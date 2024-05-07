package org.example.bank_application.service;

import jakarta.mail.MessagingException;
import lombok.*;
import org.example.bank_application.dto.LoginRequest;
import org.example.bank_application.dto.LoginResponse;
import org.example.bank_application.enums.Role;
import org.example.bank_application.model.AccountUser;
import org.example.bank_application.repository.AccountUserRepository;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Async;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AccountUserService {

    private final PasswordEncoder passwordEncoder;

    private final AuthenticationManager authenticationManager;

    private final AccountUserRepository accountUserRepository;

    private final JWTService jwtService;

    private final MessageService messageService;

    private final BankAccountService bankAccountService;

    @Async
    @CacheEvict(value = "createUserAccount",allEntries = true)
    public ResponseEntity<AccountUser> createUserAccount(AccountUser accountUser) throws MessagingException {
        accountUser.setPassword(passwordEncoder.encode(accountUser.getPassword()));
       accountUser.setRole(Role.USER);
       messageService.registrationNotification(accountUser.getUsername(), "Dear \n" + accountUser.getFirstName()
       + "You have Successfully register for Our Bank Application. Please login with your username and password to enjoy our full services \n" +
               "Thank you for Banking with us");

       bankAccountService.createBankAccount(accountUser);


        return new ResponseEntity<>(accountUserRepository.save(accountUser), HttpStatus.CREATED);
    }

    @Async
    public ResponseEntity<LoginResponse> authenticated(LoginRequest loginRequest) throws MessagingException {
        Authentication auth = authenticationManager
                .authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));
        if (auth != null){
            AccountUser accountUser = accountUserRepository.findByUsername(loginRequest.getUsername());
            String token = jwtService.createToken(accountUser);
            messageService.loginNotification(accountUser.getUsername(), "Dear \n" + accountUser.getFirstName() + "\n"
                    + "You have login successful into your Bank Application Account. Please if you come across anything feel free to contact our customer service on this number \n" +
                    "12345678, 07066929216 \n Thanks for Banking with us");
            return new ResponseEntity<>(LoginResponse.builder()
                    .accountUser(accountUser)
                    .token(token)
                    .build(), HttpStatus.OK);
        }
        return null;

    }

    @CacheEvict(value = "delete", allEntries = true)
    public ResponseEntity<String> delete (Long id){
        AccountUser accountUser = accountUserRepository.findById(id).get();
        accountUserRepository.deleteById(id);
        return new ResponseEntity<>(accountUser + "This User has deleted successfully", HttpStatus.OK);
    }

    @CacheEvict(value = {"updateAccountUser", "getAccountById"}, allEntries = true, key = "#id")
    public ResponseEntity<AccountUser> updateAccountUser(Long id, AccountUser accountUser){
        AccountUser accountUser1 = accountUserRepository.findById(id).get();
        accountUser1.setFirstName(accountUser.getFirstName());
        accountUser1.setLastName(accountUser.getLastName());
        accountUser1.setMiddleName(accountUser.getMiddleName());
        accountUser1.setUsername(accountUser.getUsername());
        accountUser1.setPassword(passwordEncoder.encode(accountUser.getPassword()));
        accountUser1.setPhoneNumber(accountUser.getPhoneNumber());
        return new ResponseEntity<>(accountUserRepository.save(accountUser1),HttpStatus.ACCEPTED);
    }

    @Cacheable(value = "getAllAccountUsers")
    public ResponseEntity<List<AccountUser>> getAllAccountUsers(){
        return new ResponseEntity<>(accountUserRepository.findAll(), HttpStatus.OK);
    }

    @Cacheable(value = "getAccountById", key = "#id")
    public ResponseEntity<AccountUser> getAccountById(Long id){
        return new ResponseEntity<>(accountUserRepository.findById(id).get(), HttpStatus.OK);
    }

    @Cacheable(value = "getAccountUserByUsername", key = "#username")
    public ResponseEntity<AccountUser> getAccountUserByUsername(String username){
        return new ResponseEntity<>(accountUserRepository.findByUsername(username), HttpStatus.OK);
    }

    @Cacheable(value = "getAccountUserByPhoneNumber", key = "#phoneNumber")
    public ResponseEntity<AccountUser> getAccountUserByPhoneNumber(String phoneNumber){
        return new ResponseEntity<>(accountUserRepository.findAccountUserByPhoneNumber(phoneNumber), HttpStatus.OK);
    }
}