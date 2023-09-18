package com.ister.repository;

import com.ister.domain.User;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class UserInMemoryRepositoryImpl implements BaseRespository<User, String> {


    private List<User> usersInMemory = new ArrayList<>();

    @Override
    public boolean create(User user) {
        usersInMemory.add(user);
        return true;
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
    public boolean delete(User user) {
        usersInMemory.remove(user);
        return true;
    }

    @Override
    public List<User> getAll() {
        return usersInMemory;
    }

    @Override
    public Optional<User> findById(String id) {
        return usersInMemory.stream().filter(u -> u.getId().equals(id)).findFirst();
    }

    public Optional<User> findByUsername(String username) {
        return usersInMemory.stream().filter(u -> u.getUsername().contentEquals(username)).findFirst();
    }
}
