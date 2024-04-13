package ru.practicum.ewm.entity.event.logging;

import lombok.experimental.UtilityClass;
import org.slf4j.Logger;
import ru.practicum.ewm.entity.event.dto.response.EventFullResponseDto;
import ru.practicum.ewm.entity.event.dto.response.EventShortResponseDto;
import ru.practicum.ewm.entity.event.entity.Event;
import ru.practicum.ewm.entity.participation.dto.request.UpdateEventParticipationStatusRequestDto;
import ru.practicum.ewm.entity.participation.dto.response.ParticipationResponseDto;

import java.util.List;

@UtilityClass
public final class EventServiceLoggerHelper {

    public static void eventSaved(
            Logger logger,
            Event event
    ) {
        logger.info("EVENT["
                        + "id={}, "
                        + "initiator_id={}, "
                        + "title='{}', "
                        + "event_date={}, "
                        + "moderation = {}] saved.",
                event.getId(),
                event.getInitiator().getId(),
                event.getTitle(),
                event.getEventDate(),
                event.getRequestModeration());
    }

    public static void eventDtoReturned(
            Logger logger,
            EventFullResponseDto eventDto
    ) {
        logger.debug("EVENT<DTO>["
                        + "id={}, "
                        + "title='{}'"
                        + "] returned.",
                eventDto.getId(),
                eventDto.getTitle());
    }

    public static void eventDtoPageByUserParametersReturned(
            Logger logger,
            List<EventShortResponseDto> eventDtos,
            Integer from,
            Integer size,
            Event.Sort sort
    ) {
        logger.debug("EVENT_PAGE<DTO>["
                        + "events_count={}, "
                        + "from={}, "
                        + "size={}, "
                        + "sort_by={}"
                        + "] by parameters returned.",
                eventDtos.size(),
                from,
                size,
                sort);
    }

    public static void eventDtoPageByAdminParametersReturned(
            Logger logger,
            Integer from,
            Integer size,
            List<EventFullResponseDto> eventDtos
    ) {
        logger.debug("EVENT_PAGE<DTO>["
                        + "from={}, "
                        + "size={}, "
                        + "events_count={}"
                        + "] by parameters returned.",
                from,
                size,
                eventDtos.size());
    }

    public static void eventDtoPageByUserReturned(
            Logger logger,
            Long userId,
            Integer from,
            Integer size,
            List<EventShortResponseDto> eventDtos
    ) {
        logger.debug("EVENT_PAGE<DTO>["
                        + "initiator_id={}, "
                        + "from={}, "
                        + "size={}, "
                        + "events_count={}"
                        + "] by user returned.",
                userId,
                from,
                size,
                eventDtos.size());
    }

    public static void requestDtoPageByEventReturned(
            Logger logger,
            Long userId,
            Long eventId,
            List<ParticipationResponseDto> requestDtos,
            Integer from,
            Integer size
    ) {
        logger.debug("PARTICIPATION_REQUESTS_PAGE<DTO>["
                        + "initiator_id={}, "
                        + "event_id={}, "
                        + "requests_count={}, "
                        + "from={}, "
                        + "size={}"
                        + "] by event returned.",
                userId,
                eventId,
                requestDtos.size(),
                from,
                size);
    }

    public static void eventUpdatedByAdmin(
            Logger logger,
            Event event
    ) {
        logger.info("EVENT["
                        + "id={}, "
                        + "initiator_id={}, "
                        + "title='{}', "
                        + "moderation = {}] updated by admin.",
                event.getId(),
                event.getInitiator().getId(),
                event.getTitle(),
                event.getRequestModeration());
    }

    public static void eventUpdatedByUser(
            Logger logger,
            Event event
    ) {
        logger.info("EVENT["
                        + "id={}, "
                        + "initiator_id={}, "
                        + "title='{}', "
                        + "moderation = {}] updated by user.",
                event.getId(),
                event.getInitiator().getId(),
                event.getTitle(),
                event.getRequestModeration());
    }

    public static void requestsUpdated(
            Logger logger,
            UpdateEventParticipationStatusRequestDto requestStatusDto
    ) {
        logger.debug("EVENT_REQUESTS["
                        + "request_ids_count={}, "
                        + "status='{}'"
                        + "] updated.",
                requestStatusDto.getRequestIds().size(),
                requestStatusDto.getStatus());
    }
}
