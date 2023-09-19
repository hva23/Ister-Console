package com.ister.repository;

import com.ister.domain.Location;
import com.ister.domain.TelemetryData;
import com.ister.domain.Things;
import com.ister.domain.User;
import com.ister.service.QueryBuilder;
import com.ister.service.ThingsService;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.sql.*;
import java.util.*;

public class ThingsJdbcRepositoryImpl implements BaseRespository<Things, Long> {

    private final String rawUrl;
    private final String username;
    private final String password;
    private final String TABLE_NAME = "USER_PRODUCTS";

    public ThingsJdbcRepositoryImpl(String rawUrl, String username, String password) {
        this.rawUrl = rawUrl;
        this.username = username;
        this.password = password;
    }

    @Override
    public boolean create(Things thing) {
        try {
            QueryBuilder queryBuilder = new QueryBuilder();
            ResultSet resultSet;
            Statement statement;
            Object[] values;
            String query;
            long lastUserProductsId = 0;
            long productId = 0;
            boolean result;

            //Read last user product ID
            Object[] obj = read(TABLE_NAME, new String[]{"ID"}, null);

            resultSet = (ResultSet) obj[0];
            statement = (Statement) obj[2];

            while (resultSet.next()) {
                if (resultSet.getLong("ID") > lastUserProductsId)
                    lastUserProductsId = resultSet.getLong("ID");
            }//End

            //Because we just have 2 product
            productId = thing.getSerialNumber().substring(0, 3).contentEquals("100") ? 1 : 2;

            values = new Object[]{
                    lastUserProductsId + 1,
                    thing.getName(),
                    thing.getSerialNumber(),
                    thing.getUser().getId(),
                    productId,
                    thing.getLocation().getId()
            };

            query = queryBuilder.create(TABLE_NAME, values);

            result = statement.executeUpdate(query) > 0;

            resultSet.close();
            statement.close();
            ((Connection) obj[1]).close();

            return result;

        } catch (Exception ex) {
            ex.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean update(Things thing) {
        try (Connection con = DriverManager.getConnection(rawUrl, username, password);
             Statement statement = con.createStatement();) {

            QueryBuilder queryBuilder = new QueryBuilder();
            Map<String, Object> columnAndValues = new HashMap<>();
            Map<String, Object> conditions = new HashMap<>();
            String query;
            int productId;

            //Because we just have 2 product
            productId = thing.getSerialNumber().substring(0, 3).contentEquals("100") ? 1 : 2;

            columnAndValues.put("NAME", thing.getName());
            columnAndValues.put("SERIAL_NUMBER", thing.getSerialNumber());
            columnAndValues.put("USER_ID", thing.getUser().getId());
            columnAndValues.put("PRODUCT_ID", productId);
            columnAndValues.put("LOCATION_ID", thing.getLocation().getId());

            conditions.put("ID", thing.getId());

            query = queryBuilder.update(TABLE_NAME, columnAndValues, conditions);

            return statement.executeUpdate(query) > 0;
        } catch (Exception ex) {
            ex.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean delete(Things thing) {
        try (Connection con = DriverManager.getConnection(rawUrl, username, password);
             Statement statement = con.createStatement();) {

            QueryBuilder queryBuilder = new QueryBuilder();
            Map<String, Object> condition = new HashMap<>();
            String query;

            condition.put("ID", thing.getId().toString());

            query = queryBuilder.delete(TABLE_NAME, condition);

            return statement.executeUpdate(query) > 0;
        } catch (Exception ex) {
            ex.printStackTrace();
            return false;
        }
    }

    @Override
    public List<Things> getAll() {
        return null;
    }

    @Override
    public Optional<Things> findById(Long id) {
        try {
            ResultSet resultSet;
            Things thing;
            Map<String, Object> condition = new HashMap<>();

            condition.put("ID", id);
            Object[] obj = read(TABLE_NAME, null, condition);

            resultSet = (ResultSet) obj[0];

            thing = setThingFields(resultSet);

            resultSet.close();
            ((Statement) obj[2]).close();
            ((Connection) obj[1]).close();

            return Optional.of(thing);
        } catch (Exception ex) {
            ex.printStackTrace();
            return Optional.empty();
        }
    }


    public Optional<Things> findBySerialNumber(String serialNumber) {
        try {
            ResultSet resultSet;
            Things thing;
            Map<String, Object> condition = new HashMap<>();

            condition.put("SERIAL_NUMBER", serialNumber);
            Object[] obj = read(TABLE_NAME, null, condition);

            resultSet = (ResultSet) obj[0];

            thing = setThingFields(resultSet);

            resultSet.close();
            ((Statement) obj[2]).close();
            ((Connection) obj[1]).close();

            return Optional.of(thing);
        } catch (Exception ex) {
            ex.printStackTrace();
            return Optional.empty();
        }
    }


    public List<Things> findByUser(User user) {
        return null;
    }


    private Things setThingFields(ResultSet resultSet) throws SQLException {
        Things thing = new Things();
        thing.setId(resultSet.getLong("ID"));
        thing.setName(resultSet.getString("NAME"));
        thing.setSerialNumber(resultSet.getString("SERIAL_NUMBER"));
    }
}
