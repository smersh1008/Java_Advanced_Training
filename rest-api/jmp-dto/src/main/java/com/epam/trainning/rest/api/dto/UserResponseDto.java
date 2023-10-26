package com.epam.trainning.rest.api.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;
import org.springframework.hateoas.RepresentationModel;

@Getter
@Setter
public class UserResponseDto extends RepresentationModel<UserResponseDto> {
    @Schema(title = "User id", example = "1")
    private Long id;
    @Schema(title = "User name", example = "Bob")
    private String name;
    @Schema(title = "User surname", example = "Bobber")
    private String surname;
    @Schema(title = "User birthday", example = "1990-05-15")
    private String birthday;
}