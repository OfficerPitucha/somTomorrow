package com.example.somtomorrow.dao;

import com.example.somtomorrow.model.Person;
import com.example.somtomorrow.model.Teacher;
import com.example.somtomorrow.routes.DbUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public enum TeacherDao {
    INSTANCE;

    /**
     * Retrieves a teacher by their ID from the database.
     * @param teacherId The ID of the teacher to retrieve.
     * @return A Teacher object if found, otherwise null.
     * @throws SQLException If there is an error with the SQL query or database connection.
     */
    public Teacher getTeacherById(int teacherId) throws SQLException {
        String sql = "SELECT t.teacher_id, t.subject, p.person_id, p.name, p.surname, p.email " +
                "FROM somtomorrow.teacher t " +
                "JOIN somtomorrow.person p ON t.teacher_id = p.person_id " +
                "WHERE t.teacher_id = ?";
        Teacher teacher = null;

        try (Connection connection = DbUtil.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {

            ps.setInt(1, teacherId);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                teacher = new Teacher();
                teacher.setTeacher_id(rs.getInt("teacher_id"));
                teacher.setSubject(rs.getString("subject"));

                Person person = new Person();
                person.setPerson_id(rs.getInt("person_id"));
                person.setName(rs.getString("name"));
                person.setSurname(rs.getString("surname"));
                person.setEmail(rs.getString("email"));

                teacher.setPerson(person);
            }
        }
        return teacher;
    }

    /**
     * Updates the details of a teacher in the database.
     * @param teacher The Teacher object containing the updated details.
     * @return True if the update was successful, otherwise false.
     * @throws SQLException If there is an error with the SQL query or database connection.
     */
    public boolean updateTeacher(Teacher teacher) throws SQLException {
        String updateTeacherSql = "UPDATE somtomorrow.teacher SET subject = ? WHERE teacher_id = ?";
        String updatePersonSql = "UPDATE somtomorrow.person SET name = ?, surname = ?, email = ? WHERE person_id = ?";

        try (Connection connection = DbUtil.getConnection();
             PreparedStatement teacherPs = connection.prepareStatement(updateTeacherSql);
             PreparedStatement personPs = connection.prepareStatement(updatePersonSql)) {

            connection.setAutoCommit(false);

            // Update teacher
            teacherPs.setString(1, teacher.getSubject());
            teacherPs.setInt(2, teacher.getTeacher_id());
            teacherPs.executeUpdate();

            // Update person
            personPs.setString(1, teacher.getPerson().getName());
            personPs.setString(2, teacher.getPerson().getSurname());
            personPs.setString(3, teacher.getPerson().getEmail());
            personPs.setInt(4, teacher.getTeacher_id());
            personPs.executeUpdate();

            connection.commit();
            return true;
        }
    }

    /**
     * Retrieves the ID of a teacher based on their username.
     * @param username The username of the teacher.
     * @return The ID of the teacher if found, otherwise null.
     * @throws SQLException If there is an error with the SQL query or database connection.
     */
    public Integer getTeacherIdByUsername(String username) throws SQLException {
        String sql = "SELECT t.teacher_id FROM somtomorrow.teacher t " +
                "JOIN somtomorrow.person p ON t.teacher_id = p.person_id " +
                "JOIN somtomorrow.account a ON p.person_id = a.person_id " +
                "WHERE a.username = ?";
        Integer teacherId = null;

        try (Connection connection = DbUtil.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {

            ps.setString(1, username);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                teacherId = rs.getInt("teacher_id");
            }
        } catch (SQLException e) {
            System.err.println("SQL Error: " + e.getMessage());
            throw e;
        }
        return teacherId;
    }
}
