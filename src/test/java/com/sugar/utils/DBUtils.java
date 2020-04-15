package com.sugar.utils;

import com.alibaba.fastjson.JSONObject;

import java.io.IOException;
import java.sql.*;
import java.util.*;

public class DBUtils {
    private static String url;
    private static String user;
    private static String password;
    private static String driver;

    static {
        init();
    }

    public static void init() {
        try {
            Properties properties = new Properties();
            properties.load(DBUtils.class.getResourceAsStream("/jdbc.properties"));
            url = properties.getProperty("jdbc.url");
            user = properties.getProperty("jdbc.user");
            password = properties.getProperty("jdbc.password");
            Class.forName(properties.getProperty("jdbc.driver"));
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static Connection getConnection() {
        try {
            return DriverManager.getConnection(url, user, password);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void execute(String sql) {
        Connection connection = null;
        Statement statement = null;
        try {
            init();
            connection =getConnection();
            statement = connection.createStatement();
            statement.execute(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            close(connection, statement);
        }
    }

    public static List<Map<String, Object>> executeQuery(String sql) {
        List<Map<String, Object>> result = new ArrayList<>();
        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;
        try {
            connection =getConnection();
            statement = connection.createStatement();
            resultSet = statement.executeQuery(sql);
            ResultSetMetaData metaData = resultSet.getMetaData();
            int columnCount = metaData.getColumnCount();
            while (resultSet.next()) {
                Map<String, Object> resultMap = new HashMap<>();
                for (int i = 1; i <= columnCount; i++) {
                    String columnName = metaData.getColumnLabel(i);
                    Object columnValue = resultSet.getObject(columnName);
                    resultMap.put(columnName, columnValue);
                }
                result.add(resultMap);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            close(connection, statement, resultSet);
        }
        return result;
    }

    public static void close(Connection connection, Statement statement, ResultSet resultSet) {
        if (resultSet != null) {
            try {
                resultSet.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        close(connection, statement);
    }

    public static void close(Connection connection, Statement statement) {
        if (statement != null) {
            try {
                statement.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        if (connection != null) {
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) throws Exception {
        // execute("update member  set reg_name='aa' where id =3");
        List<Map<String, Object>> results = executeQuery("select reg_name,type from member where id =3 or id=7");
        System.out.println(JSONObject.toJSONString(results));

    }
}
