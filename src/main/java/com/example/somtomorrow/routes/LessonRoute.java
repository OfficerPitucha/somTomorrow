package com.example.somtomorrow.routes;

import com.example.somtomorrow.dao.LessonDao;
import com.example.somtomorrow.model.Lesson;
import jakarta.json.Json;
import jakarta.json.JsonObject;
import jakarta.json.JsonReader;
import jakarta.json.JsonNumber;
import jakarta.json.JsonString;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.io.StringReader;
import java.sql.SQLException;
import java.util.List;

/**
 * RESTful API routes for managing Lesson entities.
 */
@Path("/lesson")
public class LessonRoute {

    /**
     * Creates a new lesson.
     *
     * @param data the JSON data representing the new lesson
     * @return a Response indicating the result of the operation
     * @throws SQLException if a database access error occurs
     */
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response createLesson(String data) throws SQLException {
        try (JsonReader jsonReader = Json.createReader(new StringReader(data))) {
            JsonObject jsonObject = jsonReader.readObject();

            int classId = getJsonInt(jsonObject, "classId");
            String title = jsonObject.getString("title");
            boolean hasHomework = jsonObject.getBoolean("hasHomework");
            String location = jsonObject.getString("location");
            int period = getJsonInt(jsonObject, "period");
            String day = jsonObject.getString("day");

            Lesson lesson = new Lesson(classId, title, hasHomework, location, period, day);
            boolean isCreated = LessonDao.INSTANCE.createLesson(lesson);

            if (isCreated) {
                return Response.status(Response.Status.CREATED).entity(lesson).build();
            } else {
                return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Error creating lesson").build();
            }
        } catch (Exception e) {
            e.printStackTrace();
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Error creating lesson: " + e.getMessage()).build();
        }
    }

    /**
     * Helper method to extract an integer value from a JSON object.
     *
     * @param jsonObject the JSON object
     * @param key the key for the integer value
     * @return the integer value
     */
    private int getJsonInt(JsonObject jsonObject, String key) {
        try {
            JsonNumber jsonNumber = jsonObject.getJsonNumber(key);
            return jsonNumber.intValue();
        } catch (ClassCastException e) {
            JsonString jsonString = jsonObject.getJsonString(key);
            return Integer.parseInt(jsonString.getString());
        }
    }

    /**
     * Deletes an existing lesson.
     *
     * @param lessonId the ID of the lesson to delete
     * @throws SQLException if a database access error occurs
     */
    @DELETE
    @Path("/{lessonId}")
    public void deleteLesson(@PathParam("lessonId") int lessonId) throws SQLException {
        LessonDao.INSTANCE.deleteLesson(lessonId);
    }

    /**
     * Retrieves a lesson by its ID.
     *
     * @param lesson_id the ID of the lesson to retrieve
     * @return the Lesson object if found
     * @throws SQLException if a database access error occurs
     */
    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Lesson getLesson(@PathParam("id") int lesson_id) throws SQLException {
        return LessonDao.INSTANCE.getLesson(lesson_id);
    }

    /**
     * Retrieves all lessons.
     *
     * @return a list of all Lesson objects
     * @throws SQLException if a database access error occurs
     */
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<Lesson> getAllLessons() throws SQLException {
        return LessonDao.INSTANCE.getAllLessons();
    }

    /**
     * Retrieves all lessons for a specific class.
     *
     * @param class_id the ID of the class
     * @return a list of Lesson objects for the specified class
     * @throws SQLException if a database access error occurs
     */
    @GET
    @Path("/{class_id}/lessons")
    @Produces(MediaType.APPLICATION_JSON)
    public List<Lesson> getAllLessons(@PathParam("class_id") int class_id) throws SQLException {
        return LessonDao.INSTANCE.getLessonsOfClass(class_id);
    }

    /**
     * Updates an existing lesson.
     *
     * @param lesson the Lesson object containing the updated details
     * @return true if the update was successful, false otherwise
     * @throws SQLException if a database access error occurs
     */
    @PUT
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public boolean updateLesson(Lesson lesson) throws SQLException {
        return LessonDao.INSTANCE.updateLesson(lesson);
    }
}
