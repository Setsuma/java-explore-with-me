package ru.practicum.ewm.entity.compilation.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.ewm.entity.compilation.dto.response.CompilationResponseDto;
import ru.practicum.ewm.entity.compilation.service.CompilationPublicService;

import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;

@RestController
@RequestMapping("/compilations")
@Validated
@Slf4j
@RequiredArgsConstructor
public class CompilationPublicController {
    private final CompilationPublicService compilationService;

    @GetMapping("/{compId}")
    public CompilationResponseDto getCompilationById(
            @PathVariable Long compId
    ) {
        log.info("GET COMPILATION REQUEST: comId = {}", compId);
        return compilationService.getCompilationById(compId);
    }

    @GetMapping
    public Iterable<CompilationResponseDto> getCompilationsByPinned(
            @RequestParam(required = false) Boolean pinned,
            @RequestParam(defaultValue = "0") @PositiveOrZero Integer from,
            @RequestParam(defaultValue = "10") @Positive Integer size
    ) {
        log.info("GET COMPILATIONS BY PINNED REQUEST: pinned = {}, from = {}, size = {}", pinned, from, size);
        return compilationService.getCompilationsByPinned(pinned, from, size);
    }
}
