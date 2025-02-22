package com.example.somtomorrow.model;
/**
 * The representation of homework entity in our system
 */
public class Homework {
    private boolean isDivisible;
    private java.sql.Date start_date;
    private java.sql.Date due_date;
    private String description;
    private int timeIndication;
    private int homework_id;
    private int lesson_id;
    private int student_id;
    private int class_id;

    public Homework(boolean isDivisible, java.sql.Date start_date, java.sql.Date due_date, String description, int timeIndication, int homework_id, int lesson_id, int student_id, int class_id) {
        this.isDivisible = isDivisible;
        this.start_date = start_date;
        this.due_date = due_date;
        this.description = description;
        this.timeIndication = timeIndication;
        this.homework_id = homework_id;
        this.lesson_id = lesson_id;
        this.student_id = student_id;
        this.class_id = class_id;
    }

    public Homework() {
        this.isDivisible = false;
        this.start_date = null;
        this.due_date = null;
        this.description = null;
        this.timeIndication = 0;
        this.homework_id = 0;
        this.lesson_id = 0;
        this.student_id = 0;
        this.class_id = 0;
    }

    @Override
    public String toString() {
        return "Homework{" +
                "isDivisible=" + isDivisible +
                ", start_date=" + start_date +
                ", due_date=" + due_date +
                ", description='" + description + '\'' +
                ", timeIndication=" + timeIndication +
                ", homework_id=" + homework_id +
                ", lesson_id=" + lesson_id +
                ", student_id=" + student_id +
                ", class_id=" + class_id +
                '}';
    }

    public boolean isDivisible() {
        return isDivisible;
    }

    public void setDivisible(boolean divisible) {
        isDivisible = divisible;
    }

    public java.sql.Date getStart_date() {
        return start_date;
    }

    public void setStart_date(java.sql.Date start_date) {
        this.start_date = start_date;
    }

    public java.sql.Date getDue_date() {
        return due_date;
    }

    public void setDue_date(java.sql.Date due_date) {
        this.due_date = due_date;
    }

    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }
    public int getTimeIndication() { return timeIndication; }
    public void setTimeIndication(int timeIndication) { this.timeIndication = timeIndication; }
    public int getHomework_id() { return homework_id; }
    public void setHomework_id(int homework_id) { this.homework_id = homework_id; }

    public int getLesson_id() {
        return lesson_id;
    }

    public void setLesson_id(int lesson_id) {
        this.lesson_id = lesson_id;
    }

    public int getStudent_id() {
        return student_id;
    }

    public void setStudent_id(int student_id) {
        this.student_id = student_id;
    }
    public int getClass_id() { return class_id; }
    public void setClass_id(int class_id) { this.class_id = class_id; }
}
