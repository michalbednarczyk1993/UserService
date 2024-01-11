package com.roomreservation.exceptions;

public class UserAlreadyExistException extends RuntimeException {

    public UserAlreadyExistException() {

    }

    public UserAlreadyExistException(final String msg) {
        super(msg);
    }
}
