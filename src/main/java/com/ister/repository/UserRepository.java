package com.ister.repository;

import com.ister.domain.User;

import java.util.List;
import java.util.Optional;

public interface UserRepository {

    void create(User user);

    void delete(User user);

    List<User> getAll();

    Optional<User> findById(Long id);

    Optional<User> findByUsername(String username);
}
