package com.log.financial.service;

import com.log.financial.model.entity.Log;
import com.log.financial.repository.LogRepository;
import com.log.financial.service.impl.LogServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class LogService implements LogServiceImpl {

    @Autowired
    private LogRepository logRepository;

    public List<Log> getAllLogs() {
        return logRepository.findAll();
    }

    public List<Log> getLogsByLevel(String level) {
        return logRepository.findByLevel(level);
    }

    public List<Log> getLogsByMessage(String message) {
        return logRepository.findByMessageContaining(message);
    }

    public List<Log> getLogsByDateRange(LocalDateTime start, LocalDateTime end) {
        return logRepository.findByTimestampBetween(start, end);
    }
}
