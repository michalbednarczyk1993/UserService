package com.roomreservation.exceptions;

import io.micronaut.http.HttpRequest;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.HttpStatus;
import io.micronaut.http.server.exceptions.ExceptionHandler;
import jakarta.inject.Singleton;
import jakarta.persistence.EntityNotFoundException;

import java.util.logging.Level;
import java.util.logging.Logger;

@Singleton
public class EntityNotFoundExceptionHandler  implements ExceptionHandler<EntityNotFoundException, HttpResponse<?>> {

    private static final Logger LOGGER = Logger.getLogger(EntityNotFoundException.class.getName());

    @Override
    public HttpResponse<?> handle(HttpRequest request, EntityNotFoundException exception) {
        LOGGER.log(Level.INFO, exception.toString());
        return HttpResponse.status(HttpStatus.NOT_FOUND).body("Brak dostępnych zasobów spełniających kryteria");
    }
}
