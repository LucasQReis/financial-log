package com.log.financial.service.websocket;

import com.log.financial.model.entity.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

@Service
public class LogWebSocketPublisher {

    @Autowired
    private SimpMessagingTemplate messagingTemplate;

    public void publishLog(Log log) {
        System.out.println("Mensagem: " + log); // Debug
        messagingTemplate.convertAndSend("/topic/logs", log);
    }
}
