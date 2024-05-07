package org.example.bank_application.controller;

import io.swagger.v3.oas.annotations.parameters.RequestBody;
import lombok.RequiredArgsConstructor;
import org.example.bank_application.model.BankAccount;
import org.example.bank_application.service.BankOperationService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("operation")
@RequiredArgsConstructor
public class BankOperationController {

    private final BankOperationService bankOperationService;

    @PostMapping("deposit")
    public ResponseEntity<BankAccount> depositFund(@RequestBody String accountNumber, double amount){
        return bankOperationService.depositFund(accountNumber, amount);

    }
    @PostMapping("withdraw")
    public ResponseEntity<BankAccount> withdrawFund(String accountNumber, double amount){
        return bankOperationService.withdrawFund(accountNumber,amount);
    }
    @PostMapping("transfer")
    public ResponseEntity<String> transferFunds(String accountFrom, String accountTo, double amount ){
        return bankOperationService.transferFunds(accountFrom,accountTo,amount);
    }

}
