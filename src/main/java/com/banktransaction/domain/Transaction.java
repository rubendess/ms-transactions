package com.banktransaction.domain;

import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;

import java.io.Serializable;

@Document(indexName = "banktransaction", type = "transactions")
public class Transaction implements Serializable {
    @Id
    private String id;
    private double amount;
    private long timestamp;

    public Transaction() {
    }

    public Transaction(double amount, long timestamp) {
        this.amount = amount;
        this.timestamp = timestamp;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    @Override
    public String toString() {
        return String.format("Transaction[id=%s, amount='%s', timestamp='%s']",
                this.id, this.amount, this.timestamp);
    }
}
