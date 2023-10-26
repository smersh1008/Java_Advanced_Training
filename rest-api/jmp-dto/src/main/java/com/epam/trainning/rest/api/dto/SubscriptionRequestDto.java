package com.epam.trainning.rest.api.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SubscriptionRequestDto {
    @Schema(title = "Subscription id", example = "1")
    private Long id;
    @Schema(title = "Subscription user id", example = "1")
    private Long userId;
}