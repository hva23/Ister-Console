package com.ister.service;

import com.ister.common.RequestStatus;
import com.ister.domain.Location;
import com.ister.domain.Things;
import com.ister.domain.User;
import com.ister.repository.*;

import java.util.List;
import java.util.Optional;

public class UserService {

    private UserJdbcRepositoryImpl userRepository;

    public UserService() {
        //this.userRepository = new UserInMemoryRepositoryImpl();
        //this.thingsRepository = new ThingsInMemoryRepositoryImpl();
        this.userRepository = new UserJdbcRepositoryImpl("jdbc:mysql://localhost:8080/Ister", "root", "v@h@bI2442");
    }

    public RequestStatus signUp(User user) {
        Optional<User> usr = userRepository.findByUsername(user.getUsername());
        if (usr.isPresent()) {
            System.out.printf("A user exists with this ID : %s\n", user.getId());
            return RequestStatus.Failed;
        } else {
            return  userRepository.create(user) ? RequestStatus.Successful : RequestStatus.Failed;
        }
    }

    public RequestStatus login(User user) {
        Optional<User> usr = userRepository.findByUsername(user.getUsername());
        if (usr.isPresent()) {
            if (checkPassword(usr, user.getPassword())) return RequestStatus.Successful;
            else {
                System.out.println("Password is wrong");
                return RequestStatus.Failed;
            }
        }
        System.out.println("User not found!");
        return RequestStatus.Failed;
    }

    public RequestStatus editProfile(User user) {
        if (userRepository.findById(user.getId()).isPresent()) {
            if (userRepository.update(user)) {
                System.out.println("the user updated successfully");
                return RequestStatus.Successful;
            } else {
                System.out.println("Something went wrong!");
                return RequestStatus.Failed;
            }
        } else {
            System.out.println("The user doesn't exist");
            return RequestStatus.Failed;
        }
    }

    public RequestStatus delete(User user) {
        Optional<User> usr = userRepository.findById(user.getId());
        if (usr.isPresent()) {
            if (userRepository.delete(user)) {
                System.out.println("the user deleted successfully");
                return RequestStatus.Successful;
            } else {
                System.out.println("Something went wrong!");
                return RequestStatus.Failed;
            }
        } else {
            System.out.println("The user doesn't exist");
            return RequestStatus.Failed;
        }
    }

    private boolean checkPassword(Optional<User> usr, String password) {
        User user = usr.get();
        return (user.getPassword().contentEquals(password));
    }

    public String getUserData(User user) {
        return String.format("""
                User ID : %s
                Username : %s
                Email : %s
                Created date : %s
                """, user.getId(), user.getUsername(), user.getEmail(), user.getCreatedDate());
    }

    public String getUserData(String username) {
        Optional<User> userOptional = userRepository.findByUsername(username);
        return userOptional.map(user -> String.format("""
                User ID : %s
                Username : %s
                Email : %s
                Created date : %s
                """, user.getId(), user.getUsername(), user.getEmail(), user.getCreatedDate())).orElse(null);

    }

    public User getUser(String id) {
        Optional<User> userOptional = userRepository.findById(id);
        return userOptional.orElse(null);
    }

    public User getUserByUsername(String username) {
        Optional<User> userOptional = userRepository.findByUsername(username);
        return userOptional.orElse(null);
    }

    public RequestStatus forgotPassword(User user) {
        System.out.printf("""
                Your password : %s
                """, user.getPassword());
        return RequestStatus.Successful;
    }

    public String getAllUser() {
        User user;
        List<User> userList = userRepository.getAll();
        StringBuilder stringBuilder = new StringBuilder();

        for (int i = 0; i < userList.size(); i++) {
            user = userList.get(i);
            stringBuilder.append(String.format("""
                            -- #%02d --
                            User ID : %s
                            Username : %s
                            Email : %s
                            Created date : %s
                            """,
                    i + 1,
                    user.getId(),
                    user.getUsername(),
                    user.getEmail(),
                    user.getCreatedDate()));
        }

        return stringBuilder.toString();
    }

}
