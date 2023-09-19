package com.ister.service;

import com.ister.common.RequestStatus;
import com.ister.domain.Location;
import com.ister.repository.LocationJdbcRepositoryImpl;

import java.util.*;

public class LocationService {
    LocationJdbcRepositoryImpl locationJdbcRepositoryImpl;

    public LocationService() {
        this.locationJdbcRepositoryImpl = new LocationJdbcRepositoryImpl("jdbc:mysql://localhost:8080/Ister", "root", "v@h@bI2442");
    }

    public RequestStatus addLocation(Location location) {
        if (locationJdbcRepositoryImpl.findById(location.getId()).isPresent()) {
            System.out.println("The location already exists\nOperation failed");
            return RequestStatus.Failed;
        } else {
            if (locationJdbcRepositoryImpl.create(location)) {
                System.out.println("Location added successfully");
                return RequestStatus.Successful;
            } else {
                System.out.println("Something went wrong!");
                return RequestStatus.Failed;
            }

        }
    }

    public RequestStatus editLocation(Location location) {
        if (locationJdbcRepositoryImpl.findById(location.getId()).isPresent()) {
            if (locationJdbcRepositoryImpl.update(location)) {
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

    public RequestStatus deleteLocation(Location location) {
        if (locationJdbcRepositoryImpl.findById(location.getId()).isPresent()) {
            if (locationJdbcRepositoryImpl.delete(location)) {
                System.out.println("the location deleted successfully");
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

    public String getLocationData(Long id) {
        Optional<Location> locationOptional = locationJdbcRepositoryImpl.findById(id);
        return locationOptional.map(location -> String.format("""
                        Location ID : %d
                        Province : %s
                        City : %s
                        Latitude : %s
                        Longitude : %s
                        """,
                location.getId(),
                location.getProvince(),
                location.getCity(),
                location.getLatitude(),
                location.getLongitude())).orElse("Location doesn't exist");
    }

    public String getAllLocations() {
        Location location;
        List<Location> locationList = locationJdbcRepositoryImpl.getAll();
        StringBuilder stringBuilder = new StringBuilder();

        for (int i = 0; i < locationList.size(); i++) {
            location = locationList.get(i);
            stringBuilder.append(String.format("""
                            -- #%02d --
                            Location ID : %d
                            Province : %s
                            City : %s
                            Latitude : %s
                            Longitude : %s
                            
                            """,
                    i + 1,
                    location.getId(),
                    location.getProvince(),
                    location.getCity(),
                    location.getLatitude(),
                    location.getLongitude()));
        }

        return stringBuilder.toString();
    }
}
