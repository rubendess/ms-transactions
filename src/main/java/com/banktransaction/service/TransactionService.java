package com.banktransaction.service;

import com.banktransaction.domain.Transaction;
import com.banktransaction.repository.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class TransactionService {

    @Autowired
    TransactionRepository transactionRepository;

    @Value("${bancktransaction.time-limit-seconds}")
    long timeLimitInSeconds;

    public Transaction save(Transaction transaction) {
        long currentTime = new Date().getTime();
        long timestampLimit = currentTime-(1000*this.timeLimitInSeconds);

        if(transaction.getTimestamp() >= timestampLimit)
            return transactionRepository.save(transaction);

        return null;
    }

    public List<Transaction> findByTimestampBeforeTime(long currentTime, long secondsBefore) {
        return transactionRepository.findByTimestampBetween(currentTime - (secondsBefore*1000), currentTime);
    }

    public List<Transaction> findAll() {
        return transactionRepository.findAllByAmountGreaterThan(0);
    }

    public void deleteAll() {
        transactionRepository.deleteAll();
    }
}
