package ru.practicum.ewm.entity.compilation.logging;

import lombok.experimental.UtilityClass;
import org.slf4j.Logger;
import ru.practicum.ewm.entity.compilation.dto.request.AddCompilationRequestDto;
import ru.practicum.ewm.entity.compilation.dto.request.UpdateCompilationRequestDto;

@UtilityClass
public final class CompilationControllerLoggerHelper {

    public static void addCompilation(
            Logger logger,
            AddCompilationRequestDto compilationDto
    ) {
        logger.info("add EVENT_COMPILATION["
                        + "events_count={}, "
                        + "pinned={}, "
                        + "title='{}'"
                        + "].",
                compilationDto.getEvents().size(),
                compilationDto.getPinned(),
                compilationDto.getTitle());
    }

    public static void getCompilationById(
            Logger logger,
            Long compId
    ) {
        logger.info("get COMPILATION<DTO>["
                        + "id={}"
                        + "].",
                compId);
    }

    public static void getCompilationDtoPageByPinned(
            Logger logger,
            Boolean pinned,
            Integer from,
            Integer size
    ) {
        logger.info("get EVENT_COMPILATION_PAGE<DTO>["
                        + "from={}, "
                        + "size={}, "
                        + "pinned={}"
                        + "] by parameters.",
                from,
                size,
                (pinned != null) ? pinned : "ANY");
    }

    public static void updateCompilationById(
            Logger logger,
            Long compId,
            UpdateCompilationRequestDto compilationDto
    ) {
        logger.info("update EVENT_COMPILATION["
                        + "id={}, "
                        + "events_count={}, "
                        + "pinned={}, "
                        + "title='{}'"
                        + "].",
                compId,
                compilationDto.getEvents().size(),
                compilationDto.getPinned(),
                compilationDto.getTitle());
    }

    public static void deleteCompilationById(
            Logger logger,
            Long compId
    ) {
        logger.info("delete EVENT_COMPILATION["
                        + "id={}"
                        + "].",
                compId);
    }
}
