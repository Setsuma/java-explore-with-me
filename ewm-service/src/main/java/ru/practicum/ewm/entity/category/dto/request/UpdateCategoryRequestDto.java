package ru.practicum.ewm.entity.category.dto.request;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
@NoArgsConstructor
public class UpdateCategoryRequestDto {
    @NotBlank
    @Size(min = 1, max = 50)
    private String name;
}
