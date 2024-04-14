package ru.practicum.ewm.entity.compilation.service;

import ru.practicum.ewm.entity.compilation.dto.request.AddCompilationRequestDto;
import ru.practicum.ewm.entity.compilation.dto.request.UpdateCompilationRequestDto;
import ru.practicum.ewm.entity.compilation.dto.response.CompilationResponseDto;

public interface CompilationAdminService {
    CompilationResponseDto addCompilation(AddCompilationRequestDto compilationDto);

    CompilationResponseDto updateCompilation(Long compId, UpdateCompilationRequestDto compilationDto);

    void deleteCompilationById(Long compId);
}
