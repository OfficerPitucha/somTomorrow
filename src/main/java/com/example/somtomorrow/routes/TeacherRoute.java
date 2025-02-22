package com.example.somtomorrow.routes;

import com.example.somtomorrow.dao.TeacherDao;
import com.example.somtomorrow.model.Teacher;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.sql.SQLException;

/**
 * RESTful API route for managing Teacher entities.
 */
@Path("/teacher")
public class TeacherRoute {

    /**
     * Retrieves a teacher by ID.
     *
     * @param teacherId the ID of the teacher to retrieve
     * @return a Response containing the retrieved Teacher object
     */
    @GET
    @Path("/{teacherId}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getTeacher(@PathParam("teacherId") int teacherId) {
        try {
            Teacher teacher = TeacherDao.INSTANCE.getTeacherById(teacherId);
            return Response.status(Response.Status.OK).entity(teacher).build();
        } catch (SQLException e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Error retrieving teacher: " + e.getMessage()).build();
        }
    }

    /**
     * Updates an existing teacher.
     *
     * @param teacher the Teacher object containing the updated details
     * @return a Response indicating the result of the update operation
     */
    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response updateTeacher(Teacher teacher) {
        try {
            boolean updated = TeacherDao.INSTANCE.updateTeacher(teacher);
            if (updated) {
                return Response.status(Response.Status.OK).entity("Teacher updated successfully.").build();
            } else {
                return Response.status(Response.Status.NOT_MODIFIED).entity("Teacher update failed.").build();
            }
        } catch (SQLException e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Error updating teacher: " + e.getMessage()).build();
        }
    }

    /**
     * Retrieves a teacher ID by username.
     *
     * @param username the username of the teacher
     * @return a Response containing the teacher ID
     */
    @GET
    @Path("/byUsername/{username}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getTeacherIdByUsername(@PathParam("username") String username) {
        try {
            Integer teacherId = TeacherDao.INSTANCE.getTeacherIdByUsername(username);
            if (teacherId == null) {
                return Response.status(Response.Status.NOT_FOUND).entity("Teacher not found").build();
            }
            return Response.ok(teacherId).build();
        } catch (SQLException e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Error retrieving teacher ID: " + e.getMessage()).build();
        }
    }
}
