package com.nightlyecommercebackend.service.impl;

import com.nightlyecommercebackend.dto.product.CreateProductRequest;
import com.nightlyecommercebackend.dto.product.ProductRequest;
import com.nightlyecommercebackend.dto.product.ProductResponse;
import com.nightlyecommercebackend.entity.Product;
import com.nightlyecommercebackend.repository.ProductRepository;
import com.nightlyecommercebackend.service.interfaces.ProductService;
import com.nightlyecommercebackend.specification.ProductSpecification;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;


@Service
public class ProductServiceImpl implements ProductService {
    ProductRepository productRepository;
    ModelMapper modelMapper;
    ProductSpecification productSpecification;

    public ProductServiceImpl(ProductRepository productRepository, ModelMapper modelMapper,
                              ProductSpecification productSpecification) {
        this.productRepository = productRepository;
        this.modelMapper = modelMapper;
        this.productSpecification = productSpecification;
    }


//    @Override
//    public ProductResponse createProduct(ProductRequest productRequest) {
//        Product product = modelMapper.map(productRequest, Product.class);
////        product.setId(null); // 👉 force ID to null
//        Product savedProduct = productRepository.save(product);
//        ProductResponse response = modelMapper.map(savedProduct, ProductResponse.class);
//        return response;
//    }
public ProductResponse createProduct(CreateProductRequest request) {
    Product product = modelMapper.map(request, Product.class);
    Product saved = productRepository.save(product);
    return modelMapper.map(saved, ProductResponse.class);
}



    @Override
    public ProductResponse findProductById(Long id) {
        Product product = productRepository.findById(id).orElse(null);
        if(product == null) {
            return null; // or throw an exception
        }
        ProductResponse response = modelMapper.map(product, ProductResponse.class);
        return response;
    }

    @Override
    public ProductResponse updateProduct(Long id, ProductRequest productRequest) {
        Product existingProduct = productRepository.findById((id)).orElse(null);
        if (existingProduct == null) {
            return null; // or throw an exception
        }
        // Update the existing product with new values from the request
        existingProduct.setName(productRequest.getName());
        existingProduct.setDescription(productRequest.getDescription());
        existingProduct.setPrice(productRequest.getPrice());
        existingProduct.setImageUrl(productRequest.getImageUrl());

        // Save the updated product back to the repository
        productRepository.save(existingProduct);

        // Convert the updated product to ProductResponse
        ProductResponse response = modelMapper.map(existingProduct, ProductResponse.class);
        return response;
    }

    @Override
    public Page<ProductResponse> findAllProducts(Pageable pageable) {
        Page<Product> productPage = productRepository.findAll(pageable);
        return productPage.map(product -> modelMapper.map(product, ProductResponse.class));
    }


    @Override
    public void deleteProduct(Long id) {
        Product product = productRepository.findById(id).orElse(null);
        if (product != null) {
            productRepository.delete(product);
        } else {
            // Handle the case where the product does not exist
            throw new RuntimeException("Product not found with id: " + id);
        }
    }

    @Override
    public Page<ProductResponse> findAllProducts(Long categoryId, Double minPrice, Double maxPrice, Pageable pageable) {

        Specification<Product> spec = Specification.where(null);

        if (categoryId != null) {
            spec = spec.and(productSpecification.categoryIdEquals(categoryId));
        }

        if (minPrice != null && maxPrice != null) {
            spec = spec.and(productSpecification.isBetweenPrice(minPrice, maxPrice));
        }


        return productRepository.findAll(spec, pageable).map(product -> modelMapper.map(product, ProductResponse.class));
    }
}
