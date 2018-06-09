package com.banktransaction.repository;

import com.banktransaction.domain.Transaction;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import java.util.List;

public interface TransactionRepository extends ElasticsearchRepository<Transaction, String> {

    List findAllByAmountGreaterThan(double amount);
    List<Transaction> findByTimestampBetween(long startTimestamp, long endTimestamp);
}