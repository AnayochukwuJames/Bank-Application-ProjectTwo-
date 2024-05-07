package org.example.bank_application.service;

import lombok.RequiredArgsConstructor;
import org.example.bank_application.enums.TransactionType;
import org.example.bank_application.exceptionHandler.TransactionException;
import org.example.bank_application.model.AccountUser;
import org.example.bank_application.model.BankAccount;
import org.example.bank_application.model.Transactions;
import org.example.bank_application.repository.BankAccountRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class BankOperationService {
//
//    private final BankAccountRepository bankAccountRepository;
//
//    public ResponseEntity<Double> depositFund(AccountUser accountUser, Double amount){
//        if (amount < 1) {
//            throw new TransactionException("Amount must be grater than 0");
//        }
//            BankAccount bankAccount = bankAccountRepository.getByAccountNumber(accountUser);
//        return new ResponseEntity<>(0.0, HttpStatus.BAD_REQUEST);
//
//
//    }


    private final BankAccountService bankAccountService;


    private final TransactionsService transactionService;

    @Async
    public ResponseEntity<BankAccount> depositFund(String accountNumber, double amount){
        if( amount < 1 ){
            throw new TransactionException("You can not deposit negative amount");
        }
        BankAccount account = bankAccountService.getByAccountNumber(accountNumber).getBody();
        assert account != null;

        account.setAccountBalance(amount + account.getAccountBalance());

        Transactions transaction = new Transactions();
        transaction.setAccountNumber(accountNumber);
        transaction.setAmount(amount);
        transaction.setTransactionType(TransactionType.DEPOSIT);
        transactionService.postNewTransaction(transaction);
        return new ResponseEntity<>(bankAccountService.updateAccount(account).getBody(), HttpStatus.OK);
    }
    @Transactional
    public ResponseEntity<BankAccount> withdrawFund(String accountNumber, double amount){
        if( amount < 1 ){
            throw new TransactionException("You can not deposit negative amount");
        }
        BankAccount account = bankAccountService.getByAccountNumber(accountNumber).getBody();
        assert account != null;

        if( account.getAccountBalance() < amount ){
            throw new TransactionException("Insufficient Balance");
        }
        account.setAccountBalance(account.getAccountBalance() - amount);

        Transactions transaction = new Transactions();
        transaction.setAccountNumber(accountNumber);
        transaction.setAmount(amount);
        transaction.setTransactionType(TransactionType.WITHDRAWAL);
        transactionService.postNewTransaction(transaction);
        return new ResponseEntity<>(bankAccountService.updateAccount(account).getBody(), HttpStatus.OK);
    }

    @Transactional
    public ResponseEntity<String> transferFunds(String accountFrom, String accountTo, double amount ){
        try{
            withdrawFund(accountFrom, amount);
            depositFund(accountTo, amount);
            return new ResponseEntity<>("Transaction Successful", HttpStatus.OK);
        } catch (TransactionException transactionException){
            System.out.println(transactionException.getMessage());
        }
        return new ResponseEntity<>("Transaction failed", HttpStatus.NOT_ACCEPTABLE);
    }

}
