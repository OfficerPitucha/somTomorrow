package com.example.somtomorrow.dao;

import com.example.somtomorrow.model.Class;
import com.example.somtomorrow.routes.DbUtil;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Data Access Object for the {@link Class} model.
 * This class provides methods to interact with the database for Class-related operations.
 */
public enum ClassDao {

    INSTANCE;

    static {
        try {
            java.lang.Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            System.err.println("PostgreSQL JDBC Driver not found.");
            e.printStackTrace();
        }
    }

    /**
     * Adds a new class to the database and associates all teachers with this class.
     *
     * @param newClass The {@link Class} object to be added.
     * @param classId The ID of the new class, returned from the database.
     * @throws SQLException If any SQL error occurs.
     */
    public void addClass(Class newClass, int classId) throws SQLException {
        String insertClassQuery = "INSERT INTO somtomorrow.class (name, student_count) VALUES (?, ?) RETURNING class_id";
        String insertTeachesQuery = "INSERT INTO somtomorrow.teaches (teacher_id, class_id) VALUES (?, ?)";
        String getAllTeachersQuery = "SELECT teacher_id FROM somtomorrow.teacher";

        try (Connection conn = DbUtil.getConnection();
             PreparedStatement psInsertClass = conn.prepareStatement(insertClassQuery);
             PreparedStatement psInsertTeaches = conn.prepareStatement(insertTeachesQuery);
             PreparedStatement psGetAllTeachers = conn.prepareStatement(getAllTeachersQuery)) {

            psInsertClass.setString(1, newClass.getName());
            psInsertClass.setInt(2, newClass.getStudentCount());
            ResultSet rs = psInsertClass.executeQuery();

            if (rs.next()) {
                classId = rs.getInt("class_id");
            }

            ResultSet rsTeachers = psGetAllTeachers.executeQuery();

            while (rsTeachers.next()) {
                int currentTeacherId = rsTeachers.getInt("teacher_id");
                psInsertTeaches.setInt(1, currentTeacherId);
                psInsertTeaches.setInt(2, classId);
                psInsertTeaches.addBatch();
            }

            psInsertTeaches.executeBatch();
        }
    }

    /**
     * Retrieves all classes from the database.
     *
     * @return A list of {@link Class} objects.
     * @throws SQLException If any SQL error occurs.
     */
    public List<Class> getAllClasses() throws SQLException {
        String query = "SELECT * FROM somtomorrow.class";
        try (Connection connection = DbUtil.getConnection();
             PreparedStatement statement = connection.prepareStatement(query);
             ResultSet resultSet = statement.executeQuery()) {

            List<Class> classes = new ArrayList<>();
            while (resultSet.next()) {
                Class classItem = new Class(
                        resultSet.getInt("class_id"),
                        resultSet.getString("name"),
                        resultSet.getInt("student_count")
                );
                classes.add(classItem);
            }
            return classes;
        }
    }

    /**
     * Retrieves a class by its ID from the database.
     *
     * @param classId The ID of the class to retrieve.
     * @return The {@link Class} object.
     * @throws SQLException If any SQL error occurs.
     */
    public Class getClass(int classId) throws SQLException {
        String sql = "SELECT * FROM somtomorrow.class WHERE class_id = ?";

        Class cls = new Class(0, null, 0);

        try (Connection connection = DbUtil.getConnection(); PreparedStatement ps = connection.prepareStatement(sql)){

            ps.setInt(1, classId);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                int cid = rs.getInt("class_id");
                String name = rs.getString("name");
                int studentCount = rs.getInt("student_count");

                cls = new Class(cid, name, studentCount);
            }
        } catch (SQLException sqle) {
            System.err.println("Error connecting: " + sqle);
        }
        return cls;
    }

    /**
     * Deletes a class from the database.
     *
     * @param classId The ID of the class to delete.
     */
    public void deleteClass(int classId) {
        String deleteTeachesSQL = "DELETE FROM somtomorrow.teaches WHERE class_id = ?";
        String deleteClassSQL = "DELETE FROM somtomorrow.class WHERE class_id = ?";

        try (Connection connection = DbUtil.getConnection()) {
            connection.setAutoCommit(false);

            try (PreparedStatement psTeaches = connection.prepareStatement(deleteTeachesSQL);
                 PreparedStatement psClass = connection.prepareStatement(deleteClassSQL)) {

                psTeaches.setInt(1, classId);
                psTeaches.executeUpdate();

                psClass.setInt(1, classId);
                psClass.executeUpdate();

                connection.commit();
            } catch (SQLException sqle) {
                connection.rollback();
                throw sqle;
            }

        } catch (SQLException sqle) {
            System.err.println("Error executing delete: " + sqle.getMessage());
        }
    }

    /**
     * Updates the details of an existing class in the database.
     *
     * @param classId The ID of the class to update.
     * @param updatedClass The {@link Class} object with updated details.
     * @throws SQLException If any SQL error occurs.
     */
    public void updateClass(int classId, Class updatedClass) throws SQLException {
        String query = "UPDATE somtomorrow.class SET name = ?, student_count = ? WHERE class_id = ?";
        try (Connection connection = DbUtil.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, updatedClass.getName());
            statement.setInt(2, updatedClass.getStudentCount());
            statement.setInt(3, classId);
            statement.executeUpdate();
        }
    }

    /**
     * Returns maximum homework id
     * @return maximum homework id
     * @throws SQLException If any SQL error occurs
     */
    public int getMaxId() throws SQLException {
        String sql = "SELECT MAX(class_id) AS max_id FROM somtomorrow.class";
        int maxId = 0;
        try (Connection connection = DbUtil.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)){
            // Execute the query
            ResultSet resultSet = ps.executeQuery();

            // Process the result
            if (resultSet.next()) {
                maxId = resultSet.getInt("max_id");
            }
        }
        return maxId;
    }
}
