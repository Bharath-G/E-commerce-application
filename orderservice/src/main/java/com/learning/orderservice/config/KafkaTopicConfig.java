package com.learning.orderservice.config;

import org.apache.kafka.clients.admin.NewTopic;
import org.apache.kafka.common.config.TopicConfig;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

@Configuration
public class KafkaTopicConfig {

    @Bean
    public NewTopic orderEventsTopic(){
        return TopicBuilder.name("order-events")
                .partitions(6) // rule of thumb: 2x your expected consumers
                .replicas(1)    // 1 for local/dev, 3 for production
                .config(TopicConfig.RETENTION_MS_CONFIG, "604800000")  // 7 days
                .build();
    }

    @Bean
    public NewTopic orderEventsDlt(){
        return TopicBuilder.name("order-events.DLT")
                .partitions(1)
                .replicas(1)
                .config(TopicConfig.RETENTION_MS_CONFIG, "2592000000")
                .build();
    }
}
