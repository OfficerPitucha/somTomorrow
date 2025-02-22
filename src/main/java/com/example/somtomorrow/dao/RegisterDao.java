package com.example.somtomorrow.dao;

import com.example.somtomorrow.routes.DbUtil;
import org.mindrot.jbcrypt.BCrypt;

import java.sql.*;

/**
 * Enum Singleton implementation of RegisterDao for handling user registration.
 */
public enum RegisterDao {
    INSTANCE;

    static {
        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            System.err.println("PostgreSQL JDBC Driver not found.");
            e.printStackTrace();
        }
    }

    /**
     * Registers a new user in the database.
     *
     * @param username the username of the new user
     * @param password the password of the new user
     * @return true if the registration was successful, false otherwise
     */
    public boolean registerUser(String username, String password) {
        String hashedPassword = BCrypt.hashpw(password, BCrypt.gensalt());
        Connection connection = null;
        PreparedStatement personStmt = null;
        PreparedStatement accountStmt = null;
        PreparedStatement roleStmt = null;
        ResultSet rs = null;

        try {
            connection = DbUtil.getConnection();
            connection.setAutoCommit(false);

            String personSql = "INSERT INTO somtomorrow.person (name, surname, email) VALUES (?, ?, ?) RETURNING person_id";
            personStmt = connection.prepareStatement(personSql);
            personStmt.setString(1, null);
            personStmt.setString(2, null);
            personStmt.setString(3, null);

            rs = personStmt.executeQuery();
            if (rs.next()) {
                int personId = rs.getInt("person_id");

                String accountSql = "INSERT INTO somtomorrow.account (username, password, person_id) VALUES (?, ?, ?)";
                accountStmt = connection.prepareStatement(accountSql);
                accountStmt.setString(1, username);
                accountStmt.setString(2, hashedPassword);
                accountStmt.setInt(3, personId);

                int affectedRows = accountStmt.executeUpdate();

                if (username.startsWith("T")) {
                    String teacherSql = "INSERT INTO somtomorrow.teacher (teacher_id, subject) VALUES (?, ?)";
                    roleStmt = connection.prepareStatement(teacherSql);
                    roleStmt.setInt(1, personId);
                    roleStmt.setString(2, null);
                } else if (username.startsWith("S")) {
                    String studentSql = "INSERT INTO somtomorrow.student (student_id, status, weighted_avg, schedule_id, class_id) VALUES (?, ?, ?, ?, ?)";
                    roleStmt = connection.prepareStatement(studentSql);
                    roleStmt.setInt(1, personId);
                    roleStmt.setString(2, "enrolled");
                    roleStmt.setFloat(3, 0);
                    roleStmt.setNull(4, Types.INTEGER);
                    roleStmt.setNull(5, Types.INTEGER);
                }

                if (roleStmt != null) {
                    roleStmt.executeUpdate();
                }

                connection.commit();

                return affectedRows > 0;
            } else {
                connection.rollback();
                return false;
            }
        } catch (SQLException sqle) {
            System.err.println("Error connecting: " + sqle);
            if (connection != null) {
                try {
                    connection.rollback();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            return false;
        } finally {
            try {
                if (rs != null) rs.close();
                if (personStmt != null) personStmt.close();
                if (accountStmt != null) accountStmt.close();
                if (roleStmt != null) roleStmt.close();
                if (connection != null) connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
