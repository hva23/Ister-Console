package com.ister.service;

import com.ister.common.RequestStatus;
import com.ister.domain.Location;
import com.ister.domain.TelemetryData;
import com.ister.domain.Things;
import com.ister.domain.User;
import com.ister.repository.ThingsInMemoryRepositoryImpl;
import com.ister.repository.ThingsJdbcRepositoryImpl;
import com.sun.source.tree.BreakTree;

import java.util.*;

public class ThingsService {
    ThingsJdbcRepositoryImpl thingsRepository;

    public ThingsService() {
        this.thingsRepository = new ThingsJdbcRepositoryImpl("jdbc:mysql://localhost:8080/Ister", "root", "v@h@bI2442");
    }
    public RequestStatus addThing(Things thing) {
        if (thingsRepository.findById(thing.getId()).isPresent()) {
            System.out.println("The thing/product already exists\nOperation failed");
            return RequestStatus.Failed;
        } else {
            if (thingsRepository.create(thing)) {
                System.out.println("Thing/Product added successfully");
                return RequestStatus.Successful;
            } else {
                System.out.println("Something went wrong!");
                return RequestStatus.Failed;
            }
        }
    }

    public RequestStatus editThing(Things thing) {
        if (thingsRepository.findById(thing.getId()).isPresent()) {
            if (thingsRepository.update(thing)) {
                System.out.println("the location updated successfully");
                return RequestStatus.Successful;
            } else {
                System.out.println("Something went wrong!");
                return RequestStatus.Failed;
            }
        } else {
            System.out.println("The location doesn't exist");
            return RequestStatus.Failed;
        }
    }

    public RequestStatus deleteThing(Things thing) {
        if (thingsRepository.findById(thing.getId()).isPresent()) {
            if (thingsRepository.delete(thing)) {
                System.out.println("the thing/product deleted successfully");
                return RequestStatus.Successful;
            } else {
                System.out.println("Something went wrong!");
                return RequestStatus.Failed;
            }
        } else {
            System.out.println("The thing/product doesn't exist");
            return RequestStatus.Failed;
        }
    }

    public Things getThing(String serialNumber) {
        Optional<Things> thingsOptional = thingsRepository.findBySerialNumber(serialNumber);
        return thingsOptional.orElse(null);
    }

    public String getThingData(Things thing) {
       /* return String.format("""
                            Thing name : %s
                            Thing ID : %d
                            Thing serial number : %s
                            Thing location (latitude, longitude) : %s(%.2f, %.2f)
                            Thing owner (username, ID) : %s , %s
                            Telemetry data : %s
                            """,
                thing.getName(),
                thing.getId(),
                thing.getSerialNumber(),
                location.getName(),
                location.getLatitude(),
                location.getLongitude(),
                thing.getUser().getUsername(),
                thing.getUser().getId()/*,
                telemetryData);*/return null;
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
                            Thing owner (username, ID) : %s , %s
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
