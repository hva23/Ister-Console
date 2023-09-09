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
        Optional<User> usr = userRepository.findById(user.getId());
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
            if (checkPassword(usr, user.getPassword())) {
                user = usr.get();
                System.out.printf("""
                        Welcome %s
                        you logged in with this email : %s
                        your ID : %d
                        """, user.getUsername(), user.getEmail(), user.getId());
                return RequestStatus.Successful;
            }
        }
        System.out.println("User not found!");
        return RequestStatus.Failed;
    }

    private boolean checkPassword(Optional<User> usr, String password) {
        User user = usr.get();
        return (user.getPassword().contentEquals(password));
    }

    public void getUser(User user) {
        System.out.printf("""
                user ID : %d
                username : %s
                email : %s
                created date : %s
                                
                """, user.getId(), user.getUsername(), user.getEmail(), user.getCreatedDate().toString());
    }

    public RequestStatus forgotPassword(User user) {
        System.out.printf("""
                Your password : %s
                """, user.getPassword());
        return RequestStatus.Successful;
    }

    public RequestStatus editProfile(User user) {
        try {
            userRepository.findById(user.getId());
            userRepository.delete(user);
            userRepository.create(user);
        } catch (Exception ex) {
            System.out.println("This user doesn't exists!");
        }
        return RequestStatus.Successful;
    }

    public RequestStatus delete(User user) {
        try {
            userRepository.findById(user.getId());
            userRepository.delete(user);
            thingsRepository.delete(thingsRepository.findByUser(user));
            return RequestStatus.Successful;
        } catch (Exception ex) {
            System.out.println("This user doesn't exists or something went wrong!");
            return RequestStatus.Failed;
        }

    }
}
