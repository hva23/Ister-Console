import com.ister.common.RequestStatus;
import com.ister.domain.User;
import com.ister.repository.UserInMemoryRepositoryImpl;
import com.ister.repository.UserRepository;
import com.ister.service.UserService;

import java.util.Calendar;
import java.util.Date;
import java.util.Scanner;

public class Main {

    public static void main(String args[]) {
        Scanner in = new Scanner(System.in);
        String inputResult;

        User user = new User();
        UserService userService = new UserService();

        System.out.println("""
                welcome to ister console application
                note anytime you enter "exit", application will exit.
                """);
        while (true) { //sign in and login loop
            System.out.println("Please press 1 to signup and 2 for login");
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
                user.setId(56540L);
                user.setCreatedDate(new Date(2023, Calendar.SEPTEMBER, 9));

                if(userService.signUp(user) == RequestStatus.Successful) {
                    System.out.println("your account successfully created");
                    userService.getUser(user);
                }

            } else if (inputResult.contentEquals("2")) {  //login

                /* username */
                System.out.println("Enter your username : ");
                user.setUsername(in.next());

                /* password */
                System.out.println("Enter your password : ");
                user.setPassword(in.next());

                userService.login(user);

            } else if (inputResult.contentEquals("exit")) {
                System.exit(0);
            } else {
                System.out.println("Please open your eyes and enter correct option");
            }

            //user control loop
            userControl: while (user.getId() != null) {
                System.out.printf("""
                    
                    username : %s
                    Enter any option :
                    1. Forgot password
                    2. Delete account
                    3. Add things
                    4. Edit account
                    5. back
                    
                    """, user.getUsername());
                inputResult = in.next();
                switch (inputResult) {
                    case "1" -> userService.forgotPassword(user);   //forgot password
                    case "2" -> {                                   //delete user
                        if(userService.delete(user) == RequestStatus.Successful) {
                            System.out.println("User deleted");
                            user.setId(null);
                            user.setUsername(null);
                            user.setEmail(null);
                            user.setPassword(null);
                            user.setCreatedDate(null);
                            user.setLastModifiedDate(null);
                        }
                    }
                    case "3" -> System.out.println("thing added");  //add things to current user
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

                        userService.editProfile(user);
                        System.out.println("profile successfully updated");
                        userService.getUser(user);
                    }
                    case "5" -> {
                        break userControl;
                    }
                    case "exit" -> System.exit(0);
                }

            }

        }

    }
}
