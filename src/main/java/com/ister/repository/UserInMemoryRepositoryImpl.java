package com.ister.repository;

import com.ister.domain.User;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class UserInMemoryRepositoryImpl implements UserRepository {


    private static List<User> usersInMemory = new ArrayList<>();

    public void create(User user) {
        usersInMemory.add(user);
    }


    public void update(User user) {

        Optional<User> dbUserOptional = findById(user.getId());
        if (dbUserOptional.isPresent()) {
            User dbUser = dbUserOptional.get();
            dbUser.setEmail(user.getEmail());
            dbUser.setPassword(user.getPassword());
            dbUser.setUsername(user.getUsername());
        }
    }

    public void delete(User user) {
        usersInMemory.remove(user);
    }

    public List<User> getAll() {
        return usersInMemory;
    }

    public Optional<User> findById(Long id) {
        return usersInMemory.stream().filter(u -> u.getId().equals(id)).findFirst();
    }

    @Override
    public Optional<User> findByUsername(String username) {
        return usersInMemory.stream().filter(u -> u.getUsername().contentEquals(username)).findFirst();
    }
}
