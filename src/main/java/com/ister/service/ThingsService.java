package com.ister.service;

import com.ister.common.RequestStatus;
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

    public TelemetryData getData(Things thing) {
        TelemetryData telemetryData = new TelemetryData();
        /* get data from specified thing */
        Map<String, Object> data = new HashMap<String, Object>();
        data.put("Humanity", 66);
        data.put("Temperature", 23);
        telemetryData.setData(data);
        telemetryData.setThing(thing);
        return telemetryData;
    }

    public RequestStatus sendData(Things thing) {
        return RequestStatus.Successful;
    }
}
