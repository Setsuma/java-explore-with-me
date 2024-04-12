package ru.practicum.ewm.entity.compilation.dto.request;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.Collections;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
public class AddCompilationRequestDto {
    private Set<Long> events = Collections.emptySet();

    private Boolean pinned;

    @NotBlank
    @Size(min = 1, max = 50)
    private String title;
}
