package org.example.bank_application.service;

import io.swagger.v3.oas.annotations.parameters.RequestBody;
import jakarta.mail.MessagingException;
import lombok.*;
import org.apache.commons.lang3.RandomStringUtils;
import org.example.bank_application.config.AccountConfig;
import org.example.bank_application.dto.LoginRequest;
import org.example.bank_application.dto.LoginResponse;
import org.example.bank_application.dto.ChangePasswordRequest;
import org.example.bank_application.enums.Role;
import org.example.bank_application.model.AccountUser;
import org.example.bank_application.model.BankAccount;
import org.example.bank_application.model.ResetCode;
import org.example.bank_application.repository.AccountUserRepository;
import org.example.bank_application.repository.ResetCodeRepository;
import org.springframework.beans.factory.annotation.Autowired;
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
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;
import java.util.Random;

@Service
@RequiredArgsConstructor
public class AccountUserService {


    private PasswordEncoder passwordEncoder;

    private final AuthenticationManager authenticationManager;

    private final AccountUserRepository accountUserRepository;

    private final JWTService jwtService;

    private final MessageService messageService;

    private final BankAccountService bankAccountService;

    private final AccountConfig accountConfig;

    private final ResetCodeRepository resetCodeRepository;


//    public ResponseEntity<AccountUser> postAccountUser(AccountUser user) throws MessagingException {
//        passwordEncoder = accountConfig.passwordEncoder();
//        user.setPassword(passwordEncoder.encode(user.getPassword()));
//        user.setRole(Role.USER);
//        AccountUser savedUser = accountUserRepository.save(user);
//        String accountNumber = String.valueOf(bankAccountService.createBankAccount(savedUser).getBody());
//        savedUser.setAccountNumber(accountNumber);
//        messageService.registrationNotification(user.getUsername(), user.getFirstName(), accountNumber);
//
//        return new ResponseEntity<>(savedUser, HttpStatus.CREATED);
//    }

    public ResponseEntity<AccountUser> postAccountUser(AccountUser user) throws MessagingException {
        passwordEncoder = accountConfig.passwordEncoder();
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRole(Role.USER);
        AccountUser savedUser = accountUserRepository.save(user);
        bankAccountService.createBankAccount(savedUser);
        messageService.registrationNotification(user.getUsername(), user.getFirstName());
        return new ResponseEntity<>(savedUser, HttpStatus.CREATED);
    }

    public ResponseEntity<LoginResponse> authenticated(LoginRequest loginRequest) throws MessagingException {
        Authentication auth = authenticationManager
                .authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));
        if (auth != null) {
            AccountUser accountUser = accountUserRepository.getByUsername(loginRequest.getUsername());
            String token = jwtService.createToken(accountUser);
            messageService.loginNotification(accountUser.getUsername(), "Dear " + accountUser.getFirstName() + ", \n"
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
    public ResponseEntity<AccountUser> delete(@PathVariable Long id){
        AccountUser deletedUser = accountUserRepository.findById(id).get();
        accountUserRepository.deleteById(id);
        return new ResponseEntity<>(deletedUser, HttpStatus.OK);
    }


    @CacheEvict(value = {"updateAccountUser", "getAccountById"}, allEntries = true, key = "#id")
    public ResponseEntity<AccountUser> updateAccountUser(@PathVariable Long id, @RequestBody AccountUser user){
        AccountUser updatedUser = accountUserRepository.findById(id).get();
        updatedUser.setFirstName(user.getFirstName());
        updatedUser.setMiddleName(user.getMiddleName());
        updatedUser.setLastName(user.getLastName());
        updatedUser.setPhoneNumber(user.getPhoneNumber());
        return new ResponseEntity<>(accountUserRepository.save(updatedUser), HttpStatus.OK);
    }

    @Cacheable(value = "getAllAccountUsers")
    public ResponseEntity<List<AccountUser>> getAllAccountUsers() {
        return new ResponseEntity<>(accountUserRepository.findAll(), HttpStatus.OK);
    }

    @Cacheable(value = "getAccountById", key = "#id")
    public ResponseEntity<AccountUser> getAccountById(Long id) {
        return new ResponseEntity<>(accountUserRepository.findById(id).get(), HttpStatus.OK);
    }
    @Cacheable(value = "getAccountUserByPhoneNumber", key = "#phoneNumber")
    public ResponseEntity<AccountUser> getAccountUserByPhoneNumber(String phoneNumber) {
        return new ResponseEntity<>(accountUserRepository.findAccountUserByPhoneNumber(phoneNumber), HttpStatus.OK);
    }

    @Cacheable(value = "getAccountUserByUsername", key = "#username")
    public ResponseEntity<AccountUser> getAccountUserByUsername(@RequestBody String username) {
        return new ResponseEntity<>(accountUserRepository.getByUsername(username), HttpStatus.OK);
    }

    public String resetUserPassword(String username) throws MessagingException {
        AccountUser user = accountUserRepository.getByUsername(username);
//        if (!user.getUsername().equals(username)) {
//            return "User not found";
//        }
        if (user == null) {
            return "User not found";
        }
        String randomCode = RandomStringUtils.randomNumeric(6);
        try {
            messageService.sendResetCode(user.getUsername(), randomCode);
        } catch (MessagingException exception) {
            exception.printStackTrace();
            return "Error sending reset code";
        }
        ResetCode resetCode = new ResetCode();
        resetCode.setCode(randomCode);
        resetCode.setAccountUser(user);
        resetCodeRepository.save(resetCode);
        return "Reset Code Sent Successfully";
    }

    public String changePassword(ChangePasswordRequest request) {
        ResponseEntity<AccountUser> accountUserResponse = getAccountUserByUsername(request.getUsername());
        if (accountUserResponse == null || accountUserResponse.getBody() == null) {
            return "Account not found";
        }

        AccountUser accountUser = accountUserResponse.getBody();
        ResetCode resetCodes = (ResetCode) resetCodeRepository.findByAccountUser(accountUser);
        if (resetCodes == null || resetCodes.getCode() == null) {
            return "Reset code not found";
        }

        String resetCode = resetCodes.getCode();
        if (!request.getResetCode().equals(resetCode)) {
            return "Invalid Reset Code";
        }
        if (!request.getPassword().equals(request.getConfirmPassword())) {
            return "Password Mismatch";
        }

        String encodedPassword = passwordEncoder.encode(request.getPassword());
        accountUser.setPassword(encodedPassword);
        accountUserRepository.save(accountUser);

        return "Password changed Successfully";
    }
}