package com.log.financial.repository;

import com.log.financial.model.entity.Log;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface LogRepository extends JpaRepository<Log, Long> {
    List<Log> findByLevel(String level);
    List<Log> findByMessageContaining(String message);
    List<Log> findByTimestampBetween(LocalDateTime start, LocalDateTime end);
}
