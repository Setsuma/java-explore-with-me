package ru.practicum.ewm.entity.category.service;

import ru.practicum.ewm.entity.category.dto.response.CategoryResponseDto;

public interface CategoryPublicService {
    CategoryResponseDto getCategoryById(Long catId);

    Iterable<CategoryResponseDto> getCategories(Integer from, Integer size);
}
