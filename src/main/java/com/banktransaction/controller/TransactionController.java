package com.banktransaction.controller;

import com.banktransaction.domain.Transaction;
import com.banktransaction.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@RestController
@CrossOrigin("*")
@RequestMapping("/transactions")
public class TransactionController {

    @Autowired
    TransactionService transactionService;

    @PostMapping
    public ResponseEntity<?> insertTransaction(@RequestBody Transaction transaction) {
        Transaction result = transactionService.save(transaction);

        if(result != null)
            return ResponseEntity.status(HttpStatus.CREATED).body(transaction);

        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @GetMapping
    public List getAll() {
        return transactionService.findAll();
    }

    @GetMapping("/time")
    public List getTransactionById() {
        return transactionService.findByTimestampBeforeTime(new Date().getTime(), 60);
    }

    @DeleteMapping
    public void deleteAll() {
        transactionService.deleteAll();
    }
}
