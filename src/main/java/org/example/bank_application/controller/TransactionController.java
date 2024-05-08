package org.example.bank_application.controller;

import jakarta.validation.Valid;
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

    @GetMapping("")
    public ResponseEntity<List<Transactions>> getAllTransactions(){
        return transactionsService.getAllTransactions();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Transactions> getById(@PathVariable long id){
        return transactionsService.getById(id);
    }

    @GetMapping("/transId")
    public ResponseEntity<List<Transactions>> getByTransactionId(@RequestParam String transactionId){
        return transactionsService.getByTransactionId(transactionId);
    }

    @PostMapping("")
    public ResponseEntity<Transactions> postNewTransaction(@RequestBody @Valid Transactions transactions){
        return transactionsService.postNewTransaction(transactions);
    }

}
