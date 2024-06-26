package ru.practicum.ewm.entity.event.service.contoller.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.ewm.entity.category.entity.Category;
import ru.practicum.ewm.entity.category.repository.CategoryJpaRepository;
import ru.practicum.ewm.entity.event.dto.request.UpdateEventAdminRequestDto;
import ru.practicum.ewm.entity.event.dto.response.EventFullResponseDto;
import ru.practicum.ewm.entity.event.entity.Event;
import ru.practicum.ewm.entity.event.mapper.EventMapper;
import ru.practicum.ewm.entity.event.repository.EventJpaRepository;
import ru.practicum.ewm.entity.event.service.contoller.EventAdminService;
import ru.practicum.ewm.entity.event.service.statistics.EventStatisticsService;
import ru.practicum.ewm.entity.event.validation.validator.EventValidator;
import ru.practicum.ewm.entity.participation.entity.Participation;
import ru.practicum.ewm.entity.participation.repository.jpa.ParticipationRequestJpaRepository;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
@Transactional(readOnly = true)
@Slf4j
@RequiredArgsConstructor
public class EventAdminServiceImpl implements EventAdminService {
    private final EventJpaRepository eventRepository;
    private final CategoryJpaRepository categoryRepository;
    private final ParticipationRequestJpaRepository requestRepository;
    private final EventStatisticsService eventStatisticsService;

    @Override
    public Iterable<EventFullResponseDto> searchAdminEventsByParameters(
            Set<Long> users,
            Set<Event.State> states,
            Set<Long> categories,
            LocalDateTime rangeStart,
            LocalDateTime rangeEnd,
            Integer from,
            Integer size
    ) {
        Page<Event> events = eventRepository.searchEventsByAdminParameters(
                users,
                states,
                categories,
                rangeStart,
                rangeEnd,
                PageRequest.of(from, size));

        List<EventFullResponseDto> eventDtos = EventMapper.toEventFullResponseDto(
                events,
                eventStatisticsService.getEventViews(events, false),
                requestRepository.getEventRequestsCount(events, Participation.Status.CONFIRMED));
        eventDtos.sort(Comparator.comparing(EventFullResponseDto::getId).reversed());
        log.info("ADMIN EVENTS FOUND: " + eventDtos);
        return eventDtos;
    }

    @Override
    @Transactional
    public EventFullResponseDto updateAdminEventById(Long eventId, UpdateEventAdminRequestDto adminRequest) {
        eventRepository.checkEventExistsById(eventId);
        Event event = getUpdatedEvent(eventId, adminRequest);
        checkEventAdminUpdate(event, adminRequest.getStateAction());
        performActionIfExists(event, adminRequest.getStateAction());
        Event savedEvent = eventRepository.save(event);
        log.info("ADMIN EVENT UPDATED: " + savedEvent);
        return EventMapper.toEventFullResponseDto(savedEvent, null, null);
    }

    private static void performActionIfExists(Event event, Event.AdminStateAction stateAction) {
        if (stateAction == null) {
            return;
        }

        switch (stateAction) {
            case PUBLISH_EVENT:
                event.setState(Event.State.PUBLISHED);
                event.setPublishedOn(LocalDateTime.now());
                break;
            case REJECT_EVENT:
                event.setState(Event.State.CANCELED);
                break;
            default:
                throw new RuntimeException(String.format("Action '%s' not implemented", stateAction));
        }
    }

    private static void checkEventAdminUpdate(Event event, Event.AdminStateAction stateAction) {
        EventValidator.validateEventDateMoreThanHourAfterPublication(event);

        if (stateAction == Event.AdminStateAction.PUBLISH_EVENT) {
            EventValidator.validateEventBeforePublishing(event);
        } else if (stateAction == Event.AdminStateAction.REJECT_EVENT) {
            EventValidator.validateEventBeforeRejection(event);
        }
    }

    private Event getUpdatedEvent(Long eventId, UpdateEventAdminRequestDto adminRequest) {
        Event event = eventRepository.getReferenceById(eventId);

        if (adminRequest.getCategory() != null) {
            categoryRepository.checkCategoryExistsById(adminRequest.getCategory());
            Category category = categoryRepository.getReferenceById(adminRequest.getCategory());
            event.setCategory(category);
        }

        if (adminRequest.getLocation() != null) {
            event.setLat(adminRequest.getLocation().getLat());
            event.setLon(adminRequest.getLocation().getLon());
        }

        Optional.ofNullable(adminRequest.getTitle()).ifPresent(event::setTitle);
        Optional.ofNullable(adminRequest.getAnnotation()).ifPresent(event::setAnnotation);
        Optional.ofNullable(adminRequest.getDescription()).ifPresent(event::setDescription);
        Optional.ofNullable(adminRequest.getEventDate()).ifPresent(event::setEventDate);
        Optional.ofNullable(adminRequest.getPaid()).ifPresent(event::setPaid);
        Optional.ofNullable(adminRequest.getParticipantLimit()).ifPresent(event::setParticipantLimit);
        Optional.ofNullable(adminRequest.getRequestModeration()).ifPresent(event::setRequestModeration);

        return event;
    }
}
