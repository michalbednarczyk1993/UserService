package com.roomreservation.core;

import io.micronaut.test.extensions.junit5.annotation.MicronautTest;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

@MicronautTest
class UsersRepositoryTest {

    @Test
    public void whenFindByEmail_thenReturnUser(UsersRepository usersRepository) {
        // given
        String email = "test@example.com";
        Users user = new Users("John", "Doe", email, "123456789", "USER");
        usersRepository.save(user);

        // when
        Users found = usersRepository.findByEmail(email).orElse(null);

        // then
        assertNotNull(found);
        assertEquals(user.getEmail(), found.getEmail());
    }

    @Test
    public void whenFindByEmail_thenReturnNull(UsersRepository usersRepository) {
        // given
        String email = "nonexistent@example.com";

        // when
        Users found = usersRepository.findByEmail(email).orElse(null);

        // then
        assertNull(found);
    }

    @Test
    public void whenFindByPhoneNumber_thenReturnUser(UsersRepository usersRepository) {
        // given
        String phoneNumber = "123456789";
        Users user = new Users("Jane", "Doe", "jane@example.com", phoneNumber, "USER");
        usersRepository.save(user);

        // when
        Users found = usersRepository.findByPhoneNumber(phoneNumber).orElse(null);

        // then
        assertNotNull(found);
        assertEquals(user.getPhoneNumber(), found.getPhoneNumber());
    }

    @Test
    public void whenFindByPhoneNumber_thenReturnNull(UsersRepository usersRepository) {
        // given
        String phoneNumber = "987654321";

        // when
        Users found = usersRepository.findByPhoneNumber(phoneNumber).orElse(null);

        // then
        assertNull(found);
    }


}
