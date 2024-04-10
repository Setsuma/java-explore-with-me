package ru.practicum.ewm.entity.event.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.ewm.entity.event.dto.request.AddEventRequestDto;
import ru.practicum.ewm.entity.event.dto.request.UpdateEventUserRequestDto;
import ru.practicum.ewm.entity.event.dto.request.comment.AddCommentRequestDto;
import ru.practicum.ewm.entity.event.dto.request.comment.UpdateCommentRequestDto;
import ru.practicum.ewm.entity.event.dto.response.EventFullResponseDto;
import ru.practicum.ewm.entity.event.dto.response.EventRequestsByStatusResponseDto;
import ru.practicum.ewm.entity.event.dto.response.EventShortResponseDto;
import ru.practicum.ewm.entity.event.dto.response.comment.CommentResponseDto;
import ru.practicum.ewm.entity.event.logging.EventControllerLoggerHelper;
import ru.practicum.ewm.entity.event.service.contoller.EventPrivateService;
import ru.practicum.ewm.entity.participation.dto.request.UpdateEventParticipationStatusRequestDto;
import ru.practicum.ewm.entity.participation.dto.response.ParticipationResponseDto;

import javax.validation.Valid;
import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;

@RestController
@RequestMapping("/users/{userId}/events")
@Validated
@Slf4j
@RequiredArgsConstructor
public class EventPrivateController {
    private final EventPrivateService privateEventService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public EventFullResponseDto addEvent(
            @PathVariable Long userId,
            @RequestBody @Valid AddEventRequestDto eventDto
    ) {
        EventControllerLoggerHelper.addEvent(log, eventDto);
        return privateEventService.addEvent(userId, eventDto);
    }

    @PostMapping("/{eventId}/comments")
    @ResponseStatus(HttpStatus.CREATED)
    public CommentResponseDto addComment(
            @PathVariable Long userId,
            @PathVariable Long eventId,
            @RequestBody @Valid AddCommentRequestDto commentDto
    ) {
        EventControllerLoggerHelper.addComment(log, userId, eventId, commentDto);
        return privateEventService.addComment(userId, eventId, commentDto);
    }

    @GetMapping("/{eventId}")
    public EventFullResponseDto getEventById(
            @PathVariable Long userId,
            @PathVariable Long eventId
    ) {
        EventControllerLoggerHelper.getEventById(log, userId, eventId);
        return privateEventService.getEventById(userId, eventId);
    }

    @GetMapping
    public Iterable<EventShortResponseDto> getUserEvents(
            @PathVariable Long userId,
            @RequestParam(defaultValue = "0") @PositiveOrZero Integer from,
            @RequestParam(defaultValue = "10") @Positive Integer size
    ) {
        EventControllerLoggerHelper.getUserEventDtoPage(log, userId, from, size);
        return privateEventService.getUserEvents(userId, from, size);
    }

    @GetMapping("/{eventId}/requests")
    public Iterable<ParticipationResponseDto> getEventParticipationRequests(
            @PathVariable Long userId,
            @PathVariable Long eventId,
            @RequestParam(defaultValue = "0") @PositiveOrZero Integer from,
            @RequestParam(defaultValue = "10") @Positive Integer size
    ) {
        EventControllerLoggerHelper.getEventParticipationRequests(log, userId, eventId, from, size);
        return privateEventService.getEventParticipationRequests(userId, eventId, from, size);
    }

    @PatchMapping("/{eventId}")
    public EventFullResponseDto updateUserEventById(
            @PathVariable Long userId,
            @PathVariable Long eventId,
            @RequestBody @Valid UpdateEventUserRequestDto eventDto
    ) {
        EventControllerLoggerHelper.userUpdateEventById(log, userId, eventId, eventDto);
        return privateEventService.updateEventById(userId, eventId, eventDto);
    }

    @PatchMapping("/{eventId}/requests")
    public EventRequestsByStatusResponseDto updateEventParticipationRequestStatus(
            @PathVariable Long userId,
            @PathVariable Long eventId,
            @RequestBody @Valid UpdateEventParticipationStatusRequestDto requestStatusDto
    ) {
        EventControllerLoggerHelper.updateEventRequestStatus(log, userId, eventId, requestStatusDto);
        return privateEventService.updateEventParticipationRequestStatus(userId, eventId, requestStatusDto);
    }

    @PatchMapping("/{eventId}/comments/{comId}")
    public CommentResponseDto updateCommentById(
            @PathVariable Long userId,
            @PathVariable Long eventId,
            @PathVariable Long comId,
            @RequestBody @Valid UpdateCommentRequestDto commentDto
    ) {
        EventControllerLoggerHelper.updateCommentById(log, userId, eventId, comId, commentDto);
        return privateEventService.updateCommentById(userId, eventId, comId, commentDto);
    }

    @DeleteMapping("/{eventId}/comments/{comId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteCommentById(
            @PathVariable Long userId,
            @PathVariable Long eventId,
            @PathVariable Long comId
    ) {
        EventControllerLoggerHelper.deleteCommentById(log, userId, eventId, comId);
        privateEventService.deleteCommentById(userId, eventId, comId);
    }
}
