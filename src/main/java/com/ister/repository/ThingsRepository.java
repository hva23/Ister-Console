package com.ister.repository;

import com.ister.domain.TelemetryData;
import com.ister.domain.Things;
import com.ister.domain.User;

import java.util.List;
import java.util.Optional;

public interface ThingsRepository {
    void create(Things thing);

    boolean update(Things thing);

    void delete(Things thing);

    void delete(List<Things> things);

    List<Things> getAll();

    List<TelemetryData> getAllRecords();

    TelemetryData getThingRecord(Things thing);

    Optional<Things> findById(Long id);

    Optional<Things> findBySerialNumber(String serialNumber);

    List<Things> findByUser(User user);


}
