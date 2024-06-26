package ru.practicum.ewm.entity.category.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.ewm.entity.category.dto.response.CategoryResponseDto;
import ru.practicum.ewm.entity.category.service.CategoryPublicService;

import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;

@RestController
@RequestMapping("/categories")
@Validated
@Slf4j
@RequiredArgsConstructor
public class CategoryPublicController {
    private final CategoryPublicService categoryPublicService;

    @GetMapping("/{catId}")
    public CategoryResponseDto getCategoryById(
            @PathVariable Long catId
    ) {
        log.info("GET CATEGORY REQUEST: catId = {}", catId);
        return categoryPublicService.getCategoryById(catId);
    }

    @GetMapping
    public Iterable<CategoryResponseDto> getCategories(
            @RequestParam(defaultValue = "0") @PositiveOrZero Integer from,
            @RequestParam(defaultValue = "10") @Positive Integer size
    ) {
        log.info("GET CATEGORIES REQUEST: from = {}, size = {}", from, size);
        return categoryPublicService.getCategories(from, size);
    }
}
