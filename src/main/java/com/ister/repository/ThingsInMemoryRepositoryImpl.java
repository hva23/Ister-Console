package com.ister.repository;

import com.ister.domain.TelemetryData;
import com.ister.domain.Things;
import com.ister.domain.User;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ThingsInMemoryRepositoryImpl implements ThingsRepository {

    List<Things> thingsInMemory = new ArrayList<>();

    @Override
    public void create(Things thing) {
        thingsInMemory.add(thing);
    }

    @Override
    public boolean update(Things thing) {
        Optional<Things> dbThingOptional = findById(thing.getId());
        if(dbThingOptional.isPresent()){
            Things dbThing = dbThingOptional.get();

            dbThing.setName(thing.getName());
            dbThing.setSerialNumber(thing.getSerialNumber());
            dbThing.setLocation(thing.getLocation());
            dbThing.setAttributes(thing.getAttributes());

            thingsInMemory.remove(thing);
            thingsInMemory.add(dbThing);
            return true;
        }
        return false;
    }

    @Override
    public void delete(Things thing) {
        thingsInMemory.remove(thing);
    }

    @Override
    public void delete(List<Things> things) {
        /* Delete every thing in list from repository */
        for (Things thing : things) {
            thingsInMemory.remove(thing);
        }
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
        return thingsInMemory.stream().filter(t -> t.getId().equals(id)).findFirst();
    }

    @Override
    public Optional<Things> findBySerialNumber(String serialNumber) {
        return thingsInMemory.stream().filter(things -> things.getSerialNumber().contentEquals(serialNumber)).findFirst();
    }

    @Override
    public List<Things> findByUser(User user) {
        return thingsInMemory.stream().filter(things -> things.getUser().equals(user)).toList();
    }
}
