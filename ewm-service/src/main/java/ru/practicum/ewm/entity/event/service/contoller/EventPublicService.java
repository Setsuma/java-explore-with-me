package ru.practicum.ewm.entity.event.service.contoller;

import ru.practicum.ewm.entity.event.dto.response.EventFullResponseDto;
import ru.practicum.ewm.entity.event.dto.response.EventShortResponseDto;
import ru.practicum.ewm.entity.event.entity.Event;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.Set;

@SuppressWarnings("java:S107")
public interface EventPublicService {
    EventFullResponseDto getEventById(Long id, HttpServletRequest request);


    Iterable<EventShortResponseDto> searchEventsByParameters(
            String text,
            Set<Long> categories,
            Boolean paid,
            LocalDateTime rangeStart,
            LocalDateTime rangeEnd,
            Boolean onlyAvailable,
            Event.Sort sort,
            Integer from,
            Integer size,
            HttpServletRequest request);

}
