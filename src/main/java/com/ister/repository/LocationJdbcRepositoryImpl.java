package com.ister.repository;

import com.ister.domain.Location;
import com.ister.service.QueryBuilder;
import com.mysql.cj.jdbc.ConnectionImpl;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.*;

public class LocationJdbcRepositoryImpl implements BaseRespository<Location, Long> {
    final private String rawUrl;
    final private String username;
    final private String password;
    final private String TABLE_NAME = "LOCATION";


    public LocationJdbcRepositoryImpl(String rawUrl, String username, String password) {
        this.rawUrl = rawUrl;
        this.username = username;
        this.password = password;
    }

    @Override
    public boolean create(Location location) {
        try {
            QueryBuilder queryBuilder = new QueryBuilder();
            Object[] values;
            long lastLocationId = 0;
            Statement statement;
            ResultSet resultSet;
            boolean result;
            String query;

            Object[] obj = read("LOCATION", new String[]{"ID"}, null);

            resultSet = (ResultSet) obj[0];
            statement = (Statement) obj[2];

            while (resultSet.next()) {
                if (resultSet.getLong("ID") > lastLocationId)
                    lastLocationId = resultSet.getLong("ID");
            }

            values = new Object[]{
                    lastLocationId + 1,
                    location.getProvince(),
                    location.getCity(),
                    location.getLatitude(),
                    location.getLongitude()
            };

            query = queryBuilder.create("LOCATION", values);

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
    public boolean delete(Location location) {
        try (Connection con = DriverManager.getConnection(rawUrl, username, password);
             Statement statement = con.createStatement();) {

            QueryBuilder queryBuilder = new QueryBuilder();
            Map<String, Object> condition = new HashMap<>();
            String query;

            condition.put("ID", location.getId().toString());

            query = queryBuilder.delete("Location", condition);

            return statement.executeUpdate(query) > 0;
        } catch (Exception ex) {
            ex.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean update(Location location) {
        try (Connection con = DriverManager.getConnection(rawUrl, username, password);
             Statement statement = con.createStatement();) {

            QueryBuilder queryBuilder = new QueryBuilder();
            Map<String, Object> columnAndValues = new HashMap<>();
            Map<String, Object> conditions = new HashMap<>();
            String query;

            columnAndValues.put("PROVINCE", location.getProvince());
            columnAndValues.put("CITY", location.getCity());
            columnAndValues.put("LATITUDE", location.getLatitude());
            columnAndValues.put("LONGITUDE", location.getLongitude());

            conditions.put("ID", location.getId());
            query = queryBuilder.update(TABLE_NAME, columnAndValues, conditions);
            return true;
        } catch (Exception ex) {
            ex.printStackTrace();
            return false;
        }
    }

    @Override
    public List<Location> getAll() {
        return null;
    }

    @Override
    public Optional<Location> findById(Long id) {
        Location location;
        Map<String, String> condition = new HashMap<>();

        return Optional.empty();
    }


}
