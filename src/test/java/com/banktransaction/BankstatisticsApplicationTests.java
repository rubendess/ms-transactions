package com.banktransaction;

import com.banktransaction.vo.StatisticsVO;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Date;

import static org.junit.Assert.assertTrue;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class BankstatisticsApplicationTests extends BanktransactionApplicationTests {

    public ResponseEntity<StatisticsVO> getStatisticsFromAPI() {
        return rest.getForEntity("/statistics", StatisticsVO.class);
    }

    @Test
    public void getStatisticsOKTest() {
        // Removing all transactions
        deleteAllTransactions();

        // Inserting 3 transactions
        long timestamp = new Date().getTime();
        insertTransaction(10.5, timestamp);
        insertTransaction(4.5, timestamp);
        insertTransaction(6, timestamp);

        ResponseEntity res = this.getStatisticsFromAPI();
        if(!res.getStatusCode().equals(HttpStatus.OK)) assertTrue(false);

        StatisticsVO statisticsVO = objectMapper.convertValue(res.getBody(), StatisticsVO.class);

        // Checking if the results are correct
        assertTrue(statisticsVO.getAvg() == 7
                    && statisticsVO.getCount() == 3
                    && statisticsVO.getMax() == 10.5
                    && statisticsVO.getMin() == 4.5
                    && statisticsVO.getSum() == 21);
    }
}
