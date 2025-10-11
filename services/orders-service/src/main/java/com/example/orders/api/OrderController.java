package com.example.orders.api;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

record Order(String id, String sku, int qty, String status) { }

@RestController
@RequestMapping("/api/orders")
public class OrderController {

  private final Map<String, Order> store = new HashMap<>();

  @PostMapping
  public Order place(@RequestBody Map<String, Object> req) {
    String id = UUID.randomUUID().toString();
    Order order = new Order(
        id,
        (String) req.get("sku"),
        ((Number) req.get("qty")).intValue(),
        "CONFIRMED"
    );

    store.put(id, order);
    return order;
  }

  @GetMapping("/{id}")
  public Order get(@PathVariable String id) {
    return store.get(id);
  }
}
