import com.ister.common.RequestStatus;
import com.ister.domain.Location;
import com.ister.domain.Things;
import com.ister.domain.User;
import com.ister.service.LocationService;
import com.ister.service.MenuService;
import com.ister.service.ThingsService;
import com.ister.service.UserService;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

public class Main {

    public static void main(String args[]) {
        java.util.Date date = new java.util.Date();
        final String dateTime = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(date);
        /*MenuService menuService = new MenuService();

        System.out.println("""
                welcome to ister console application
                note anytime you enter "exit", application will exit.
                """);

        menuService.menu();*/
        /*
        UserService userService = new UserService();
        User user = new User();


        user.setId(UUID.randomUUID().toString());
        user.setUsername("hossein");
        user.setEmail("hossein@gmail.com");
        user.setPassword("1234");
        user.setPhoneNumber("09352455249");
        user.setCreatedDate(dateTime);
        user.setLastModifiedDate(dateTime);

        userService.signUp(user);

         */
        /*
        User user = new User();
        user.setId("b2c45aac-f853-4d88-8163-c9764c9afd69");
        Location location = new Location();
        location.setId(4L);
        ThingsService thingsService = new ThingsService();
        Things thing = new Things();

        thing.setName("camera 3");
        thing.setSerialNumber("100s-9glq-a1c5");
        thing.setUser(user);
        thing.setLocation(location);

        thingsService.addThing(thing);
*/
UserService userService = new UserService();
        System.out.println(userService.getAllUser());
    }
}
