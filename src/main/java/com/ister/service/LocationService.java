package com.ister.service;

import com.ister.common.RequestStatus;
import com.ister.domain.Location;
import com.ister.repository.LocationJdbcRepository;
import com.ister.repository.ThingsJdbcRepositoryImpl;

public class LocationService {
    LocationJdbcRepository locationJdbcRepository;
    public LocationService() {
        this.locationJdbcRepository = new LocationJdbcRepository("jdbc:mysql://localhost:8080/Ister", "root", "v@h@bI2442");
    }
    public RequestStatus addLocation(Location location) {
        return locationJdbcRepository.create(location) ? RequestStatus.Successful : RequestStatus.Failed;
    }

    public RequestStatus editLocation(Location location) {
        return RequestStatus.Failed;
    }

    public RequestStatus deleteLocation(Location location) {
        return RequestStatus.Failed;
    }

    public Location getLocationData(Long id) {
        return null;
    }

}
