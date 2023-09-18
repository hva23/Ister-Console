package com.ister.repository;

import com.ister.domain.User;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class UserJdbcRepositoryImpl implements BaseRespository<User, String> {


    public boolean create(User user) {
        return true;
    }

    public boolean delete(User user) {
        return true;
    }

    public boolean update(User user){
        return true;
    }
    public List<User> getAll() {
        return new ArrayList<>();
    }
    @Override
    public Optional<User> findById(String id) {
        return Optional.empty();
    }

    public Optional<User> findByUsername(String username) {
        return Optional.empty();
    }


}
