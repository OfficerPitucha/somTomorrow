package com.example.somtomorrow.dao;

import com.example.somtomorrow.model.Lesson;
import com.example.somtomorrow.routes.DbUtil;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Enum Singleton implementation of LessonDao for handling database operations related to Lesson.
 */
public enum LessonDao {

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
     * Retrieves a Lesson from the database by its ID.
     *
     * @param id the ID of the Lesson to retrieve
     * @return the Lesson object if found, otherwise null
     * @throws SQLException if a database access error occurs
     */
    public Lesson getLesson(int id) throws SQLException {
        String sql = "SELECT * FROM somtomorrow.lesson WHERE lesson_id = ?";

        Lesson lesson = null;

        try (Connection connection = DbUtil.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {

            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                int lid = rs.getInt("lesson_id");
                int cid = rs.getInt("class_id");
                String title = rs.getString("title");
                boolean hasHomework = rs.getBoolean("has_homework");
                String location = rs.getString("location");
                int period = rs.getInt("period");
                String day = rs.getString("day");

                lesson = new Lesson(lid, cid, title, hasHomework, location, period, day);
            }
        } catch (SQLException sqle) {
            System.err.println("Error connecting: " + sqle);
        }
        return lesson;
    }

    /**
     * Retrieves all Lessons from the database.
     *
     * @return a list of all Lessons
     * @throws SQLException if a database access error occurs
     */
    public List<Lesson> getAllLessons() throws SQLException {
        List<Lesson> lessons = new ArrayList<>();
        String sql = "SELECT * FROM somtomorrow.lesson";

        try (Connection connection = DbUtil.getConnection();
             Statement ps = connection.createStatement();
             ResultSet rs = ps.executeQuery(sql)) {

            while (rs.next()) {
                Lesson lesson = new Lesson();
                lesson.setLessonId(rs.getInt("lesson_id"));
                lesson.setClassId(rs.getInt("class_id"));
                lesson.setTitle(rs.getString("title"));
                lesson.setHasHomework(rs.getBoolean("has_homework"));
                lesson.setLocation(rs.getString("location"));
                lesson.setPeriod(rs.getInt("period"));
                lesson.setDay(rs.getString("day"));
                lesson.setIcon(rs.getInt("icon"));
                lessons.add(lesson);
            }
        } catch (SQLException sqle) {
            System.err.println("Error connecting: " + sqle);
        }
        return lessons;
    }

    /**
     * Retrieves all Lessons associated with a specific class.
     *
     * @param classId the ID of the class
     * @return a list of Lessons associated with the class
     * @throws SQLException if a database access error occurs
     */
    public List<Lesson> getLessonsOfClass(int classId) throws SQLException {
        List<Lesson> lessons = new ArrayList<>();
        String sql = "SELECT * FROM somtomorrow.lesson WHERE class_id = ?";

        try (Connection connection = DbUtil.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {

            ps.setInt(1, classId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Lesson lesson = new Lesson();
                    lesson.setLessonId(rs.getInt("lesson_id"));
                    lesson.setClassId(rs.getInt("class_id"));
                    lesson.setTitle(rs.getString("title"));
                    lesson.setHasHomework(rs.getBoolean("has_homework"));
                    lesson.setLocation(rs.getString("location"));
                    lesson.setPeriod(rs.getInt("period"));
                    lesson.setDay(rs.getString("day"));
                    lesson.setIcon(rs.getInt("icon"));
                    lessons.add(lesson);
                }
            }
        } catch (SQLException sqle) {
            System.err.println("Error connecting: " + sqle);
        }
        return lessons;
    }

    /**
     * Creates a new Lesson in the database.
     *
     * @param lesson the Lesson object to be created
     * @return true if the Lesson was successfully created, false otherwise
     * @throws SQLException if a database access error occurs
     */
    public boolean createLesson(Lesson lesson) throws SQLException {
        String sql = "INSERT INTO somtomorrow.lesson (class_id, title, has_homework, location, period, day) VALUES (?, ?, ?, ?, ?, ?)";

        try (Connection connection = DbUtil.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            ps.setInt(1, lesson.getClassId());
            ps.setString(2, lesson.getTitle());
            ps.setBoolean(3, lesson.hasHomework());
            ps.setString(4, lesson.getLocation());
            ps.setInt(5, lesson.getPeriod());
            ps.setString(6, lesson.getDay());

            int affectedRows = ps.executeUpdate();

            if (affectedRows > 0) {
                try (ResultSet generatedKeys = ps.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        lesson.setLessonId(generatedKeys.getInt(1));
                    } else {
                        throw new SQLException("Creating lesson failed, no ID obtained.");
                    }
                }
            }
            return affectedRows > 0;
        } catch (SQLException sqle) {
            System.err.println("Error connecting: " + sqle);
            return false;
        }
    }

    /**
     * Deletes a Lesson from the database by its ID.
     *
     * @param lessonId the ID of the Lesson to delete
     * @return true if the Lesson was successfully deleted, false otherwise
     * @throws SQLException if a database access error occurs
     */
    public boolean deleteLesson(int lessonId) throws SQLException {
        String sql = "DELETE FROM somtomorrow.lesson WHERE lesson_id = ?";

        try (Connection connection = DbUtil.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, lessonId);

            return ps.executeUpdate() > 0;

        } catch (SQLException sqle) {
            System.err.println("Error connecting: " + sqle);
        }

        return false;
    }

    /**
     * Updates an existing Lesson in the database.
     *
     * @param lesson the Lesson object with updated information
     * @return true if the Lesson was successfully updated, false otherwise
     * @throws SQLException if a database access error occurs
     */
    public boolean updateLesson(Lesson lesson) throws SQLException {
        String sql = "UPDATE somtomorrow.lesson SET lesson_id = ?, class_id = ?, title = ?, has_homework = ?," +
                " location = ?, period = ?, day = ? WHERE lesson_id = ?";

        try (Connection connection = DbUtil.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {

            ps.setInt(8, lesson.getLessonId());
            ps.setInt(1, lesson.getLessonId());
            ps.setInt(2, lesson.getClassId());
            ps.setString(3, lesson.getTitle());
            ps.setBoolean(4, lesson.hasHomework());
            ps.setString(5, lesson.getLocation());
            ps.setInt(6, lesson.getPeriod());
            ps.setString(7, lesson.getDay());

            return ps.executeUpdate() > 0;

        } catch (SQLException sqle) {
            System.err.println("Error connecting: " + sqle);
        }
        return false;
    }

    /**
     * Returns maximum homework id
     * @return maximum homework id
     * @throws SQLException If any SQL error occurs
     */
    public int getMaxId() throws SQLException {
        String sql = "SELECT MAX(lesson_id) AS max_id FROM somtomorrow.lesson";
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