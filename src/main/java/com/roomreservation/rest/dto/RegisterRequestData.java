package com.roomreservation.rest.dto;

import com.roomreservation.core.Users;
import com.roomreservation.utils.validator.ValidPhone;
import io.micronaut.core.annotation.Introspected;
import io.micronaut.serde.annotation.Serdeable;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

@Introspected
@Serdeable
@Schema(description = "Dane przesyłane podczas tworzenia nowego użytkownika")
public record RegisterRequestData(
        @Schema(description = "Imię") @NotBlank String name,
        @Schema(description = "Nazwisko") @NotBlank String surname,
        @Schema(description = "Adres e-mail")  @NotBlank @Email String email,
        @Schema(description = "Numer telefonu") @ValidPhone String phoneNumber,
        @Schema(description = "Rola") @NotEmpty String role
) {

    public static RegisterRequestData toDto(@NotNull Users entity) {
        return new RegisterRequestData(
                entity.getName(),
                entity.getSurname(),
                entity.getEmail(),
                entity.getPhoneNumber(),
                entity.getRole()
        );
    }

    public Users toEntity() {
        return new Users(name, surname, email, phoneNumber, role);
    }

    public Users toEntity(Integer id) {
        return new Users(id, name, surname, email, phoneNumber, role);
    }
}
