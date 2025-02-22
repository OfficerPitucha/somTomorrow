package com.example.somtomorrow.dao;

import com.example.somtomorrow.routes.DbUtil;
import org.mindrot.jbcrypt.BCrypt;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * DAO class for handling login operations.
 */
public enum LoginDao {
    INSTANCE;

    private String host = "bronto.ewi.utwente.nl";
    private String dbName = "dab_dda23242b_120";
    private String url = "jdbc:postgresql://" + host + ":5432/" + dbName;
    private String dbUsername = "dab_dda23242b_120";
    private String dbPassword = "eECvjtqGc1oe1nq7";

    static {
        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            System.err.println("PostgreSQL JDBC Driver not found.");
            e.printStackTrace();
        }
    }

    /**
     * Validates the login credentials.
     *
     * @param userName the username of the user
     * @param passWord the password of the user
     * @return a string indicating the user type ("teacher", "student") or "null" if invalid
     */
    public String validateLogin(String userName, String passWord) {
        String sqlQuery = "SELECT * FROM somtomorrow.account WHERE username = ?";

        try (Connection connection = DbUtil.getConnection();
             PreparedStatement statement = connection.prepareStatement(sqlQuery)) {

            statement.setString(1, userName);

            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                String retrievedPassword = resultSet.getString("password");

                if (BCrypt.checkpw(passWord, retrievedPassword)) {
                    if (userName.startsWith("T")) {
                        return "teacher";
                    } else if (userName.startsWith("S")) {
                        return "student";
                    }
                }
            } else {
                System.out.println("No matching user found");
            }
        } catch (SQLException sqle) {
            System.err.println("Error connecting: " + sqle);
        }
        return "null";
    }
}
