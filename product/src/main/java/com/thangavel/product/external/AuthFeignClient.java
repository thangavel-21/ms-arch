package com.thangavel.product.external;

import com.thangavel.product.utils.ProductUtils;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;

import java.util.Map;

@FeignClient(name = "authentication", url = ProductUtils.AUTH_SERVICE_URL)
public interface AuthFeignClient {

    @GetMapping("/validate")
    Map<String, Object> validateToken(@RequestHeader("Authorization") String token);

}