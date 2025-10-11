rootProject.name = "microservices-app"

// Each microservice module
include(":services:orders-service")
include(":services:inventory-service")
include(":services:payment-service")
