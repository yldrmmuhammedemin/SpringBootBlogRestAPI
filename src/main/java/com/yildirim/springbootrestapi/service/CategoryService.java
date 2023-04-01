package com.yildirim.springbootrestapi.service;

import com.yildirim.springbootrestapi.payload.CategoryDto;

import java.util.List;

public interface CategoryService {
    CategoryDto addCategory(CategoryDto categoryDto);

    List<CategoryDto> getCategories();

    CategoryDto getCategoryById(Long categoryId);

    CategoryDto updateCategory(Long categoryId, CategoryDto categoryDto);

    String deleteCategory(Long categoryId);


}
