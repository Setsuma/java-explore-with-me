package ru.practicum.ewm.entity.event.comment.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import lombok.NoArgsConstructor;

import static com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonInclude(Include.NON_NULL)
@Data
@NoArgsConstructor
public class CommentResponseDto {
    private Long id;
    private String text;
    private Long authorId;
    private Long eventId;
}