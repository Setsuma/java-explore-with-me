package ru.practicum.ewm.entity.event.service.contoller.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.ewm.entity.category.entity.Category;
import ru.practicum.ewm.entity.category.repository.CategoryJpaRepository;
import ru.practicum.ewm.entity.event.dto.request.AddEventRequestDto;
import ru.practicum.ewm.entity.event.dto.request.UpdateEventUserRequestDto;
import ru.practicum.ewm.entity.event.dto.request.comment.AddCommentRequestDto;
import ru.practicum.ewm.entity.event.dto.request.comment.UpdateCommentRequestDto;
import ru.practicum.ewm.entity.event.dto.response.EventFullResponseDto;
import ru.practicum.ewm.entity.event.dto.response.EventRequestsByStatusResponseDto;
import ru.practicum.ewm.entity.event.dto.response.EventShortResponseDto;
import ru.practicum.ewm.entity.event.dto.response.comment.CommentResponseDto;
import ru.practicum.ewm.entity.event.entity.Event;
import ru.practicum.ewm.entity.event.entity.comment.Comment;
import ru.practicum.ewm.entity.event.logging.EventServiceLoggerHelper;
import ru.practicum.ewm.entity.event.mapper.EventMapper;
import ru.practicum.ewm.entity.event.mapper.comment.CommentMapper;
import ru.practicum.ewm.entity.event.repository.EventJpaRepository;
import ru.practicum.ewm.entity.event.repository.comment.CommentJpaRepository;
import ru.practicum.ewm.entity.event.service.contoller.EventPrivateService;
import ru.practicum.ewm.entity.event.validation.validator.EventValidator;
import ru.practicum.ewm.entity.participation.dto.request.UpdateEventParticipationStatusRequestDto;
import ru.practicum.ewm.entity.participation.dto.response.ParticipationResponseDto;
import ru.practicum.ewm.entity.participation.entity.Participation;
import ru.practicum.ewm.entity.participation.mapper.ParticipationMapper;
import ru.practicum.ewm.entity.participation.repository.jpa.ParticipationRequestJpaRepository;
import ru.practicum.ewm.entity.participation.validation.validator.ParticipationValidator;
import ru.practicum.ewm.entity.user.entity.User;
import ru.practicum.ewm.entity.user.repository.UserJpaRepository;

import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
@Slf4j
@RequiredArgsConstructor
@SuppressWarnings("java:S1200")
public class EventPrivateServiceImpl implements EventPrivateService {
    private final EventJpaRepository eventRepository;
    private final UserJpaRepository userRepository;
    private final CategoryJpaRepository categoryRepository;
    private final ParticipationRequestJpaRepository requestRepository;
    private final CommentJpaRepository commentRepository;

    @Override
    @Transactional
    public EventFullResponseDto addEvent(Long userId, AddEventRequestDto eventDto) {
        userRepository.checkUserExistsById(userId);
        categoryRepository.checkCategoryExistsById(eventDto.getCategory());
        Event event = getEvent(eventDto, userId);
        EventValidator.validateEventDateMoreThanTwoHoursAfterCurrentTime(event);
        Event savedEvent = eventRepository.save(event);
        EventServiceLoggerHelper.eventSaved(log, savedEvent);
        return EventMapper.toEventFullResponseDto(savedEvent, null, null);
    }

    @Override
    @Transactional
    public CommentResponseDto addComment(Long userId, Long eventId, AddCommentRequestDto commentDto) {
        userRepository.checkUserExistsById(userId);
        eventRepository.checkEventExistsById(eventId);
        Comment comment = getComment(commentDto, userId, eventId);
        Comment savedComment = commentRepository.save(comment);
        EventServiceLoggerHelper.commentSaved(log, savedComment);
        return CommentMapper.toCommentResponseDto(savedComment);
    }

