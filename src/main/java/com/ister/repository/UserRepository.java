package com.ister.repository;

import com.ister.domain.User;

import java.util.List;

public interface UserRepository {

    void create(User user);

    void delete(User user);

    List<User> getAll();

    User findById(Long id);

    User findByUsername(String username);
}
