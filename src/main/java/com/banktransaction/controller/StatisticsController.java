package com.banktransaction.controller;

import com.banktransaction.service.StatisticsService;
import com.banktransaction.vo.StatisticsVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin("*")
@RequestMapping("/statistics")
public class StatisticsController {

    @Autowired
    StatisticsService statisticsService;

    @GetMapping
    public ResponseEntity<?> getStatistics() {
        StatisticsVO statisticsVO = statisticsService.getStatistics();
        return ResponseEntity.status(HttpStatus.OK).body(statisticsVO);
    }

}
