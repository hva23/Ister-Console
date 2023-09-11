package com.ister.service;

import com.ister.common.RequestStatus;
import com.ister.common.UserStatus;
import com.ister.domain.User;

import java.util.Calendar;
import java.util.Date;
import java.util.Scanner;

public class MenuService {

    private static long id;
    private Scanner in = new Scanner(System.in);
    private String inputResult;
    private UserService userService = new UserService();

    public void menu() {


    }

    private UserStatus login() {
        User user = new User();

        while (true) { //sign in and login loop
            System.out.println("""
                    Unknown user,
                    Please press 1 to signup and 2 for login
                    """);

            inputResult = in.next();

            if (inputResult.contentEquals("1")) { //sign up

                /* username */
                System.out.println("Enter your username : ");
                user.setUsername(in.next());

                /* password */
                System.out.println("Enter your password : ");
                user.setPassword(in.next());

                /* email */
                System.out.println("Enter your email : ");
                user.setEmail(in.next());

                /* created date and id assigning */
                user.setId(100L + id);
                id++;   //for next user creation
                user.setCreatedDate(new Date(2023, Calendar.SEPTEMBER, 9));

                if (userService.signUp(user) == RequestStatus.Successful) {
                    System.out.println("your account successfully created");
                    userService.getUserData(user);
                    return UserStatus.SignedUp;
                }
                user.setId(null);   //user didn't create
                return UserStatus.Null;


            } else if (inputResult.contentEquals("2")) {  //login

                /* username */
                System.out.println("Enter your username : ");
                user.setUsername(in.next());

                /* password */
                System.out.println("Enter your password : ");
                user.setPassword(in.next());

                if (userService.login(user) == RequestStatus.Successful) {
                    System.out.println(userService.getUserData(user));
                    return UserStatus.LoggedIn;
                }
                user.setId(null);   //user didn't login
                return UserStatus.Null;

            } else if (inputResult.contentEquals("exit")) {
                System.out.println("Closing...");
                System.exit(0);
            } else {
                System.out.println("Please open your eyes and enter correct option");
            }
        }
    }

    private UserStatus userOptions() {
        User user = new User();

        //user control loop
        userControl:
        while (user.getId() != null) { //jump in to this loop if user is available
            System.out.printf("""
                    ************    username : %s    ************
                    Enter any option :
                        1. Forgot password
                        2. Delete account
                        3. Add things
                        4. Edit account
                        5. User information
                        6. back
                    """, user.getUsername());
            inputResult = in.next();
            switch (inputResult) {
                case "1" -> userService.forgotPassword(user);   //forgot password
                case "2" -> {                                   //delete user
                    if (userService.delete(user) == RequestStatus.Successful) {
                        Long id = user.getId();
                        if (userService.getUser(id) == null) {
                            System.out.println("User deleted");
                            user.setId(null);
                            user.setUsername(null);
                            user.setEmail(null);
                            user.setPassword(null);
                            user.setCreatedDate(null);
                            user.setLastModifiedDate(null);
                            return UserStatus.Deleted;
                        }
                        System.out.println("Something went wrong\nCan't delete the user");
                        userService.getUserData(user);
                        return UserStatus.Failed;
                    }
                }
                case "3" -> {
                    System.out.println("thing added");  //add things to current user
                    return UserStatus.ThingsAdded;
                }
                case "4" -> {                                   //edit current user profile

                    /* username */
                    System.out.println("Enter your username : ");
                    user.setUsername(in.next());

                    /* password */
                    System.out.println("Enter your password : ");
                    user.setPassword(in.next());

                    /* email */
                    System.out.println("Enter your email : ");
                    user.setEmail(in.next());

                    if (userService.editProfile(user) == RequestStatus.Successful) {
                        System.out.println("profile successfully updated");
                        System.out.println(userService.getUserData(user));
                        return UserStatus.Edited;
                    } else {
                        System.out.println("profile didn't changed");
                        System.out.println(userService.getUserData(user));
                        return UserStatus.Failed;
                    }
                }
                case "5" -> System.out.println(userService.getUserData(user));
                case "6" -> {
                    return UserStatus.Null;
                }
                case "exit" -> {
                    System.out.println("Closing...");
                    System.exit(0);
                }
            }

        }
        return null;
    }
}
