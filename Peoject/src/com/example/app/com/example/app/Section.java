package com.example.app;

import java.io.Serializable;

public class Section implements Serializable {
    private String courseName;
    private String sectionNumber;
    private String type;
    private int crn;
    private String instructor;
    private String days;
    private String time;
    private String location;
    private String status;
    private String waitList;

    public Section(String courseName ,String sectionNumber , String type , int crn, String instructor, String days, String time, String location, String status, String waitList){
        this.courseName = courseName;
        this.sectionNumber = sectionNumber;
        this.type = type;
        this.crn = crn;
        this.instructor = instructor;
        this.days = days;
        this.time = time;
        this.location = location;
        this.status = status;
        this.waitList = waitList;
    }

    public String getSectionNumber() {
        return sectionNumber;
    }

    public String getType() {
        return type;
    }

    public int getCrn() {
        return crn;
    }

    public String getLocation() {
        return location;
    }
    public String getTime() {
        return time;
    }

    public String getInstructor() {
        return instructor;
    }

    public String getStatus() {
        return status;
    }

    public String getWaitList() {
        return waitList;
    }
    
    public String getCourseName() {
        return courseName;
    }

    public String getDays() {
        return days;
    }

    @Override
    public String toString() {
        return String.format("%s %s %s %d %s %s %s %s %s %s", courseName, sectionNumber, type, crn, instructor, days, time, location, status, waitList);
    }
}
