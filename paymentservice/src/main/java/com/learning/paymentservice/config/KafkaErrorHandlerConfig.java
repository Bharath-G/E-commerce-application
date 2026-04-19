package com.learning.paymentservice.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.listener.DeadLetterPublishingRecoverer;
import org.springframework.kafka.listener.DefaultErrorHandler;
import org.springframework.util.backoff.ExponentialBackOff;

@Configuration
public class KafkaErrorHandlerConfig {
    @Bean
    public DefaultErrorHandler errorHandler(KafkaTemplate<String,Object> kafkaTemplate){
        var recoverer = new DeadLetterPublishingRecoverer(kafkaTemplate);
        //Retry 3 times: wait 1s, then 2s then 4s
        var backoff = new ExponentialBackOff(1000L,2.0); //1s, 2s, 4s
        backoff.setMaxAttempts(3);
        return new DefaultErrorHandler(recoverer,backoff);
    }
}
