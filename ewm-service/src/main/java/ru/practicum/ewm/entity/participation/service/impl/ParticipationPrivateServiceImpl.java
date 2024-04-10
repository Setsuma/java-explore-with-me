package ru.practicum.ewm.entity.participation.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.ewm.entity.event.entity.Event;
import ru.practicum.ewm.entity.event.repository.EventJpaRepository;
import ru.practicum.ewm.entity.participation.dto.response.ParticipationResponseDto;
import ru.practicum.ewm.entity.participation.entity.Participation;
import ru.practicum.ewm.entity.participation.logging.ParticipationServiceLoggingHelper;
import ru.practicum.ewm.entity.participation.mapper.ParticipationMapper;
import ru.practicum.ewm.entity.participation.repository.jpa.ParticipationRequestJpaRepository;
import ru.practicum.ewm.entity.participation.service.ParticipationPrivateService;
import ru.practicum.ewm.entity.participation.validation.validator.ParticipationValidator;
import ru.practicum.ewm.entity.user.entity.User;
import ru.practicum.ewm.entity.user.repository.UserJpaRepository;
import ru.practicum.ewm.exception.ConflictException;

import java.util.List;

@Service
@Transactional(readOnly = true)
@Slf4j
@RequiredArgsConstructor
public class ParticipationPrivateServiceImpl implements ParticipationPrivateService {
    private final ParticipationRequestJpaRepository requestRepository;
    private final UserJpaRepository userRepository;
    private final EventJpaRepository eventRepository;

    @Override
    @Transactional
    public ParticipationResponseDto addRequest(Long requesterId, Long eventId) {
        userRepository.checkUserExistsById(requesterId);
        eventRepository.checkEventExistsById(eventId);
        checkRequest(requesterId, eventId);
        Participation request = getParticipationRequest(requesterId, eventId);
        Participation savedRequest = requestRepository.save(request);
        ParticipationServiceLoggingHelper.participationSaved(log, savedRequest);
        return ParticipationMapper.toParticipationResponseDto(savedRequest);
    }

    @Override
    public Iterable<ParticipationResponseDto> getRequestsByRequesterId(Long userId) {
        userRepository.checkUserExistsById(userId);
        List<Participation> userRequests = requestRepository.findAllByRequesterId(userId);
        var userRequestDtos = ParticipationMapper.toParticipationResponseDto(userRequests);
        ParticipationServiceLoggingHelper.participationDtoPageByRequesterIdReturned(log, userId, userRequestDtos);
        return userRequestDtos;
    }

    @Override
    @Transactional
    public ParticipationResponseDto cancelRequestById(Long userId, Long requestId) {
        userRepository.checkUserExistsById(userId);
        requestRepository.checkParticipationExistsById(requestId);
        Participation canceledRequest = cancelRequest(requestId);
        Participation savedRequest = requestRepository.save(canceledRequest);
        ParticipationServiceLoggingHelper.participationCanceled(log, savedRequest);
        return ParticipationMapper.toParticipationResponseDto(savedRequest);
    }

    private Participation cancelRequest(Long requestId) {
        Participation request = requestRepository.getReferenceById(requestId);

        request.setStatus(Participation.Status.CANCELED);

        return request;
    }

    private Participation getParticipationRequest(Long userId, Long eventId) {
        Participation request = new Participation();

        User requester = userRepository.getReferenceById(userId);
        request.setRequester(requester);

        Event event = eventRepository.getReferenceById(eventId);
        request.setEvent(event);

        if (requestRepository.existsByEventIdAndRequesterId(eventId, userId)) {
            throw new ConflictException("Request already exists");
        }

        if (event.getRequestModeration() == Boolean.FALSE) {
            request.setStatus(Participation.Status.CONFIRMED);
        }

        return request;
    }

    private void checkRequest(Long userId, Long eventId) {
        User requester = userRepository.getReferenceById(userId);
        Event event = eventRepository.getReferenceById(eventId);
        Integer confirmedRequests = requestRepository.getEventRequestsCount(eventId, Participation.Status.CONFIRMED);

        ParticipationValidator.validateRequesterIsNotInitiator(requester.getId(), event.getInitiator().getId());
        ParticipationValidator.validateEventPublished(event.getState());
        ParticipationValidator.validateLimitNotExceeded(1, confirmedRequests, event.getParticipantLimit());
    }
}
