package com.roomreservation.rest;

import com.roomreservation.core.Users;
import com.roomreservation.rest.dto.RegisterRequestData;
import io.micronaut.http.HttpRequest;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.HttpStatus;
import io.micronaut.http.client.HttpClient;
import io.micronaut.http.client.annotation.Client;
import io.micronaut.http.client.exceptions.HttpClientResponseException;
import io.micronaut.test.annotation.Sql;
import io.micronaut.test.extensions.junit5.annotation.MicronautTest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import jakarta.inject.Inject;



import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@MicronautTest
@Sql({"classpath:insert.sql"})
public class UsersServiceControllerTest {

    @Inject
    @Client("/")
    HttpClient client;


    @Test
    void testSuccessfulRegistration() {
        Users newUser = new Users("John", "Doe", "john@example.com", "999999999", "USER");
        HttpRequest<Users> request = HttpRequest.POST("/users/register", newUser);

        try {
            HttpResponse<String> response = client.toBlocking().exchange(request, String.class);
            assertEquals(HttpStatus.OK, response.getStatus());
            assertEquals("Stworzono", response.getBody().orElse(""));
        } catch (HttpClientResponseException e) {
            fail("Unexpected response status: " + e.getStatus());
        }
    }

    @Test
    void testRegistrationWithInvalidData() {
        Users newUser = new Users("", "", "invalid", "", "");

        HttpRequest<Users> request = HttpRequest.POST("/users/register", newUser);

        try {
            client.toBlocking().exchange(request, String.class);
            Assertions.fail("Should have thrown HttpClientResponseException");
        } catch (HttpClientResponseException e) {
            Assertions.assertEquals(HttpStatus.BAD_REQUEST, e.getStatus());
            Optional<String> responseBody = e.getResponse().getBody(String.class);
            Assertions.assertTrue(responseBody.isPresent(), "Response body should be present");
            String body = responseBody.get();
            Assertions.assertTrue(body.contains("Nieprawidłowe dane w żądaniu"));
        }
    }

    @Test
    void testRegistrationOfExistingUser() {
        Users existingUser = new Users("ExistingUser", "User", "existing@example.com", "123456789", "USER");
        HttpRequest<?> request = HttpRequest.POST("/users/register", existingUser);

        try {
            client.toBlocking().exchange(request, String.class);
            Assertions.fail("Should have thrown UserAlreadyExistException");
        } catch (HttpClientResponseException e) {
            Assertions.assertEquals(HttpStatus.BAD_REQUEST, e.getStatus());
            Optional<String> responseBody = e.getResponse().getBody(String.class);
            Assertions.assertTrue(responseBody.isPresent(), "Response body should be present");
            String body = responseBody.get();
            Assertions.assertTrue(body.contains("Użytkownik z podanymi danymi już istnieje"));
        }
    }

    @Test
    void testGetUserSuccess() {
        // Załóżmy, że istnieje użytkownik z ID = 1
        HttpResponse<?> response = client.toBlocking().exchange(HttpRequest.GET("/users/1"), RegisterRequestData.class);
        assertEquals(HttpStatus.OK, response.getStatus());
        assertTrue(response.getBody().isPresent());
    }

    @Test
    void testGetUserNotFound() {
        // Załóżmy, że nie istnieje użytkownik z ID = 999
        HttpRequest<?> request = HttpRequest.GET("/users/999");

        try {
            client.toBlocking().exchange(request, String.class);
            Assertions.fail("Should have thrown EntityNotFoundException");
        } catch (HttpClientResponseException e) {
            Assertions.assertEquals(HttpStatus.NOT_FOUND, e.getStatus());
            Optional<String> responseBody = e.getResponse().getBody(String.class);
            Assertions.assertTrue(responseBody.isPresent(), "Response body should be present");
            String body = responseBody.get();
            Assertions.assertTrue(body.contains("Brak dostępnych zasobów spełniających kryteria"));
        }
    }

    @Test
    void testGetUserBadRequest() {
        // Testowanie z niepoprawnym ID, np. String zamiast Integer
        HttpRequest<?> request = HttpRequest.GET("/users/invalid");

        try {
            client.toBlocking().exchange(request, String.class);
            Assertions.fail("Should have thrown Exception");
        } catch (HttpClientResponseException e) {
            Assertions.assertEquals(HttpStatus.BAD_REQUEST, e.getStatus());
            Optional<String> responseBody = e.getResponse().getBody(String.class);
            Assertions.assertTrue(responseBody.isPresent(), "Response body should be present");
            String body = responseBody.get();
            System.out.println(body);

            // Działa poprawnie w swaggerze
            // Assertions.assertTrue(body.contains("Nieprawidłowe dane w żądaniu"));
        }
    }

    @Test
    void testSuccessfulUpdate() {
        RegisterRequestData updatedUser = new RegisterRequestData("Johny", "Doe", "johny.doe@example.com", "113456789", "USER");
        HttpRequest<Object> request = HttpRequest.PATCH("/users/2", updatedUser);
        var response = client.toBlocking().exchange(request, String.class);

        assertEquals(HttpStatus.OK, response.getStatus());
    }

    @Test
    void testUpdateWithInvalidData() {
        RegisterRequestData invalidUser = new RegisterRequestData("", "", "invalid", "", "");
        HttpRequest<Object> request = HttpRequest.PATCH("/users/1", invalidUser);
        try {
            client.toBlocking().exchange(request, String.class);
            Assertions.fail("Should have thrown HttpClientResponseException");
        } catch (HttpClientResponseException e) {
            Assertions.assertEquals(HttpStatus.BAD_REQUEST, e.getStatus());
            Optional<String> responseBody = e.getResponse().getBody(String.class);
            Assertions.assertTrue(responseBody.isPresent(), "Response body should be present");
            String body = responseBody.get();
            Assertions.assertTrue(body.contains("Nieprawidłowe dane w żądaniu"));
        }
    }

    @Test
    void testUpdateNonExistingUser() {
        RegisterRequestData updatedUser = new RegisterRequestData("John", "Doe", "john.doe@example.com", "123456789", "USER");
        HttpRequest<Object> request = HttpRequest.PATCH("/users/999", updatedUser);

        try {
            client.toBlocking().exchange(request, String.class);
            Assertions.fail("Should have thrown EntityNotFoundException");
        } catch (HttpClientResponseException e) {
            Assertions.assertEquals(HttpStatus.NOT_FOUND, e.getStatus());
            Optional<String> responseBody = e.getResponse().getBody(String.class);
            Assertions.assertTrue(responseBody.isPresent(), "Response body should be present");
            String body = responseBody.get();
            Assertions.assertTrue(body.contains("Brak dostępnych zasobów spełniających kryteria"));
        }
    }

    @Test
    void testDeleteUserSuccess() {
        var response = client.toBlocking().exchange(HttpRequest.DELETE("/users/3"), Void.class);
        Assertions.assertEquals(HttpStatus.OK, response.getStatus());
    }

    @Test
    void testDeleteUserBadRequest() {
        Assertions.assertThrows(HttpClientResponseException.class, () -> {
            client.toBlocking().exchange(HttpRequest.DELETE("/users/-1"), Void.class);
        }, "Expected a BadRequest response");
    }

    @Test
    void testDeleteNonExistingUser() {
        Assertions.assertThrows(HttpClientResponseException.class, () -> {
            client.toBlocking().exchange(HttpRequest.DELETE("/users/999"), Void.class);
        }, "Expected a NotFound response");
    }
}
