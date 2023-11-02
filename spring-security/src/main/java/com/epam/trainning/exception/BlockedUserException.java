package com.epam.trainning.exception;

public class BlockedUserException extends RuntimeException {
    public BlockedUserException(final String message) {
        super(message);
    }
}