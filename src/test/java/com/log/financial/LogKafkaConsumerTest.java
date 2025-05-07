package com.log.financial;

import com.log.financial.kafka.consumer.LogKafkaConsumer;
import com.log.financial.model.entity.Log;
import com.log.financial.repository.LogRepository;
import com.log.financial.service.websocket.LogWebSocketPublisher;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Captor;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;

import java.time.LocalDateTime;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.any;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@RunWith(MockitoJUnitRunner.class)
class LogKafkaConsumerTest {

    @InjectMocks
    private LogKafkaConsumer consumer;

    @Mock
    private LogRepository mockRepository;

    @Mock
    private LogWebSocketPublisher mockPublisher;

    @Captor
    private ArgumentCaptor<Log> captor;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testConsume_ValidInfoLogMessage() {
        String logMessage = String.format("[%s] [INFO] Transação R$99.99 realizada com sucesso (ClienteID: 1234)", LocalDateTime.now());
        consumer.consume(logMessage);
        assertLogPersistedCorrectly("INFO", "Transação R$99.99 realizada com sucesso");
    }

    @Test
    void testConsume_ValidWarnLogMessage() {
        String logMessage = String.format("[%s] [WARN] Transação suspeita detectada (ClienteID: 1234, valor: R$99.99)", LocalDateTime.now());
        consumer.consume(logMessage);
        assertLogPersistedCorrectly("WARN", "Transação suspeita detectada");
    }

    @Test
    void testConsume_ValidErrorLogMessage() {
        String logMessage = String.format("[%s] [ERROR] Falha na transação: Saldo insuficiente (ClienteID: 1234)", LocalDateTime.now());
        consumer.consume(logMessage);
        assertLogPersistedCorrectly("ERROR", "Falha na transação");
    }

    @Test
    void testConsume_InvalidLogMessage() {
        consumer.consume("mensagem");
        verify(mockRepository, never()).save(any());
        verify(mockPublisher, never()).publishLog(any());
    }

    @Test
    void testConsume_InvalidTimestamp_ThrowsException() {
        String invalidTimestampLog = "[data invalida] [INFO] Mensagem qualquer";

        consumer.consume(invalidTimestampLog);

        verify(mockRepository, never()).save(any());
        verify(mockPublisher, never()).publishLog(any());
    }

    @Test
    void testConsume_WebSocketPublisherThrowsException() {
        String validLog = String.format("[%s] [INFO] Mensagem qualquer", LocalDateTime.now());

        doThrow(new RuntimeException("Erro no WebSocket")).when(mockPublisher).publishLog(any(Log.class));

        consumer.consume(validLog);

        verify(mockPublisher, times(1)).publishLog(any(Log.class));
    }

    @Test
    void testConsume_LogRepositoryThrowsException() {
        String validLog = String.format("[%s] [INFO] Mensagem qualquer", LocalDateTime.now());

        doThrow(new RuntimeException("Erro no banco")).when(mockRepository).save(any(Log.class));

        consumer.consume(validLog);

        verify(mockPublisher, times(1)).publishLog(any(Log.class));
        verify(mockRepository, times(1)).save(any(Log.class));
    }

    private void assertLogPersistedCorrectly(String expectedLevel, String expectedMessageSnippet) {
        verify(mockRepository).save(captor.capture());
        verify(mockPublisher).publishLog(captor.getValue());

        Log savedLog = captor.getValue();
        assertEquals(expectedLevel, savedLog.getLevel());
        assertTrue(savedLog.getMessage().contains(expectedMessageSnippet));
    }
}
