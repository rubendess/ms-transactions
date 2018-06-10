package com.banktransaction;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.*;

import static org.junit.Assert.assertTrue;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class BanktransactionApplicationTests {

    @Autowired
    TestRestTemplate rest;
    @Autowired
    ObjectMapper objectMapper;

    private synchronized HttpEntity getEntityForSend(Object body) {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Content-Type", "application/json");
        return new HttpEntity(body, headers);
    }

	public void deleteAllTransactions() {
        // Removing all transactions
        rest.delete("/transactions");
    }

    public ResponseEntity insertTransaction(double amount, long timestamp) {
        Map body = new HashMap();
        body.put("amount", amount);
        body.put("timestamp", timestamp);
        return rest.postForEntity("/transactions", this.getEntityForSend(body), String.class);
    }

    @After
    public void deleteAllTest() {
        this.deleteAllTransactions();
    }

    @Test
    public void insertTransactionInAcceptableTimeTest() {
        ResponseEntity res = this.insertTransaction(50, new Date().getTime());
        assertTrue(res.getStatusCode().equals(HttpStatus.CREATED));
    }

    @Test
    public void insertTransactionInNotAcceptableTimeTest() {
        // Try insert transaction older than 60 seconds
        ResponseEntity res = this.insertTransaction(50, new Date().getTime()-(61000));
        assertTrue(res.getStatusCode().equals(HttpStatus.NO_CONTENT));
    }

	@Test
    public void saveManyTransactionsTest() {
        int totalToInsert = 60;
        int totalInserted = 0;
	    for (int i = 0; i < totalToInsert; i++) {
            long timestamp = new Date().getTime();
            ResponseEntity res = this.insertTransaction(i*15.9, timestamp);
            if(res.getStatusCode().equals(HttpStatus.CREATED)) {
                totalInserted++;
            }
        }
        assertTrue(totalToInsert == totalInserted);
    }

}
