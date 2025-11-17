package com.github.ASE.Reflection;

import java.lang.reflect.Field;

public class ORM {
    public static String generateInsertSQL(Object obj) {
        Class<?> clazz = obj.getClass();

        if (!clazz.isAnnotationPresent(Table.class)) {
            throw new RuntimeException("Missing @Table annotation");
        }

        Table tableAnnotation = clazz.getAnnotation(Table.class);
        String tableName = tableAnnotation.name();

        StringBuilder columns = new StringBuilder();
        StringBuilder values = new StringBuilder();

        for (Field field : clazz.getDeclaredFields()) {
            if (field.isAnnotationPresent(Column.class)) {
                Column columnAnnotation = field.getAnnotation(Column.class);
                field.setAccessible(true); // Bypass private visibility

                try {
                    columns.append(columnAnnotation.name()).append(",");
                    if (field.getType().isAssignableFrom(String.class)) {
                        values.append("'").append(field.get(obj)).append("',");
                    } else {
                        values.append(field.get(obj)).append(",");
                    }
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
        }

        // Remove last comma
        columns.setLength(columns.length() - 1);
        values.setLength(values.length() - 1);

        return "INSERT INTO " + tableName + " (" + columns + ") VALUES (" + values + ");";
    }
}
