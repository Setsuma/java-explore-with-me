package ru.practicum.ewm.entity.compilation.logging;

import lombok.experimental.UtilityClass;
import org.slf4j.Logger;
import ru.practicum.ewm.entity.compilation.dto.response.CompilationResponseDto;
import ru.practicum.ewm.entity.compilation.entity.Compilation;

import java.util.List;

@UtilityClass
@SuppressWarnings("java:S1192")
public final class CompilationServiceLoggerHelper {

    public static void compilationSaved(
            Logger logger,
            Compilation compilation
    ) {
        logger.debug("COMPILATION["
                        + "id={}, "
                        + "title='{}', "
                        + "pinned={}, "
                        + "events_count={}"
                        + "] returned.",
                compilation.getId(),
                compilation.getTitle(),
                compilation.getPinned(),
                (compilation.getEvents() != null) ? compilation.getEvents().size() : 0);
    }

    public static void compilationDtoReturned(
            Logger logger,
            CompilationResponseDto compilationDto
    ) {
        logger.debug("COMPILATION<DTO>["
                        + "id={}, "
                        + "title='{}', "
                        + "pinned={}, "
                        + "events_count={}"
                        + "] returned.",
                compilationDto.getId(),
                compilationDto.getTitle(),
                compilationDto.getPinned(),
                (compilationDto.getEvents() != null) ? compilationDto.getEvents().size() : 0);
    }

    public static void compilationDtoPageReturned(
            Logger logger,
            Integer from,
            Integer size,
            List<CompilationResponseDto> compilations
    ) {
        logger.debug("COMPILATION_PAGE<DTO>["
                        + "from={}, "
                        + "size={}, "
                        + "compilation_count={}"
                        + "] returned.",
                from,
                size,
                compilations.size());
    }

    public static void compilationUpdated(
            Logger logger,
            Compilation compilation
    ) {
        logger.debug("COMPILATION["
                        + "id={}, "
                        + "title='{}', "
                        + "pinned={}, "
                        + "events_count={}"
                        + "] updated.",
                compilation.getId(),
                compilation.getTitle(),
                compilation.getPinned(),
                (compilation.getEvents() != null) ? compilation.getEvents().size() : 0);
    }

    public static void compilationDeleted(
            Logger logger,
            Long compId
    ) {
        logger.debug("COMPILATION["
                        + "id={}"
                        + "] deleted",
                compId);
    }
}
