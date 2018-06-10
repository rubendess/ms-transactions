package com.banktransaction.dao;

import com.banktransaction.vo.StatisticsVO;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.aggregations.Aggregations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.core.ResultsExtractor;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.data.elasticsearch.core.query.SearchQuery;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.elasticsearch.index.query.QueryBuilders.matchQuery;
import static org.elasticsearch.index.query.QueryBuilders.rangeQuery;

@Repository
public class TransactionStatisticsDAO {

    @Autowired
    ElasticsearchTemplate elasticsearchTemplate;
    @Autowired
    ObjectMapper objectMapper;

    private Object getValueFromMap(Object value) {
        if(value == null || value.equals(Double.NaN)
                || value.equals(Double.NEGATIVE_INFINITY) || value.equals(Double.POSITIVE_INFINITY))
            return 0;

        return value;
    }

    private StatisticsVO parseAggregationResultsToStatisticsVO(List statisticsAggregation) {
        Map statisticsMap = new HashMap();

        statisticsAggregation.stream().forEach(item -> {
            Map itemMap = objectMapper.convertValue(item, Map.class);
            Object key = itemMap.get("name");
            Object value = this.getValueFromMap(itemMap.get("value"));
            statisticsMap.put(key, value);
        });

        return objectMapper.convertValue(statisticsMap, StatisticsVO.class);
    }

    public StatisticsVO getStatisticsStartingByTimeLimit(long inititalTimestamp) {
        SearchQuery searchQuery = new NativeSearchQueryBuilder()
                .withQuery(rangeQuery("timestamp").gte(inititalTimestamp))
                .addAggregation(
                        AggregationBuilders.sum("sum")
                                .field("amount")
                )
                .addAggregation(
                        AggregationBuilders.avg("avg")
                                .field("amount")
                )
                .addAggregation(
                        AggregationBuilders.max("max")
                                .field("amount")
                )
                .addAggregation(
                        AggregationBuilders.min("min")
                                .field("amount")
                )
                .addAggregation(
                        AggregationBuilders.count("count")
                                .field("amount")
                )
                .build();

        Aggregations aggregations = elasticsearchTemplate.query(searchQuery, new ResultsExtractor<Aggregations>() {
            @Override
            public Aggregations extract(SearchResponse response) {
                return response.getAggregations();
            }
        });

        List aggregationResults = aggregations.asList();
        return this.parseAggregationResultsToStatisticsVO(aggregationResults);
    }
}
