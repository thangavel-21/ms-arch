package com.thangavel.product.external;

import io.jsonwebtoken.Claims;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;

import java.util.Map;

@FeignClient(name = "authentication", url = "http://localhost:8005/api/v1/auth")
public interface AuthFeignClient {

    @GetMapping("/validate")
    Map<String, Object> validateToken(@RequestHeader("Authorization") String token);

}