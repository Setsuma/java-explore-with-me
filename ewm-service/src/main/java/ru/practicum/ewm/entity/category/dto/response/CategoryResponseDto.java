package ru.practicum.ewm.entity.category.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import static com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonInclude(Include.NON_NULL)
@Getter
@Setter
@NoArgsConstructor
public class CategoryResponseDto {
    private Long id;
    private String name;
}
