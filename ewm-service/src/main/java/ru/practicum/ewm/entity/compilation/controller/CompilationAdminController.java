package ru.practicum.ewm.entity.compilation.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.practicum.ewm.entity.compilation.dto.request.AddCompilationRequestDto;
import ru.practicum.ewm.entity.compilation.dto.request.UpdateCompilationRequestDto;
import ru.practicum.ewm.entity.compilation.dto.response.CompilationResponseDto;
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
        log.info("ADD COMPILATION REQUEST: " + compilationDto);
        return compilationService.addCompilation(compilationDto);
    }

    @PatchMapping("/{compId}")
    public CompilationResponseDto updateCompilationById(
            @PathVariable Long compId,
            @RequestBody @Valid UpdateCompilationRequestDto compilationDto
    ) {
        log.info("UPDATE COMPILATION REQUEST: comId = {}, " + compilationDto, compId);
        return compilationService.updateCompilation(compId, compilationDto);
    }

    @DeleteMapping("/{compId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteCompilationById(
            @PathVariable Long compId
    ) {
        log.info("DELETE COMPILATION REQUEST: comId = {}", compId);
        compilationService.deleteCompilationById(compId);
    }
}
