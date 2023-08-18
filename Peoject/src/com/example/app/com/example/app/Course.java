package com.example.app;

import java.io.Serializable;

// class for both finshed courses and degree plan

public class Course implements Serializable {

    // i used null chracter to differ between finshed courses and degree plan

    private String grade = null;
    private int credit = 0;
    private String name;
    private String pre = null;
    private String co = null;
    private int term = 0;
    

    public Course(String name, int credit, String pre, String co){ //degree plan constructor
        this.name = name;
        this.credit = credit;
        this.pre = pre;
        this.co = co;
    }

    public Course(String name, int term, String grade){ // finshed course constructor
        this.name = name;
        this.term = term;
        this.grade = grade;
    }


    // this method could return two pre courses, split method should be used when calling it.
    public String getPre(){
        String p = "";
        String[] t = pre.split(";");
        for(int i =0; i< t.length; i++){
            p += t[i];
            if(i != t.length -1)
                p+= " ";
                
        }
        return p;
    }
    public String getCo(){
        return co;
    }

    public int getCredit() {
        return credit;
    }
    
    public String getName() {
        return name;
    }

    public String getGrade() {
        return grade;
    }

    public int getTerm() {
        return term;
    }

    @Override
    public String toString() {
        return String.format("%s %d %s %s %s %d", name,credit,pre,co , grade , term);
    }
    
}
