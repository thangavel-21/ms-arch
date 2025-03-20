package com.thangavel.product.service;

import com.thangavel.product.dto.ProductDTO;
import com.thangavel.product.dao.ProductModel;
import com.thangavel.product.repository.ProductRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Slf4j
@Service
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;

    public ProductServiceImpl(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public ProductDTO saveProduct(UUID userId, ProductDTO productDTO) {
        ProductModel productModel = convertToEntity(userId, productDTO);
        ProductModel savedProduct = productRepository.save(productModel);
        return convertToDTO(savedProduct);
    }

    @Override
    public ProductDTO updateProduct(Long id, ProductDTO productDTO) {
        ProductModel productModel = productRepository.findById(id).orElseThrow();
        productModel.setName(productDTO.name());
        productModel.setDescription(productDTO.description());
        productModel.setId(productModel.getId());
        productModel.setPrice(productDTO.price());
        ProductModel updatedProduct = productRepository.save(productModel);
        return convertToDTO(updatedProduct);
    }

    @Override
    public void deleteProduct(Long id) {
        productRepository.deleteById(id);
    }

    @Override
    public Optional<ProductDTO> getProductById(Long id) {
        return productRepository.findById(id).map(this::convertToDTO);
    }

    @Override
    public List<ProductDTO> getAllProducts(UUID userId) {
        return productRepository.findAllByUserId(userId)
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    private ProductDTO convertToDTO(ProductModel product) {
        return new ProductDTO(product.getId(), product.getName(), product.getDescription(), product.getCategory(), product.getPrice());
    }

    private ProductModel convertToEntity(UUID userId, ProductDTO productDTO) {
        ProductModel product = new ProductModel();
        product.setUserId(userId);
        product.setId(product.getId());
        product.setCategory(productDTO.category());
        product.setName(productDTO.name());
        product.setDescription(productDTO.description());
        product.setPrice(productDTO.price());
        return product;
    }
}
