package ru.practicum.ewm.entity.user.dto.request;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
@NoArgsConstructor
public class AddUserRequestDto {
    @NotBlank
    private String name;

    @NotBlank
    private String email;
}
