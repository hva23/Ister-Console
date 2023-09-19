package com.ister.service;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Map;

public class QueryBuilder {

    private String mapToKeyValue(@NotNull Map<String, Object> data, @NotNull String seperator) {
        StringBuilder stringBuilder = new StringBuilder();
        //making column1 = value1 and so on with string builder
        for (Map.Entry entry : data.entrySet()) {
            stringBuilder.append(entry.getKey());
            stringBuilder.append(" = \"");
            stringBuilder.append(entry.getValue());
            stringBuilder.append("\" ");
            stringBuilder.append(seperator);//should be AND or ,
            stringBuilder.append(" ");
        }
        stringBuilder.delete(stringBuilder.length() - (seperator.length() + 2), stringBuilder.length()); //delete last "," or "AND"
        return stringBuilder.toString();
    }

    public String create(@NotNull String tableName, Object[] values) {
        StringBuilder valuesStringBuilder = new StringBuilder();
        String query;

        //Constructing values like "value1", "value2", "value3", ... and so on.
        for (int i = 0; i < values.length; i++) {
            if (values[i] != null) valuesStringBuilder.append(String.format("\"%s\"", values[i]));
            else valuesStringBuilder.append(values[i]);
            if (i < values.length - 1) valuesStringBuilder.append(", ");
        }

        query = String.format("INSERT INTO %S VALUES(%s)", tableName, valuesStringBuilder);
        return query;
    }

    public String read(@NotNull String tableName, @Nullable String[] columns, @Nullable Map<String, Object> conditions) {
        StringBuilder stringBuilder = new StringBuilder();
        String conditionSequence;
        String query;

        //Constructing columns like column1, column2, column3, ... OR *.
        if (columns != null && columns.length > 0) {
            for (String column : columns) {
                stringBuilder.append(column);
                stringBuilder.append(", ");
            }
            stringBuilder.deleteCharAt(stringBuilder.length() - 2); //Delete last , character
        } else stringBuilder.append("*");

        if (conditions != null) {
            //Constructing condition sequence just like key1 = value1, key2 = value2, ... and so on.
            conditionSequence = mapToKeyValue(conditions, "AND");
            query = String.format("SELECT %s FROM %S WHERE %s", stringBuilder, tableName, conditionSequence);
        } else
            query = String.format("SELECT %s FROM %S", stringBuilder, tableName);

        return query;
    }

    public String update(@NotNull String tableName, @NotNull Map<String, Object> columnsAndValues, @Nullable Map<String, Object> conditions) {
        String columnsAndValuesSequence;
        String conditionsSequence;
        String query;

        //Constructing columns and values like column1 = value1, column2 = value2, ... and so on.
        columnsAndValuesSequence = mapToKeyValue(columnsAndValues, ",");

        if (conditions != null) {
            //Constructing condition sequence just like key1 = value1, key2 = value2, ... and so on.
            conditionsSequence = mapToKeyValue(conditions, "AND");
            query = String.format("UPDATE %S SET %s WHERE %s", tableName, columnsAndValuesSequence, conditionsSequence);
        } else
            query = String.format("UPDATE %S SET %s", tableName, columnsAndValuesSequence);

        return query;
    }

    public String delete(@NotNull String tableName, @Nullable Map<String, Object> conditions) {
        String query;
        String conditionsSequence;

        if (conditions != null) {
            //Constructing condition sequence just like key1 = value1, key2 = value2, ... and so on.
            conditionsSequence = mapToKeyValue(conditions, "AND");
            query = String.format("DELETE FROM %S WHERE %s", tableName, conditionsSequence);
        } else
            query = String.format("DELETE FROM %S", tableName);

        return query;
    }
}
