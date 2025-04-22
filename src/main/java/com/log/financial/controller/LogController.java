package com.log.financial.controller;

import com.log.financial.model.entity.Log;
import com.log.financial.service.LogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/logs")
public class LogController {

    @Autowired
    private LogService logService;

    @GetMapping
    public List<Log> getAllLogs() {
        return logService.getAllLogs();
    }

    @GetMapping("/level")
    public List<Log> getLogsByLevel(@RequestParam String level) {
        return logService.getLogsByLevel(level);
    }

    @GetMapping("/search")
    public List<Log> searchLogs(@RequestParam String message) {
        return logService.getLogsByMessage(message);
    }

    @GetMapping("/date-range")
    public List<Log> getLogsByDateRange(@RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime start,
                                        @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime end) {
        return logService.getLogsByDateRange(start, end);
    }
}