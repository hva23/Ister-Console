package com.ister.repository;

import com.ister.domain.TelemetryData;
import com.ister.domain.Things;
import com.ister.domain.User;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ThingsInMemoryRepositoryImpl implements ThingsRepository{

    List<Things> thingsInMemory = new ArrayList<>();
    @Override
    public void create(Things thing) {
        thingsInMemory.add(thing);
    }

    @Override
    public void delete(Things thing) {
        thingsInMemory.remove(thing);
    }

    @Override
    public void delete(List<Things> things){

    }

    @Override
    public List<Things> getAll() {
        return thingsInMemory;
    }

    @Override
    public List<TelemetryData> getAllRecords() {
        return null;//thingsInMemory.stream().filter(t -> t.getAttributes())
    }

    @Override
    public TelemetryData getThingRecord(Things thing) {
        return null;
    }

    @Override
    public Optional<Things> findById(Long id) {
        return null;
    }

    @Override
    public Optional<Things> findBySerialNumber(String serialNumber) {
        return null;
    }

    @Override
    public List<Things> findByUser(User user){
        return null;
    }
}
