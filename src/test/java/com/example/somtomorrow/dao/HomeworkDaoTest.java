package com.example.somtomorrow.dao;

import com.example.somtomorrow.model.Homework;
import java.sql.Date;
import java.sql.SQLException;
import java.util.List;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * This class will contain methods that will test the Homework
 */
public class HomeworkDaoTest {

    /**
     * This test check if homework is put in the database if a new homework is added.
     */
    @Test
    public void addHomeworkTest() throws SQLException {
        //Delete this homework if it already exists
        HomeworkDao.INSTANCE.deleteHomework(HomeworkDao.INSTANCE.getMaxId());

        //Create new Homework
        Homework newHomework = new Homework();
        newHomework.setDivisible(true);
        newHomework.setStart_date(Date.valueOf("2024-06-30"));
        newHomework.setDue_date(Date.valueOf("2024-07-03"));
        newHomework.setDescription("Math: Chapter 15");
        newHomework.setLesson_id(5);
        newHomework.setTimeIndication(60);
        newHomework.setClass_id(3);
        HomeworkDao.INSTANCE.createHomework(newHomework);

        //Get created homework from database
        Homework returnedHomework = HomeworkDao.INSTANCE.getHomework(HomeworkDao.INSTANCE.getMaxId());

        //Check if both homeworks are the same
                assertNotNull(returnedHomework);
        assertEquals(newHomework.isDivisible(), returnedHomework.isDivisible());
        assertEquals(newHomework.getStart_date(), returnedHomework.getStart_date());
        assertEquals(newHomework.getDue_date(), returnedHomework.getDue_date());
        assertEquals(newHomework.getDescription(), returnedHomework.getDescription());
        assertEquals(newHomework.getLesson_id(), returnedHomework.getLesson_id());
        assertEquals(newHomework.getTimeIndication(), returnedHomework.getTimeIndication());
    }

    /**
     * In this test we return all the homeworks in the database so far.
     * Because they are so much homework, only the first 2 and last 3 will be checked
     * if they are in the database.
     */
    @Test
    public void getAllHomeworkFromTheDatabaseTest() throws SQLException {
        //Get all classes from the database
        List<Homework> allHomework = HomeworkDao.INSTANCE.getAllHomework();

        assertEquals(allHomework.get(0).isDivisible(), true);
        assertEquals(allHomework.get(0).getStart_date(), Date.valueOf("2024-06-26"));
        assertEquals(allHomework.get(0).getDue_date(), Date.valueOf("2024-07-05"));
        assertEquals(allHomework.get(0).getDescription(), "Homework assignment");
        assertEquals(allHomework.get(0).getLesson_id(), 11);
        assertEquals(allHomework.get(0).getTimeIndication(), 45);
        assertEquals(allHomework.get(0).getHomework_id(), 1);


        assertEquals(allHomework.get(1).isDivisible(), false);
        assertEquals(allHomework.get(1).getStart_date(), Date.valueOf("2024-06-20"));
        assertEquals(allHomework.get(1).getDue_date(), Date.valueOf("2024-07-01"));
        assertEquals(allHomework.get(1).getDescription(), "Homework assignment");
        assertEquals(allHomework.get(1).getLesson_id(), 17);
        assertEquals(allHomework.get(1).getTimeIndication(), 45);
        assertEquals(allHomework.get(1).getHomework_id(), 2);


        assertEquals(allHomework.get(2).isDivisible(), true);
        assertEquals(allHomework.get(2).getStart_date(), Date.valueOf("2024-06-17"));
        assertEquals(allHomework.get(2).getDue_date(), Date.valueOf("2024-07-11"));
        assertEquals(allHomework.get(2).getDescription(), "Homework assignment");
        assertEquals(allHomework.get(2).getLesson_id(), 6);
        assertEquals(allHomework.get(2).getTimeIndication(), 45);
        assertEquals(allHomework.get(2).getHomework_id(), 3);
        //This was the data in the database at this point in time.
        //        true,2024-06-26,2024-07-05,,11,20
        //        false,2024-06-20,2024-07-01,,17,12
        //        true,2024-06-17,2024-07-11,,6,16
        //
    }

    /**
     * This method updates homework and tests if is updated right.
     */
    @Test
    public void updateAClassAndCheckIfCorrectTest() throws SQLException {
        //Delete this homework if it already exists
        HomeworkDao.INSTANCE.deleteHomework(HomeworkDao.INSTANCE.getMaxId());

        //Create new Homework
        Homework newHomework = new Homework();
        newHomework.setDivisible(true);
        newHomework.setStart_date(Date.valueOf("2024-06-30"));
        newHomework.setDue_date(Date.valueOf("2024-07-03"));
        newHomework.setDescription("Math: Chapter 15");
        newHomework.setLesson_id(30);
        newHomework.setClass_id(5);
        newHomework.setTimeIndication(40);
        HomeworkDao.INSTANCE.createHomework(newHomework);

        //We update this class, lessonId and studentId should stay same.
        Homework updatedHomework = new Homework(false, Date.valueOf("2024-06-15"),
                                                Date.valueOf("2024-07-15"), "Math: Chapter 15.5",
                                                30, HomeworkDao.INSTANCE.getMaxId(), 6, 5, 7);
        HomeworkDao.INSTANCE.updateHomework(updatedHomework);

        Homework returnedHomework = HomeworkDao.INSTANCE.getHomework(HomeworkDao.INSTANCE.getMaxId());

        //We check if the returned data of this homework is the updated/new data.
        assertEquals(updatedHomework.getLesson_id(), returnedHomework.getLesson_id());
        assertEquals(updatedHomework.getClass_id(), returnedHomework.getClass_id());
        assertEquals(updatedHomework.isDivisible(), returnedHomework.isDivisible());
        assertEquals(updatedHomework.getStart_date(), returnedHomework.getStart_date());
        assertEquals(updatedHomework.getDue_date(), returnedHomework.getDue_date());
        assertEquals(updatedHomework.getDescription(), returnedHomework.getDescription());

        //Check if the old data is not in the database
        assertNotEquals(newHomework.getLesson_id(), returnedHomework.getLesson_id());
        assertNotEquals(newHomework.getStudent_id(), returnedHomework.getStudent_id());
        assertNotEquals(newHomework.isDivisible(), returnedHomework.isDivisible());
        assertNotEquals(newHomework.getStart_date(), returnedHomework.getStart_date());
        assertNotEquals(newHomework.getDue_date(), returnedHomework.getDue_date());
        assertNotEquals(newHomework.getDescription(), returnedHomework.getDescription());

        //Delete this homework from database
        HomeworkDao.INSTANCE.deleteHomework(HomeworkDao.INSTANCE.getMaxId());
            }
    }

