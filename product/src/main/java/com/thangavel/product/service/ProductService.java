package com.thangavel.product.service;

import com.thangavel.product.dto.ProductDTO;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ProductService {

    ProductDTO saveProduct(UUID userId, ProductDTO productDTO);

    ProductDTO updateProduct(Long id, ProductDTO productDTO);

    void deleteProduct(Long id);

    Optional<ProductDTO> getProductById(Long id);

    List<ProductDTO> getAllProducts(UUID userId);
}
