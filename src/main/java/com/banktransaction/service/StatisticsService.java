package com.banktransaction.service;

import com.banktransaction.dao.TransactionStatisticsDAO;
import com.banktransaction.helper.TimestampHelper;
import com.banktransaction.vo.StatisticsVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class StatisticsService {

    @Autowired
    TransactionStatisticsDAO transactionStatisticsDAO;
    @Autowired
    TimestampHelper timestampHelper;

    public StatisticsVO getStatistics() {
        return transactionStatisticsDAO.getStatistics(timestampHelper.getLimitStartTimestamp());
    }
}
