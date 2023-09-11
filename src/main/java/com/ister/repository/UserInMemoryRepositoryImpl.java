package com.ister.repository;

import com.ister.domain.User;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class UserInMemoryRepositoryImpl implements UserRepository {


    private List<User> usersInMemory = new ArrayList<>();

    @Override
    public void create(User user) {
        usersInMemory.add(user);
    }

    @Override
    public boolean update(User user) {
        Optional<User> dbUserOptional = findById(user.getId());
        if (dbUserOptional.isPresent()) {
            User dbUser = dbUserOptional.get();
            dbUser.setEmail(user.getEmail());
            dbUser.setPassword(user.getPassword());
            dbUser.setUsername(user.getUsername());
            usersInMemory.remove(user);
            usersInMemory.add(dbUser);
            return true;
        }
        return false;
    }

    @Override
    public void delete(User user) {
        usersInMemory.remove(user);
    }

    @Override
    public List<User> getAll() {
        return usersInMemory;
    }

    @Override
    public Optional<User> findById(Long id) {
        return usersInMemory.stream().filter(u -> u.getId().equals(id)).findFirst();
    }

    @Override
    public Optional<User> findByUsername(String username) {
        return usersInMemory.stream().filter(u -> u.getUsername().contentEquals(username)).findFirst();
    }
}
