package com.banktransaction.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.elasticsearch.client.NodeClientFactoryBean;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.repository.config.EnableElasticsearchRepositories;


@Configuration
@EnableElasticsearchRepositories(basePackages = "com.banktransaction.repository")
public class ElasticsearchConfiguration {

    @Value("${elasticsearch.islocal}")
    private boolean esIsLocal;

    @Bean
    public ElasticsearchOperations elasticsearchTemplate() throws Exception {
        return new ElasticsearchTemplate(new NodeClientFactoryBean(esIsLocal).getObject());
    }
}
