package com.example.somtomorrow.routes;

import com.example.somtomorrow.dao.RegisterDao;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import jakarta.json.Json;
import jakarta.json.JsonObject;
import jakarta.json.JsonReader;

import java.io.StringReader;

/**
 * RESTful API route for user registration.
 */
@Path("/register")
public class RegisterRoute {

    /**
     * Registers a new user.
     *
     * @param data the JSON data representing the new user
     * @return a Response indicating the result of the registration process
     */
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response registerUser(String data) {
        try (JsonReader jsonReader = Json.createReader(new StringReader(data))) {
            JsonObject json = jsonReader.readObject();
            String username = json.getString("username");
            String password = json.getString("password");

            if (!isValidUsername(username)) {
                JsonObject responseJson = Json.createObjectBuilder()
                        .add("message", "Invalid username format")
                        .build();
                return Response.status(Response.Status.BAD_REQUEST)
                        .entity(responseJson.toString())
                        .build();
            }

            boolean success = RegisterDao.INSTANCE.registerUser(username, password);

            if (success) {
                JsonObject responseJson = Json.createObjectBuilder()
                        .add("message", "Registration successful")
                        .build();
                return Response.ok(responseJson.toString()).build();
            } else {
                JsonObject responseJson = Json.createObjectBuilder()
                        .add("message", "Registration failed")
                        .build();
                return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                        .entity(responseJson.toString())
                        .build();
            }
        } catch (RuntimeException e) {
            JsonObject errorJson = Json.createObjectBuilder()
                    .add("message", "An error occurred while processing the request")
                    .build();
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity(errorJson.toString())
                    .build();
        }
    }

    /**
     * Checks if the given username is valid.
     *
     * @param username the username to check
     * @return true if the username is valid, false otherwise
     */
    private boolean isValidUsername(String username) {
        return username.startsWith("S") || username.startsWith("T");
    }
}
