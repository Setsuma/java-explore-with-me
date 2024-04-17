package ru.practicum.ewm.entity.event.comment.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
@NoArgsConstructor
public class AddCommentRequestDto {
    @Size(min = 5, max = 255)
    @NotBlank
    private String text;
}