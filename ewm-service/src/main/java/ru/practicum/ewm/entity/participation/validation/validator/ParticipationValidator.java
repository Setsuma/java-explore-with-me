package ru.practicum.ewm.entity.participation.validation.validator;

import lombok.experimental.UtilityClass;
import ru.practicum.ewm.entity.event.entity.Event;
import ru.practicum.ewm.exception.ConflictException;

@UtilityClass
public final class ParticipationValidator {

    public static void validateRequesterIsNotInitiator(Long requesterId, Long initiatorId) {
        if (initiatorId.equals(requesterId)) {
            throw new ConflictException("Initiator of the event cannot add a request to participate in his event");
        }
    }

    public static void validateEventPublished(Event.State state) {
        if (state != Event.State.PUBLISHED) {
            throw new ConflictException("It is forbidden to participate in an unpublished event");
        }
    }

    public static void validateLimitNotExceeded(
            Integer participants,
            Integer confirmedRequests,
            Integer participantLimit
    ) {
        if (participantLimit != 0 && (confirmedRequests + participants) > participantLimit) {
            throw new ConflictException("Event has reached the limit of participation requests");
        }
    }
}
