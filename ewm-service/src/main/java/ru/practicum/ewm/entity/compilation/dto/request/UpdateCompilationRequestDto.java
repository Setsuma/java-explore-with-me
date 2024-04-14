package ru.practicum.ewm.entity.compilation.dto.request;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Size;
import java.util.Collections;
import java.util.Set;

@Data
@NoArgsConstructor
public class UpdateCompilationRequestDto {
    private Set<Long> events = Collections.emptySet();

    private Boolean pinned;

    @Size(min = 1, max = 50)
    private String title;
}
