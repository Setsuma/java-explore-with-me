package ru.practicum.hit.server.endpointhit.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.practicum.hit.dto.EndpointHitDto;
import ru.practicum.hit.dto.ViewStatsDto;
import ru.practicum.hit.dto.exception.DateException;
import ru.practicum.hit.server.endpointhit.service.EndpointHitService;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class EndpointHitController {
    private final EndpointHitService endpointHitService;

    @PostMapping("/hit")
    @ResponseStatus(HttpStatus.CREATED)
    public EndpointHitDto addEndpointHit(@RequestBody EndpointHitDto endpointHitDto) {
        return endpointHitService.addEndpointHit(endpointHitDto);
    }

    @GetMapping("/stats")
    @ResponseStatus(HttpStatus.OK)
    public List<ViewStatsDto> getStats(
            @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime start,
            @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime end,
            @RequestParam(defaultValue = "") List<String> uris,
            @RequestParam(defaultValue = "false") Boolean unique) {

        if (start.isEqual(end) || start.isAfter(end)) throw new DateException("date exception");

        List<String> uri = null;
        for (String u : uris) if (!u.isBlank()) uri = uris;
        return endpointHitService.getStats(start, end, uri, unique);
    }

    @ExceptionHandler(DateException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public DateException handleDateException(final Exception exception) {
        return new DateException(exception.getMessage());
    }
}
