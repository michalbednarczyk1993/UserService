package com.roomreservation.rest;

import com.roomreservation.core.Users;
import com.roomreservation.core.UsersRepository;
import com.roomreservation.exceptions.UserAlreadyExistException;
import jakarta.inject.Singleton;

@Singleton
public class UsersService {

    private final UsersRepository usersRepository;

    public UsersService(UsersRepository usersRepository) {
        this.usersRepository = usersRepository;
    }

//    public void register(RegisterRequestData newUser) {
//    public void register(RegisterRequestData newUser) {
//        if (usersRepository.findByEmail(newUser.email()).isPresent() ||
//                usersRepository.findByPhoneNumber(newUser.phoneNumber()).isPresent())
//            throw new UserAlreadyExistException();
//
//        usersRepository.save(newUser.toEntity());
//    }

    public void register(Users newUser) {
        if (usersRepository.findByEmail(newUser.getEmail()).isPresent() ||
                usersRepository.findByPhoneNumber(newUser.getPhoneNumber()).isPresent())
            throw new UserAlreadyExistException();

        usersRepository.save(newUser);
    }

    public void update(Integer id, Users updatedUser) {
    }

    public void delete(Integer id) {
    }
}
