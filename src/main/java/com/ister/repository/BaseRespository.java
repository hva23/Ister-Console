package com.ister.repository;

import com.ister.common.RequestStatus;
import com.ister.domain.User;
import com.ister.service.QueryBuilder;
import org.jetbrains.annotations.NotNull;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface BaseRespository<T, PK> {
    boolean create(T entity);

    boolean delete(T entity);

    boolean update(T entity);

    List<T> getAll();

    Optional<T> findById(PK id);

    default Object[] read(String tableName, String[] columns, Map<String, Object> conditions) {
        String url = "jdbc:mysql://localhost:8080/Ister";
        String username = "root";
        String password = "v@h@bI2442";

        QueryBuilder queryBuilder = new QueryBuilder();
        try {
            Connection connection = DriverManager.getConnection(url, username, password);
            Statement statement = connection.createStatement();
            ResultSet resultSet;

            String query;
            Object[] obj = new Object[3];

            //Make a READ query
            query = queryBuilder.read(tableName, columns, conditions);
            //Execute query
            resultSet = statement.executeQuery(query);

            if(resultSet.next()) {
                obj[0] = resultSet;
                obj[1] = connection;
                obj[2] = statement;
                return obj;
            } else {
                resultSet.close();
                statement.close();
                connection.close();
                return null;
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            return  null;
        }
    }
}