    @Override
    public EventFullResponseDto getEventById(Long userId, Long eventId) {
        userRepository.checkUserExistsById(userId);
        eventRepository.checkEventExistsById(eventId);
        Event event = eventRepository.getReferenceById(eventId);
        EventFullResponseDto eventDto = EventMapper.toEventFullResponseDto(event, null, null);
        EventServiceLoggerHelper.eventDtoReturned(log, eventDto);
        return eventDto;
    }

    @Override
    public Iterable<EventShortResponseDto> getUserEvents(Long userId, Integer from, Integer size) {
        userRepository.checkUserExistsById(userId);
        Page<Event> events = eventRepository.findAllByInitiatorId(userId, PageRequest.of(from, size));
        List<EventShortResponseDto> eventDtos = EventMapper.toShortResponseDto(events, null, null);
        EventServiceLoggerHelper.eventDtoPageByUserReturned(log, userId, from, size, eventDtos);
        return eventDtos;
    }

    @Override
    public Iterable<ParticipationResponseDto> getEventParticipationRequests(
            Long userId, Long eventId,
            Integer from, Integer size
    ) {
        userRepository.checkUserExistsById(userId);
        eventRepository.checkEventExistsById(eventId);
        Page<Participation> requests = requestRepository.findAllByEventInitiatorIdAndEventId(
                userId,
                eventId,
                PageRequest.of(from, size));
        List<ParticipationResponseDto> requestDtos = ParticipationMapper.toParticipationResponseDto(requests);
        EventServiceLoggerHelper.requestDtoPageByEventReturned(log, userId, eventId, requestDtos, from, size);
        return requestDtos;
    }

    @Override
    @Transactional
    public EventFullResponseDto updateEventById(Long userId, Long eventId, UpdateEventUserRequestDto eventDto) {
        userRepository.checkUserExistsById(userId);
        eventRepository.checkEventExistsById(eventId);
        Event updatedEvent = getUpdatedEvent(eventId, eventDto);
        EventValidator.validateEventBeforeUpdating(updatedEvent);
        EventValidator.validateEventDateMoreThanTwoHoursAfterCurrentTime(updatedEvent);
        performActionIfExists(updatedEvent, eventDto.getStateAction());
        Event savedEvent = eventRepository.save(updatedEvent);
        EventServiceLoggerHelper.eventUpdatedByUser(log, savedEvent);
        return EventMapper.toEventFullResponseDto(savedEvent, null, null);
    }

    @Override
    @Transactional
    public EventRequestsByStatusResponseDto updateEventParticipationRequestStatus(
            Long userId,
            Long eventId,
            UpdateEventParticipationStatusRequestDto requestStatusDto
    ) {
        userRepository.checkUserExistsById(userId);
        eventRepository.checkEventExistsById(eventId);
        List<Participation> requests = considerRequests(eventId, requestStatusDto);
        requestRepository.saveAll(requests);
        EventServiceLoggerHelper.requestsUpdated(log, requestStatusDto);
        return EventMapper.toEventRequestStatusUpdateResponseDto(requests);
    }

    @Override
    @Transactional
    public CommentResponseDto updateCommentById(
            Long userId,
            Long eventId,
            Long comId,
            UpdateCommentRequestDto commentDto
    ) {
        userRepository.checkUserExistsById(userId);
        eventRepository.checkEventExistsById(eventId);
        commentRepository.checkCommentExistsById(comId);
        Comment updatedComment = getUpdatedComment(comId, commentDto);
        Comment savedComment = commentRepository.save(updatedComment);
        EventServiceLoggerHelper.commentUpdated(log, savedComment);
        return CommentMapper.toCommentResponseDto(savedComment);
    }

    @Override
    @Transactional
    public void deleteCommentById(Long userId, Long eventId, Long comId) {
        userRepository.checkUserExistsById(userId);
        eventRepository.checkEventExistsById(eventId);
        commentRepository.checkCommentExistsById(comId);
        commentRepository.deleteById(comId);
        EventServiceLoggerHelper.commentDeleted(log, comId, userId, eventId);
    }

