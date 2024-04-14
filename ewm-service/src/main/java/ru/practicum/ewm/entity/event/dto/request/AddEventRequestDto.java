package ru.practicum.ewm.entity.event.dto.request;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;

@Data
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

    private Boolean paid = false;
    @PositiveOrZero
    private Integer participantLimit = 0;
    private Boolean requestModeration = Boolean.TRUE;

    @NotBlank
    @Size(min = 3, max = 120)
    private String title;

    @Data
    @NoArgsConstructor
    public static class Location {
        private Float lat;
        private Float lon;
    }
}
