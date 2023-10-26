package com.epam.trainning.rest.api.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;
import org.springframework.hateoas.RepresentationModel;

@Getter
@Setter
public class SubscriptionResponseDto extends RepresentationModel<SubscriptionResponseDto> {
    @Schema(title = "Subscription id", example = "1")
    private Long id;
    @Schema(title = "Subscription user id", example = "1")
    private Long userId;
    @Schema(title = "Subscription start date", example = "2023-10-23")
    private String startDate;
}