package com.roomreservation.rest;

import com.roomreservation.rest.dto.RegisterRequestData;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.HttpStatus;
import io.micronaut.http.annotation.*;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;


@OpenAPIDefinition(
        info = @Info(
                title = "Users-Service",
                version = "0.1"))
@Controller("/users")
public class UsersServiceController {

    private final UsersService usersService;

    public UsersServiceController(UsersService usersService) {
        this.usersService = usersService;
    }


    @Get(uri = "/", produces = "text/plain")
    public String index() {
        return "Example Response";
    }

    @Post("/register")
    @Operation(summary = "Rejestracja nowego użytkownika")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Stworzono"),
            @ApiResponse(responseCode = "400", description = "Nieprawidłowe dane w żądaniu"),
            @ApiResponse(responseCode = "400", description = "Użytkownik z podanym adresem e-mail już istnieje"),
            @ApiResponse(responseCode = "500", description = "Błąd serwera")
    })
    public HttpResponse<String> registerUser(@Body @Valid RegisterRequestData newUser) {
        usersService.register(newUser);
        return HttpResponse.status(HttpStatus.OK).body("Stworzono");
    }

    @Get("/{userId}")
    @Operation(summary = "Zwraca dane użytkownika o podanym ID")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Sukces"),
            @ApiResponse(responseCode = "400", description = "Nieprawidłowe dane w żądaniu"),
            @ApiResponse(responseCode = "404", description = "Brak dostępnych zasobów spełniających kryteria"),
            @ApiResponse(responseCode = "500", description = "Błąd serwera")
    })
    public HttpResponse<?> getUser(@PathVariable @NotNull @Positive Integer userId) {
        RegisterRequestData user = usersService.getUser(userId);
        return HttpResponse.ok(user);
    }

    @Patch("/{id}")
    @Operation(summary = "Aktualizuje dane użytkownika o danym ID")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Sukces"),
            @ApiResponse(responseCode = "400", description = "Nieprawidłowe dane w żądaniu"),
            @ApiResponse(responseCode = "404", description = "Brak dostępnych zasobów spełniających kryteria"),
            @ApiResponse(responseCode = "500", description = "Błąd serwera")
    })
    public HttpResponse<?> updateUser(@PathVariable @NotNull @Positive Integer id, @Body @Valid RegisterRequestData updatedUser) {
        usersService.update(id, updatedUser);
        return HttpResponse.ok();
    }

    @Delete("/{id}")
    @Operation(summary = "Usuwa użytkownika o danym ID")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Sukces"),
            @ApiResponse(responseCode = "400", description = "Nieprawidłowe dane w żądaniu"),
            @ApiResponse(responseCode = "404", description = "Brak dostępnych zasobów spełniających kryteria"),
            @ApiResponse(responseCode = "500", description = "Błąd serwera")

    })
    public HttpResponse<Void> deleteUser(@PathVariable @NotNull @Positive Integer id) {
        usersService.delete(id);
        return HttpResponse.ok();
    }
}