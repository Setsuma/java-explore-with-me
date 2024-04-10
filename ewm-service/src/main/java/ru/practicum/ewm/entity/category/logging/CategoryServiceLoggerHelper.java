package ru.practicum.ewm.entity.category.logging;

import lombok.experimental.UtilityClass;
import org.slf4j.Logger;
import ru.practicum.ewm.entity.category.dto.response.CategoryResponseDto;
import ru.practicum.ewm.entity.category.entity.Category;

import java.util.Collection;

@UtilityClass
@SuppressWarnings("java:S1192")
public final class CategoryServiceLoggerHelper {

    public static void categorySaved(
            Logger logger,
            Category category
    ) {
        logger.debug("CATEGORY["
                        + "id={}, "
                        + "name='{}'"
                        + "] saved.",
                category.getId(),
                category.getName());
    }

    public static void categoryDtoReturned(
            Logger logger,
            CategoryResponseDto categoryDto
    ) {
        logger.debug("CATEGORY<DTO>["
                        + "id={}, "
                        + "name='{}'"
                        + "] returned",
                categoryDto.getId(),
                categoryDto.getName());
    }

    public static void categoryDtoPageReturned(
            Logger logger,
            Integer from,
            Integer size,
            Collection<CategoryResponseDto> categoryDtos
    ) {
        logger.debug("CATEGORY_PAGE<DTO>["
                        + "from={}, "
                        + "size={}, "
                        + "categories_count={}"
                        + "] returned.",
                from, size, categoryDtos.size());
    }

    public static void categoryUpdated(
            Logger logger,
            Category category
    ) {
        logger.debug("CATEGORY["
                        + "id={}, "
                        + "name='{}'"
                        + "] updated.",
                category.getId(),
                category.getName());
    }

    public static void categoryDeleted(
            Logger logger,
            Long catId
    ) {
        logger.debug("CATEGORY["
                        + "id={}"
                        + "] deleted.",
                catId);
    }
}
