package com.example.somtomorrow.routes;

import com.example.somtomorrow.dao.LoginDao;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import jakarta.json.Json;
import jakarta.json.JsonObject;
import jakarta.json.JsonReader;

import java.io.StringReader;
import java.util.regex.Pattern;

/**
 * RESTful API route for handling login operations.
 */
@Path("/login")
public class LoginRoute {

    private static final Pattern USERNAME_PATTERN = Pattern.compile("^[a-zA-Z0-9]{3,20}$");
    private static final Pattern PASSWORD_PATTERN = Pattern.compile("^[a-zA-Z0-9@#$%^&+=]{8,20}$");

    /**
     * Validates the login credentials.
     *
     * @param data JSON string containing username and password
     * @return Response object containing the role of the user if credentials are valid, otherwise an error message
     */
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response validateLogin(String data) {
        try (JsonReader jsonReader = Json.createReader(new StringReader(data))) {
            JsonObject json = jsonReader.readObject();
            String username = json.getString("username");
            String password = json.getString("password");

            if (!isValidInput(username, password)) {
                JsonObject responseJson = Json.createObjectBuilder()
                        .add("message", "Invalid input format")
                        .build();
                return Response.status(Response.Status.BAD_REQUEST)
                        .entity(responseJson.toString())
                        .build();
            }

            String role = LoginDao.INSTANCE.validateLogin(username, password);

            if ("null".equals(role)) {
                JsonObject responseJson = Json.createObjectBuilder()
                        .add("message", "Incorrect username or password")
                        .build();
                return Response.status(Response.Status.UNAUTHORIZED)
                        .entity(responseJson.toString())
                        .build();
            }

            JsonObject responseJson = Json.createObjectBuilder()
                    .add("role", role)
                    .build();
            return Response.ok(responseJson.toString()).build();
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
     * Validates the format of the username and password.
     *
     * @param username the username to validate
     * @param password the password to validate
     * @return true if both username and password are in valid format, false otherwise
     */
    private boolean isValidInput(String username, String password) {
        return USERNAME_PATTERN.matcher(username).matches() && PASSWORD_PATTERN.matcher(password).matches();
    }
}
