package org.example.bank_application.controller;

import jakarta.validation.Valid;
import org.example.bank_application.dto.LoginRequest;
import org.example.bank_application.dto.LoginResponse;
import org.example.bank_application.model.AccountUser;
import org.example.bank_application.service.AccountUserService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/user")
public class AccountUserController {
    private final AccountUserService accountUserService;

    public AccountUserController(AccountUserService accountUserService) {
        this.accountUserService = accountUserService;
    }
    @PostMapping("register")
    public ResponseEntity<AccountUser> createUserAccount(@RequestBody @Valid AccountUser accountUser){
        return accountUserService.createUserAccount(accountUser);
    }

    @PostMapping("login")
    public ResponseEntity<LoginResponse> authenticateUser(@RequestBody @Valid LoginRequest loginRequest){
        return accountUserService.authenticated(loginRequest);
    }

    @PutMapping("update/{id}")
    public ResponseEntity<AccountUser> updateAccountUser(@Valid @PathVariable Long id, @RequestBody AccountUser accountUser){
        return accountUserService.updateAccountUser(id, accountUser);
    }

    @GetMapping("all")
    @PreAuthorize("hasRole(ADMIN)")
    public ResponseEntity<List<AccountUser>> getAllAccountUsers(){
        return accountUserService.getAllAccountUsers();
    }
    @GetMapping("getById/{id}")
    public ResponseEntity<AccountUser> getAccountUserById(@PathVariable Long id){
        return accountUserService.getAccountById(id);
    }
    @DeleteMapping("delete/{id}")
    public ResponseEntity<String> delete(@PathVariable Long id){
        return accountUserService.delete(id);
    }
    @GetMapping("username")
    public ResponseEntity<AccountUser> getAccountUserByUsername(@RequestParam String username){
        return accountUserService.getAccountUserByUsername(username);

    }  @GetMapping("phoneNumber")
    public ResponseEntity<AccountUser> getAccountUserByPhoneNumber(@RequestParam String phoneNumber){
        return accountUserService.getAccountUserByPhoneNumber(phoneNumber);
    }

}