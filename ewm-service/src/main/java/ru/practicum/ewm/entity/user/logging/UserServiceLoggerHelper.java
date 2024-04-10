package ru.practicum.ewm.entity.user.logging;

import lombok.experimental.UtilityClass;
import org.slf4j.Logger;
import ru.practicum.ewm.entity.user.dto.response.UserResponseDto;
import ru.practicum.ewm.entity.user.entity.User;

import java.util.Collection;

@UtilityClass
public final class UserServiceLoggerHelper {

    public static void userSaved(
            Logger logger,
            User user
    ) {
        logger.debug("USER["
                        + "id={}, "
                        + "name='{}', "
                        + "email='{}'"
                        + "] saved.",
                user.getId(),
                user.getName(),
                user.getEmail());
    }

    public static void userDtoPageReturned(
            Logger logger,
            Integer from,
            Integer size,
            Collection<UserResponseDto> userDtos
    ) {
        logger.debug("USER_PAGE<DTO>["
                        + "from={}, "
                        + "size={}, "
                        + "users_count={}"
                        + "] returned.",
                from,
                size,
                userDtos.size());
    }

    public static void userDeleted(
            Logger logger,
            Long userId
    ) {
        logger.debug("USER["
                        + "id={}"
                        + "] deleted.",
                userId);
    }
}
