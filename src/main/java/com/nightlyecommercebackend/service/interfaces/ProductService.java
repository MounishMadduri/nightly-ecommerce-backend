package com.nightlyecommercebackend.service.interfaces;

import com.nightlyecommercebackend.dto.product.CreateProductRequest;
import com.nightlyecommercebackend.dto.product.ProductRequest;
import com.nightlyecommercebackend.dto.product.ProductResponse;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import org.springframework.data.domain.Pageable;

@Service
public interface ProductService {
    ProductResponse createProduct(CreateProductRequest createProductRequest);

    ProductResponse findProductById(Long id);

    ProductResponse updateProduct(Long id, ProductRequest productRequest);

    Page<ProductResponse> findAllProducts(Pageable pageable);

    void deleteProduct(Long id);

    Page<ProductResponse> findAllProducts(Long categoryId, Double minPrice, Double maxPrice, Pageable pageable);
}
