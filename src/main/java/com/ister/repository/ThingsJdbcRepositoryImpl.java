package com.ister.repository;

import com.ister.domain.TelemetryData;
import com.ister.domain.Things;
import com.ister.domain.User;
import org.jetbrains.annotations.Nullable;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.List;
import java.util.Optional;

public class ThingsJdbcRepositoryImpl implements BaseRespository<Things, Long> {

    private final String rawUrl;
    private final String username;
    private final String password;

    public ThingsJdbcRepositoryImpl(String rawUrl, String username, String password) {
        this.rawUrl = rawUrl;
        this.username = username;
        this.password = password;
    }

    @Override
    public boolean create(Things thing) {
        try {
            Connection connection = DriverManager.getConnection(rawUrl, username, password);
            Statement statement = connection.createStatement();  //insert into USER_PRODUCTS table

            long lastUserProductsId;
            long productId;

            lastUserProductsId = read("USER_PRODUCTS", new String[]{"ID"}).getLong("ID");

            productId = thing.getSerialNumber().substring(0, 3).contentEquals("100") ? 1 : 2;

            statement.executeUpdate(String.format("insert into USER_PRODUCTS " +
                            "values(%d, \"%s\", \"%s\", \"%s\", %d, %d)",
                    lastUserProductsId + 1, thing.getName(), thing.getSerialNumber(),
                    thing.getUser().getId(), productId, thing.getLocation().getId()));
            return true;

        } catch (Exception ex) {
            ex.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean update(Things thing) {
        return false;
    }

    @Override
    public boolean delete(Things thing) {
        return true;
    }

    public void delete(List<Things> things) {

    }

    private ResultSet read(String tableName, @Nullable String[] columns) {
        try {
            Connection con = DriverManager.getConnection(rawUrl, username, password);
            Statement statement = con.createStatement();
            ResultSet resultSet;
            StringBuilder stringBuilder = new StringBuilder();

            if (columns.length > 0) {
                for (int i = 0; i < columns.length; i++) {
                    stringBuilder.append(columns[i]);
                    if (i < columns.length - 1) stringBuilder.append(", ");
                }
            } else stringBuilder.append("*");

            resultSet = statement.executeQuery(String.format("SELECT %s FROM %s", stringBuilder, tableName));

            if (resultSet.next())
                return resultSet;

            return null;
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    @Override
    public List<Things> getAll() {
        return null;
    }

    @Override
    public Optional<Things> findById(Long id) {
        return null;
    }

    public Optional<Things> findBySerialNumber(String serialNumber) {
        return null;
    }


    public List<Things> findByUser(User user) {
        return null;
    }
}
