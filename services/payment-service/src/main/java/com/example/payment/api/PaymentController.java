package com.example.payment.api;
import org.springframework.beans.factory.annotation.Value; import org.springframework.web.bind.annotation.*; import java.util.*;
@RestController @RequestMapping("/api/payment")
public class PaymentController {
  @Value("${toggle.fail:false}") boolean fail; @Value("${toggle.latencyMs:0}") long latency;
  @PostMapping("/charge") public Map<String,Object> charge(@RequestBody Map<String,Object> req) throws Exception{
    if(latency>0) Thread.sleep(latency); if(fail) return Map.of("status","DECLINED");
    return Map.of("status","CAPTURED","authId",UUID.randomUUID().toString());
  }
}
