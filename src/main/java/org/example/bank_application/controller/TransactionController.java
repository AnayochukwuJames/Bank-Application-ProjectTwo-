package org.example.bank_application.controller;

import lombok.*;
import org.example.bank_application.model.Transactions;
import org.example.bank_application.service.TransactionsService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/transaction")
@RequiredArgsConstructor
public class TransactionController {

    private final TransactionsService transactionsService;

    @GetMapping("getALl")
   public ResponseEntity<List<Transactions>> getAllTransactions(){
        return transactionsService.getAllTransactions();
    }
    @GetMapping("transactions/{id}")
    public ResponseEntity<Transactions> getById(@PathVariable Long id){
        return transactionsService.getById(id);
    }

    @GetMapping("transactionId")
    public ResponseEntity<Transactions>getTransactionsById(@RequestParam String transactionId){
        return transactionsService.getTransactionsById(transactionId);
    }

    @PostMapping("create")
    public ResponseEntity<Transactions> createTransaction(@RequestBody Transactions transactions){
        return transactionsService.createTransaction(transactions);
    }

}
