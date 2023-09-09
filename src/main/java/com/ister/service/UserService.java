package com.ister.service;

import com.ister.domain.User;
import com.ister.repository.UserJdbcRepositoryImpl;
import com.ister.repository.UserRepository;

public class UserService {

    private UserRepository userRepository;

    public UserService() {
        this.userRepository = new UserJdbcRepositoryImpl();
    }

    public void signUp(User user) {

    }

    public void delete(User user) {

    }
}
