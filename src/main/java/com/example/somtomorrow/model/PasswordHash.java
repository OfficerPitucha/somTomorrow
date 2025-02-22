package com.example.somtomorrow.model;

import org.mindrot.jbcrypt.BCrypt;

/**
 * This class provides password hashing functionality
 */
public class PasswordHash {
    public static String hashPassword(String password) {
        return BCrypt.hashpw(password, BCrypt.gensalt());
    }

    public static boolean checkPassword(String candidate, String hashed) {
        return BCrypt.checkpw(candidate, hashed);
    }
}
