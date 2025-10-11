package com.example.inventory.api;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/inventory")
public class InventoryController {

  private final Map<String, Integer> stock =
      new ConcurrentHashMap<>(Map.of("SKU-123", 10, "SKU-456", 5));

  @PostMapping("/reserve")
  public Map<String, Object> reserve(@RequestBody Map<String, Object> req) {
    String sku = (String) req.get("sku");
    int qty = ((Number) req.get("qty")).intValue();

    stock.putIfAbsent(sku, 0);

    if (stock.get(sku) < qty) {
      return Map.of("status", "INSUFFICIENT");
    }

    stock.computeIfPresent(sku, (k, v) -> v - qty);
    return Map.of("status", "OK", "remaining", stock.get(sku));
  }

  @GetMapping("/stock/{sku}")
  public Map<String, Object> stock(@PathVariable String sku) {
    return Map.of("sku", sku, "qty", stock.getOrDefault(sku, 0));
  }
}
