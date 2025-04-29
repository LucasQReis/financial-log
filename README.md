# Sistema de Notificações de Logs Financeiros

Este é um projeto Java utilizando Spring Boot para simular um sistema de envio de notificações de logs financeiros (como em um dashboard financeiro). O sistema usa Apache Kafka como intermediario de processamento de streaming de dados para envio das mensagens, e WebSockets para transmitir essas notificações em tempo real para um dashboard desenvolvido em Angular.

Principais funcionalidades:
- Produção de mensagens financeiras simuladas (logs de transações, alertas, etc.)
- Enfileiramento e persistência dessas mensagens usando Kafka e banco de dados MySql
- Consumo e distribuição em tempo real para o frontend via WebSockets
- Dashboard Angular para visualização dos logs em tempo real

---

### Backend (Em desenvolvimento)
- **Spring Boot**
- **Apache Kafka**
- **Spring WebSocket**
- **Spring Data / JPA**
- **Banco MySQL**

### Frontend (Em desenvolvimento)
- **Angular**
- **RxJS / WebSocket API**

---

### Pré-requisitos

- Java 17+
- Docker e docker-compose (para executar os containers necessários)

Para executar a aplicação com Docker:

Inicie o container do banco de dados executando a imagem db para subir o banco MySQL necessário para a aplicação

- docker-compose up -d db

Após o banco estar em execução, compile o projeto e gere o arquivo .jar com o Maven:

- mvn clean package

Isso criará o arquivo financial-0.0.1-SNAPSHOT.jar dentro da pasta target. Com o JAR gerado, inicie a aplicação e os demais serviços definidos no docker-compose.yml:

- docker-compose up -d

### Desenvolvimento e contribuições
Este projeto ainda está em fase de desenvolvimento.
Funcionalidades, estrutura de código e integração com serviços podem sofrer alterações frequentes.
No momento, o foco principal das melhorias são:
- Performance, escalabilidade, e confiabilidade do envio e consumo de mensagens financeiras.
- Evolução do frontend em Angular, construção de um dashboard mais interativo, com visualização em tempo real e uma melhor experiência de usuário.
  
Contribuições são bem-vindas! Sinta-se livre para abrir issues ou enviar pull requests.
