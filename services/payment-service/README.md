# Payment Service
Spring Boot 3.3+ service that simulates payment authorization and capture.

## Endpoints
- `POST /api/payment/charge` – processes a mock payment request
- `GET /actuator/health` – readiness probe
- Swagger UI: `/swagger`

## Environment Variables
| Variable | Description | Default |
|-----------|-------------|----------|
| `TOGGLE_FAIL` | Simulate payment failure when `true` | `false` |
| `TOGGLE_LATENCYMS` | Add artificial latency in milliseconds | `0` |

## Dependencies
- None (runs standalone)
- Called by **orders-service** to simulate payments
- No external payment provider; purely local simulation for testing

## Local Run
```bash
./gradlew :services:payment-service:bootRun
```
