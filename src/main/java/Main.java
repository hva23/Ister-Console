import com.ister.common.RequestStatus;
import com.ister.domain.User;
import com.ister.service.MenuService;
import com.ister.service.UserService;

import java.util.Calendar;
import java.util.Date;
import java.util.Scanner;

public class Main {

    public static void main(String args[]) {
        MenuService menuService = new MenuService();

        System.out.println("""
                welcome to ister console application
                note anytime you enter "exit", application will exit.
                """);

        menuService.menu();

    }
}
