package org.example.bank_application.service;

import lombok.RequiredArgsConstructor;
import org.example.bank_application.model.AccountUser;
import org.example.bank_application.model.BankAccount;
import org.example.bank_application.repository.BankAccountRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Random;

@RequiredArgsConstructor
@Service
public class BankAccountService {

    private final BankAccountRepository bankAccountRepository;

//    private final MessageService messageService;

//    public ResponseEntity<List<BankAccount>> getAllBankAccounts(){
//        return new ResponseEntity<>(bankAccountRepository.findAll(), HttpStatus.OK);
//    }
//
//    public ResponseEntity<BankAccount> getById(Long id){
//        return new ResponseEntity<>(bankAccountRepository.findById(id).get(),HttpStatus.OK);
//    }
//
//    public ResponseEntity<BankAccount> getByUser(String accountUser){
//        return new ResponseEntity<>(bankAccountRepository.findByUser(accountUser),HttpStatus.OK);
//    }

    public ResponseEntity<BankAccount> createBankAccount(AccountUser accountUser, double openingAmount ){
        StringBuilder accountNumber = new StringBuilder();
        int count = 0;
        while(count < 10){
            int randomInt = new Random().nextInt(10);
            accountNumber.append(randomInt);
            count++;
        }
        BankAccount account = new BankAccount((long) 1.0, accountNumber.toString(), openingAmount, accountUser);
        System.out.println(account);
        return new ResponseEntity<>(bankAccountRepository.save(account), HttpStatus.CREATED);
    }

    public ResponseEntity<BankAccount> createBankAccount(AccountUser accountUser ){
        StringBuilder accountNumber = new StringBuilder();
        int count = 0;
        while(count < 10){
            int randomInt = new Random().nextInt(10);
            accountNumber.append(randomInt);
            count++;
        }
        BankAccount account = new BankAccount((long) 1, accountNumber.toString(), 0.0, accountUser);
        System.out.println(account);
        return new ResponseEntity<>(bankAccountRepository.save(account), HttpStatus.CREATED);
    }

    public ResponseEntity<BankAccount> getByAccountNumber(String accountNumber){
        return new ResponseEntity<>(bankAccountRepository.findBankAccountByAccountNumber(accountNumber), HttpStatus.OK);
    }

    public ResponseEntity<BankAccount> updateAccount(BankAccount bankAccount){
        return new ResponseEntity<>(bankAccountRepository.save(bankAccount), HttpStatus.OK);
    }


}
