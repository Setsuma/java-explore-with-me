package ru.practicum.hit.server.endpointhit.service;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.JpaSort;
import org.springframework.stereotype.Service;
import ru.practicum.hit.dto.EndpointHitDto;
import ru.practicum.hit.dto.ViewStatsDto;
import ru.practicum.hit.server.endpointhit.model.EndpointHit;
import ru.practicum.hit.server.endpointhit.repository.EndpointHitRepository;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class EndpointHitService {
    private final EndpointHitRepository endpointHitRepository;
    private final ModelMapper mapper;

    public EndpointHitDto addEndpointHit(EndpointHitDto endpointHitDto) {
        return mapper.map(
                endpointHitRepository.save(mapper.map(endpointHitDto, EndpointHit.class)),
                EndpointHitDto.class);
    }

    public List<ViewStatsDto> getStats(LocalDateTime start, LocalDateTime end, List<String> uris, Boolean unique) {
        List<ViewStatsDto> viewStats;
        Sort sort = JpaSort.unsafe("COUNT(DISTINCT hits.ip)").descending();

        if (unique == Boolean.TRUE) {
            viewStats = endpointHitRepository.collectUniqueEndpointStats(start, end, uris, sort);
        } else {
            viewStats = endpointHitRepository.collectEndpointHitStats(start, end, uris, sort);
        }

        return viewStats;
    }
}
