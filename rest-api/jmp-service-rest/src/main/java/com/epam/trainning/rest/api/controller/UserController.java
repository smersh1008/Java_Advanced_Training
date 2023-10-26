package com.epam.trainning.rest.api.controller;

import com.epam.trainning.rest.api.dto.UserRequestDto;
import com.epam.trainning.rest.api.dto.UserResponseDto;
import com.epam.trainning.rest.api.model.User;
import com.epam.trainning.rest.api.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MimeTypeUtils;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@AllArgsConstructor
@Tag(name = "Users API")
@RestController
@RequestMapping("/api/v1/")
public class UserController {
    private final UserService userService;
    private final ModelMapper modelMapper;

    @ApiResponse(responseCode = "201", description = "User created")
    @PostMapping(value = "users", produces = MimeTypeUtils.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(description = "Create new user")
    public @ResponseBody ResponseEntity<?> createUser(@RequestBody final UserRequestDto dto) {
        final var user = modelMapper.map(dto, User.class);
        final var userResponseDto = modelMapper.map(userService.create(user), UserResponseDto.class);
        return ResponseEntity.created(ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                        .buildAndExpand(userResponseDto.getId())
                        .toUri())
                .body(userResponseDto);
    }

    @ApiResponse(responseCode = "200", description = "User is updated")
    @ApiResponse(responseCode = "404", description = "User is missing")
    @PutMapping(value = "users", produces = MimeTypeUtils.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    @Operation(description = "Update user")
    public @ResponseBody ResponseEntity<UserResponseDto> updateUser(@RequestBody final UserRequestDto dto) {
        final var user = modelMapper.map(dto, User.class);
        final var userResponseDto = modelMapper.map(userService.update(user), UserResponseDto.class);
        return ResponseEntity.ok(userResponseDto);
    }

    @ApiResponse(responseCode = "204", description = "User is deleted")
    @DeleteMapping(value = "users/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(description = "Delete user by id")
    public @ResponseBody ResponseEntity<?> deleteUser(@Schema(description = "User id") @PathVariable("id") final Long id) {
        userService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @ApiResponse(responseCode = "200", description = "User data")
    @ApiResponse(responseCode = "404", description = "User is missing by id")
    @ResponseStatus(HttpStatus.OK)
    @Operation(description = "Get user by id")
    @GetMapping(value = "users/{id}", produces = MimeTypeUtils.APPLICATION_JSON_VALUE)
    public @ResponseBody ResponseEntity<UserResponseDto> getUser(@Schema(description = "User id") @PathVariable("id") final Long id) {
        return ResponseEntity.ok(modelMapper.map(userService.getById(id), UserResponseDto.class));
    }

    @ApiResponse(responseCode = "200", description = "All users data")
    @Operation(description = "Get all users")
    @ResponseStatus(HttpStatus.OK)
    @GetMapping(value = "users", produces = MimeTypeUtils.APPLICATION_JSON_VALUE)
    public @ResponseBody ResponseEntity<List<UserResponseDto>> getUsers() {
        return ResponseEntity.ok(userService.getAll().stream()
                .map(user -> modelMapper.map(user, UserResponseDto.class))
                .map(userResponseDto -> userResponseDto.add(linkTo(methodOn(UserController.class)
                        .getUser(userResponseDto.getId()))
                        .withRel(userResponseDto.getId().toString())))
                .toList());
    }
}