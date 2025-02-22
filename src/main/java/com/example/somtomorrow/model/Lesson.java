package com.example.somtomorrow.model;

/**
 * The representation of lesson entity in our system
 */
public class Lesson {
    private static int uniqueId = 36;

    private int lessonId;
    private int classId;
    private String title;
    private boolean hasHomework;
    private String location;
    private int period;
    private String day;

    private int icon;
    public Lesson(int lessonId, int classId, String title, boolean hasHomework, String location, int period, String day,int icon) {
        this.lessonId = lessonId;
        this.classId = classId;
        this.title = title;
        this.hasHomework = hasHomework;
        this.location = location;
        this.period = period;
        this.day = day;
        this.icon = icon;
    }
    public Lesson(int lessonId, int classId, String title, boolean hasHomework, String location, int period, String day) {
        this.lessonId = lessonId;
        this.classId = classId;
        this.title = title;
        this.hasHomework = hasHomework;
        this.location = location;
        this.period = period;
        this.day = day;
        this.icon = 0;
    }

    public Lesson(int classId, String title, boolean hasHomework, String location, int period, String day) {
        this.lessonId = uniqueId;
        this.classId = classId;
        this.title = title;
        this.hasHomework = hasHomework;
        this.location = location;
        this.period = period;
        this.day = day;
        this.icon = 0;
        uniqueId++;
    }

    public Lesson() {
        this.lessonId = 0;
        this.classId = 0;
        this.title = null;
        this.hasHomework = false;
        this.location = null;
        this.period = 0;
        this.day = null;
        this.icon = 0;
    }


    public int getLessonId() {
        return lessonId;
    }

    public void setLessonId(int lessonId) {
        this.lessonId = lessonId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public boolean hasHomework() {
        return hasHomework;
    }

    public void setHasHomework(boolean hasHomework) {
        this.hasHomework = hasHomework;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public int getClassId() {
        return classId;
    }

    public void setClassId(int classId) {
        this.classId = classId;
    }

    public int getPeriod() {
        return period;
    }

    public void setPeriod(int period) {
        this.period = period;
    }

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public void setIcon(int icon) {
        this.icon = icon;
    }

    public int getIcon() {
        return icon;
    }

    @Override
    public String toString() {
        return "Lesson{" + "lessonId=" + lessonId + ", classId=" + classId + ", title='" + title + '\'' + ", hasHomework=" + hasHomework + ", icon=" + icon + ", location='" + location + '\'' + ", period=" + period + ", day='" + day + '\'' + '}';
    }
}
