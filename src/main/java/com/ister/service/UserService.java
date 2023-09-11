package com.ister.service;

import com.ister.common.RequestStatus;
import com.ister.domain.User;
import com.ister.repository.*;

import java.util.Optional;

public class UserService {

    private UserRepository userRepository;
    private ThingsRepository thingsRepository;

    public UserService() {
        this.userRepository = new UserInMemoryRepositoryImpl();
        this.thingsRepository = new ThingsInMemoryRepositoryImpl();
    }

    public RequestStatus signUp(User user) {
        Optional<User> usr = userRepository.findByUsername(user.getUsername());
        if (usr.isPresent()) {
            System.out.printf("A user exists with this ID : %d", user.getId());
            return RequestStatus.Failed;
        } else {
            userRepository.create(user);
            return RequestStatus.Successful;
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

    private boolean checkPassword(Optional<User> usr, String password) {
        User user = usr.get();
        return (user.getPassword().contentEquals(password));
    }

    public String getUserData(User user) {
        return String.format("""
                user ID : %d
                username : %s
                email : %s
                created date : %s           
                """, user.getId(), user.getUsername(), user.getEmail(), user.getCreatedDate());
    }

    public String getUserData(String username) {
        Optional<User> userOptional = userRepository.findByUsername(username);
        if(userOptional.isPresent())
            return String.format("""
                    user ID : %d
                    username : %s
                    email : %s
                    created date : %s           
                    """, userOptional.get().getId(), userOptional.get().getUsername(), userOptional.get().getEmail(), userOptional.get().getCreatedDate());
        else return null;
    }

    public User getUser(Long id) {
        Optional<User> userOptional = userRepository.findById(id);
        return userOptional.orElse(null);
    }

    public User getUser(String username) {
        Optional<User> userOptional = userRepository.findByUsername(username);
        return userOptional.orElse(null);
    }

    public RequestStatus forgotPassword(User user) {
        System.out.printf("""
                Your password : %s
                """, user.getPassword());
        return RequestStatus.Successful;
    }

    public RequestStatus editProfile(User user) {

        if (userRepository.update(user))
            return RequestStatus.Successful;
        else {
            System.out.println("This user doesn't exists!");
            return RequestStatus.Failed;
        }
    }

    public RequestStatus delete(User user) {
        Optional<User> usr = userRepository.findById(user.getId());
        if (usr.isPresent()) {
            userRepository.delete(user);
            //thingsRepository.delete(thingsRepository.findByUser(user));
            return RequestStatus.Successful;
        }
        System.out.println("This user doesn't exists or something went wrong!");
        return RequestStatus.Failed;
    }
}
