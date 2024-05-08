package org.example.bank_application.service;

import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import org.example.bank_application.enums.TransactionType;
import org.example.bank_application.exceptionHandler.TransactionException;
import org.example.bank_application.model.AccountUser;
import org.example.bank_application.model.BankAccount;
import org.example.bank_application.model.Transactions;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class BankOperationService {

    private final BankAccountService bankAccountService;


    private final TransactionsService transactionService;

    private final AccountUserService accountUserService;

    private final MessageService messageService;
    public ResponseEntity<BankAccount> depositFund(String accountNumber, double amount, String transId) throws MessagingException {
        if( amount <= 0 ){
            System.out.println(amount);
            throw new TransactionException("You can not deposit negative amount");
        }
        BankAccount account = bankAccountService.getByAccountNumber(accountNumber).getBody();
        assert account != null;

        account.setAccountBalance(amount + account.getAccountBalance());

        Transactions transaction = new Transactions();
        transaction.setAccountNumber(accountNumber);
        transaction.setAmount(amount);
        transaction.setTransactionType(TransactionType.DEPOSIT);
        transaction.setTransactionId(transId);
        transactionService.postNewTransaction(transaction);
        AccountUser user = accountUserService.getAccountById(account.getAccountUser().getId()).getBody();
//        AccountUser user = accountUserService.getAccountUserById(account.getAccountUser().getId()).getBody();
        assert user != null;
        messageService.depositNotification(user.getFirstName(), user.getUsername(), amount);
        return new ResponseEntity<>(bankAccountService.updateAccount(account).getBody(), HttpStatus.OK);
    }

    public ResponseEntity<BankAccount> withdrawFund(String accountNumber, double amount, String transId) throws MessagingException{
        if( amount <= 0 ){
            System.out.println(amount);
            throw new TransactionException("You can not withdraw negative amount");
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
        transaction.setTransactionId(transId);
        transactionService.postNewTransaction(transaction);
        AccountUser user = accountUserService.getAccountById(account.getAccountUser().getId()).getBody();
//        AccountUser user = accountUserService.getAccountUserById(account.getUser().getId()).getBody();
        assert user != null;
        messageService.withdrawalNotification(user.getFirstName(), user.getUsername(), amount);
        return new ResponseEntity<>(bankAccountService.updateAccount(account).getBody(), HttpStatus.OK);
    }

    @Transactional
    public ResponseEntity<String> transferFunds(String accountFrom, String accountTo, double amount ) throws MessagingException{
        try{
            String transId = transactionService.generateTxnId();
            withdrawFund(accountFrom, amount, transId);
            depositFund(accountTo, amount, transId);
            return new ResponseEntity<>("Transaction Successful", HttpStatus.OK);
        } catch (TransactionException transactionException){
            System.out.println(transactionException.getMessage());
        }
        return new ResponseEntity<>("Transaction failed", HttpStatus.NOT_ACCEPTABLE);
    }
}
