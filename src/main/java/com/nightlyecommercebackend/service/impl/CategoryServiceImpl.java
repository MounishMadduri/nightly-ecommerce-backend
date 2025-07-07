package com.nightlyecommercebackend.service.impl;

import com.nightlyecommercebackend.dto.category.CategoryRequest;
import com.nightlyecommercebackend.dto.category.CategoryResponse;
import com.nightlyecommercebackend.entity.Category;
import com.nightlyecommercebackend.repository.CategoryRepository;
import com.nightlyecommercebackend.service.interfaces.CategoryService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryServiceImpl implements CategoryService {
    CategoryRepository categoryRepository ;
    ModelMapper modelMapper;

    public CategoryServiceImpl(CategoryRepository categoryRepository, ModelMapper modelMapper) {
        this.categoryRepository = categoryRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public CategoryResponse createCategory(CategoryRequest req) {

        // Convert CategoryRequest to Category entity
        Category category = modelMapper.map(req, Category.class);
        // Save the category entity to the database
        Category saved = categoryRepository.save(category);
        // Convert the saved Category entity back to CategoryResponse
        CategoryResponse response = modelMapper.map(saved, CategoryResponse.class);
        return response;
    }

    @Override
    public List<CategoryResponse> getAllCategories() {
        // Fetch all categories from the repository
        List<CategoryResponse> categories = categoryRepository.findAll().stream()
                .map(category -> modelMapper.map(category, CategoryResponse.class))
                .toList();
        return categories;
    }

    @Override
    public CategoryResponse getCategoryById(Long id) {
        // Find the category by ID
        Category category = categoryRepository.findById(id).orElse(null);
        CategoryResponse response = modelMapper.map(category, CategoryResponse.class);
        return response;
    }

    @Override
    public CategoryResponse updateCategory(Long id, CategoryRequest categoryRequest) {
        // Find the existing category by ID
        Category existingCategory = categoryRepository.findById(id).orElse(null);
        if (existingCategory == null) {
            return null; // or throw an exception
        }

        // Update the existing category with new values from the request
        modelMapper.map(categoryRequest, existingCategory);

        // Save the updated category back to the repository
        Category updatedCategory = categoryRepository.save(existingCategory);

        // Convert the updated entity to response DTO
        CategoryResponse response = modelMapper.map(updatedCategory, CategoryResponse.class);
        return response;
    }

    @Override
    public void deleteCategory(Long id) {
        // Check if the category exists
        if (categoryRepository.existsById(id)) {
            // Delete the category by ID
            categoryRepository.deleteById(id);
        } else {
            // Handle the case where the category does not exist
            throw new RuntimeException("Category not found with id: " + id);
        }
    }
}
