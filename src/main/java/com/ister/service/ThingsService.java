package com.ister.service;

import com.ister.common.RequestStatus;
import com.ister.domain.TelemetryData;
import com.ister.domain.Things;
import com.ister.domain.User;
import com.ister.repository.ThingsInMemoryRepositoryImpl;
import com.ister.repository.ThingsRepository;

public class ThingsService {
    ThingsRepository thingsRepository = new ThingsInMemoryRepositoryImpl();
    public RequestStatus addThing(Things thing){
        try{
            thingsRepository.findById(thing.getId());
            System.out.printf("A thing exists with this ID : %d", thing.getId());
            return RequestStatus.Failed;
        }catch (Exception ex){
            thingsRepository.create(thing);
        }
        return RequestStatus.Successful;
    }

    public RequestStatus editThing(Things thing){
        try{
            thingsRepository.findById(thing.getId());
            thingsRepository.delete(thing);
            thingsRepository.create(thing);
        }catch (Exception ex){
            System.out.println("This user doesn't exists!");
        }
        return RequestStatus.Successful;
    }

    public RequestStatus delete(Things thing){
        try{
            thingsRepository.findById(thing.getId());
            thingsRepository.delete(thing);
            return RequestStatus.Successful;
        }catch (Exception ex){
            System.out.println("This thing doesn't exists or something went wrong!");
            return RequestStatus.Failed;
        }
    }

    public TelemetryData getData(Things thing){
        return null;
    }

    public RequestStatus sendData(Things thing){
        return RequestStatus.Successful;
    }
}
