package com.example.somtomorrow.dao;

import com.example.somtomorrow.model.Student;
import com.example.somtomorrow.routes.DbUtil;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * DAO class for handling operations related to the Student entity.
 */
public enum StudentDao {

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
     * Returns maximum homework id
     * @return maximum homework id
     * @throws SQLException If any SQL error occurs
     */
    public int getMaxId() throws SQLException {
        String sql = "SELECT MAX(student_id) AS max_id FROM somtomorrow.student";
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

    /**
     * Retrieves a student based on class ID and student ID.
     *
     * @param classId the ID of the class
     * @param studentId the ID of the student
     * @return a Student object
     */
    public Student getAStudent(int classId, int studentId) {
        String sqlQuery = "SELECT s.student_id, s.class_id, s.schedule_id, s.status, s.weighted_avg, p.name, p.surname, p.email FROM" +
                " somtomorrow.student s, somtomorrow.person p WHERE" +
                " s.student_id = p.person_id AND s.class_id = ? AND s.student_id = ?;";

        try (Connection connection = DbUtil.getConnection();
             PreparedStatement statement = connection.prepareStatement(sqlQuery)) {

            statement.setInt(1, classId);
            statement.setInt(2, studentId);

            ResultSet rs = statement.executeQuery();
            Student student = new Student();

            if (rs.next()) {
                student.setName(rs.getString("name"));
                student.setSurname(rs.getString("surname"));
                student.setStudent_id(rs.getInt("student_id"));
                student.setClass_id(rs.getInt("class_id"));
                student.setEmail(rs.getString("email"));
                student.setSchedule_id(rs.getInt("schedule_id"));
                student.setStatus(rs.getString("status"));
                student.setWeighted_avg(rs.getDouble("weighted_avg"));
            }
            return student;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Retrieves a list of students based on the class ID.
     *
     * @param classID the ID of the class
     * @return a list of Student objects
     */
    public List<Student> getStudentsOfAClass(int classID) {
        List<Student> students = new ArrayList<>();

        String sqlQuery =
                "SELECT p.name, p.surname, s.student_id, s.schedule_id, s.class_id, s.status, s.weighted_avg" +
                        " FROM somtomorrow.person p, somtomorrow.student s" +
                        " WHERE s.student_id = p.person_id AND s.class_id = ?";

        try (Connection connection = DbUtil.getConnection();
             PreparedStatement statement = connection.prepareStatement(sqlQuery)) {

            statement.setInt(1, classID);

            ResultSet rs = statement.executeQuery();

            while (rs.next()) {
                Student student = new Student();
                student.setName(rs.getString("name"));
                student.setSurname(rs.getString("surname"));
                student.setStudent_id(rs.getInt("student_id"));
                student.setSchedule_id(rs.getInt("schedule_id"));
                student.setClass_id(rs.getInt("class_id"));
                student.setStatus(rs.getString("status"));
                student.setWeighted_avg(rs.getDouble("weighted_avg"));

                students.add(student);
            }

            return students;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Adds a new student to the database.
     *
     * @param student the Student object to be added
     * @return true if the student was added successfully, false otherwise
     */
    public boolean addStudent(Student student) {
        String personQuery = "INSERT INTO somtomorrow.person (name, surname, person_id, email) VALUES (?, ?, ?, ?)";
        String studentQuery = "INSERT INTO somtomorrow.student (student_id, schedule_id, class_id, status, weighted_avg) VALUES (?, ?, ?, ?, ?)";

        Connection connection = null;

        try {
            connection = DbUtil.getConnection();

            // Begin transaction
            connection.setAutoCommit(false);

            // Insert into person table
            try (PreparedStatement personStatement = connection.prepareStatement(personQuery)) {
                personStatement.setString(1, student.getName());
                personStatement.setString(2, student.getSurname());
                personStatement.setInt(3, student.getStudent_id());
                personStatement.setString(4, student.getEmail());
                personStatement.executeUpdate();
            }

            // Insert into student table
            try (PreparedStatement studentStatement = connection.prepareStatement(studentQuery)) {
                studentStatement.setInt(1, student.getStudent_id());
                studentStatement.setInt(2, student.getSchedule_id());
                studentStatement.setInt(3, student.getClass_id());
                studentStatement.setString(4, student.getStatus());
                studentStatement.setDouble(5, student.getWeighted_avg());
                int affectedRows = studentStatement.executeUpdate();

                // Commit transaction
                connection.commit();

                return affectedRows > 0;
            }

        } catch (SQLException e) {
            if (connection != null) {
                try {
                    // Rollback transaction in case of an error
                    connection.rollback();
                } catch (SQLException rollbackException) {
                    throw new RuntimeException("Failed to rollback transaction", rollbackException);
                }
            }
            throw new RuntimeException("Failed to add student", e);
        } finally {
            if (connection != null) {
                try {
                    connection.setAutoCommit(true);
                    connection.close();
                } catch (SQLException finalException) {
                    throw new RuntimeException("Failed to reset autocommit or close connection", finalException);
                }
            }
        }
    }

    /**
     * Deletes a student from the database based on the student ID.
     *
     * @param student_id the ID of the student to be deleted
     * @return true if the student was deleted successfully, false otherwise
     */
    public boolean deleteStudent(int student_id) {
        String sql = "DELETE FROM somtomorrow.student WHERE student_id = ?;" +
                "DELETE FROM somtomorrow.person WHERE person_id = ?";

        try (Connection connection = DbUtil.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {

            ps.setInt(1, student_id);
            ps.setInt(2, student_id);

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Retrieves a student based on the student ID.
     *
     * @param studentId the ID of the student
     * @return a Student object
     */
    public Student getStudentClassId(int studentId) {
        String sqlQuery = "SELECT * FROM somtomorrow.student s WHERE s.student_id = ?;";

        try (Connection connection = DbUtil.getConnection();
             PreparedStatement statement = connection.prepareStatement(sqlQuery)) {

            statement.setInt(1, studentId);

            Student student = new Student();
            ResultSet rs = statement.executeQuery();

            if (rs.next()) {
                student.setStatus(rs.getString("status"));
                student.setWeighted_avg(rs.getDouble("weighted_avg"));
                student.setSchedule_id(rs.getInt("schedule_id"));
                student.setClass_id(rs.getInt("class_id"));
                student.setStudent_id(studentId);
            } else {
                student.setClass_id(100);
                student.setStatus("hui");
                student.setWeighted_avg(6.6);
                student.setSchedule_id(100);
                student.setClass_id(100);
                student.setStudent_id(studentId);
            }
            return student;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Updates an existing student's details in the database.
     *
     * @param student the Student object with updated details
     * @return true if the student was updated successfully, false otherwise
     * @throws SQLException if a database access error occurs
     */
    public boolean updateStudent(Student student) throws SQLException {
        String studentSql = "UPDATE somtomorrow.student SET schedule_id = ?, class_id = ?, status = ?, weighted_avg = ?" +
                " WHERE student_id = ?";
        String personSql = "UPDATE somtomorrow.person SET name = ?, surname = ?, email = ? WHERE person_id = ?";

        boolean con1 = false;
        boolean con2 = false;

        try (Connection connection = DbUtil.getConnection();
             PreparedStatement ps = connection.prepareStatement(studentSql)) {

            ps.setInt(1, student.getSchedule_id());
            ps.setInt(2, student.getClass_id());
            ps.setString(3, student.getStatus());
            ps.setDouble(4, student.getWeighted_avg());
            ps.setInt(5, student.getStudent_id());

            ps.executeUpdate();

            con1 = ps.executeUpdate() > 0;

        }

        try (Connection connection = DbUtil.getConnection();
             PreparedStatement ps2 = connection.prepareStatement(personSql)) {

            ps2.setString(1, student.getName());
            ps2.setString(2, student.getSurname());
            ps2.setString(3, student.getEmail());
            ps2.setInt(4, student.getStudent_id());

            con2 = ps2.executeUpdate() > 0;
        }

        return con1 && con2;
    }
}
