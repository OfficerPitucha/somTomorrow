package com.example.somtomorrow.routes;

import com.example.somtomorrow.dao.StudentDao;
import com.example.somtomorrow.model.Student;
import jakarta.json.Json;
import jakarta.json.JsonObject;
import jakarta.json.JsonReader;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;

import java.io.StringReader;
import java.sql.SQLException;
import java.util.List;

/**
 * RESTful API route for managing student operations.
 */
@Path("/student")
public class StudentRoute {

    /**
     * Creates a new student.
     *
     * @param data JSON string containing student details
     * @return true if the student was successfully created, false otherwise
     */
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public boolean createStudent(String data) {
        try (JsonReader jsonReader = Json.createReader(new StringReader(data))) {
            JsonObject jsonObject = jsonReader.readObject();

            String name = jsonObject.getString("name");
            String surname = jsonObject.getString("surname");
            int classId = jsonObject.getInt("class_id");
            String status = jsonObject.getString("status");
            int schedule_id = jsonObject.getInt("schedule_id");
            double weighted_avg = 0.0; //Standard new student
            String email = jsonObject.getString("email");

            Student student = new Student(name, surname, classId, status, schedule_id, weighted_avg, email);
            return StudentDao.INSTANCE.addStudent(student);
        }
    }

    /**
     * Deletes a student by their ID.
     *
     * @param student_id the ID of the student to delete
     * @return true if the student was successfully deleted, false otherwise
     */
    @DELETE
    @Path("/{studentId}")
    public boolean deleteStudent(@PathParam("studentId") int student_id) {
        return StudentDao.INSTANCE.deleteStudent(student_id);
    }

    /**
     * Retrieves a student by their class ID and student ID.
     *
     * @param class_id the class ID
     * @param student_id the student ID
     * @return the student object
     * @throws SQLException if a database access error occurs
     */
    @GET
    @Path("/{classId}/{studentId}")
    @Produces(MediaType.APPLICATION_JSON)
    public Student getStudent(@PathParam("classId") int class_id, @PathParam("studentId") int student_id) throws SQLException {
        return StudentDao.INSTANCE.getAStudent(class_id, student_id);
    }

    /**
     * Retrieves all students in a class.
     *
     * @param class_id the class ID
     * @return a list of students in the class
     * @throws SQLException if a database access error occurs
     */
    @GET
    @Path("/{classId}")
    @Produces(MediaType.APPLICATION_JSON)
    public List<Student> getAllStudents(@PathParam("classId") int class_id) throws SQLException {
        return StudentDao.INSTANCE.getStudentsOfAClass(class_id);
    }

    /**
     * Retrieves the class ID for a student.
     *
     * @param student_id the student ID
     * @return the student object containing the class ID
     * @throws SQLException if a database access error occurs
     */
    @GET
    @Path("/{studentId}/class")
    @Produces(MediaType.APPLICATION_JSON)
    public Student getClassId(@PathParam("studentId") int student_id) throws SQLException {
        return StudentDao.INSTANCE.getStudentClassId(student_id);
    }

    /**
     * Updates the details of a student.
     *
     * @param data JSON string containing updated student details
     * @return true if the student was successfully updated, false otherwise
     * @throws SQLException if a database access error occurs
     */
    @PUT
    @Path("/update")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public boolean updateStudent(String data) throws SQLException {
        try (JsonReader jsonReader = Json.createReader(new StringReader(data))) {
            JsonObject jsonObject = jsonReader.readObject();

            String name = jsonObject.getString("name");
            String surname = jsonObject.getString("surname");
            int classId = jsonObject.getInt("class_id");
            int studentId = jsonObject.getInt("student_id");
            String status = jsonObject.getString("status");
            int schedule_id = jsonObject.getInt("schedule_id");
            double weighted_avg = 0.0; //Standard new student
            String email = jsonObject.getString("email");

            Student student = new Student(name, surname, studentId, classId, status, schedule_id,
                                          weighted_avg, email);
            return StudentDao.INSTANCE.updateStudent(student);
        }
    }
}
