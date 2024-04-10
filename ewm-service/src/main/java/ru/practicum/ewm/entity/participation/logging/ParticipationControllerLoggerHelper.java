package ru.practicum.ewm.entity.participation.logging;

import lombok.experimental.UtilityClass;
import org.slf4j.Logger;

@UtilityClass
public final class ParticipationControllerLoggerHelper {

    public static void addRequest(
            Logger logger,
            Long userId,
            Long eventId
    ) {
        logger.info("add PARTICIPATION_REQUEST["
                        + "requester_id={}, "
                        + "event_id={}"
                        + "].",
                userId,
                eventId);
    }

    public static void getUserParticipationRequestDtos(
            Logger logger,
            Long userId
    ) {
        logger.info("get PARTICIPATION_REQUESTS<DTO>["
                        + "initiator_id={}"
                        + "] by initiator.",
                userId);
    }

    public static void cancelRequest(
            Logger logger,
            Long userId,
            Long requestId
    ) {
        logger.info("cancel(update) PARTICIPATION_REQUEST["
                        + "requester_id={}, "
                        + "request_id={}"
                        + "].",
                userId,
                requestId);
    }
}
