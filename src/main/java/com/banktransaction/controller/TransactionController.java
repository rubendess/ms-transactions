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

        return ResponseEntity.status(result != null ? HttpStatus.CREATED : HttpStatus.NO_CONTENT).build();
    }

    @DeleteMapping
    public void deleteAll() {
        transactionService.deleteAll();
    }
}
