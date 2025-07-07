package com.nightlyecommercebackend.dto.product;

import com.nightlyecommercebackend.entity.Category;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public class ProductRequest {
    @NotBlank(message = "message can't be blank")
    private String name;
    private String description;
    @NotNull(message = "price is required")
    @Positive(message = "price must be positive")
    private double price;
    @NotNull(message = "stock quantity can't be null")
    @Positive(message = "stock quantity must be positive")
    private Integer stockQuantity;
    private String imageUrl;
    @NotNull(message = "SKU is required")
    @Positive(message = "SKU must be positive")
    private Integer sku;
    private Boolean available;
    @NotNull(message = "category ID is required")
    private Long categoryId;

    public ProductRequest(String name, String description, double price, Integer stockQuantity, String imageUrl, Integer sku, Boolean available, Long categoryId) {
        this.name = name;
        this.description = description;
        this.price = price;
        this.stockQuantity = stockQuantity;
        this.imageUrl = imageUrl;
        this.sku = sku;
        this.available = available;
        this.categoryId = categoryId;
    }
public ProductRequest() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public Integer getStockQuantity() {
        return stockQuantity;
    }

    public void setStockQuantity(Integer stockQuantity) {
        this.stockQuantity = stockQuantity;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public Integer getSku() {
        return sku;
    }

    public void setSku(Integer sku) {
        this.sku = sku;
    }

    public Boolean getAvailable() {
        return available;
    }

    public void setAvailable(Boolean available) {
        this.available = available;
    }

    public Long getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Long categoryId) {
        this.categoryId = categoryId;
    }


}
