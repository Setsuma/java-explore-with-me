package ru.practicum.ewm.entity.category.logging;

import lombok.experimental.UtilityClass;
import org.slf4j.Logger;
import ru.practicum.ewm.entity.category.dto.request.AddCategoryRequestDto;
import ru.practicum.ewm.entity.category.dto.request.UpdateCategoryRequestDto;

@UtilityClass
public final class CategoryControllerLoggerHelper {

    public static void addCategory(
            Logger logger,
            AddCategoryRequestDto categoryDto
    ) {
        logger.info("add CATEGORY["
                        + "name={}"
                        + "].",
                categoryDto.getName());
    }

    public static void getCategoryDtoById(
            Logger logger,
            Long catId
    ) {
        logger.info("get CATEGORY<DTO>["
                        + "id={}"
                        + "].",
                catId);
    }

    public static void getCategoryDtoPage(
            Logger logger,
            Integer from,
            Integer size
    ) {
        logger.info("get CATEGORY_PAGE<DTO>["
                        + "from={}, "
                        + "size={}"
                        + "].",
                from,
                size);
    }

    public static void updateCategory(
            Logger logger,
            Long catId,
            UpdateCategoryRequestDto categoryDto
    ) {
        logger.info("update CATEGORY["
                        + "id={}, "
                        + "new_name='{}'"
                        + "].",
                catId,
                categoryDto.getName());
    }

    public static void deleteCategory(
            Logger logger,
            Long catId
    ) {
        logger.info("delete CATEGORY["
                        + "id={}"
                        + "].",
                catId);
    }
}
