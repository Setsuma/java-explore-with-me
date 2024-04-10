package ru.practicum.ewm.entity.category.service;

import ru.practicum.ewm.entity.category.dto.request.AddCategoryRequestDto;
import ru.practicum.ewm.entity.category.dto.request.UpdateCategoryRequestDto;
import ru.practicum.ewm.entity.category.dto.response.CategoryResponseDto;

public interface CategoryAdminService {
    CategoryResponseDto addCategory(AddCategoryRequestDto categoryDto);

    CategoryResponseDto updateCategoryById(Long catId, UpdateCategoryRequestDto categoryDto);

    void deleteCategoryById(Long catId);
}
