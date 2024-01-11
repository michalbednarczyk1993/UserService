package com.roomreservation.rest;

import com.roomreservation.core.Users;
import io.micronaut.http.HttpStatus;
import io.micronaut.test.extensions.junit5.annotation.MicronautTest;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.Test;
import jakarta.inject.Inject;
import io.micronaut.http.client.HttpClient;
import io.micronaut.http.client.annotation.Client;

import static io.restassured.RestAssured.given;

@MicronautTest
public class UsersServiceControllerTest {

    @Inject
    @Client("/")
    HttpClient client;

    @Test
    void testSuccessfulRegistration() {
        Users newUser = new Users("John", "Doe", "john@example.com", "123456789", "USER");

        given().body(newUser)
                .contentType(ContentType.JSON)
                .when()
                .post("/register")
                .then().log().all()
                .statusCode(HttpStatus.OK.getCode());
    }

    @Test
    void testRegistrationWithInvalidData() {
        Users newUser = new Users("", "", "invalid", "", "");

        given().body(newUser)
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .when()
                .post("/register")
                .then()
                .statusCode(HttpStatus.BAD_REQUEST.getCode());
    }

    @Test
    void testRegistrationOfExistingUser() {
        Users existingUser = new Users("ExistingUser", "User", "existing@example.com", "123456789", "USER");

        given().body(existingUser)
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .when()
                .post("/register")
                .then()
                .statusCode(HttpStatus.BAD_REQUEST.getCode());
    }

    // Test obsługi niespodziewanego błędu serwera (np. błąd połączenia z bazą danych)
    // Ten test może wymagać dodatkowej konfiguracji lub symulacji błędu w aplikacji
//    @Test
//    void testInternalServerError() {
//        // Przykład, w jaki sposób można zasymulować błąd serwera.
//        // Może wymagać modyfikacji zależnie od logiki aplikacji.
//    }
}
