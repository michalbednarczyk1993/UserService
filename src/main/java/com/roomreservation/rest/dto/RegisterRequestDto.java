package com.roomreservation.rest.dto;

import com.roomreservation.core.Users;
import com.roomreservation.utils.validator.ValidPhone;
import io.micronaut.core.annotation.Introspected;
import io.micronaut.serde.annotation.Serdeable;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.inject.Inject;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

@Introspected
@Serdeable
@Schema(description = "Dane przesyłane podczas tworzenia nowego użytkownika")
public class RegisterRequestDto {
    private @Schema(description = "Imię") @NotBlank String name;
    private @Schema(description = "Nazwisko") @NotBlank String surname;
    private @Schema(description = "Adres e-mail")  @NotBlank @Email String email;
    private @Schema(description = "Numer telefonu") @ValidPhone String phoneNumber;
    private @Schema(description = "Rola") @NotEmpty String role;

    public RegisterRequestDto() {
    }

    public RegisterRequestDto(String name, String surname, String email, String phoneNumber, String role) {
        this.name = name;
        this.surname = surname;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.role = role;
    }

    public static RegisterRequestDto toDto(@NotNull Users entity) {
        return new RegisterRequestDto(
                entity.getName(),
                entity.getSurname(),
                entity.getEmail(),
                entity.getPhoneNumber(),
                entity.getRole());
    }

        public Users toEntity() {
            return new Users(name, surname, email, phoneNumber, role);
        }
}
