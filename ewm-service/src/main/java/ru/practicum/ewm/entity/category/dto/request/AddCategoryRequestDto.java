package ru.practicum.ewm.entity.category.dto.request;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Getter
@Setter
@NoArgsConstructor
public class AddCategoryRequestDto {
    @NotBlank
    @Size(min = 3, max = 120)
    private String name;
}
