package com.example.somtomorrow.routes;

import com.example.somtomorrow.dao.ClassDao;
import com.example.somtomorrow.model.Class;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.sql.SQLException;
import java.util.List;

/**
 * RESTful API routes for managing Class entities.
 */
@Path("/class")
public class ClassRoute {

    /**
     * Creates a new class.
     *
     * @param classId the ID of the class to create
     * @param newClass the Class object containing the new class details
     * @return a Response indicating the result of the operation
     */
    @POST
    @Path("/{classId}/")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response createClass(@PathParam("classId") int classId, Class newClass) {
        try {
            ClassDao.INSTANCE.addClass(newClass, classId);
            return Response.status(Response.Status.CREATED).build();
        } catch (SQLException e) {
            e.printStackTrace();
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Error creating class").build();
        }
    }

    /**
     * Deletes an existing class.
     *
     * @param classId the ID of the class to delete
     * @return a Response indicating the result of the operation
     */
    @DELETE
    @Path("/{classId}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response deleteClass(@PathParam("classId") int classId) {
        ClassDao.INSTANCE.deleteClass(classId);
        return Response.status(Response.Status.NO_CONTENT).build();
    }

    /**
     * Retrieves a class by its ID.
     *
     * @param classId the ID of the class to retrieve
     * @return the Class object if found
     * @throws SQLException if a database access error occurs
     */
    @GET
    @Path("/{classId}")
    @Produces(MediaType.APPLICATION_JSON)
    public Class getClass(@PathParam("classId") int classId) throws SQLException {
        return ClassDao.INSTANCE.getClass(classId);
    }

    /**
     * Retrieves all classes.
     *
     * @return a Response containing a list of all classes
     */
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllClasses() {
        try {
            List<Class> classes = ClassDao.INSTANCE.getAllClasses();
            return Response.ok(classes).build();
        } catch (SQLException e) {
            e.printStackTrace();
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Error fetching classes").build();
        }
    }

    /**
     * Updates an existing class.
     *
     * @param classId the ID of the class to update
     * @param updatedClass the Class object containing the updated class details
     * @return a Response indicating the result of the operation
     */
    @PUT
    @Path("/{classId}")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response updateClass(@PathParam("classId") int classId, Class updatedClass) {
        try {
            ClassDao.INSTANCE.updateClass(classId, updatedClass);
            return Response.ok().build();
        } catch (SQLException e) {
            e.printStackTrace();
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Error updating class").build();
        }
    }
}

