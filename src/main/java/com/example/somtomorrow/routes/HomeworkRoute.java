package com.example.somtomorrow.routes;

import com.example.somtomorrow.dao.HomeworkDao;
import com.example.somtomorrow.model.Homework;
import jakarta.json.Json;
import jakarta.json.JsonObject;
import jakarta.json.JsonReader;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import java.io.StringReader;
import java.sql.Date;
import java.sql.SQLException;
import java.util.List;

/**
 * RESTful API endpoints for managing homework.
 */
@Path("/homework")
public class HomeworkRoute {

    /**
     * Creates a new homework entry.
     *
     * @param data JSON representation of the homework data
     * @return true if homework is created successfully, false otherwise
     */
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public boolean createHomework(String data) {
        try (JsonReader jsonReader = Json.createReader(new StringReader(data))) {
            JsonObject jsonObject = jsonReader.readObject();

            boolean isDivisible = jsonObject.getBoolean("isDivisible");
            String start = jsonObject.getString("start_date");
            String due = jsonObject.getString("due_date");
            String description = jsonObject.getString("description");
            int timeIndication = jsonObject.getInt("timeIndication");
            int homework_id = jsonObject.getInt("homework_id");
            int lesson_id = jsonObject.getInt("lesson_id");
            int student_id = jsonObject.getInt("student_id");
            int class_id = jsonObject.getInt("class_id");

            Date start_date = Date.valueOf(start);
            Date due_date = Date.valueOf(due);

            Homework homework =
                    new Homework(isDivisible, start_date, due_date, description, timeIndication,
                                 homework_id, lesson_id, student_id, class_id);

            HomeworkDao.INSTANCE.createHomework(homework);
            return true;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Deletes a homework entry by its ID.
     *
     * @param homework_id the ID of the homework to be deleted
     * @throws SQLException if a database access error occurs
     */
    @DELETE
    @Path("/{homework_id}")
    public void deleteHomework(@PathParam("homework_id") int homework_id) throws SQLException {
        HomeworkDao.INSTANCE.deleteHomework(homework_id);
    }

    /**
     * Retrieves a homework entry by its ID.
     *
     * @param homework_id the ID of the homework to be retrieved
     * @return the Homework object
     * @throws SQLException if a database access error occurs
     */
    @GET
    @Path("/{homework_id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Homework getHomework(@PathParam("homework_id") int homework_id) throws SQLException {
        return HomeworkDao.INSTANCE.getHomework(homework_id);
    }

    /**
     * Retrieves all homework entries for a specific lesson.
     *
     * @param lesson_id the ID of the lesson
     * @return a list of Homework objects
     * @throws SQLException if a database access error occurs
     */
    @GET
    @Path("/{lesson_id}/lesson")
    @Produces(MediaType.APPLICATION_JSON)
    public List<Homework> getHomeworkForLesson(@PathParam("lesson_id") int lesson_id) throws SQLException {
        return HomeworkDao.INSTANCE.getAllHomework(lesson_id);
    }

    /**
     * Retrieves all homework entries for a specific class.
     *
     * @param class_id the ID of the class
     * @return a list of Homework objects
     * @throws SQLException if a database access error occurs
     */
    @GET
    @Path("/{class_id}/class")
    @Produces(MediaType.APPLICATION_JSON)
    public List<Homework> getHomeworkForClass(@PathParam("class_id") int class_id) throws SQLException {
        return HomeworkDao.INSTANCE.getHomeworkClass(class_id);
    }

    /**
     * Retrieves all homework entries.
     *
     * @return a list of all Homework objects
     * @throws SQLException if a database access error occurs
     */
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<Homework> getAllHomework() throws SQLException {
        return HomeworkDao.INSTANCE.getAllHomework();
    }

    /**
     * Retrieves all homework entries for a specific class and lesson.
     *
     * @param class_id the ID of the class
     * @param lesson_id the ID of the lesson
     * @return a list of Homework objects
     * @throws SQLException if a database access error occurs
     */
    @GET
    @Path("/{class_id}/{lesson_id}")
    @Produces(MediaType.APPLICATION_JSON)
    public List<Homework> getLessonHomework(@PathParam("class_id") int class_id, @PathParam("lesson_id") int lesson_id) throws SQLException {
        return HomeworkDao.INSTANCE.getLessonHomework(class_id, lesson_id);
    }

    /**
     * Updates an existing homework entry.
     *
     * @param data JSON representation of the homework data
     * @return true if homework is updated successfully, false otherwise
     * @throws SQLException if a database access error occurs
     */
    @PUT
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public boolean updateHomework(String data) throws SQLException {
        try (JsonReader jsonReader = Json.createReader(new StringReader(data))) {
            JsonObject jsonObject = jsonReader.readObject();

            boolean isDivisible = jsonObject.getBoolean("isDivisible");
            String start = jsonObject.getString("start_date");
            String due = jsonObject.getString("due_date");
            String description = jsonObject.getString("description");
            int timeIndication = jsonObject.getInt("timeIndication");
            int homework_id = jsonObject.getInt("homework_id");
            int lesson_id = jsonObject.getInt("lesson_id");
            int student_id = jsonObject.getInt("student_id");
            int class_id = jsonObject.getInt("class_id");

            Date start_date = Date.valueOf(start);
            Date due_date = Date.valueOf(due);

            Homework homework =
                    new Homework(isDivisible, start_date, due_date, description, timeIndication,
                                 homework_id, lesson_id, student_id, class_id);

            HomeworkDao.INSTANCE.updateHomework(homework);
            return true;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

}
