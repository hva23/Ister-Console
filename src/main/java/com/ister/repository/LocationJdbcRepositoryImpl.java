package com.ister.repository;

import com.ister.domain.Location;
import com.ister.domain.User;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class LocationJdbcRepository implements BaseRespository<Location, Long> {
    final private String rawUrl;
    final private String username;
    final private String password;


    public LocationJdbcRepository(String rawUrl, String username, String password) {
        this.rawUrl = rawUrl;
        this.username = username;
        this.password = password;
    }

    @Override
    public boolean create(Location location) {
        try {
            long lastLocationId = 0;
            Statement statement;
            ResultSet resultSet;
            boolean result;
            String query;

            Object[] obj = read("LOCATION", new String[] {"ID"}, null);

            resultSet = (ResultSet) obj[0];
            statement = (Statement) obj[2];

            while (resultSet.next()) {
                lastLocationId = resultSet.getLong("ID");
            }

            query = String.format("INSERT INTO LOCATION VALUES(%d, \"%s\", \"%s\", %.4f, %.4f)",
                    lastLocationId + 1,
                    location.getProvince(),
                    location.getCity(),
                    location.getLatitude(),
                    location.getLongitude());

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
        return false;
    }

    @Override
    public boolean update(Location location) {
        return false;
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
