package ru.practicum.ewm.entity.participation.logging;

import lombok.experimental.UtilityClass;
import org.slf4j.Logger;
import ru.practicum.ewm.entity.participation.dto.response.ParticipationResponseDto;
import ru.practicum.ewm.entity.participation.entity.Participation;

import java.util.List;

@UtilityClass
@SuppressWarnings("java:S1192")
public final class ParticipationServiceLoggingHelper {

    public static void participationSaved(
            Logger logger,
            Participation participation
    ) {
        logger.debug("PARTICIPATION_REQUEST["
                        + "id={}, "
                        + "event_id={}, "
                        + "requester_id={}, "
                        + "status='{}'"
                        + "] saved.",
                participation.getId(),
                participation.getEvent().getId(),
                participation.getRequester().getId(),
                participation.getStatus());
    }

    public static void participationDtoPageByRequesterIdReturned(
            Logger logger,
            Long requesterId,
            List<ParticipationResponseDto> userRequestDtos
    ) {
        logger.debug("PARTICIPATION_REQUEST_PAGE_BY_REQUESTER_ID<DTO>["
                        + "requester_id={}, "
                        + "size={}"
                        + "] returned.",
                requesterId,
                userRequestDtos.size());
    }

    public static void participationCanceled(
            Logger logger,
            Participation request
    ) {
        logger.debug("PARTICIPATION_REQUEST["
                        + "id={}, "
                        + "requester_id={}, "
                        + "event_id={}"
                        + "] canceled.",
                request.getId(),
                request.getRequester().getId(),
                request.getEvent().getId());
    }
}
