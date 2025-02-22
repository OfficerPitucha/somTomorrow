package com.example.somtomorrow.dao;

import com.example.somtomorrow.model.Student;
import java.sql.SQLException;
import java.util.List;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * This class will test the StudentDao class.
 */
public class StudentDaoTest {
    /**
     * For this test a new student will first be added to the database and then
     * returned to check if he/she is the right class.
     */
    @Test
    public void addAStudentCorrectlyToTheDatabaseTest() throws SQLException {
        //We add a new student
        Student newStudent = new Student("Kim", "Johnson", StudentDao.INSTANCE.getMaxId() + 1, 7, "enrolled", 1, 1.1, "ab@ba");
        StudentDao.INSTANCE.addStudent(newStudent);

        //We get the student from the database
        Student returnedStudent = StudentDao.INSTANCE.getAStudent(7, StudentDao.INSTANCE.getMaxId());

        System.out.println(returnedStudent);
        assertEquals(newStudent.getStudent_id(), returnedStudent.getStudent_id());
        assertEquals(newStudent.getName(), returnedStudent.getName());
        assertEquals(newStudent.getSurname(), returnedStudent.getSurname());

//        //Delete student
        StudentDao.INSTANCE.deleteStudent(StudentDao.INSTANCE.getMaxId());
    }

    /**
     * Test for getting all students from a class.
     */
    @Test
    public void getAllStudentsFromAClassTest() throws SQLException {
        //Get all students from a class
        List<Student> studentsC1 = StudentDao.INSTANCE.getStudentsOfAClass(3);

        System.out.println(studentsC1);

        assertEquals(studentsC1.get(0).getStudent_id(),20);
        assertEquals(studentsC1.get(0).getName(), "Orson");
        assertEquals(studentsC1.get(0).getSurname(), "Swales");

        assertEquals(studentsC1.get(1).getStudent_id(), 25);
        assertEquals(studentsC1.get(1).getName(), "Kim");
        assertEquals(studentsC1.get(1).getSurname(), "Johnson");

    }
}
