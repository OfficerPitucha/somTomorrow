package com.example.somtomorrow.dao;

import com.example.somtomorrow.model.Class;
import java.sql.SQLException;
import java.util.List;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

/**
 * This class tests the methods of the ClassDao.
 */
public class ClassDaoTest {

    /**
     * For this test a new class will first be added to the database and then
     * returned to check if it is the right class.
     */
    @Test
    public void addAClassCorrectlyToTheDatabaseTest() throws SQLException {


        //We add a new class
        Class newClass = new Class(ClassDao.INSTANCE.getMaxId() + 1, "C10", 0);
        ClassDao.INSTANCE.addClass(newClass, ClassDao.INSTANCE.getMaxId());

        //We get the class from the database
        Class returnedClass = ClassDao.INSTANCE.getClass(ClassDao.INSTANCE.getMaxId());

        assertEquals(newClass.getName(), returnedClass.getName());
        assertEquals(newClass.getStudentCount(), returnedClass.getStudentCount());

        //Delete class
        ClassDao.INSTANCE.deleteClass(ClassDao.INSTANCE.getMaxId());
    }

    /**
     * In this test we return all the classes in the database so far.
     * Because they are so many classes, only the first 5 will be checked
     * if they are in the database.
     */
    @Test
    public void getAllClassesFromTheDatabaseTest() throws SQLException {
        //Get all classes from the database
        List<Class> allClasses = ClassDao.INSTANCE.getAllClasses();

        assertEquals(allClasses.get(0).getClassId(), 74);
        assertEquals(allClasses.get(0).getName(), "testingTHISCLASS");
        assertEquals(allClasses.get(0).getStudentCount(), 0);

        assertEquals(allClasses.get(1).getClassId(), 78);
        assertEquals(allClasses.get(1).getName(), "maintestclass");
        assertEquals(allClasses.get(1).getStudentCount(), 0);

        assertEquals(allClasses.get(3).getClassId(), 82);
        assertEquals(allClasses.get(3).getName(), "Probability");
        assertEquals(allClasses.get(3).getStudentCount(), 0);


//        This was the data in the database at this point in time.
//        74,testingTHISCLASS,0
//        78,maintestclass,0
//        82,Probability,0

    }

    /**
     * This method updates a class and tests if is updated right.
     */
    @Test
    public void updateAClassCorrectlyInTheDatabaseTest() throws SQLException {

        //We add a new class
        Class firstClass = new Class(ClassDao.INSTANCE.getMaxId() + 1, "B12", 0);
        ClassDao.INSTANCE.addClass(firstClass, 0);

        //We update this class, classId stays same
        Class updatedClass = new Class(ClassDao.INSTANCE.getMaxId(), "F23", 0);
        ClassDao.INSTANCE.updateClass(ClassDao.INSTANCE.getMaxId(), updatedClass);

        Class returnedClass = ClassDao.INSTANCE.getClass(ClassDao.INSTANCE.getMaxId());

        //We check if the returned data of this class is the updated/new data.
        assertEquals(updatedClass.getName(), returnedClass.getName());
        assertEquals(updatedClass.getStudentCount(), returnedClass.getStudentCount());

        //Check if the old data is not in the database
        assertNotEquals(firstClass.getName(), returnedClass.getName());
        assertEquals(firstClass.getStudentCount(), returnedClass.getStudentCount());

        //Before running we delete previous class with same id.
        ClassDao.INSTANCE.deleteClass(ClassDao.INSTANCE.getMaxId());
    }
}
