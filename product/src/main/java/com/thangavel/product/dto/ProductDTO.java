package com.thangavel.product.dto;

import java.util.UUID;

public record ProductDTO(
        Long productId,
        String name,
        String description,
        String category,
        double price) {
}
