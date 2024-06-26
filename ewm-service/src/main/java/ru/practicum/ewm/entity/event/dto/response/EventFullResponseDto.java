package ru.practicum.ewm.entity.event.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.practicum.ewm.entity.category.entity.Category;
import ru.practicum.ewm.entity.event.entity.Event;
import ru.practicum.ewm.entity.user.entity.User;

import java.time.LocalDateTime;

@JsonInclude(Include.NON_NULL)
@Data
@NoArgsConstructor
public class EventFullResponseDto {
    private Long id;
    private String title;
    private String annotation;
    private CategoryDto category;
    private Boolean paid;
    private LocalDateTime eventDate;
    private UserShortDto initiator;
    private Long views;
    private Integer confirmedRequests;
    private String description;
    private Integer participantLimit;
    private Event.State state;
    private LocalDateTime createdOn;
    private LocalDateTime publishedOn;
    private Location location;
    private Boolean requestModeration;

    @JsonInclude(Include.NON_NULL)
    @Data
    @NoArgsConstructor
    public static class CategoryDto {
        private Long id;
        private String name;

        public static CategoryDto fromCategory(Category category) {
            CategoryDto categoryDto = new CategoryDto();

            categoryDto.setId(category.getId());
            categoryDto.setName(category.getName());

            return categoryDto;
        }
    }

    @JsonInclude(Include.NON_NULL)
    @Data
    @NoArgsConstructor
    public static class UserShortDto {
        private Long id;
        private String name;

        public static UserShortDto fromUser(User user) {
            UserShortDto userDto = new UserShortDto();

            userDto.setId(user.getId());
            userDto.setName(user.getName());

            return userDto;
        }
    }

    @JsonInclude(Include.NON_NULL)
    @Data
    @NoArgsConstructor
    public static class Location {
        private Float lat;
        private Float lon;

        public static Location fromEvent(Event event) {
            Location location = new Location();

            location.setLat(event.getLat());
            location.setLon(event.getLon());

            return location;
        }
    }
}
