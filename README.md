# BTG Pactual - Desafio Backend

Aplicação Spring Boot que consome mensagens de pedidos do RabbitMQ e persiste em MongoDB. Inclui Docker Compose para provisionar MongoDB e RabbitMQ (com painel de gestão), conversão JSON com Jackson e uso de Lombok.

## Visão Geral

- Consome mensagens da fila `order-created-queue`
- Converte o payload JSON para `OrderCreatedEvent` e salva em `orders` (MongoDB)
- Cálculo automático do `total` do pedido baseado em itens e preços
- Provisionamento local de MongoDB e RabbitMQ via Docker Compose

## Tecnologias

- Java `21`
- Spring Boot `4.0.0`
- Spring AMQP (RabbitMQ)
- Spring Data MongoDB
- Docker e Docker Compose
- Lombok

## Serviços Utilizados

- Docker
- RabbitMQ Management (UI)

## Pré-requisitos

- `Java 21` instalado
- `Docker` e `Docker Compose` instalados
- Portas `27017` (MongoDB), `5672` e `15672` (RabbitMQ) disponíveis

## Configuração de Ambiente

- Configurações em `src/main/resources/application.properties`:

```
spring.data.mongodb.uri=mongodb://admin:123456@localhost:27017/btgpactualdb?authSource=admin

spring.rabbitmq.host=localhost
spring.rabbitmq.port=5672
spring.rabbitmq.username=appuser
spring.rabbitmq.password=apppass
spring.rabbitmq.virtual-host=/
```

> Ajuste as credenciais conforme seu ambiente.

## Subir MongoDB e RabbitMQ (Docker)

Inicie os serviços definidos em `docker-compose.yml`:

```bash
docker compose up -d
```

- MongoDB: `localhost:27017`
- RabbitMQ UI: `http://localhost:15672` (login: `appuser/apppass`)

## Executar a Aplicação

- Via Maven Wrapper (Windows):

```bash
./mvnw.cmd spring-boot:run
```

- Via Maven Wrapper (Linux/Mac):

```bash
./mvnw spring-boot:run
```

## Mensageria

- Fila principal: `order-created-queue`
- Formato do evento (`OrderCreatedEvent`):

```json
{
  "orderId": 1001,
  "customerId": 2002,
  "items": [
    { "product": "p1", "quantity": 2, "price": 10.5 },
    { "product": "p2", "quantity": 1, "price": 5 }
  ]
}
```

### Publicar pelo RabbitMQ Management

1. Acesse `Queues` → `order-created-queue` → `Publish message`
2. Preencha o `Payload` com o JSON acima
3. Em `properties`, configure:
   - `content_type = application/json`
   - `priority = 0`
4. Clique em `Publish message`

> Observação: As propriedades `content_type` e `priority` são necessárias para a mensagem ser enviada e processada corretamente.

## Persistência

- As mensagens consumidas são persistidas na collection `orders` do banco `btgpactualdb`.
- O campo `total` é calculado somando `price * quantity` de cada item.

## Versionamento

- `1.0.0`

## Autor

- `https://www.linkedin.com/in/luizfernando-react-java-fullstack/`

Obrigado por visitar e bons códigos!
