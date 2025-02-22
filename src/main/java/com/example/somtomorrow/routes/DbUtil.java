package com.example.somtomorrow.routes;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Utility class for handling database connections.
 */
public class DbUtil {
    private static final String host = "bronto.ewi.utwente.nl";
    private static final String dbName = "dab_dda23242b_120";
    private static final String url = "jdbc:postgresql://" + host + ":5432/" + dbName;
    private static final String username = "dab_dda23242b_120";
    private static final String password = "eECvjtqGc1oe1nq7";

    /**
     * Private constructor to prevent instantiation.
     */
    private DbUtil() {
        // Prevent instantiation
    }

    /**
     * Establishes and returns a connection to the database.
     *
     * @return a Connection object to the database
     * @throws SQLException if a database access error occurs or the url is null
     */
    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(url, username, password);
    }

    /**
     * Closes the given database connection.
     *
     * @param connection the Connection object to close
     */
    public static void closeConnection(Connection connection) {
        if (connection != null) {
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
