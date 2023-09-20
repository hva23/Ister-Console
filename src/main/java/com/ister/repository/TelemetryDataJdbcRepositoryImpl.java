package com.ister.repository;

import com.ister.domain.Location;
import com.ister.domain.TelemetryData;
import com.ister.domain.Things;
import com.ister.service.QueryBuilder;

import java.sql.*;
import java.util.*;
import java.util.Date;

public class TelemetryDataJdbcRepositoryImpl implements BaseRespository<TelemetryData, Long> {
    final private String rawUrl;
    final private String username;
    final private String password;
    final private String TABLE_NAME = "TELEMETRY_DATA";

    public TelemetryDataJdbcRepositoryImpl(String rawUrl, String username, String password) {
        this.rawUrl = rawUrl;
        this.username = username;
        this.password = password;
    }

    @Override
    public boolean create(TelemetryData telemetryData) {
        try {
            java.util.Date date = new Date();
            String dateTime = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(date);

            QueryBuilder queryBuilder = new QueryBuilder();
            String key;
            String val;
            Object[] values;
            long lastTelemetryDataId = 0;
            Statement statement;
            ResultSet resultSet;
            boolean result;
            String query;

            Object[] obj = read(TABLE_NAME, new String[]{"ID"}, null);

            resultSet = (ResultSet) obj[0];
            statement = (Statement) obj[2];

            key = telemetryData.getData().keySet().toArray(new String[1])[0];
            val = (String) telemetryData.getData().values().toArray(new Object[1])[0];

            do {
                if (resultSet.getLong("ID") > lastTelemetryDataId)
                    lastTelemetryDataId = resultSet.getLong("ID");
            }
            while (resultSet.next());

            values = new Object[]{
                    lastTelemetryDataId + 1,
                    key,
                    val,
                    dateTime,
                    telemetryData.getThing().getId()
            };

            query = queryBuilder.create(TABLE_NAME, values);

            result = statement.executeUpdate(query) > 0;

            ((ResultSet) obj[0]).close(); //or resultSet.close();

            ((Statement) obj[2]).close(); //or statement.close();

            ((Connection) obj[1]).close();

            return result;
        } catch (Exception ex) {
            ex.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean delete(TelemetryData telemetryData) {
        try (Connection con = DriverManager.getConnection(rawUrl, username, password);
             Statement statement = con.createStatement();) {

            QueryBuilder queryBuilder = new QueryBuilder();
            Map<String, Object> condition = new HashMap<>();
            String query;

            condition.put("ID", telemetryData.getId().toString());

            query = queryBuilder.delete(TABLE_NAME, condition);

            return statement.executeUpdate(query) > 0;
        } catch (Exception ex) {
            ex.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean update(TelemetryData telemetryData) {
        return false;
    }

    @Override
    public List<TelemetryData> getAll() {
        try {
            Things thing = new Things();
            TelemetryData telemetryData = new TelemetryData();
            List<TelemetryData> telemetryDataList = new ArrayList<>();
            ResultSet resultSet;
            Object[] obj;

            obj = read(TABLE_NAME, null, null);
            resultSet = (ResultSet) obj[0];


            do {
                telemetryData = setTelemetryData(resultSet);

                telemetryDataList.add(telemetryData);
            }
            while (resultSet.next());

            resultSet.close();
            ((Statement) obj[2]).close();
            ((Connection) obj[1]).close();

            return telemetryDataList;
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    @Override
    public Optional<TelemetryData> findById(Long id) {
        /*
            obj[0] -> ResultSet
            obj[1] -> Connection
            obj[2] -> Statement
         */
        try {
            TelemetryData telemetryData = new TelemetryData();
            Map<String, Object> conditions = new HashMap<>();
            Things thing = new Things();
            ResultSet resultSet;
            conditions.put("ID", id);

            Object[] obj = read(TABLE_NAME, null, conditions);

            if(obj == null)
                return Optional.empty();

            resultSet = (ResultSet) obj[0];

            thing.setId(resultSet.getLong("USER_PRODUCTS_ID"));

            telemetryData.setData(resultSet.getString("DATA_KEY"), resultSet.getString("DATA_VALUE"));
            telemetryData.setCreatedDate(resultSet.getDate("CREATED_DATE") + " " + resultSet.getTime("CREATED_DATE"));
            telemetryData.setThing(thing);

            resultSet.close();
            ((Statement) obj[2]).close();
            ((Connection) obj[1]).close();

            return Optional.of(telemetryData);
        } catch (Exception ex) {
            ex.printStackTrace();
            return Optional.empty();
        }
    }

    private TelemetryData setTelemetryData(ResultSet resultSet) throws SQLException {
        Things thing = new Things();
        TelemetryData telemetryData = new TelemetryData();

        thing.setId(resultSet.getLong("USER_PRODUCTS_ID"));

        telemetryData.setId(resultSet.getLong("ID"));
        telemetryData.setData(resultSet.getString("DATA_KEY"), resultSet.getString("DATA_VALUE"));
        telemetryData.setCreatedDate(resultSet.getDate("CREATED_DATE") + " " + resultSet.getTime("CREATED_DATE"));
        telemetryData.setThing(thing);

        return telemetryData;
    }
}
