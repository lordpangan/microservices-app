# Orders Service
Spring Boot 3.3+ service that manages orders, coordinates with inventory and payment.

## Endpoints
- `POST /api/orders` – place an order (validates inventory + payment)
- `GET /api/orders/{id}` – fetch order status
- `GET /actuator/health` – readiness probe
- Swagger UI: `/swagger`

## Dependencies
- **inventory-service**
- **payment-service**
- Resilience4j (circuit breaker + timeout around payment)

## Local Run
```bash
./gradlew :services:orders-service:bootRun
```