    @SuppressWarnings("java:S112")
    private static void performActionIfExists(Event event, Event.InitiatorStateAction stateAction) {
        if (stateAction == null) {
            return;
        }

        switch (stateAction) {
            case CANCEL_REVIEW:
                event.setState(Event.State.CANCELED);
                break;
            case SEND_TO_REVIEW:
                event.setState(Event.State.PENDING);
                break;
            default:
                String message = String.format("action %s not implemented", stateAction);
                throw new RuntimeException(message);
        }
    }

    private Event getEvent(AddEventRequestDto eventDto, Long initiatorId) {
        Category category = categoryRepository.getReferenceById(eventDto.getCategory());
        User initiator = userRepository.getReferenceById(initiatorId);
        return EventMapper.toEvent(eventDto, category, initiator);
    }

    private Comment getUpdatedComment(Long comId, UpdateCommentRequestDto commentDto) {
        Comment comment = commentRepository.getReferenceById(comId);

        comment.setText(commentDto.getText());

        return comment;
    }

    private Comment getComment(AddCommentRequestDto commentDto, Long userId, Long eventId) {
        User author = userRepository.getReferenceById(userId);
        Event event = eventRepository.getReferenceById(eventId);
        return CommentMapper.toComment(commentDto, author, event);
    }


    private Event getUpdatedEvent(Long eventId, UpdateEventUserRequestDto eventDto) {
        Event event = eventRepository.getReferenceById(eventId);

        if (eventDto.getCategory() != null) {
            categoryRepository.checkCategoryExistsById(eventDto.getCategory());
            event.setCategory(categoryRepository.getReferenceById(eventDto.getCategory()));
        }

        if (eventDto.getLocation() != null) {
            event.setLat(eventDto.getLocation().getLat());
            event.setLon(eventDto.getLocation().getLon());
        }

        Optional.ofNullable(eventDto.getAnnotation()).ifPresent(event::setAnnotation);
        Optional.ofNullable(eventDto.getDescription()).ifPresent(event::setDescription);
        Optional.ofNullable(eventDto.getEventDate()).ifPresent(event::setEventDate);
        Optional.ofNullable(eventDto.getPaid()).ifPresent(event::setPaid);
        Optional.ofNullable(eventDto.getParticipantLimit()).ifPresent(event::setParticipantLimit);
        Optional.ofNullable(eventDto.getRequestModeration()).ifPresent(event::setRequestModeration);

        return event;
    }

    private List<Participation> considerRequests(
            Long eventId,
            UpdateEventParticipationStatusRequestDto requestStatusDto
    ) {
        Event event = eventRepository.getReferenceById(eventId);
        int limit = event.getParticipantLimit();
        int confirmedRequests = requestRepository.getEventRequestsCount(event.getId(), Participation.Status.CONFIRMED);

        ParticipationValidator.validateLimitNotExceeded(1, confirmedRequests, limit);

        List<Participation> requests = requestRepository.findAllById(requestStatusDto.getRequestIds());
        if (requestStatusDto.getStatus() == Participation.Status.CONFIRMED) {
            for (Participation request : requests) {
                EventValidator.validateParticipationBeforeConfirmationOrRejection(request);

                // no limit
                if (event.getParticipantLimit() == 0) {
                    request.setStatus(Participation.Status.CONFIRMED);
                    continue;
                }

                if (limit - confirmedRequests > 0) {
                    request.setStatus(Participation.Status.CONFIRMED);
                    confirmedRequests++;
                } else {
                    request.setStatus(Participation.Status.REJECTED);
                }
            }
        }

        if (requestStatusDto.getStatus() == Participation.Status.REJECTED) {
            for (Participation request : requests) {
                EventValidator.validateParticipationBeforeConfirmationOrRejection(request);
                request.setStatus(Participation.Status.REJECTED);
            }
        }

        return requests;
    }
}
