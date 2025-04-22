package com.log.financial.service.generator;

import com.log.financial.kafka.producer.LogKafkaProducer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Random;

@Component
public class LogGenerator {
    private static final String[] levels = { "INFO", "ERROR", "WARN" };
    private static final Random random = new Random();

    @Autowired
    private LogKafkaProducer kafkaProducer;

    @Scheduled(fixedDelay = 20000) // a cada 2 min
    public void generateLog() {
        String level = levels[random.nextInt(levels.length)];
        String message = generateMessage(level);
        String logEntry = String.format("[%s] [%s] %s", LocalDateTime.now(), level, message);
        kafkaProducer.sendLog(logEntry);
    }

    private String generateMessage(String level) {
        int clientId = 1000 + random.nextInt(9000);
        double value = Math.round((50 + random.nextDouble() * 10000) * 100.0) / 100.0;

        return switch (level) {
            case "INFO" -> String.format("Transação R$%.2f realizada com sucesso (ClienteID: %d)", value, clientId);
            case "ERROR" -> String.format("Falha na transação: Saldo insuficiente (ClienteID: %d)", clientId);
            case "WARN" -> String.format("Transação suspeita detectada (ClienteID: %d, valor: R$%.2f)", clientId, value);
            default -> "Evento desconhecido";
        };
    }
}