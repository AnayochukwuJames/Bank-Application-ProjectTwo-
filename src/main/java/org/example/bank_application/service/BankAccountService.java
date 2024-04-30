package org.example.bank_application.service;

import lombok.RequiredArgsConstructor;
import org.example.bank_application.model.AccountUser;
import org.example.bank_application.model.BankAccount;
import org.example.bank_application.repository.BankAccountRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Random;

@RequiredArgsConstructor
@Service
public class BankAccountService {

    private final BankAccountRepository bankAccountRepository;

    public ResponseEntity<List<BankAccount>> getAllBankAccounts(){
        return new ResponseEntity<>(bankAccountRepository.findAll(), HttpStatus.OK);
    }

    public ResponseEntity<BankAccount> getById(Long id){
        return new ResponseEntity<>(bankAccountRepository.findById(id).get(),HttpStatus.OK);
    }

    public ResponseEntity<BankAccount> findByAccountUserUsername(String accountUser){
        return new ResponseEntity<>(bankAccountRepository.findByAccountUserUsername(accountUser),HttpStatus.OK);
    }

    public ResponseEntity<BankAccount> createBankAccountWithAmount(AccountUser accountUser, double openingAmount){
        StringBuilder accountNumber = new StringBuilder();
        int count = 0;
        while (count < accountNumber.length()){
            int randomInt = new Random().nextInt(10);
            accountNumber.append(randomInt);
        }
        BankAccount bankAccount = new BankAccount();
        return new ResponseEntity<>(bankAccountRepository.save(bankAccount),HttpStatus.CREATED);
    }

    public ResponseEntity<BankAccount> createBankAccount(AccountUser accountUser){
        StringBuilder accountNumber = new StringBuilder();
        int count = 0;
        while (count < accountNumber.length()){
            int randomInt = new Random().nextInt(10);
            accountNumber.append(randomInt);
        }
        BankAccount bankAccount = new BankAccount();
        return new ResponseEntity<>(bankAccountRepository.save(bankAccount),HttpStatus.CREATED);
    }
}
