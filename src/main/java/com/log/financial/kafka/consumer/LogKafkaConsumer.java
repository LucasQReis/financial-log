package com.log.financial.kafka.consumer;

import com.log.financial.model.entity.Log;
import com.log.financial.repository.LogRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class LogKafkaConsumer {

    @Autowired
    private LogRepository logRepository;

    @KafkaListener(topics = "financial-logs", groupId = "financial-log-group")
    public void consume(String linha) {
        Log log = parseLogLine(linha);
        if (log != null) {
            logRepository.save(log);
        }
    }

    private Log parseLogLine(String line) {
        Pattern pattern = Pattern.compile("\\[(.*?)\\] \\[(.*?)\\] (.*)");
        Matcher matcher = pattern.matcher(line);
        if (matcher.matches()) {
            try {
                LocalDateTime timestamp = LocalDateTime.parse(matcher.group(1));
                String level = matcher.group(2);
                String message = matcher.group(3);
                return new Log(timestamp, level, message);
            } catch (Exception e) {
                System.err.println("Erro ao converter log: " + line);
            }
        }
        return null;
    }
}
