package com.example.somtomorrow.dao;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNotEquals;

/**
 * This class will test the log in dao.
 */
public class LoginDaoTest {
    /**
     * This test will test the validate log in functionality. We will give some wrong and valid
     * passwords and see if the method returns correctly.
     */
    @Test
    public void validateLogInTest() {
        //Wrong password
        assertNotEquals(LoginDao.INSTANCE.validateLogin("T1", "jP2`oRT"), "teacher");
        assertNotEquals(LoginDao.INSTANCE.validateLogin("T8", "qX4@}}vl!yh"), "teacher");
        assertNotEquals(LoginDao.INSTANCE.validateLogin("T10", "kA1@)1UlZ6<DYP"), "teacher");
        assertNotEquals(LoginDao.INSTANCE.validateLogin("S11", "dD(XVF0Z&bxY8"), "student");
        assertNotEquals(LoginDao.INSTANCE.validateLogin("S13", "cF5/5pcJyc_{"), "student");
        assertNotEquals(LoginDao.INSTANCE.validateLogin("S20", "dV1</I>pL5765"), "student");
    }
}