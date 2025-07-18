package com.nightlyecommercebackend.dto.category;


import jakarta.validation.constraints.NotBlank;

public class CategoryRequest {
    @NotBlank(message = "Name cannot be blank")
    private String name;
    private String description;
    private String imageUrl;

    public CategoryRequest(String name, String description, String imageUrl) {
        this.name = name;
        this.description = description;
        this.imageUrl = imageUrl;
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

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}
