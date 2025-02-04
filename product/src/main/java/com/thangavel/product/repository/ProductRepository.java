package com.thangavel.product.repository;

import com.thangavel.product.dao.ProductModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface ProductRepository extends JpaRepository<ProductModel, Long> {

    List<ProductModel> findAllByUserId(UUID userId);
}
