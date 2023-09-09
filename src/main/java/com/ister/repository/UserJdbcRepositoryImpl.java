package com.ister.repository;

import com.ister.domain.User;

import java.util.ArrayList;
import java.util.List;

public class UserJdbcRepositoryImpl implements UserRepository {


    public void create(User user) {
    }

    public void delete(User user) {
    }

    public List<User> getAll() {
        return new ArrayList<>();
    }

    public User findById(Long id) {
        return null;
    }

    @Override
    public User findBuUsername(String username) {
        return null;
    }
}
