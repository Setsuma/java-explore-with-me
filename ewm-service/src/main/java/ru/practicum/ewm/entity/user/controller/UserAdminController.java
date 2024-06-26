package ru.practicum.ewm.entity.user.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.ewm.entity.user.dto.request.AddUserRequestDto;
import ru.practicum.ewm.entity.user.dto.response.UserResponseDto;
import ru.practicum.ewm.entity.user.service.UserAdminService;

import javax.validation.Valid;
import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
import java.util.Set;

@RestController
@RequestMapping("/admin/users")
@Validated
@Slf4j
@RequiredArgsConstructor
public class UserAdminController {
    private final UserAdminService userAdminService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public UserResponseDto addUser(@RequestBody @Valid AddUserRequestDto userDto) {
        log.info("ADD USER REQUEST: " + userDto);
        return userAdminService.addUser(userDto);
    }

    @GetMapping
    public Iterable<UserResponseDto> getUsersByIds(
            @RequestParam(required = false) Set<Long> ids,
            @RequestParam(defaultValue = "0") @PositiveOrZero Integer from,
            @RequestParam(defaultValue = "10") @Positive Integer size
    ) {
        log.info("GET USERS REQUEST: ids = {}, from = {}, size = {}", ids, from, size);
        return userAdminService.getUsersByIds(ids, from, size);
    }

    @DeleteMapping("/{userId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteUserById(
            @PathVariable Long userId
    ) {
        log.info("DELETE USER REQUEST: id = {}", userId);
        userAdminService.deleteUserById(userId);
    }
}
