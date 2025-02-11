package com.thangavel.product.controller;

import com.thangavel.product.dto.ProductDTO;
import com.thangavel.product.exception.CustomException;
import com.thangavel.product.exception.ErrorCode;
import com.thangavel.product.service.ProductServiceImpl;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/products")
public class ProductController {

    @Autowired
    private ProductServiceImpl service;

    @PostMapping("/add-item")
    public ProductDTO createProduct(HttpServletRequest request, @RequestBody ProductDTO product) {
        UUID userId = UUID.fromString((String) request.getAttribute("userId"));
        return service.saveProduct(userId, product);
    }

    @GetMapping("/items")
    public List<ProductDTO> getProduct(HttpServletRequest request) {
        UUID userId = UUID.fromString((String) request.getAttribute("userId"));
        return service.getAllProducts(userId);
    }

    @GetMapping
    public ResponseEntity<ProductDTO> getProductById(@RequestParam Long id) {
        Optional<ProductDTO> product = service.getProductById(id);
        return product.map(ResponseEntity::ok)
                .orElseThrow(() -> new CustomException(ErrorCode.ProductNotFoundException));
    }

    @PutMapping
    public ResponseEntity<ProductDTO> updateProduct(@RequestParam Long id, @RequestBody ProductDTO product) {
        ProductDTO updatedProduct = service.updateProduct(id, product);
        return ResponseEntity.ok(updatedProduct);
    }

    @DeleteMapping
    public ResponseEntity<Void> deleteProduct(@RequestParam Long id) {
        service.deleteProduct(id);
        return ResponseEntity.noContent().build();
    }
}
