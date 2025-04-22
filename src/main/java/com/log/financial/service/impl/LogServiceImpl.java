package com.log.financial.service.impl;

import com.log.financial.model.entity.Log;

import java.time.LocalDateTime;
import java.util.List;

public interface LogServiceImpl {
    List<Log> getAllLogs();
    List<Log> getLogsByLevel(String level);
    List<Log> getLogsByMessage(String message);
    List<Log> getLogsByDateRange(LocalDateTime start, LocalDateTime end);
}
