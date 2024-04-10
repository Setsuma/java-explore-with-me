package ru.practicum.ewm.entity.participation.dto.request;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.practicum.ewm.entity.participation.entity.Participation;

import javax.validation.constraints.NotNull;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
public class UpdateEventParticipationStatusRequestDto {
    @NotNull
    private Set<Long> requestIds;

    @NotNull
    private Participation.Status status;
}
