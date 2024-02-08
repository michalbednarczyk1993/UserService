package com.roomreservation.exceptions;

import io.micronaut.context.annotation.Replaces;
import io.micronaut.context.annotation.Requires;
import io.micronaut.http.HttpRequest;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.HttpResponseFactory;
import io.micronaut.http.HttpStatus;
import io.micronaut.http.server.exceptions.ExceptionHandler;
import io.micronaut.validation.exceptions.ConstraintExceptionHandler;
import jakarta.inject.Singleton;

import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.ConstraintViolationException;
import java.util.logging.Level;
import java.util.logging.Logger;

@Singleton
@Requires(classes = {ConstraintViolationException.class, ExceptionHandler.class})
@Replaces(ConstraintExceptionHandler.class)
public class ValidationExceptionHandler implements ExceptionHandler<ConstraintViolationException, HttpResponse<?>> {

    private static final Logger LOGGER = Logger.getLogger(EntityNotFoundException.class.getName());

    @Override
    public HttpResponse<?> handle(HttpRequest request, ConstraintViolationException exception) {
        exception.getConstraintViolations().forEach(violation -> {
            LOGGER.log(Level.INFO, violation.getPropertyPath().toString() + " : " + violation.getMessage());
        });
        return HttpResponseFactory.INSTANCE.status(HttpStatus.BAD_REQUEST)
                .body("Nieprawidłowe dane w żądaniu");
    }
}
