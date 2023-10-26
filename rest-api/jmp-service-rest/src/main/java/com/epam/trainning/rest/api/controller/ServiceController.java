package com.epam.trainning.rest.api.controller;

import com.epam.trainning.rest.api.dto.SubscriptionRequestDto;
import com.epam.trainning.rest.api.dto.SubscriptionResponseDto;
import com.epam.trainning.rest.api.model.Subscription;
import com.epam.trainning.rest.api.service.SubscriptionService;
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
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.time.LocalDate;
import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@AllArgsConstructor
@Tag(name = "Subscriptions API")
@RestController
@RequestMapping("/api/v1/")
public class ServiceController {
    private final SubscriptionService subscriptionService;
    private final ModelMapper modelMapper;

    @ApiResponse(responseCode = "201", description = "Subscription is created")
    @ApiResponse(responseCode = "404", description = "User is missing and subscription is not created")
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(description = "Create new subscription")
    @PostMapping(value = "subscriptions", produces = MimeTypeUtils.APPLICATION_JSON_VALUE)
    public @ResponseBody ResponseEntity<SubscriptionResponseDto> createSubscription(@RequestBody final SubscriptionRequestDto dto) {
        final var subscription = modelMapper.map(dto, Subscription.class);
        subscription.setStartDate(LocalDate.now());
        final var subscriptionResponseDto = modelMapper.map(subscriptionService.create(subscription), SubscriptionResponseDto.class);
        return ResponseEntity.created(ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                        .buildAndExpand(subscriptionResponseDto.getId())
                        .toUri())
                .body(subscriptionResponseDto);
    }

    @ApiResponse(responseCode = "200", description = "Subscription is updated")
    @ApiResponse(responseCode = "404", description = "Subscription and/or user are missing")
    @ResponseStatus(HttpStatus.OK)
    @Operation(description = "Update subscription")
    @PatchMapping(value = "subscriptions", produces = MimeTypeUtils.APPLICATION_JSON_VALUE)
    public @ResponseBody ResponseEntity<SubscriptionResponseDto> updateSubscription(@RequestBody final SubscriptionRequestDto dto) {
        final var subscription = modelMapper.map(dto, Subscription.class);
        final var subscriptionResponseDto = modelMapper.map(subscriptionService.update(subscription), SubscriptionResponseDto.class);
        return ResponseEntity.ok(subscriptionResponseDto);
    }

    @ApiResponse(responseCode = "204", description = "Subscription is deleted or missing")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(description = "Delete subscription by id")
    @DeleteMapping(value = "subscriptions/{id}")
    public @ResponseBody ResponseEntity<?> deleteSubscription(@Schema(description = "Subscription id") @PathVariable("id") final Long id) {
        subscriptionService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @ApiResponse(responseCode = "200", description = "Subscription data")
    @ApiResponse(responseCode = "404", description = "Subscription is missing by id")
    @Operation(description = "Get subscription by id")
    @ResponseStatus(HttpStatus.OK)
    @GetMapping(value = "subscriptions/{id}", produces = MimeTypeUtils.APPLICATION_JSON_VALUE)
    public @ResponseBody ResponseEntity<SubscriptionResponseDto> getSubscription(@Schema(description = "Subscription id") @PathVariable("id") final Long id) {
        return ResponseEntity.ok(modelMapper.map(subscriptionService.getById(id), SubscriptionResponseDto.class));
    }

    @ApiResponse(responseCode = "200", description = "All subscriptions data")
    @Operation(description = "Get all subscriptions")
    @ResponseStatus(HttpStatus.OK)
    @GetMapping(value = "subscriptions", produces = MimeTypeUtils.APPLICATION_JSON_VALUE)
    public @ResponseBody ResponseEntity<List<SubscriptionResponseDto>> getSubscriptions() {
        return ResponseEntity.ok(subscriptionService.getAll().stream()
                .map(subscription -> modelMapper.map(subscription, SubscriptionResponseDto.class))
                .map(subscriptionResponseDto -> subscriptionResponseDto.add(linkTo(methodOn(ServiceController.class)
                        .getSubscription(subscriptionResponseDto.getId()))
                        .withRel(subscriptionResponseDto.getId().toString())))
                .toList());
    }
}