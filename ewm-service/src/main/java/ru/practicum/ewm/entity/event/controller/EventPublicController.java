package ru.practicum.ewm.entity.event.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.ewm.entity.event.comment.dto.CommentResponseDto;
import ru.practicum.ewm.entity.event.dto.response.EventFullResponseDto;
import ru.practicum.ewm.entity.event.dto.response.EventShortResponseDto;
import ru.practicum.ewm.entity.event.entity.Event;
import ru.practicum.ewm.entity.event.service.contoller.EventPublicService;

import javax.servlet.http.HttpServletRequest;
import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
import java.time.LocalDateTime;
import java.util.Set;

@RestController
@RequestMapping("/events")
@Validated
@Slf4j
@RequiredArgsConstructor
public class EventPublicController {
    private final EventPublicService eventPublicService;

    @GetMapping("/{id}")
    public EventFullResponseDto getEventById(
            @PathVariable Long id,
            HttpServletRequest request
    ) {
        log.info("GET EVENT BY ID: eventID = {}", id);
        return eventPublicService.getEventById(id, request);
    }

    @GetMapping("/{id}/comments/{comId}")
    public CommentResponseDto getCommentById(
            @PathVariable Long id,
            @PathVariable Long comId
    ) {
        log.info("GET EVENT COMMENT BY ID: id = {}, comId = {}", id, comId);
        return eventPublicService.getCommentById(id, comId);
    }

    @GetMapping
    public Iterable<EventShortResponseDto> getEventsByParameters(
            @RequestParam(required = false) String text,
            @RequestParam(required = false) Set<Long> categories,
            @RequestParam(required = false) Boolean paid,
            @RequestParam(required = false) LocalDateTime rangeStart,
            @RequestParam(required = false) LocalDateTime rangeEnd,
            @RequestParam(defaultValue = "false") Boolean onlyAvailable,
            @RequestParam(required = false) Event.Sort sort,
            @RequestParam(defaultValue = "0") @PositiveOrZero Integer from,
            @RequestParam(defaultValue = "10") @Positive Integer size,
            HttpServletRequest request
    ) {
        log.info("GET EVENTS BY PARAMETERS PUBLIC REQUEST: text = {}, " + categories + ", paid = {}, " +
                        "start = {}, end = {}, onlyAvailable = {}, sort = {}, from = {}, size = {}",
                text, paid, rangeStart, rangeEnd, onlyAvailable, sort, from, size);
        return eventPublicService.searchEventsByParameters(
                text, categories, paid, rangeStart, rangeEnd, onlyAvailable, sort, from, size, request);
    }

    @GetMapping("/{id}/comments")
    public Iterable<CommentResponseDto> getEventComments(
            @PathVariable Long id,
            @RequestParam(defaultValue = "0") @PositiveOrZero Integer from,
            @RequestParam(defaultValue = "10") @Positive Integer size
    ) {
        log.info("GET EVENT COMMENTS: id = {}, from = {}, size = {}", id, from, size);
        return eventPublicService.getComments(id, from, size);
    }
}
