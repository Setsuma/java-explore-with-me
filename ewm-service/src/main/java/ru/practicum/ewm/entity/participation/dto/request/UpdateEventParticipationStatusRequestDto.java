package ru.practicum.ewm.entity.participation.dto.request;

import lombok.Data;
import lombok.NoArgsConstructor;
import ru.practicum.ewm.entity.participation.entity.Participation;

import javax.validation.constraints.NotNull;
import java.util.Set;

@Data
@NoArgsConstructor
public class UpdateEventParticipationStatusRequestDto {
    @NotNull
    private Set<Long> requestIds;

    @NotNull
    private Participation.Status status;
}
