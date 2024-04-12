package ru.practicum.ewm.entity.category.dto.request;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Getter
@Setter
@NoArgsConstructor
public class UpdateCategoryRequestDto {
    @NotBlank
    @Size(min = 1, max = 50)
    private String name;
}
