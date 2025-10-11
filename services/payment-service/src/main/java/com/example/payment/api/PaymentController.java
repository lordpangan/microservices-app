package com.example.payment.api;

import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/payment")
public class PaymentController {

  @Value("${toggle.fail:false}")
  private boolean fail;

  @Value("${toggle.latencyMs:0}")
  private long latency;

  @PostMapping("/charge")
  public Map<String, Object> charge(@RequestBody Map<String, Object> req) throws Exception {
    if (latency > 0) {
      Thread.sleep(latency);
    }

    if (fail) {
      return Map.of("status", "DECLINED");
    }

    return Map.of("status", "CAPTURED", "authId", UUID.randomUUID().toString());
  }
}
