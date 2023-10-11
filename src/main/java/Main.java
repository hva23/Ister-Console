import com.ister.common.RequestStatus;
import com.ister.domain.Location;
import com.ister.domain.TelemetryData;
import com.ister.domain.Things;
import com.ister.domain.User;
import com.ister.service.*;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

public class Main {

    public static void main(String[] args) {

        MenuService menuService = new MenuService();

        System.out.println("""
                welcome to ister console application
                note anytime you enter "exit", application will exit.
                """);
        menuService.menu();
    }
}
