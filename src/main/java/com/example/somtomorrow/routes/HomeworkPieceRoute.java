package com.example.somtomorrow.routes;

import com.example.somtomorrow.dao.HomeworkPieceDao;
import com.example.somtomorrow.model.HomeworkPiece;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import java.sql.SQLException;
import java.util.List;

/**
 * RESTful API routes for handling HomeworkPiece operations.
 */
@Path("/pieces")
public class HomeworkPieceRoute {

    /**
     * Creates a new homework piece for a specific student and lesson.
     *
     * @param homeworkPiece the HomeworkPiece object to be created
     * @param lesson_id the ID of the lesson
     * @param student_id the ID of the student
     * @throws SQLException if a database access error occurs
     */
    @POST
    @Path("/{student_id}/{lesson_id}")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public void createPiece(HomeworkPiece homeworkPiece, @PathParam("lesson_id") int lesson_id, @PathParam("student_id") int student_id) throws SQLException {
        HomeworkPieceDao.INSTANCE.createPiece(homeworkPiece, lesson_id, student_id);
    }

    /**
     * Deletes a homework piece based on its ID.
     *
     * @param homework_id the ID of the homework piece to be deleted
     * @throws SQLException if a database access error occurs
     */
    @DELETE
    @Path("/{id}")
    public void deletePiece(@PathParam("id") int homework_id) throws SQLException {
        HomeworkPieceDao.INSTANCE.deletePiece(homework_id);
    }

    /**
     * Retrieves a list of homework pieces based on the homework ID.
     *
     * @param homework_id the ID of the homework piece
     * @return a list of HomeworkPiece objects
     * @throws SQLException if a database access error occurs
     */
    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public List<HomeworkPiece> getPiece(@PathParam("id") int homework_id) throws SQLException {
        return HomeworkPieceDao.INSTANCE.getPiece(homework_id);
    }

    /**
     * Retrieves all homework pieces for a specific student and lesson.
     *
     * @param student_id the ID of the student
     * @param lesson_id the ID of the lesson
     * @return a list of HomeworkPiece objects
     * @throws SQLException if a database access error occurs
     */
    @GET
    @Path("/{student_id}/{lesson_id}")
    @Produces(MediaType.APPLICATION_JSON)
    public List<HomeworkPiece> getAllPieces(@PathParam("student_id") int student_id, @PathParam("lesson_id") int lesson_id) throws SQLException {
        return HomeworkPieceDao.INSTANCE.getAllPieces(lesson_id, student_id);
    }

    /**
     * Updates an existing homework piece.
     *
     * @param homeworkPiece the HomeworkPiece object with updated details
     * @return true if the homework piece was updated successfully, false otherwise
     * @throws SQLException if a database access error occurs
     */
    @PUT
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public boolean updatePiece(HomeworkPiece homeworkPiece) throws SQLException {
        return HomeworkPieceDao.INSTANCE.updatePiece(homeworkPiece);
    }
}
