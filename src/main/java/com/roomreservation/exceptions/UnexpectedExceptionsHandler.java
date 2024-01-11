package com.roomreservation.exceptions;


import io.micronaut.http.HttpRequest;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.HttpStatus;
import io.micronaut.http.server.exceptions.ExceptionHandler;
import jakarta.inject.Singleton;

import java.util.logging.Level;
import java.util.logging.Logger;

@Singleton
public class UnexpectedExceptionsHandler implements ExceptionHandler<Exception, HttpResponse<?>> {
    private static final Logger LOGGER = Logger.getLogger(Exception.class.getName());


    @Override
    public HttpResponse<?> handle(HttpRequest request, Exception exception) {
        LOGGER.log(Level.SEVERE, exception.toString());
        return HttpResponse.status(HttpStatus.INTERNAL_SERVER_ERROR);
    }

}
