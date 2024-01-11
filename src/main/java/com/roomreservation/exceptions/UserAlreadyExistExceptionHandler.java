package com.roomreservation.exceptions;


import io.micronaut.http.HttpRequest;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.HttpStatus;
import io.micronaut.http.server.exceptions.ExceptionHandler;
import jakarta.inject.Singleton;


@Singleton
public class UserAlreadyExistExceptionHandler implements ExceptionHandler<UserAlreadyExistException, HttpResponse<?>> {

    @Override
    public HttpResponse<?> handle(HttpRequest request, UserAlreadyExistException exception) {
        exception.printStackTrace();
        return HttpResponse.status(HttpStatus.BAD_REQUEST)
                .body("Użytkownik z podanymi danymi już istnieje");
    }
}
