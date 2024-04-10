package ru.practicum.ewm.entity.category.mapper;

import lombok.experimental.UtilityClass;
import ru.practicum.ewm.entity.category.dto.request.AddCategoryRequestDto;
import ru.practicum.ewm.entity.category.dto.response.CategoryResponseDto;
import ru.practicum.ewm.entity.category.entity.Category;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@UtilityClass
public final class CategoryMapper {

    public static Category toCategory(AddCategoryRequestDto categoryDto) {
        Category category = new Category();

        category.setName(categoryDto.getName());

        return category;
    }

    public static CategoryResponseDto toCategoryResponseDto(Category category) {
        CategoryResponseDto categoryDto = new CategoryResponseDto();

        categoryDto.setId(category.getId());
        categoryDto.setName(category.getName());

        return categoryDto;
    }

    public static List<CategoryResponseDto> toCategoryResponseDto(Iterable<Category> categories) {
        return StreamSupport.stream(categories.spliterator(), false)
                .map(CategoryMapper::toCategoryResponseDto)
                .collect(Collectors.toList());
    }
}
