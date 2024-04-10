package ru.practicum.ewm.entity.compilation.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.practicum.ewm.entity.compilation.dto.request.AddCompilationRequestDto;
import ru.practicum.ewm.entity.compilation.dto.request.UpdateCompilationRequestDto;
import ru.practicum.ewm.entity.compilation.dto.response.CompilationResponseDto;
import ru.practicum.ewm.entity.compilation.logging.CompilationControllerLoggerHelper;
import ru.practicum.ewm.entity.compilation.service.CompilationAdminService;

import javax.validation.Valid;

@RestController
@RequestMapping("/admin/compilations")
@Slf4j
@RequiredArgsConstructor
public class CompilationAdminController {
    private final CompilationAdminService compilationService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CompilationResponseDto addCompilation(
            @RequestBody @Valid AddCompilationRequestDto compilationDto
    ) {
        CompilationControllerLoggerHelper.addCompilation(log, compilationDto);
        return compilationService.addCompilation(compilationDto);
    }

    @PatchMapping("/{compId}")
    public CompilationResponseDto updateCompilationById(
            @PathVariable Long compId,
            @RequestBody @Valid UpdateCompilationRequestDto compilationDto
    ) {
        CompilationControllerLoggerHelper.updateCompilationById(log, compId, compilationDto);
        return compilationService.updateCompilation(compId, compilationDto);
    }

    @DeleteMapping("/{compId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteCompilationById(
            @PathVariable Long compId
    ) {
        CompilationControllerLoggerHelper.deleteCompilationById(log, compId);
        compilationService.deleteCompilationById(compId);
    }
}
