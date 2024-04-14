package ru.practicum.ewm.entity.event.dto.request;

import lombok.Data;
import lombok.NoArgsConstructor;
import ru.practicum.ewm.entity.event.entity.Event;

import javax.validation.constraints.PositiveOrZero;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
public class UpdateEventUserRequestDto {
    @Size(min = 20, max = 2_000)
    private String annotation;

    private Long category;

    @Size(min = 20, max = 7_000)
    private String description;

    private LocalDateTime eventDate;
    private Location location;
    private Boolean paid;
    @PositiveOrZero
    private Integer participantLimit;
    private Boolean requestModeration = null;
    private Event.InitiatorStateAction stateAction;

    @Size(min = 3, max = 120)
    private String title;

    @Data
    @NoArgsConstructor
    public static class Location {
        private Float lat;
        private Float lon;
    }
}
