package com.example.somtomorrow.model;

/**
 * The representation of student entity in our system
 */
public class Student {
    private static int uniqueId = 52;
    private String name;
    private String surname;
    private int student_id;
    private int class_id;
    private String status;
    private int schedule_id;
    private double weighted_avg;
    private String email;

    public Student(String name, String surname, int student_id, int class_id, String status, int schedule_id, double weighted_avg, String email) {
        this.name = name;
        this.surname = surname;
        this.student_id = student_id;
        this.class_id = class_id;
        this.status = status;
        this.schedule_id = schedule_id;
        this.weighted_avg = weighted_avg;
        this.email = email;
    }

    public Student(String name, String surname, int class_id, String status, int schedule_id, double weighted_avg, String email) {
        this.name = name;
        this.surname = surname;
        this.student_id = uniqueId;
        this.class_id = class_id;
        this.status = status;
        this.schedule_id = schedule_id;
        this.weighted_avg = weighted_avg;
        this.email = email;
        uniqueId++;
    }

    public Student() {
        this.name = null;
        this.surname = null;
        this.student_id = 0;
        this.class_id = 0;
        this.status = null;
        this.schedule_id = 0;
        this.weighted_avg = 0;
    }


    //    public String toJson() {
    //        return String.format("{\"name\":\"%s\", \"surname\":\"%s\", \"id\":\"%s\"}", name, surname, student_id);
    //    }


    public int getStudent_id() {
        return student_id;
    }

    public void setStudent_id(int student_id) {
        this.student_id = student_id;
    }

    public int getClass_id() {
        return class_id;
    }

    public void setClass_id(int class_id) {
        this.class_id = class_id;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getSchedule_id() {
        return schedule_id;
    }

    public void setSchedule_id(int schedule_id) {
        this.schedule_id = schedule_id;
    }

    public double getWeighted_avg() {
        return weighted_avg;
    }

    public void setWeighted_avg(double weighted_avg) {
        this.weighted_avg = weighted_avg;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public String toString() {
        return "Student{name='" + name + "', surname='" + surname + "', id='" + student_id + "'}";
    }
}
