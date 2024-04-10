package ru.practicum.ewm.entity.category.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.ewm.entity.category.dto.response.CategoryResponseDto;
import ru.practicum.ewm.entity.category.entity.Category;
import ru.practicum.ewm.entity.category.logging.CategoryServiceLoggerHelper;
import ru.practicum.ewm.entity.category.mapper.CategoryMapper;
import ru.practicum.ewm.entity.category.repository.CategoryJpaRepository;
import ru.practicum.ewm.entity.category.service.CategoryPublicService;

import java.util.List;

@Service
@Transactional(readOnly = true)
@Slf4j
@RequiredArgsConstructor
public class CategoryPublicServiceImpl implements CategoryPublicService {
    private final CategoryJpaRepository categoryRepository;

    @Override
    public CategoryResponseDto getCategoryById(Long catId) {
        categoryRepository.checkCategoryExistsById(catId);
        Category category = categoryRepository.getReferenceById(catId);
        CategoryResponseDto categoryDto = CategoryMapper.toCategoryResponseDto(category);
        CategoryServiceLoggerHelper.categoryDtoReturned(log, categoryDto);
        return categoryDto;
    }

    @Override
    public List<CategoryResponseDto> getCategories(Integer from, Integer size) {
        Page<Category> categories = categoryRepository.findAll(PageRequest.of(from, size));
        List<CategoryResponseDto> categoryDtos = CategoryMapper.toCategoryResponseDto(categories);
        CategoryServiceLoggerHelper.categoryDtoPageReturned(log, from, size, categoryDtos);
        return categoryDtos;
    }
}
