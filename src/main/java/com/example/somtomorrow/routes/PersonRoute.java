package com.example.somtomorrow.routes;

import com.example.somtomorrow.dao.PersonDao;
import com.example.somtomorrow.model.Person;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import java.sql.SQLException;

/**
 * RESTful API endpoints for managing persons.
 */
@Path("/person")
public class PersonRoute {

    /**
     * Retrieves a person by their ID.
     *
     * @param personId the ID of the person to be retrieved
     * @return a Response containing the person details or an error message
     */
    @GET
    @Path("/{personId}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getPerson(@PathParam("personId") int personId) {
        try {
            Person person = PersonDao.INSTANCE.getPersonById(personId);
            return Response.ok(person).build();
        } catch (SQLException e) {
            return Response.status(Response.Status.BAD_REQUEST).entity("Error fetching person details: " + e.getMessage()).build();
        }
    }

    /**
     * Updates a person's details.
     *
     * @param person the Person object containing updated details
     * @return a Response indicating the result of the update operation
     */
    @PUT
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response updatePerson(Person person) {
        try {
            boolean updated = PersonDao.INSTANCE.updatePerson(person);
            if (updated) {
                return Response.ok().build();
            } else {
                return Response.status(Response.Status.NOT_FOUND).entity("Person not found").build();
            }
        } catch (SQLException e) {
            return Response.status(Response.Status.BAD_REQUEST).entity("Error updating person details: " + e.getMessage()).build();
        }
    }
}
