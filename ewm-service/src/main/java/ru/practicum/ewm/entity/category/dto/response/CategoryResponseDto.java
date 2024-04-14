package ru.practicum.ewm.entity.category.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import lombok.NoArgsConstructor;

import static com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonInclude(Include.NON_NULL)
@Data
@NoArgsConstructor
public class CategoryResponseDto {
    private Long id;
    private String name;
}
