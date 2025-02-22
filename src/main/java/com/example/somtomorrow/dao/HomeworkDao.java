package com.example.somtomorrow.dao;

import com.example.somtomorrow.model.Homework;
import com.example.somtomorrow.routes.DbUtil;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Data Access Object (DAO) for handling Homework entities in the database.
 */
public enum HomeworkDao {

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
        String sql = "SELECT MAX(homework_id) AS max_id FROM somtomorrow.homework";
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
     * Retrieves a Homework entity by its ID from the database.
     *
     * @param homework_id The ID of the homework to retrieve.
     * @return The Homework object if found, null otherwise.
     * @throws SQLException If an SQL exception occurs.
     */
    public Homework getHomework(int homework_id) throws SQLException {
        String sql = "SELECT isdivisible, start_date, due_date, description, \"timeIndication\", homework_id, lesson_id, class_id" +
                " FROM somtomorrow.homework WHERE homework_id = ?";

        Homework homework = null;

        try (Connection connection = DbUtil.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)){

            ps.setInt(1, homework_id);

            ResultSet rs = ps.executeQuery();
            if (rs.next()) {

                boolean isDivisible = rs.getBoolean("isdivisible");
                Date start_date = rs.getDate("start_date");
                Date due_date = rs.getDate("due_date");
                String description = rs.getString("description");
                int time_indication = rs.getInt("timeIndication");
                int hid = rs.getInt("homework_id");
                int lid = rs.getInt("lesson_id");
                int sid = 15;
                int cid = rs.getInt("class_id");

                homework = new Homework(isDivisible, start_date, due_date, description, time_indication, hid, lid, sid, cid);
            }

        } catch (SQLException sqle) {
            System.err.println("Error connecting: " + sqle);
        }
        return homework;

    }

    /**
     * Retrieves all Homework entities from the database.
     *
     * @return A List of all Homework objects found in the database.
     * @throws SQLException If an SQL exception occurs.
     */
    public List<Homework> getAllHomework() throws SQLException {
        List<Homework> homeworks = new ArrayList<>();
        String sql = "SELECT * FROM somtomorrow.homework";

        try (Connection connection = DbUtil.getConnection();
             Statement ps = connection.createStatement();
             ResultSet rs = ps.executeQuery(sql)) {

            while (rs.next()) {

                Homework homework = new Homework();

                homework.setDivisible(rs.getBoolean("isdivisible"));
                homework.setStart_date(rs.getDate("start_date"));
                homework.setDue_date(rs.getDate("due_date"));
                homework.setDescription(rs.getString("description"));
                homework.setTimeIndication(rs.getInt("timeIndication"));
                homework.setHomework_id(rs.getInt("homework_id"));
                homework.setLesson_id(rs.getInt("lesson_id"));
                homework.setClass_id(rs.getInt("class_id"));

                homeworks.add(homework);
            }

        } catch (SQLException sqle) {
            System.err.println("Error connecting: " + sqle);
        }

        return homeworks;
    }

    /**
     * Retrieves all Homework entities for a specific lesson from the database.
     *
     * @param lesson_id The ID of the lesson for which to retrieve homework.
     * @return A List of Homework objects related to the specified lesson.
     * @throws SQLException If an SQL exception occurs.
     */
    public List<Homework> getAllHomework(int lesson_id) throws SQLException {
        List<Homework> homeworks = new ArrayList<>();
        String sql = "SELECT * FROM somtomorrow.homework WHERE lesson_id = ?";

        try (Connection connection = DbUtil.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {

            ps.setInt(1, lesson_id);

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {

                Homework homework = new Homework();

                homework.setDivisible(rs.getBoolean("isdivisible"));
                homework.setStart_date(rs.getDate("start_date"));
                homework.setDue_date(rs.getDate("due_date"));
                homework.setDescription(rs.getString("description"));
                homework.setTimeIndication(rs.getInt("timeIndication"));
                homework.setHomework_id(rs.getInt("homework_id"));
                homework.setLesson_id(rs.getInt("lesson_id"));
                homework.setClass_id(rs.getInt("class_id"));

                homeworks.add(homework);
            }

        } catch (SQLException sqle) {
            System.err.println("Error connecting: " + sqle);
        }

        return homeworks;
    }

    /**
     * Retrieves all Homework entities for a specific class and lesson from the database.
     *
     * @param class_id  The ID of the class for which to retrieve homework.
     * @param lesson_id The ID of the lesson for which to retrieve homework.
     * @return A List of Homework objects related to the specified class and lesson.
     */
    public List<Homework> getLessonHomework(int class_id, int lesson_id) {
        List<Homework> homeworks = new ArrayList<>();

        String sqlQuery = "SELECT * FROM somtomorrow.homework h\n" +
                "JOIN somtomorrow.lesson l ON l.lesson_id = h.lesson_id\n" +
                "WHERE l.class_id = ? AND h.lesson_id = ?";

        try (Connection connection =  DbUtil.getConnection();
             PreparedStatement statement = connection.prepareStatement(sqlQuery)) {

                statement.setInt(1,class_id);
                statement.setInt(2,lesson_id);

                ResultSet rs = statement.executeQuery();

                while (rs.next()) {
                    Homework homework = new Homework();

                    homework.setDivisible(rs.getBoolean("isdivisible"));
                    homework.setStart_date(rs.getDate("start_date"));
                    homework.setDue_date(rs.getDate("due_date"));
                    homework.setDescription(rs.getString("description"));
                    homework.setTimeIndication(rs.getInt("timeIndication"));
                    homework.setHomework_id(rs.getInt("homework_id"));
                    homework.setLesson_id(rs.getInt("lesson_id"));
                    homework.setClass_id(rs.getInt("class_id"));


                    homeworks.add(homework);
                }

        } catch (SQLException sqle) {
            System.err.println("Error connecting: " + sqle);
        }

        return homeworks;
    }

    /**
     * Creates a new Homework entity in the database.
     *
     * @param homework The Homework object to create.
     * @return true if the creation was successful, false otherwise.
     * @throws SQLException If an SQL exception occurs.
     */
    public boolean createHomework(Homework homework) throws SQLException {

        String insertHomeworkQuery =
                "INSERT INTO somtomorrow.homework (isdivisible, start_date, due_date, description, lesson_id, \"timeIndication\", class_id) VALUES (?, ?, ?, ?, ?, ?, ?) RETURNING homework_id";
        String insertGetHomeworkQuery =
                "INSERT INTO somtomorrow.getsHomework (student_id, homework_id) VALUES (?, ?)";
        String getClassOfStudents =
                "SELECT class_id FROM somtomorrow.homework WHERE homework_id = ?";
        String getAllStudents = "SELECT student_id FROM somtomorrow.student WHERE class_id = ?";

        try (Connection conn = DbUtil.getConnection();
             PreparedStatement psInsertHomework = conn.prepareStatement(insertHomeworkQuery);
             PreparedStatement psInsertGetHomework = conn.prepareStatement(insertGetHomeworkQuery);
             PreparedStatement psGetClassOfStudents = conn.prepareStatement(getClassOfStudents);
             PreparedStatement psGetAllStudents = conn.prepareStatement(getAllStudents)) {

            psInsertHomework.setBoolean(1, homework.isDivisible());
            psInsertHomework.setDate(2, homework.getStart_date());
            psInsertHomework.setDate(3, homework.getDue_date());
            psInsertHomework.setString(4, homework.getDescription());
            psInsertHomework.setInt(5, homework.getLesson_id());
            psInsertHomework.setInt(6, homework.getTimeIndication());
            psInsertHomework.setInt(7, homework.getClass_id());

            ResultSet rs = psInsertHomework.executeQuery();

            int homework_id = homework.getHomework_id();

            if (rs.next()) {
                homework_id = rs.getInt("homework_id");
            }

            psGetClassOfStudents.setInt(1, homework_id);
            ResultSet rsClassOfStudents = psGetClassOfStudents.executeQuery();

            int classIdOfStudentsClass = -1;
            if (rsClassOfStudents.next()) {
                classIdOfStudentsClass = rsClassOfStudents.getInt(1);
            }

            psGetAllStudents.setInt(1, classIdOfStudentsClass);
            ResultSet rsStudents = psGetAllStudents.executeQuery();

            while (rsStudents.next()) {
                int currentStudentId = rsStudents.getInt("student_id");
                psInsertGetHomework.setInt(1, currentStudentId);
                psInsertGetHomework.setInt(2, homework_id);
                psInsertGetHomework.addBatch();
            }

            psInsertGetHomework.executeBatch();
        }
        return true;
    }

    /**
     * Deletes a Homework entity from the database by its ID.
     *
     * @param homework_id the ID of the homework to delete
     * @return true if the deletion was successful, false otherwise
     * @throws SQLException if an SQL exception occurs
     */
    public boolean deleteHomework(int homework_id) throws SQLException {
        String deleteFromGetsHomework = "DELETE FROM somtomorrow.getshomework WHERE homework_id = ?";
        String deleteFromHomework = "DELETE FROM somtomorrow.homework WHERE homework_id = ?";

        try (Connection connection = DbUtil.getConnection();
             PreparedStatement ps1 = connection.prepareStatement(deleteFromGetsHomework);
             PreparedStatement ps2 = connection.prepareStatement(deleteFromHomework)) {

            // Delete references from getshomework table
            ps1.setInt(1, homework_id);
            int affectedRowsGetsHomework = ps1.executeUpdate();

            // Delete from homework table
            ps2.setInt(1, homework_id);
            int affectedRowsHomework = ps2.executeUpdate();

            return affectedRowsHomework > 0;
        } catch (SQLException sqle) {
            throw new RuntimeException(sqle);
        }
    }

    /**
     * Updates an existing Homework entity in the database.
     *
     * @param homework The Homework object containing updated information.
     * @return true if the update was successful, false otherwise.
     * @throws SQLException If an SQL exception occurs.
     */
    public boolean updateHomework(Homework homework) throws SQLException {
        String sql = "UPDATE somtomorrow.homework SET isdivisible = ?, start_date = ?, due_date = ?, description = ?, \"timeIndication\" = ?, lesson_id = ?, class_id = ? " +
                " WHERE homework_id = ?";

        try (Connection connection = DbUtil.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {

            ps.setBoolean(1, homework.isDivisible());
            ps.setDate(2, homework.getStart_date());
            ps.setDate(3, homework.getDue_date());
            ps.setString(4, homework.getDescription());
            ps.setInt(5, homework.getTimeIndication());
            ps.setInt(6, homework.getLesson_id());
            ps.setInt(7, homework.getClass_id());
            ps.setInt(8, homework.getHomework_id());


            return ps.executeUpdate() > 0;

        } catch (SQLException sqle) {
            System.err.println("Error connecting: " + sqle);
        }

        return false;
    }

    /**
     * Retrieves all Homework entities for a specific class from the database.
     *
     * @param class_id the ID of the class for which to retrieve homework
     * @return a List of Homework objects related to the specified class
     * @throws SQLException if an SQL exception occurs
     */
    public List<Homework> getHomeworkClass(int class_id) throws SQLException {
        List<Homework> homeworks = new ArrayList<>();
        String sql = "SELECT * FROM somtomorrow.homework WHERE class_id = ?";

        try (Connection connection = DbUtil.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {

            ps.setInt(1, class_id);

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {

                Homework homework = new Homework();

                homework.setDivisible(rs.getBoolean("isdivisible"));
                homework.setStart_date(rs.getDate("start_date"));
                homework.setDue_date(rs.getDate("due_date"));
                homework.setDescription(rs.getString("description"));
                homework.setTimeIndication(rs.getInt("timeIndication"));
                homework.setHomework_id(rs.getInt("homework_id"));
                homework.setLesson_id(rs.getInt("lesson_id"));
                homework.setStudent_id(0);
                homework.setClass_id(rs.getInt("class_id"));

                homeworks.add(homework);
            }

        } catch (SQLException sqle) {
            System.err.println("Error connecting: " + sqle);
        }

        return homeworks;
    }

}


