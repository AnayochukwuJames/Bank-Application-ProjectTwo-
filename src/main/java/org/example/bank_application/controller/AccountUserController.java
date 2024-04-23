package org.example.bank_application.controller;

import jakarta.validation.Valid;
import org.example.bank_application.model.AccountUser;
import org.example.bank_application.service.AccountUserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/user")
public class AccountUserController {
    private final AccountUserService accountUserService;

    public AccountUserController(AccountUserService accountUserService) {
        this.accountUserService = accountUserService;
    }
    @PostMapping("create")
    public ResponseEntity<AccountUser> createUserAccount(@RequestBody @Valid AccountUser accountUser){
        return accountUserService.createUserAccount(accountUser);
    }
    @PutMapping("update/{id}")
    public ResponseEntity<AccountUser> updateAccountUser(@Valid @PathVariable Long id, @RequestBody AccountUser accountUser){
        return accountUserService.updateAccountUser(id, accountUser);
    }
    @GetMapping("getAll")
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