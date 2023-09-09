package com.ister.repository;

import com.ister.domain.TelemetryData;
import com.ister.domain.Things;
import com.ister.domain.User;

import java.util.List;

public interface ThingsRepository {
    void create(Things thing);

    void delete(Things thing);
    void delete(List<Things> things);

    List<Things> getAll();

    List<TelemetryData> getAllRecords();

    TelemetryData getThingRecord(Things thing);

    Things findById(Long id);

    Things findBySerialNumber(String serialNumber);

    List<Things> findByUser(User user);


}
