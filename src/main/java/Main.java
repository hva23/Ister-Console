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

        while (true) {
            User user = new User();
            UserService userService = new UserService();

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
                user.setId(50L);
                user.setCreatedDate(new Date(2023, Calendar.SEPTEMBER, 9));

                userService.signUp(user);
                System.out.println("your account successfully created");
                userService.getUser(user);

            } else if (inputResult.contentEquals("2")) {  //login

                /* username */
                System.out.println("Enter your username : ");
                user.setUsername(in.next());

                /* password */
                System.out.println("Enter your password : ");
                user.setPassword(in.next());

                userService.login(user);
            } else {
                System.out.println("Please open your eyes and enter correct option");
                break;
            }
        }
    }
}
