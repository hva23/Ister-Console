package com.ister.repository;

import com.ister.domain.Location;
import com.ister.domain.Things;
import com.ister.domain.User;
import com.ister.service.QueryBuilder;

import java.sql.*;
import java.util.*;
import java.util.Date;

public class ThingsJdbcRepository implements BaseRespository<Things, Long> {

    private final String rawUrl;
    private final String username;
    private final String password;
    private final String TABLE_NAME = "USER_PRODUCTS";

    public ThingsJdbcRepository(String rawUrl, String username, String password) {
        this.rawUrl = rawUrl;
        this.username = username;
        this.password = password;
    }

    @Override
    public boolean create(Things thing) {
        try {
            java.util.Date date = new Date();
            String dateTime = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(date);

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

            do {
                if (resultSet.getLong("ID") > lastUserProductsId)
                    lastUserProductsId = resultSet.getLong("ID");
            }
            while (resultSet.next());//End

            //Because we just have 2 product
            productId = thing.getSerialNumber().substring(0, 3).contentEquals("100") ? 1 : 2;

            values = new Object[]{
                    lastUserProductsId + 1,
                    thing.getName(),
                    thing.getSerialNumber(),
                    thing.getUser().getId(),
                    productId,
                    thing.getLocation().getId(),
                    dateTime
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
        try {
            Things things;
            List<Things> userList = new ArrayList<>();
            Object[] obj;

            obj = read(TABLE_NAME, null, null);

            do {
                things = setThingFields((ResultSet) obj[0]);

                userList.add(things);
            }
            while (((ResultSet) obj[0]).next());

            ((ResultSet) obj[0]).close();
            ((Statement) obj[2]).close();
            ((Connection) obj[1]).close();

            return userList;
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    @Override
    public Optional<Things> findById(Long id) {
        try {
            ResultSet resultSet;
            Things thing;
            Map<String, Object> condition = new HashMap<>();

            condition.put("ID", id);
            Object[] obj = read(TABLE_NAME, null, condition);

            if(obj == null)
                return Optional.empty();

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
        try {
            Things things;
            List<Things> userList = new ArrayList<>();
            Map<String, Object> condition = new HashMap<>();
            Object[] obj;

            if (user.getId() != null)
                condition.put("USER_ID", user.getId());
            else
                return null;

            obj = read(TABLE_NAME, null, condition);

            do {
                things = setThingFields((ResultSet) obj[0]);

                userList.add(things);
            }
            while (((ResultSet) obj[0]).next());

            ((ResultSet) obj[0]).close();
            ((Statement) obj[2]).close();
            ((Connection) obj[1]).close();

            return userList;
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }


    private Things setThingFields(ResultSet resultSet) throws SQLException {
        Things thing = new Things();
        Location location = new Location();
        User user = new User();

        user.setId(resultSet.getString("USER_ID"));
        location.setId(resultSet.getLong("LOCATION_ID"));

        thing.setId(resultSet.getLong("ID"));
        thing.setName(resultSet.getString("NAME"));
        thing.setSerialNumber(resultSet.getString("SERIAL_NUMBER"));
        thing.setLocation(location);
        thing.setUser(user);
        thing.setCreatedDate(resultSet.getString("CREATED_DATE"));

        return thing;
    }
}
