package com.log.financial.service.generator;

import com.log.financial.model.entity.Log;
import com.log.financial.repository.LogRepository;
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
    private LogRepository logRepository;

    @Scheduled(fixedDelay = 10000)
    public void gerarLogFinanceiro() {
        String level = levels[random.nextInt(levels.length)];
        String mensagem = gerarMensagem(level);
        LocalDateTime timestamp = LocalDateTime.now();
        Log log = new Log(timestamp, level, mensagem);
        logRepository.save(log);
    }

    private String gerarMensagem(String level) {
        int clienteId = 1000 + random.nextInt(9000);
        double valor = Math.round((50 + random.nextDouble() * 10000) * 100.0) / 100.0;

        return switch (level) {
            case "INFO" -> String.format("Transação R$%.2f realizada com sucesso (ClienteID: %d)", valor, clienteId);
            case "ERROR" -> String.format("Falha na transação: Saldo insuficiente (ClienteID: %d)", clienteId);
            case "WARN" -> String.format("Transação suspeita detectada (ClienteID: %d, valor: R$%.2f)", clienteId, valor);
            default -> "Evento desconhecido";
        };
    }
}