package ru.practicum.ewm.entity.compilation.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.ewm.entity.compilation.dto.request.AddCompilationRequestDto;
import ru.practicum.ewm.entity.compilation.dto.request.UpdateCompilationRequestDto;
import ru.practicum.ewm.entity.compilation.dto.response.CompilationResponseDto;
import ru.practicum.ewm.entity.compilation.entity.Compilation;
import ru.practicum.ewm.entity.compilation.mapper.CompilationMapper;
import ru.practicum.ewm.entity.compilation.repository.CompilationJpaRepository;
import ru.practicum.ewm.entity.compilation.service.CompilationAdminService;
import ru.practicum.ewm.entity.event.entity.Event;
import ru.practicum.ewm.entity.event.repository.EventJpaRepository;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Service
@Transactional(readOnly = true)
@Slf4j
@RequiredArgsConstructor
public class CompilationAdminServiceImpl implements CompilationAdminService {
    private final CompilationJpaRepository compilationRepository;
    private final EventJpaRepository eventRepository;

    @Override
    @Transactional
    public CompilationResponseDto addCompilation(AddCompilationRequestDto compilationDto) {
        Set<Event> existsEvents = new HashSet<>(eventRepository.findAllById(compilationDto.getEvents()));
        Compilation compilation = CompilationMapper.toCompilation(compilationDto, existsEvents);
        Compilation savedCompilation = compilationRepository.save(compilation);
        log.info("COMPILATION SAVED: " + savedCompilation);
        return CompilationMapper.toCompilationResponseDto(savedCompilation, null, null);
    }

    @Override
    @Transactional
    public CompilationResponseDto updateCompilation(Long compId, UpdateCompilationRequestDto compilationDto) {
        compilationRepository.checkCompilationExistsById(compId);
        Compilation updatedCompilation = getUpdatedCompilation(compId, compilationDto);
        Compilation savedCompilation = compilationRepository.save(updatedCompilation);
        log.info("COMPILATION UPDATED: " + savedCompilation);
        return CompilationMapper.toCompilationResponseDto(savedCompilation, null, null);
    }

    @Override
    @Transactional
    public void deleteCompilationById(Long compId) {
        compilationRepository.checkCompilationExistsById(compId);
        compilationRepository.deleteById(compId);
        log.info("COMPILATION DELETED: comId = {}", compId);
    }

    private Compilation getUpdatedCompilation(Long compId, UpdateCompilationRequestDto compilationDto) {
        Compilation compilation = compilationRepository.getReferenceById(compId);

        Optional.ofNullable(compilationDto.getTitle()).ifPresent(compilation::setTitle);
        Optional.ofNullable(compilationDto.getPinned()).ifPresent(compilation::setPinned);

        if (compilationDto.getEvents() != null && !compilationDto.getEvents().isEmpty()) {
            Set<Event> existsEvents = new HashSet<>(eventRepository.findAllById(compilationDto.getEvents()));
            compilation.setEvents(existsEvents);
        }

        return compilation;
    }
}
