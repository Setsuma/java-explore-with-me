package ru.practicum.ewm.entity.category.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.practicum.ewm.entity.category.dto.request.AddCategoryRequestDto;
import ru.practicum.ewm.entity.category.dto.request.UpdateCategoryRequestDto;
import ru.practicum.ewm.entity.category.dto.response.CategoryResponseDto;
import ru.practicum.ewm.entity.category.logging.CategoryControllerLoggerHelper;
import ru.practicum.ewm.entity.category.service.CategoryAdminService;

import javax.validation.Valid;

@RestController
@RequestMapping("/admin/categories")
@Slf4j
@RequiredArgsConstructor
public class CategoryAdminController {
    private final CategoryAdminService categoryAdminService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CategoryResponseDto addCategory(
            @RequestBody @Valid AddCategoryRequestDto categoryDto
    ) {
        CategoryControllerLoggerHelper.addCategory(log, categoryDto);
        return categoryAdminService.addCategory(categoryDto);
    }

    @PatchMapping("/{catId}")
    public CategoryResponseDto updateCategory(
            @PathVariable Long catId,
            @RequestBody @Valid UpdateCategoryRequestDto categoryDto
    ) {
        CategoryControllerLoggerHelper.updateCategory(log, catId, categoryDto);
        return categoryAdminService.updateCategoryById(catId, categoryDto);
    }

    @DeleteMapping("/{catId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteCategoryById(
            @PathVariable Long catId
    ) {
        CategoryControllerLoggerHelper.deleteCategory(log, catId);
        categoryAdminService.deleteCategoryById(catId);
    }
}
