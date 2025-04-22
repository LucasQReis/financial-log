package com.log.financial.kafka.producer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class LogKafkaProducer {

    private static final String TOPIC = "financial-logs";

    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    public void sendLog(String message) {
        kafkaTemplate.send(TOPIC, message);
    }
}
