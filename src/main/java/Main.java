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

    public static void main(String args[]) {

        MenuService menuService = new MenuService();

        System.out.println("""
                welcome to ister console application
                note anytime you enter "exit", application will exit.
                """);

        menuService.menu();
        /*
        ThingsService thingsService = new ThingsService();
        System.out.println(thingsService.getThingData("200s-9fad-1dhg"));*//*
        TelemetryData telemetryData = new TelemetryData();
        Things things = new Things();
        things.setId(1L);
        telemetryData.setId(4L);
        telemetryData.setData("Status", "Safe");
        telemetryData.setThing(things);
        TelemetryDataService telemetryDataService = new TelemetryDataService();
//        telemetryDataService.addTelemetryData(telemetryData);
        System.out.println(telemetryDataService.getAllTelemetryData());
        telemetryDataService.deleteTelemetryData(telemetryData);
        System.out.println(telemetryDataService.getAllTelemetryData());
        System.out.println(telemetryDataService.getTelemetryData(4L));*/
    }
}
