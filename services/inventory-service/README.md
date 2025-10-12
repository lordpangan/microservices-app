# Inventory Service
Spring Boot 3.3+ service that manages product stock levels and reservations for orders.

## Endpoints
- `POST /api/inventory/reserve` – reserves items for an order (decrements stock)
- `GET /api/inventory/stock/{sku}` – returns available stock quantity
- `GET /actuator/health/readiness` – readiness probe
- Swagger UI: `/swagger`

## Dependencies
- None (runs standalone)
- Used by **orders-service** to check and reserve inventory
- Component tests use **Postgres** via **Testcontainers 1.19+**

## Local Run
```bash
./gradlew :services:inventory-service:bootRun
```