package ru.practicum.ewm.entity.user.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.ewm.entity.user.dto.request.AddUserRequestDto;
import ru.practicum.ewm.entity.user.dto.response.UserResponseDto;
import ru.practicum.ewm.entity.user.entity.User;
import ru.practicum.ewm.entity.user.logging.UserServiceLoggerHelper;
import ru.practicum.ewm.entity.user.mapper.UserMapper;
import ru.practicum.ewm.entity.user.repository.UserJpaRepository;
import ru.practicum.ewm.entity.user.service.UserAdminService;

import java.util.List;
import java.util.Set;

@Service
@Transactional(readOnly = true)
@Slf4j
@RequiredArgsConstructor
public class UserAdminServiceImpl implements UserAdminService {
    private final UserJpaRepository userRepository;

    @Override
    @Transactional
    public UserResponseDto addUser(AddUserRequestDto userDto) {
        User user = UserMapper.toUser(userDto);
        User savedUser = userRepository.save(user);
        UserServiceLoggerHelper.userSaved(log, savedUser);
        return UserMapper.toUserResponseDto(savedUser);
    }

    @Override
    public List<UserResponseDto> getUsersByIds(Set<Long> ids, Integer from, Integer size) {
        Iterable<User> users;
        if (ids != null) {
            users = userRepository.findAllById(ids);
        } else {
            users = userRepository.findAll(PageRequest.of(from, size));
        }
        List<UserResponseDto> userDtos = UserMapper.toUserResponseDto(users);
        UserServiceLoggerHelper.userDtoPageReturned(log, from, size, userDtos);
        return userDtos;
    }

    @Override
    @Transactional
    public void deleteUserById(Long userId) {
        userRepository.checkUserExistsById(userId);
        userRepository.deleteById(userId);
        UserServiceLoggerHelper.userDeleted(log, userId);
    }
}
