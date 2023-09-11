package com.ister.service;

import com.ister.common.RequestStatus;
import com.ister.common.UserStatus;
import com.ister.domain.Location;
import com.ister.domain.TelemetryData;
import com.ister.domain.Things;
import com.ister.domain.User;

import java.util.*;

public class MenuService {

    private static long userId;
    private static long thingId;
    private Scanner in = new Scanner(System.in);
    private String inputResult;
    private UserService userService = new UserService();
    private ThingsService thingsService = new ThingsService();

    public void menu() {
        List<Object> userList;
        while (true) {
            userList = login();
            if (!userList.contains(UserStatus.Null)) {
                userOptions((Long) userList.get(1));
            }
        }
    }

    private List<Object> login() {
        User user = new User();
        List<Object> userList = new ArrayList<>();

        while (true) { //sign in and login loop
            System.out.println("""
                    Unknown user,
                    Please press 1 to signup and 2 for login
                    press 3 for sending data to defined things""");

            inputResult = in.next();

            if (inputResult.contentEquals("1")) { //sign up

                /* username */
                System.out.print("Enter your username : ");
                user.setUsername(in.next());

                /* password */
                System.out.print("Enter your password : ");
                user.setPassword(in.next());

                /* email */
                System.out.print("Enter your email : ");
                user.setEmail(in.next());

                /* created date and id assigning */
                user.setId(100L + userId);
                userId++;   //for next user creation
                user.setCreatedDate(new Date(2023, Calendar.SEPTEMBER, 9));

                if (userService.signUp(user) == RequestStatus.Successful) {
                    System.out.println("your account successfully created");
                    System.out.println(userService.getUserData(user));
                    userList.add(0, UserStatus.SignedUp);
                    userList.add(1, user.getId());
                    return userList;
                }
                userList.add(0, UserStatus.Null);
                return userList;


            } else if (inputResult.contentEquals("2")) {  //login

                /* username */
                System.out.print("Enter your username : ");
                user.setUsername(in.next());

                /* password */
                System.out.print("Enter your password : ");
                user.setPassword(in.next());

                if (userService.login(user) == RequestStatus.Successful) {
                    System.out.println(userService.getUserData(user.getUsername()));
                    userList.add(0, UserStatus.LoggedIn);
                    userList.add(1, userService.getUser(user.getUsername()).getId());
                    return userList;
                }
                userList.add(0, UserStatus.Null);
                return userList;

            } else if (inputResult.contentEquals("3")) {

                TelemetryData telemetryData = new TelemetryData();
                Map<String, Object> data = new HashMap<>();
                Things thing = new Things();


                System.out.print("Enter thing serial number : ");
                inputResult = in.next();
                try {
                    thing = (Things) thingsService.getThing(inputResult).clone();
                } catch (Exception ex) {
                    System.out.println(ex);
                }

                telemetryData.setThing(thing);
                System.out.print("Enter data (key, value) : ");
                data.put(in.next(), in.next());

                telemetryData.setData(data);
                thingsService.sendTelemetryData(telemetryData);

            } else if (inputResult.contentEquals("exit")) {
                System.out.println("Closing...");
                System.exit(0);
            } else {
                System.out.println("Please open your eyes and enter correct option");
            }
        }
    }

    private UserStatus userOptions(Long userId) {
        User user = new User();
        try {
            user = (User) userService.getUser(userId).clone();
        } catch (Exception ex) {
            System.out.println(ex);
        }

        //user control loop
        userControl:
        while (true) { //jump in to this loop if user is available
            System.out.printf("""
                    ************    username : %s    ************
                    Enter any option :
                        1. Forgot password
                        2. Delete account
                        3. Add things
                        4. Show things
                        5. Edit account
                        6. User information
                        7. back
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
                    Things thing = new Things();
                    Location location = new Location();
                    Map<String, Object> attributes = new HashMap<String, Object>();

                    /* thing name */
                    System.out.print("Enter thing name : ");
                    thing.setName(in.next());

                    /* serial number */
                    System.out.print("Enter serial number : ");
                    thing.setSerialNumber(in.next());

                    /* thing location */
                    System.out.println("Enter thing location");

                    /* latitude */
                    System.out.print("location latitude: ");
                    location.setLatitude(in.nextDouble());

                    /* longitude */
                    System.out.print("location longitude : ");
                    location.setLongitude(in.nextDouble());

                    /* name the location */
                    System.out.print("name thing location : ");
                    location.setName(in.next());

                    /* set thing */
                    location.setThing(thing);
                    thing.setLocation(location);

                    /* user assigning */
                    thing.setUser(userService.getUser(userId));

                    /* version and color as attributes */
                    System.out.print("Enter thing color : ");
                    attributes.put("Color", in.next());

                    System.out.print("Enter thing version : ");
                    attributes.put("Version", in.next());
                    thing.setAttributes(attributes);

                    /* created date and id assigning */
                    thing.setId(100L + thingId);
                    thingId++;      //for next thing creation
                    thing.setCreatedDate(new Date(2023, Calendar.SEPTEMBER, 9));

                    if (thingsService.addThing(thing) == RequestStatus.Successful) {
                        System.out.println("thing successfully added");
                        System.out.println(thingsService.getThingData(thing.getSerialNumber()));
                    } else {
                        System.out.println("Thing didn't add");
                        return UserStatus.Failed;
                    }
                }
                case "4" -> {                                   //show things
                    List<Things> things = thingsService.getUserThing(user);
                    for(Things thing : things)
                        System.out.println(thingsService.getThingData(thing.getSerialNumber()));
                    /*
                    Location location;
                    for (int i = 0; i < things.size(); i++) {
                        location = things.get(i).getLocation();
                        System.out.printf("""
                                                                    
                                        no %d
                                        Thing name : %s
                                        Thing ID : %d
                                        Thing serial number : %s
                                        Thing location (latitude, longitude) : %s(%.2f, %.2f)
                                        Thing owner (username, ID) : %s , %d
                                                                    
                                        """,
                                i,
                                things.get(i).getName(),
                                things.get(i).getId(),
                                things.get(i).getSerialNumber(),
                                location.getName(),
                                location.getLatitude(),
                                location.getLongitude(),
                                things.get(i).getUser().getUsername(),
                                things.get(i).getUser().getId());
                    }

                     */
                }
                case "5" -> {                                   //edit current user profile

                    /* username */
                    System.out.print("Enter your username : ");
                    user.setUsername(in.next());

                    /* password */
                    System.out.print("Enter your password : ");
                    user.setPassword(in.next());

                    /* email */
                    System.out.print("Enter your email : ");
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
                case "6" -> System.out.println(userService.getUserData(user));
                case "7" -> {
                    return UserStatus.Null;
                }
                case "exit" -> {
                    System.out.println("Closing...");
                    System.exit(0);
                }
            }

        }
    }
}
