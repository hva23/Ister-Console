package com.ister.repository;

import com.ister.domain.User;

import java.util.ArrayList;
import java.util.List;

public class UserInMemoryRepositoryImpl implements UserRepository {


    private static List<User> usersInMemory = new ArrayList<>();

    public void create(User user) {
        usersInMemory.add(user);
    }

    public void delete(User user) {
        usersInMemory.remove(user);
    }

    public List<User> getAll() {
        return usersInMemory;
    }

    public User findById(Long id) {
        return usersInMemory.stream().filter(u -> u.getId().equals(id)).findFirst().get();
    }

    @Override
    public User findBuUsername(String username) {
        return null;
    }
}
