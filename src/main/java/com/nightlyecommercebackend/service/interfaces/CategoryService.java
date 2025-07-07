package com.nightlyecommercebackend.service.interfaces;

import com.nightlyecommercebackend.dto.category.CategoryRequest;
import com.nightlyecommercebackend.dto.category.CategoryResponse;
import com.nightlyecommercebackend.entity.Category;

import java.util.List;

public interface CategoryService {
    CategoryResponse createCategory(CategoryRequest req);
    List<CategoryResponse> getAllCategories();
    CategoryResponse getCategoryById(Long id);

    CategoryResponse updateCategory(Long id, CategoryRequest categoryRequest);

    void deleteCategory(Long id);
}
