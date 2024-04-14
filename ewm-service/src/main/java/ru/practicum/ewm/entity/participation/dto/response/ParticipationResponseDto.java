package ru.practicum.ewm.entity.participation.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.practicum.ewm.entity.participation.entity.Participation;

import java.time.LocalDateTime;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
@NoArgsConstructor
public class ParticipationResponseDto {
    private Long id;
    private Long requester;
    private Long event;
    private Participation.Status status;
    private LocalDateTime created;
}
