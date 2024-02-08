package com.roomreservation;

import io.micronaut.core.io.ResourceLoader;
import io.micronaut.http.client.BlockingHttpClient;
import io.micronaut.http.client.HttpClient;
import io.micronaut.http.client.annotation.Client;
import io.micronaut.runtime.EmbeddedApplication;
import io.micronaut.test.extensions.junit5.annotation.MicronautTest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;

import jakarta.inject.Inject;


@MicronautTest
class UserServiceApplicationTest {

    @Inject
    EmbeddedApplication<?> application;

    @Test
    void testItWorks() {
        Assertions.assertTrue(application.isRunning());
    }

    @Test
    void openApi(@Client("/") HttpClient httpClient) {
        BlockingHttpClient client = httpClient.toBlocking();
        // Below name depends on @OpenAPIDefinition info which is generated in Application.java
        Assertions.assertDoesNotThrow(() -> client.exchange("/swagger/users-service-0.1.yml"));
    }

    @Test
    void buildGeneratesOpenApi(ResourceLoader resourceLoader) {
        Assertions.assertTrue(resourceLoader.getResource("META-INF/swagger/views/swagger-ui/index.html").isPresent());
    }
}
