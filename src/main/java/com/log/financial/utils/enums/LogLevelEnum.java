package com.log.financial.utils.enums;

public enum LogLevelEnum {
    INFO {
        @Override
        public String generateMessage(int clientId, double value) {
            return String.format("Transação R$%.2f realizada com sucesso (ClienteID: %d)", value, clientId);
        }
    },
    ERROR {
        @Override
        public String generateMessage(int clientId, double value) {
            return String.format("Falha na transação: Saldo insuficiente (ClienteID: %d)", clientId);
        }
    },
    WARN {
        @Override
        public String generateMessage(int clientId, double value) {
            return String.format("Transação suspeita detectada (ClienteID: %d, valor: R$%.2f)", clientId, value);
        }
    };

    public abstract String generateMessage(int clientId, double value);
}

