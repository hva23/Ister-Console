package com.ister.service;

import com.ister.common.RequestStatus;
import com.ister.domain.TelemetryData;
import com.ister.repository.TelemetryDataJdbcRepository;

import java.util.List;
import java.util.Optional;

public class TelemetryDataService {
    TelemetryDataJdbcRepository telemetryDataRepository = new TelemetryDataJdbcRepository("jdbc:mysql://localhost:8080/Ister", "root", "v@h@bI2442");

    public RequestStatus addTelemetryData(TelemetryData telemetryData) {
        if (telemetryDataRepository.findById(telemetryData.getId()).isPresent()) {
            System.out.println("The data with this id already exists\nOperation failed");
            return RequestStatus.Failed;
        } else {
            if (telemetryDataRepository.create(telemetryData)) {
                System.out.println("Telemetry data submitted");
                return RequestStatus.Successful;
            } else {
                System.out.println("Something went wrong!");
                return RequestStatus.Failed;
            }
        }
    }

    public RequestStatus deleteTelemetryData(TelemetryData telemetryData) {
        if (telemetryDataRepository.findById(telemetryData.getId()).isPresent()) {
            if (telemetryDataRepository.delete(telemetryData)) {
                System.out.println("The telemetry data deleted successfully");
                return RequestStatus.Successful;
            } else {
                System.out.println("Something went wrong!");
                return RequestStatus.Failed;
            }
        } else {
            System.out.println("The telemetry data doesn't exist");
            return RequestStatus.Failed;
        }
    }

    public String getTelemetryData(Long id) {
        Optional<TelemetryData> telemetryDataOptional = telemetryDataRepository.findById(id);
        return telemetryDataOptional.map(telemetryData -> String.format("""
                        Telemetry Data ID : %d
                        Data : %s -> %s
                        Received date : %s
                        Received time : %s
                                                    
                        """,
                id,
                telemetryData.getData().keySet().toArray(new String[1])[0],
                telemetryData.getData().values().toArray(new Object[1])[0],
                telemetryData.getCreatedDate().split(" ")[0],
                telemetryData.getCreatedDate().split(" ")[1])).orElse("The telemetry data doesn't exist");
    }

    public String getAllTelemetryData() {
        TelemetryData telemetryData;
        List<TelemetryData> telemetryDataList = telemetryDataRepository.getAll();
        StringBuilder stringBuilder = new StringBuilder();

        for (int i = 0; i < telemetryDataList.size(); i++) {
            telemetryData = telemetryDataList.get(i);

            String key = telemetryData.getData().keySet().toArray(new String[1])[0];
            String val = (String) telemetryData.getData().values().toArray(new Object[1])[0];

            stringBuilder.append(String.format("""
                            -- #%02d --
                            Telemetry Data ID : %d
                            Data : %s -> %s
                            Received date : %s
                            Received time : %s
                                                        
                            """,
                    i + 1,
                    telemetryData.getId(),
                    key,
                    val,
                    telemetryData.getCreatedDate().split(" ")[0],
                    telemetryData.getCreatedDate().split(" ")[1]));
        }

        return stringBuilder.toString();
    }

}
