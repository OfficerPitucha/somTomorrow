package com.example.somtomorrow.model;

/**
 * The representation of class entity in our system
 */
public class Class {
    private int classId;
    private String name;
    private int studentCount;

    public Class(int classId, String name, int studentCount) {
        this.classId = classId;
        this.name = name;
        this.studentCount = studentCount;
    }

    public Class(String name) {
        this.name = name;
        this.studentCount = 0;
    }

    public Class(){
        classId = 0;
        name = null;
        studentCount = 0;
    }


    public int getClassId() {
        return classId;
    }

    public void setClassId(int classId) {
        this.classId = classId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getStudentCount() {
        return studentCount;
    }

    public void setStudentCount(int studentCount) {
        this.studentCount = studentCount;
    }

    @Override
    public String toString() {
        return "Class: {" +
                "classId = " + classId +
                ", name = '" + name + '\'' +
                ", studentCount = " + studentCount +
                "}";
    }
}
