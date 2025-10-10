package com.example.orders.api;
import org.springframework.web.bind.annotation.*; import java.util.*; record Order(String id,String sku,int qty,String status){}
@RestController @RequestMapping("/api/orders")
public class OrderController {
  private final Map<String,Order> store=new HashMap<>();
  @PostMapping public Order place(@RequestBody Map<String,Object> req){
    String id=UUID.randomUUID().toString();
    Order o=new Order(id,(String)req.get("sku"),((Number)req.get("qty")).intValue(),"CONFIRMED");
    store.put(id,o); return o;
  }
  @GetMapping("/{id}") public Order get(@PathVariable String id){ return store.get(id); }
}
