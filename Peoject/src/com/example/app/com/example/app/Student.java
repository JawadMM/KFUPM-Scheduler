package com.example.app;

public class Student {
    private Course[] finishedCourses;
    
    public Student(Course[] finishedCourses){
        this.finishedCourses = finishedCourses;
    }

    public Course[] getFinishedCourses() {
        return finishedCourses;
    }

}
