package com.banktransaction.helper;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class TimestampHelper {

    @Value("${bancktransaction.time-limit-seconds}")
    long timeLimitInSeconds;

    public long getLimitStartTimestamp() {
        long currentTime = new Date().getTime();
        return currentTime - (1000 * timeLimitInSeconds);
    }
}
