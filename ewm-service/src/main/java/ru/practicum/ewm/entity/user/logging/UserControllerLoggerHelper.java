package ru.practicum.ewm.entity.user.logging;

import lombok.experimental.UtilityClass;
import org.slf4j.Logger;
import ru.practicum.ewm.entity.user.dto.request.AddUserRequestDto;

import java.util.Set;

@UtilityClass
public final class UserControllerLoggerHelper {

    public static void addUser(
            Logger logger,
            AddUserRequestDto userDto
    ) {
        logger.info("add USER["
                        + "name='{}', "
                        + "email='{}'"
                        + "].",
                userDto.getName(),
                userDto.getEmail());
    }

    public static void getUserDtoPageByIds(
            Logger logger,
            Integer from,
            Integer size,
            Set<Long> ids
    ) {
        logger.info("get USER_PAGE<DTO>["
                        + "from={}, "
                        + "size={}, "
                        + "ids_count={}"
                        + "].",
                from,
                size,
                (ids != null) ? ids.size() : "null");
    }

    public static void deleteUserById(
            Logger logger,
            Long userId
    ) {
        logger.info("delete USER["
                + "id={}"
                + "].", userId);
    }
}
