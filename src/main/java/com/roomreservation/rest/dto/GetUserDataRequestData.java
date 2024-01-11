package com.roomreservation.rest.dto;

import io.micronaut.core.annotation.Introspected;
import io.micronaut.serde.annotation.Serdeable;
import io.swagger.v3.oas.annotations.media.Schema;

@Introspected
@Serdeable
@Schema(description = "Dane przesyłane w odpowiedzi na żądanie od dane konkretnego użytkownika")
public record GetUserDataRequestData (
        Integer userId,
        String name,
        String surnam,
        String emai,
        String phoneNumbe,
        String accessToke,
        String refreshToken
)
{

}
