package com.ister.service;

import com.ister.common.RequestStatus;
import com.ister.domain.Location;
import com.ister.domain.TelemetryData;
import com.ister.domain.Things;
import com.ister.domain.User;
import com.ister.repository.ThingsInMemoryRepositoryImpl;
import com.ister.repository.ThingsRepository;

import java.util.*;

public class ThingsService {
    ThingsRepository thingsRepository = new ThingsInMemoryRepositoryImpl();

    public RequestStatus addThing(Things thing) {
        Optional<Things> dbThingOptional = thingsRepository.findById(thing.getId());
        if (dbThingOptional.isPresent()) {
            System.out.printf("A thing exists with this ID : %d", thing.getId());
            return RequestStatus.Failed;
        }
        thingsRepository.create(thing);
        return RequestStatus.Successful;
    }

    public RequestStatus editThing(Things thing) {
        if (thingsRepository.update(thing))
            return RequestStatus.Successful;
        else {
            System.out.println("This thing doesn't exists!");
            return RequestStatus.Failed;
        }
    }

    public RequestStatus delete(Things thing) {
        Optional<Things> dbThingOptional = thingsRepository.findById(thing.getId());
        if (dbThingOptional.isPresent()) {
            thingsRepository.delete(thing);
            return RequestStatus.Successful;
        }
        System.out.println("This thing doesn't exists or something went wrong!");
        return RequestStatus.Failed;
    }

    public Things getThing(String serialNumber) {
        Optional<Things> thingsOptional = thingsRepository.findBySerialNumber(serialNumber);
        return thingsOptional.orElse(null);
    }

    public String getThingData(String serialNumber) {
        Optional<Things> thingOptional = thingsRepository.findBySerialNumber(serialNumber);
        if (thingOptional.isPresent()) {

            Location location = thingOptional.get().getLocation();
            StringBuilder telemetryData = new StringBuilder();
            Map<String, Object> telemetryDataSet;
            if(thingOptional.get().getTelemetryData() != null) {
                telemetryDataSet = thingOptional.get().getTelemetryData().getData();

                for (Map.Entry<String, Object> data : telemetryDataSet.entrySet()) {
                    telemetryData.append(data.getKey());
                    telemetryData.append(" -> ");
                    telemetryData.append(data.getValue());
                }
            }
            return String.format("""
                            Thing name : %s
                            Thing ID : %d
                            Thing serial number : %s
                            Thing location (latitude, longitude) : %s(%.2f, %.2f)
                            Thing owner (username, ID) : %s , %d
                            Telemetry data : %s
                            """,
                    thingOptional.get().getName(),
                    thingOptional.get().getId(),
                    thingOptional.get().getSerialNumber(),
                    location.getName(),
                    location.getLatitude(),
                    location.getLongitude(),
                    thingOptional.get().getUser().getUsername(),
                    thingOptional.get().getUser().getId(),
                    telemetryData);
        }
        return null;

    }

    public List<Things> getUserThing(User user) {
        return thingsRepository.findByUser(user);
    }

    public TelemetryData getTelemetryData(Things thing) {
        return thing.getTelemetryData();
    }

    public RequestStatus sendTelemetryData(TelemetryData telemetryData) {
        telemetryData.getThing().setTelemetryData(telemetryData);
        thingsRepository.update(telemetryData.getThing());
        return RequestStatus.Successful;
    }
}
