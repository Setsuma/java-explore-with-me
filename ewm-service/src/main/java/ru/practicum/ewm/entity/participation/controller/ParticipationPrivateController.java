package ru.practicum.ewm.entity.participation.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.practicum.ewm.entity.participation.dto.response.ParticipationResponseDto;
import ru.practicum.ewm.entity.participation.logging.ParticipationControllerLoggerHelper;
import ru.practicum.ewm.entity.participation.service.ParticipationPrivateService;

@RestController
@RequestMapping("/users/{userId}/requests")
@Slf4j
@RequiredArgsConstructor
public class ParticipationPrivateController {
    private final ParticipationPrivateService participationRequestService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ParticipationResponseDto addParticipationRequest(
            @PathVariable Long userId,
            @RequestParam Long eventId
    ) {
        ParticipationControllerLoggerHelper.addRequest(log, userId, eventId);
        return participationRequestService.addRequest(userId, eventId);
    }

    @GetMapping
    public Iterable<ParticipationResponseDto> getUserParticipationRequests(
            @PathVariable Long userId
    ) {
        ParticipationControllerLoggerHelper.getUserParticipationRequestDtos(log, userId);
        return participationRequestService.getRequestsByRequesterId(userId);
    }

    @PatchMapping("/{requestId}/cancel")
    public ParticipationResponseDto cancelUserRequest(
            @PathVariable Long userId,
            @PathVariable Long requestId
    ) {
        ParticipationControllerLoggerHelper.cancelRequest(log, userId, requestId);
        return participationRequestService.cancelRequestById(userId, requestId);
    }
}
