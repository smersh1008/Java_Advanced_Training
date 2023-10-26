package com.epam.trainning.rest.api.handler;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.NoSuchElementException;

@RestControllerAdvice
public class GlobalControllerExceptionHandler {
    @ExceptionHandler(NoSuchElementException.class)
    public @ResponseBody ResponseEntity<?> return404(final NoSuchElementException exception) {
        final var errors = Map.of(
                "timestamp", LocalDateTime.now(),
                "code", HttpStatus.NOT_FOUND.value(),
                "errorMessage", exception.getMessage()
        );
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errors);
    }
}