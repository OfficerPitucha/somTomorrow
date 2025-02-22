package com.example.somtomorrow.model;

/**
 * The representation of teacher in our system
 */
public class Teacher {
    private int teacher_id;
    private String subject;
    private Person person;

    // Getters and Setters
    public int getTeacher_id() {
        return teacher_id;
    }

    public void setTeacher_id(int teacher_id) {
        this.teacher_id = teacher_id;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public Person getPerson() {
        return person;
    }

    public void setPerson(Person person) {
        this.person = person;
    }
}
