package com.log.financial.service.generator;

import com.log.financial.kafka.producer.LogKafkaProducer;
import com.log.financial.utils.enums.LogLevelEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Random;

@Component
public class LogGenerator {
    private static final LogLevelEnum[] LEVELS = LogLevelEnum.values();
    private final Random random = new Random();
    private final LogKafkaProducer kafkaProducer;

    @Autowired
    public LogGenerator(LogKafkaProducer kafkaProducer) {
        this.kafkaProducer = kafkaProducer;
    }

    @Scheduled(fixedDelay = 20000) // a cada 20 seg
    public void generateLog() {
        LogLevelEnum level = getRandomLevel();
        String message = createLogMessage(level);
        String logEntry = formatLogEntry(level, message);
        kafkaProducer.sendLog(logEntry);
    }

    private LogLevelEnum getRandomLevel() {
        return LEVELS[random.nextInt(LEVELS.length)];
    }

    private String createLogMessage(LogLevelEnum level) {
        int clientId = 1000 + random.nextInt(9000);
        double value = Math.round((50 + random.nextDouble() * 10000) * 100.0) / 100.0;
        return level.generateMessage(clientId, value);
    }

    private String formatLogEntry(LogLevelEnum level, String message) {
        return String.format("[%s] [%s] %s", LocalDateTime.now(), level.name(), message);
    }
}