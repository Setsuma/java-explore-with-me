package ru.practicum.ewm.entity.event.dto.request;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.boot.context.properties.bind.DefaultValue;

import javax.validation.constraints.*;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
public class AddEventRequestDto {
    @NotBlank
    @Size(min = 20, max = 2_000)
    private String annotation;

    @NotNull
    private Long category;

    @NotBlank
    @Size(min = 20, max = 7_000)
    private String description;

    @NotNull
    private LocalDateTime eventDate;

    @NotNull
    private Location location;

    private Boolean paid;
    @PositiveOrZero
    private Integer participantLimit = 0;
    private Boolean requestModeration;

    @NotBlank
    @Size(min = 3, max = 120)
    private String title;

    @Getter
    @Setter
    @NoArgsConstructor
    public static class Location {
        private Float lat;
        private Float lon;
    }
}
