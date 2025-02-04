package com.thangavel.product.dto;

import java.util.UUID;

public record ProductDTO(
        UUID userId,
        Long productId,
        String name,
        String description,
        String category,
        double price) {
}
