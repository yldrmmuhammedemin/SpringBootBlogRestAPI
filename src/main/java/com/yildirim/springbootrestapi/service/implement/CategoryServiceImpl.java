package com.yildirim.springbootrestapi.service.implement;

import com.yildirim.springbootrestapi.entity.Category;
import com.yildirim.springbootrestapi.exception.ResourceNotFoundException;
import com.yildirim.springbootrestapi.payload.CategoryDto;
import com.yildirim.springbootrestapi.repository.CategoryRepository;
import com.yildirim.springbootrestapi.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {
    private final CategoryRepository categoryRepository;
    private final ModelMapper modelMapper;

    @Override
    public CategoryDto addCategory(CategoryDto categoryDto) {
        Category category = mapToEntity(categoryDto);
        return mapToDto(categoryRepository.save(category));
    }

    @Override
    public List<CategoryDto> getCategories() {
        List<Category> categoryDtoList = categoryRepository.findAll();
        List<CategoryDto> categoryDtos = categoryDtoList
                .stream()
                .map(
                category -> mapToDto(category))
                .collect(Collectors.toList());
        return categoryDtos;
    }

    @Override
    public CategoryDto getCategoryById(Long categoryId) {
        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(()->
                        new ResourceNotFoundException("Category", "Id", categoryId));
        return mapToDto(category);
    }

    @Override
    public CategoryDto updateCategory(Long categoryId, CategoryDto categoryDto) {
        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(()->
                        new ResourceNotFoundException("Category", "Id", categoryId));
        Category newCategory = mapToEntity(categoryDto);
        category.setDescription(newCategory.getDescription());
        category.setName(newCategory.getName());
        categoryRepository.save(category);
        return mapToDto(category);
    }

    @Override
    public String deleteCategory(Long categoryId) {
        categoryRepository.findById(categoryId)
                .orElseThrow(()->
                        new ResourceNotFoundException("Category", "Id", categoryId));
        categoryRepository.deleteById(categoryId);
        return "Successfully category deleted.";
    }

    private CategoryDto mapToDto(Category category){
        CategoryDto categoryDto = modelMapper.map(category, CategoryDto.class);
        return categoryDto;
    }

    private Category mapToEntity(CategoryDto categoryDto){
        Category category = modelMapper.map(categoryDto, Category.class);
        return category;
    }


}
