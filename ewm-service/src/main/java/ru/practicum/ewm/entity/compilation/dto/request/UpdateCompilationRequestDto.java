package ru.practicum.ewm.entity.compilation.dto.request;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Size;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
public class UpdateCompilationRequestDto {
    private Set<Long> events;

    private Boolean pinned;

    @Size(min = 1, max = 50)
    private String title;
}
