package org.example.bank_application.service;

import lombok.*;
import org.example.bank_application.model.Transactions;
import org.example.bank_application.repository.TransactionRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Random;

@Service
@RequiredArgsConstructor
public class TransactionsService {

    private final TransactionRepository transactionRepository;

    public ResponseEntity<List<Transactions>> getAllTransactions(){
        return new ResponseEntity<>(transactionRepository.findAll(), HttpStatus.OK);
    }

    public ResponseEntity<Transactions> getById(Long id){
        return new ResponseEntity<>(transactionRepository.findById(id).get(), HttpStatus.OK);
    }

    public ResponseEntity<Transactions> getTransactionsById(String transactionId){
        return new ResponseEntity<>(transactionRepository.findByTransactionId(transactionId),HttpStatus.OK);
    }

    public ResponseEntity<Transactions> createTransaction(Transactions transactions){
        return new ResponseEntity<>(transactionRepository.save(transactions), HttpStatus.CREATED);
    }

    public ResponseEntity<Transactions> postNewTransaction(Transactions transactions){
        StringBuilder transId = new StringBuilder();
        transId.append("TXN");
        for( int i = 0; i < 10; i++ ){
            int random = new Random().nextInt(10);
            transId.append(random);
        }
        transactions.setTransactionId(transId.toString());
        transactions.setTransactionDate(new Date(System.currentTimeMillis()));
        return new ResponseEntity<>(transactionRepository.save(transactions), HttpStatus.CREATED);
    }
}
