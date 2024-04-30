package org.example.bank_application.controller;

import lombok.RequiredArgsConstructor;
import org.example.bank_application.model.AccountUser;
import org.example.bank_application.model.BankAccount;
import org.example.bank_application.service.BankAccountService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@RequiredArgsConstructor
@RestController
@RequestMapping("bankAccount")
public class BankAccountController {


    private final BankAccountService bankAccountService;

    @GetMapping("")
    public ResponseEntity<List<BankAccount>> getAllBankAccounts (){
        return bankAccountService.getAllBankAccounts();
    }

    @GetMapping("/{id}")
    public ResponseEntity<BankAccount> getById(@PathVariable Long id){
        return bankAccountService.getById(id);
    }

    @GetMapping("/AccountUser")
    public ResponseEntity<BankAccount> findByAccountUserUsername(@RequestParam String accountUser){
        return bankAccountService.findByAccountUserUsername(accountUser);
    }
    @PostMapping("create")
    public ResponseEntity<BankAccount>createBankAccount(@RequestBody AccountUser accountUser){
        return bankAccountService.createBankAccount(accountUser);
    }

    @PostMapping("createWithAmount")
    public ResponseEntity<BankAccount>createBankAccountWithAmount(@RequestBody AccountUser accountUser, double openingAmount){
        return bankAccountService.createBankAccountWithAmount(accountUser, openingAmount);
    }

}
