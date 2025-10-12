package com.example.orders.api;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

record Order(String id, String sku, int qty, String status) {}

@RestController
@RequestMapping("/api/orders")
public class OrderController {
  private final Map<String, Order> store = new HashMap<>();
  private final RestTemplate rest = new RestTemplate();

  @Value("${inventory.url}")
  private String inventoryUrl;

  @Value("${payment.url}")
  private String paymentUrl;

  @PostMapping
  public Order place(@RequestBody Map<String, Object> req) {
    String sku = (String) req.get("sku");
    int qty = ((Number) req.get("qty")).intValue();
    String id = UUID.randomUUID().toString();

    // Reserve inventory
    Map<String, Object> reserveReq = Map.of("sku", sku, "qty", qty);
    ResponseEntity<Map> invRes =
        rest.postForEntity(inventoryUrl + "/api/inventory/reserve", reserveReq, Map.class);
    if (!"OK".equals(invRes.getBody().get("status"))) {
      return new Order(id, sku, qty, "INSUFFICIENT_STOCK");
    }

    // Charge payment
    Map<String, Object> payReq = Map.of("orderId", id, "amount", qty * 100);
    ResponseEntity<Map> payRes =
        rest.postForEntity(paymentUrl + "/api/payment/charge", payReq, Map.class);
    String status =
        "DECLINED".equals(payRes.getBody().get("status")) ? "PAYMENT_FAILED" : "CONFIRMED";

    Order o = new Order(id, sku, qty, status);
    store.put(id, o);
    return o;
  }

  @GetMapping("/{id}")
  public Order get(@PathVariable String id) {
    return store.get(id);
  }
}
