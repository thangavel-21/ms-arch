package com.thangavel.product.dao;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.Columns;

import java.util.UUID;

@Setter
@Getter
@Data
@Entity
@ToString
@Table(name = "product_model")
public class ProductModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private UUID userId;
    private String name;
    private String description;
    private String category;
    private double price;
}
