package ru.practicum.ewm.entity.event.logging;

import lombok.experimental.UtilityClass;
import org.slf4j.Logger;
import ru.practicum.ewm.entity.event.dto.response.EventFullResponseDto;
import ru.practicum.ewm.entity.event.dto.response.EventShortResponseDto;
import ru.practicum.ewm.entity.event.dto.response.comment.CommentResponseDto;
import ru.practicum.ewm.entity.event.entity.Event;
import ru.practicum.ewm.entity.event.entity.comment.Comment;
import ru.practicum.ewm.entity.participation.dto.request.UpdateEventParticipationStatusRequestDto;
import ru.practicum.ewm.entity.participation.dto.response.ParticipationResponseDto;

import java.util.List;

@UtilityClass
@SuppressWarnings("java:S1192")
public final class EventServiceLoggerHelper {

    public static void eventSaved(
            Logger logger,
            Event event
    ) {
        logger.debug("EVENT["
                        + "id={}, "
                        + "initiator_id={}, "
                        + "title='{}', "
                        + "event_date={}"
                        + "] saved.",
                event.getId(),
                event.getInitiator().getId(),
                event.getTitle(),
                event.getEventDate());
    }

    public static void commentSaved(
            Logger logger,
            Comment comment
    ) {
        logger.debug("COMMENT["
                        + "id={}, "
                        + "author_id={}, "
                        + "event_id={}, "
                        + "text='{}'"
                        + "] saved.",
                comment.getId(),
                comment.getAuthor().getId(),
                comment.getEvent().getId(),
                comment.getText());
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
        logger.debug("EVENT["
                        + "id={}, "
                        + "initiator_id={}, "
                        + "title='{}'"
                        + "] updated by admin.",
                event.getId(),
                event.getInitiator().getId(),
                event.getTitle());
    }

    public static void eventUpdatedByUser(
            Logger logger,
            Event event
    ) {
        logger.debug("EVENT["
                        + "id={}, "
                        + "initiator_id={}, "
                        + "title='{}'] updated by user.",
                event.getId(),
                event.getInitiator().getId(),
                event.getTitle());
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

    public static void commentDtoReturned(
            Logger logger,
            CommentResponseDto commentDto
    ) {
        logger.debug("COMMENT<DTO>["
                        + "comment_id={}, "
                        + "author_id={}, "
                        + "event_id={}, "
                        + "text='{}'"
                        + "] returned.",
                commentDto.getId(),
                commentDto.getAuthorId(),
                commentDto.getEventId(),
                commentDto.getText());
    }

    public static void commentDtoPageReturned(
            Logger logger,
            Integer from,
            Integer size,
            List<CommentResponseDto> commentDtos
    ) {
        logger.debug("COMMENT_PAGE<DTO>["
                        + "from={}, "
                        + "size={}, "
                        + "comments_count={}"
                        + "] returned.",
                from,
                size,
                commentDtos.size());
    }

    public static void commentUpdated(
            Logger logger,
            Comment comment
    ) {
        logger.debug("COMMENT["
                        + "comment_id={}, "
                        + "updated_text='{}'"
                        + "] updated.",
                comment.getId(),
                comment.getText());
    }

    public static void commentDeleted(
            Logger logger,
            Long comId,
            Long userId,
            Long eventId
    ) {
        logger.debug("COMMENT["
                        + "comment_id={}, "
                        + "author_id={}, "
                        + "event_id={}"
                        + "] deleted.",
                comId,
                userId,
                eventId);
    }
}
