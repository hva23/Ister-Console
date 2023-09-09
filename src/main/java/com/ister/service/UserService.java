package com.ister.service;

import com.ister.common.RequestStatus;
import com.ister.domain.User;
import com.ister.repository.*;

public class UserService {

    private UserRepository userRepository;
    private ThingsRepository thingsRepository;

    public UserService() {
        this.userRepository = new UserInMemoryRepositoryImpl();
        this.thingsRepository = new ThingsInMemoryRepositoryImpl();
    }

    public RequestStatus signUp(User user) {
        try{
            userRepository.findById(user.getId());
            System.out.printf("A user exists with this ID : %d", user.getId());
            return RequestStatus.Failed;
        }catch (Exception ex){
            userRepository.create(user);
        }
        return RequestStatus.Successful;
    }

    public RequestStatus login(User user){
        try{
            userRepository.findById(user.getId());
            System.out.printf("""
                Welcome %s
                you logged in with this email : %s
                """, user.getUsername(), user.getEmail());
            return RequestStatus.Successful;
        }catch (Exception ex){
            System.out.printf("A user exists with this ID : %d", user.getId());
            return RequestStatus.Failed;
        }

    }

    public RequestStatus forgotPassword(User user){
        System.out.printf("""
                Your password : %s
                """, user.getPassword());
        return RequestStatus.Successful;
    }

    public RequestStatus editProfile(User user){
        try{
            userRepository.findById(user.getId());
            userRepository.delete(user);
            userRepository.create(user);
        }catch (Exception ex){
            System.out.println("This user doesn't exists!");
        }
        return RequestStatus.Successful;
    }

    public RequestStatus delete(User user) {
        try{
            userRepository.findById(user.getId());
            userRepository.delete(user);
            thingsRepository.delete(thingsRepository.findByUser(user));
            return RequestStatus.Successful;
        }catch (Exception ex){
            System.out.println("This user doesn't exists or something went wrong!");
            return RequestStatus.Failed;
        }

    }
}
