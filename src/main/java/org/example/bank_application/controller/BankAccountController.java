package org.example.bank_application.controller;

import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import org.example.bank_application.dto.BankOperationRequest;
import org.example.bank_application.dto.TransferRequest;
import org.example.bank_application.exceptionHandler.TransactionException;
import org.example.bank_application.model.AccountUser;
import org.example.bank_application.model.BankAccount;
import org.example.bank_application.service.BankAccountService;
import org.example.bank_application.service.BankOperationService;
import org.example.bank_application.service.TransactionsService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@RequiredArgsConstructor
@RestController
@RequestMapping("bankAccount")
public class BankAccountController {


    private final BankOperationService bankOperationService;

    private final TransactionsService transactionsService;


    @PostMapping("/deposit")
    public ResponseEntity<BankAccount> depositFund(@RequestBody BankOperationRequest request) throws MessagingException {
        return new ResponseEntity<>(bankOperationService.depositFund(request.getAccountNumber(), request.getAmount(), transactionsService.generateTxnId()).getBody(), HttpStatus.OK);
    }

    @PostMapping("/withdraw")
    public ResponseEntity<BankAccount> withdrawFund(@RequestBody BankOperationRequest request) throws MessagingException{
        return new ResponseEntity<>(bankOperationService.withdrawFund(request.getAccountNumber(), request.getAmount(), transactionsService.generateTxnId()).getBody(), HttpStatus.OK);
    }

    @PostMapping("/transfer")
    @Transactional
    public ResponseEntity<String> transferFunds(@RequestBody TransferRequest request ) throws MessagingException {
        try{
            bankOperationService.transferFunds(request.getAccountFrom(), request.getAccountTo(), request.getAmount());
            return new ResponseEntity<>("Transaction Successful", HttpStatus.ACCEPTED);
        } catch (TransactionException transactionException){
            System.out.println(transactionException.getMessage());
        }
        return new ResponseEntity<>("Transaction failed", HttpStatus.NOT_ACCEPTABLE);
    }

}
