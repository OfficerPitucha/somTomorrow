package com.example.somtomorrow.model;

/**
 * The representation of homework piece entity in our system (the part of cutting up functionality)
 */
public class HomeworkPiece {

    private int pieceId;
    private int homeworkId;
    private String name;
    private int lessonId;
    private int studentId;
    private String progress;
    private String tasks;
    private java.sql.Date start_date;
    private java.sql.Date due_date;
    private String goal;

    public HomeworkPiece(int pieceId, int homeworkId, String goal, int lessonId, int studentId, String progress, String tasks, java.sql.Date start_date, java.sql.Date due_date) {
        this.goal = goal;
        this.pieceId = pieceId;
        this.homeworkId = homeworkId;
        this.lessonId = lessonId;
        this.studentId = studentId;
        this.progress = progress;
        this.tasks = tasks;
        this.start_date = start_date;
        this.due_date = due_date;
    }

    public HomeworkPiece() {
        this.pieceId = 0;
        this.lessonId = 0;
        this.studentId = 0;
        this.progress = null;
        this.tasks = null;
        this.start_date = null;
        this.due_date = null;
        this.goal = null;
    }

    public int getPieceId() {
        return pieceId;
    }

    public void setPieceId(int pieceId) {
        this.pieceId = pieceId;
    }

    public int getHomeworkId() {
        return homeworkId;
    }

    public void setHomeworkId(int homeworkId) {
        this.homeworkId = homeworkId;
    }

    public String getName(){
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public int getLessonId() {
        return lessonId;
    }
    public void setLessonId(int id){
        this.lessonId = id;
    }
    public int getStudentId() {
        return studentId;
    }
    public void setStudentId(int id) {
        this.studentId = id;
    }
    public String getProgress() {
        return progress;
    }
    public void setProgress(String pr) {
        this.progress = pr;
    }
    public String getTasks() {
        return tasks;
    }
    public void setTasks(String tsk) {
        this.tasks = tsk;
    }
    public java.sql.Date getStart_date(){
        return start_date;
    }
    public void setStart_date(java.sql.Date date) {
        this.start_date = date;
    }
    public java.sql.Date getDue_date() {
        return due_date;
    }
    public void setDue_date(java.sql.Date date) {
        this.due_date = date;
    }

    public String getGoal() {
        return goal;
    }

    public void setGoal(String goal) {
        this.goal = goal;
    }
}
