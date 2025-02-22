package com.example.somtomorrow.dao;

import com.example.somtomorrow.model.HomeworkPiece;
import com.example.somtomorrow.routes.DbUtil;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Data Access Object (DAO) for handling HomeworkPiece entities in the database.
 */
public enum HomeworkPieceDao {

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
     * Retrieves all HomeworkPiece entities associated with a specific homework ID.
     *
     * @param homework_id the ID of the homework
     * @return a List of HomeworkPiece objects
     * @throws SQLException if an SQL exception occurs
     */
    public List<HomeworkPiece> getPiece(int homework_id) throws SQLException {
        String sql = "SELECT * FROM somtomorrow.homework_piece WHERE homework_id = ?";

        List<HomeworkPiece> list = new ArrayList<>();

        try (Connection connection = DbUtil.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {

            ps.setInt(1, homework_id);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                int pieceId = rs.getInt("homework_piece_id");
                int homeworkId = rs.getInt("homework_id");
                String goal = rs.getString("goal");
                int lessonid = rs.getInt("lesson_id");
                int studentId = rs.getInt("student_id");
                String progress = rs.getString("progress");
                String tasks = rs.getString("tasks");
                Date start = rs.getDate("start_date");
                Date end = rs.getDate("due_date");

                HomeworkPiece hp = new HomeworkPiece(pieceId, homeworkId, goal, lessonid, studentId, progress, tasks, start, end);
                list.add(hp);
            }
        } catch (SQLException sqle) {
            System.err.println("Error connecting: " + sqle);
        }
        return list;
    }

    /**
     * Retrieves all HomeworkPiece entities for a specific lesson and student.
     *
     * @param lesson_id the ID of the lesson
     * @param student_id the ID of the student
     * @return a List of HomeworkPiece objects
     * @throws SQLException if an SQL exception occurs
     */
    public List<HomeworkPiece> getAllPieces(int lesson_id, int student_id) throws SQLException {
        List<HomeworkPiece> pieces = new ArrayList<>();
        String sql = "SELECT * FROM somtomorrow.homework_piece WHERE lesson_id = ? AND student_id = ?";

        try (Connection connection = DbUtil.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {

            ps.setInt(1, lesson_id);
            ps.setInt(2, student_id);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                HomeworkPiece hp = new HomeworkPiece();

                hp.setPieceId(rs.getInt("homework_piece_id"));
                hp.setProgress(rs.getString("progress"));
                hp.setTasks(rs.getString("tasks"));
                hp.setStart_date(rs.getDate("start_date"));
                hp.setDue_date(rs.getDate("due_date"));
                hp.setLessonId(rs.getInt("lesson_id"));
                hp.setStudentId(rs.getInt("student_id"));
                hp.setGoal(rs.getString("goal"));
                hp.setHomeworkId(rs.getInt("homework_id"));

                pieces.add(hp);
            }
        } catch (SQLException sqle) {
            System.err.println("Error connecting: " + sqle);
        }
        return pieces;
    }

    /**
     * Creates a new HomeworkPiece entity in the database.
     *
     * @param homeworkPiece the HomeworkPiece object to create
     * @param lesson_id the ID of the lesson associated with the homework piece
     * @param student_id the ID of the student associated with the homework piece
     * @return true if the creation was successful, false otherwise
     * @throws SQLException if an SQL exception occurs
     */
    public boolean createPiece(HomeworkPiece homeworkPiece, int lesson_id, int student_id) throws SQLException {
        String sql = "INSERT INTO somtomorrow.homework_piece (progress, tasks, start_date, due_date, lesson_id, student_id, goal)" +
                " VALUES (?, ?, ?, ?, ?, ?, ?)";

        try (Connection connection = DbUtil.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {

            ps.setString(1, homeworkPiece.getProgress());
            ps.setString(2, homeworkPiece.getTasks());
            ps.setDate(3, homeworkPiece.getStart_date());
            ps.setDate(4, homeworkPiece.getDue_date());
            ps.setInt(5, lesson_id);
            ps.setInt(6, student_id);
            ps.setString(7, homeworkPiece.getGoal());

            return ps.executeUpdate() > 0;
        } catch (SQLException sqle) {
            System.err.println("Error connecting: " + sqle);
        }
        return false;
    }

    /**
     * Deletes a HomeworkPiece entity from the database by its homework ID.
     *
     * @param homework_id the ID of the homework associated with the homework piece
     * @return true if the deletion was successful, false otherwise
     * @throws SQLException if an SQL exception occurs
     */
    public boolean deletePiece(int homework_id) throws SQLException {
        String sql = "DELETE FROM somtomorrow.homework_piece " +
                " WHERE homework_id = ?";

        try (Connection connection = DbUtil.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)){

            ps.setInt(1, homework_id);

            return ps.executeUpdate() > 0;

        } catch (SQLException sqle) {
            System.err.println("Error connecting: " + sqle);
        }

        return false;
    }

    /**
     * Updates an existing HomeworkPiece entity in the database.
     *
     * @param homeworkPiece the HomeworkPiece object containing updated information
     * @return true if the update was successful, false otherwise
     * @throws SQLException if an SQL exception occurs
     */
    public boolean updatePiece(HomeworkPiece homeworkPiece) throws SQLException {
        String sql = "UPDATE somtomorrow.homework_piece SET progress = ?, tasks = ?, start_date = ?, due_date = ?, goal = ?" +
                " WHERE homework_id = ?";

        try (Connection connection = DbUtil.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {

            ps.setString(1, homeworkPiece.getProgress());
            ps.setString(2, homeworkPiece.getTasks());
            ps.setDate(3, homeworkPiece.getStart_date());
            ps.setDate(4, homeworkPiece.getDue_date());
            ps.setString(5, homeworkPiece.getGoal());
            ps.setInt(6, homeworkPiece.getHomeworkId());

            return ps.executeUpdate() > 0;

        } catch (SQLException sqle) {
            System.err.println("Error connecting: " + sqle);
        }

        return false;
    }
}

