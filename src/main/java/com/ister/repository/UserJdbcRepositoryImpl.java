package com.ister.repository;

import com.ister.domain.Location;
import com.ister.domain.User;
import com.ister.service.QueryBuilder;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.sql.*;
import java.util.*;

public class UserJdbcRepositoryImpl implements BaseRespository<User, String> {

    private final String rawUrl;
    private final String username;
    private final String password;

    private final String TABLE_NAME = "USERS";

    public UserJdbcRepositoryImpl(String rawUrl, String username, String password) {
        this.rawUrl = rawUrl;
        this.username = username;
        this.password = password;
    }

    public boolean create(User user) {
        try (Connection con = DriverManager.getConnection(rawUrl, username, password);
             Statement statement = con.createStatement();) {

            QueryBuilder queryBuilder = new QueryBuilder();
            String query;

            Object[] values = new Object[]{
                    UUID.randomUUID().toString(),
                    user.getUsername(),
                    user.getPassword(),
                    user.getEmail(),
                    user.getPhoneNumber(),
                    user.getCreatedDate(),
                    user.getLastModifiedDate()
            };

            query = queryBuilder.create(TABLE_NAME, values);
            return statement.executeUpdate(query) > 0;


        } catch (Exception ex) {
            ex.printStackTrace();
            return false;
        }

    }

    public boolean delete(User user) {
        try (Connection con = DriverManager.getConnection(rawUrl, username, password);
             Statement statement = con.createStatement();) {

            QueryBuilder queryBuilder = new QueryBuilder();
            Map<String, Object> condition = new HashMap<>();
            String query;

            condition.put("UUID", user.getId());

            query = queryBuilder.delete(TABLE_NAME, condition);

            return statement.executeUpdate(query) > 0;
        } catch (Exception ex) {
            ex.printStackTrace();
            return false;
        }
    }

    public boolean update(User user) {
        java.util.Date date = new java.util.Date();
        String currentDateTime = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(date);
        try (Connection con = DriverManager.getConnection(rawUrl, username, password);
             Statement statement = con.createStatement();) {

            QueryBuilder queryBuilder = new QueryBuilder();
            Map<String, Object> columnAndValues = new HashMap<>();
            Map<String, Object> conditions = new HashMap<>();
            String query;

            columnAndValues.put("USERNAME", user.getUsername());
            columnAndValues.put("PASSWORD", user.getPassword());
            columnAndValues.put("EMAIL", user.getEmail());
            columnAndValues.put("PHONE_NUMBER", user.getPhoneNumber());
            columnAndValues.put("CREATED_DATE", user.getCreatedDate());
            columnAndValues.put("LAST_MODIFIED_DATE", currentDateTime);

            conditions.put("UUID", user.getId());

            query = queryBuilder.update(TABLE_NAME, columnAndValues, conditions);

            return statement.executeUpdate(query) > 0;
        } catch (Exception ex) {
            ex.printStackTrace();
            return false;
        }
    }

    public List<User> getAll() {
        try {
            User user;
            List<User> userList = new ArrayList<>();
            Object[] obj;

            obj = read(TABLE_NAME, null, null);

            do {
                user = setUserFields((ResultSet) obj[0]);

                userList.add(user);
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
    public Optional<User> findById(String id) {
        try {
            ResultSet resultSet;
            User user = new User();
            Map<String, Object> condition = new HashMap<>();

            condition.put("UUID", id);
            Object[] obj = read("USERS", null, condition);

            resultSet = (ResultSet) obj[0];

            user = setUserFields(resultSet);

            resultSet.close();
            ((Statement) obj[2]).close();
            ((Connection) obj[1]).close();

            return Optional.of(user);
        } catch (Exception ex) {
            ex.printStackTrace();
            return Optional.empty();
        }
    }

    public Optional<User> findByUsername(String username) {
        try {
            ResultSet resultSet;
            User user = new User();
            Map<String, Object> condition = new HashMap<>();

            condition.put("USERNAME", username);
            Object[] obj = read("USERS", null, condition);

            resultSet = (ResultSet) obj[0];

            user = setUserFields(resultSet);

            resultSet.close();
            ((Statement) obj[2]).close();
            ((Connection) obj[1]).close();

            return Optional.of(user);
        } catch (Exception ex) {
            ex.printStackTrace();
            return Optional.empty();
        }
    }

    private User setUserFields(ResultSet resultSet) throws SQLException {
        User user = new User();

        user.setId(resultSet.getString("UUID"));
        user.setUsername(resultSet.getString("USERNAME"));
        user.setEmail(resultSet.getString("EMAIL"));
        user.setPassword(resultSet.getString("PASSWORD"));
        user.setPhoneNumber(resultSet.getString("PHONE_NUMBER"));
        user.setCreatedDate(resultSet.getDate("CREATED_DATE").toString() + " " + resultSet.getTime("CREATED_DATE").toString());
        user.setLastModifiedDate(resultSet.getDate("LAST_MODIFIED_DATE").toString() + " " + resultSet.getTime("LAST_MODIFIED_DATE").toString());

        return user;
    }
}
