package com.example.somtomorrow.dao;

import com.example.somtomorrow.model.Lesson;
import java.sql.SQLException;
import java.util.List;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * This class will test the methods of the LessonDao class.
 */
public class LessonDaoTest {
    /**
     * For this test a new lesson will first be added to the database and then
     * returned to check if it is the right lesson.
     */
    @Test
    public void addALessonCorrectlyToTheDatabaseTest() throws SQLException {
        //We add a new lesson
        Lesson newLesson = new Lesson(4,
                                      "Mathematics", true,
                                      "SP", 5, "Monday");
        LessonDao.INSTANCE.createLesson(newLesson);

        //We get the lesson from the database
        Lesson returnedLesson = LessonDao.INSTANCE.getLesson(LessonDao.INSTANCE.getMaxId());

        assertEquals(newLesson.getLessonId(), returnedLesson.getLessonId());
        assertEquals(newLesson.getClassId(), returnedLesson.getClassId());
        assertEquals(newLesson.getTitle(), returnedLesson.getTitle());
        assertEquals(newLesson.hasHomework(), returnedLesson.hasHomework());
        assertEquals(newLesson.getLocation(), returnedLesson.getLocation());
        assertEquals(newLesson.getPeriod(), returnedLesson.getPeriod());
        assertEquals(newLesson.getDay(), returnedLesson.getDay());

        //Delete lesson from database
        LessonDao.INSTANCE.deleteLesson(LessonDao.INSTANCE.getMaxId());
    }

    /**
     * In this test we return all the lessons in the database so far.
     * Because they are so many lessons, only the first 2 and last 3 will be checked
     * if they are in returned correctly.
     */
    @Test
    public void getAllLessonOfAClass() throws SQLException {
        //Get all lessons of a class from the database

        List<Lesson> allLessons = LessonDao.INSTANCE.getLessonsOfClass(1);
        assertEquals(allLessons.get(0).getLessonId(), 36);
        assertEquals(allLessons.get(0).getClassId(), 1);
        assertEquals(allLessons.get(0).getTitle(), "Science");
        assertEquals(allLessons.get(0).hasHomework(), false);
        assertEquals(allLessons.get(0).getLocation(), "sw");
        assertEquals(allLessons.get(0).getPeriod(),6);
        assertEquals(allLessons.get(0).getDay(), "Tuesday");

        assertEquals(allLessons.get(1).getLessonId(), 19);
        assertEquals(allLessons.get(1).getClassId(), 1);
        assertEquals(allLessons.get(1).getTitle(), "Music");
        assertEquals(allLessons.get(1).hasHomework(), false);
        assertEquals(allLessons.get(1).getLocation(), "AS");
        assertEquals(allLessons.get(1).getPeriod(),0);
        assertEquals(allLessons.get(1).getDay(), "");

        assertEquals(allLessons.get(2).getLessonId(), 4);
        assertEquals(allLessons.get(2).getClassId(), 1);
        assertEquals(allLessons.get(2).getTitle(), "German language");
        assertEquals(allLessons.get(2).hasHomework(), false);
        assertEquals(allLessons.get(2).getLocation(), "AS");
        assertEquals(allLessons.get(2).getPeriod(),0);
        assertEquals(allLessons.get(2).getDay(), "");
    }



}
