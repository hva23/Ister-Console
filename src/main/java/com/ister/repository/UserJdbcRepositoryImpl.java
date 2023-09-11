package com.ister.repository;

import com.ister.domain.User;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class UserJdbcRepositoryImpl implements UserRepository {


    public void create(User user) {
    }

    public void delete(User user) {
    }

    public boolean update(User user){
        return true;
    }
    public List<User> getAll() {
        return new ArrayList<>();
    }

    public Optional<User> findById(Long id) {
        return null;
    }

    @Override
    public Optional<User> findByUsername(String username) {
        return null;
    }
}
