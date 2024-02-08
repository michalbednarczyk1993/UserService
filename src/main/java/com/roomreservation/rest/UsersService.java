package com.roomreservation.rest;

import com.roomreservation.core.Users;
import com.roomreservation.core.UsersRepository;
import com.roomreservation.exceptions.UserAlreadyExistException;
import com.roomreservation.rest.dto.RegisterRequestData;
import jakarta.inject.Singleton;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;

@Singleton
public class UsersService {

    private final UsersRepository usersRepository;

    public UsersService(UsersRepository usersRepository) {
        this.usersRepository = usersRepository;
    }

    @Transactional
    public void register(RegisterRequestData newUser) {
        if (usersRepository.findByEmail(newUser.email()).isPresent() ||
                usersRepository.findByPhoneNumber(newUser.phoneNumber()).isPresent())
            throw new UserAlreadyExistException();

        usersRepository.save(newUser.toEntity());
    }

    public RegisterRequestData getUser(Integer userId) {
        Users user = usersRepository.findById(userId).orElseThrow(EntityNotFoundException::new);
        return RegisterRequestData.toDto(user);
    }

    @Transactional
    public void update(Integer id, RegisterRequestData newUser) {
        usersRepository.findById(id).orElseThrow(EntityNotFoundException::new);
        usersRepository.update(newUser.toEntity(id));
    }

    @Transactional
    public void delete(Integer id) {
        usersRepository.findById(id).orElseThrow(EntityNotFoundException::new);
        usersRepository.deleteById(id);
    }

}
