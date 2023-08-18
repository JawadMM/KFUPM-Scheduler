package com.example.app;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.example.app.GridPaneSchedual.getSectionColumns;
import static com.example.app.GridPaneSchedual.getSectionRow;
//import static com.example.app.Functions.getSections;

public class Functions {
    public static void main(String[] args) throws IOException {
//        ArrayList<Section> offering = getSections();
//        List<Section> availableSections = new ArrayList<>();
//        for(int i = 0 ; i < offering.size() ; i++ ){
//            if(isValidCourse(offering.get(i).getCourseName())){
//                availableSections.add(offering.get(i));
//            }
//        }
//        ArrayList<Section> addedSections = new ArrayList<Section>();
//        ArrayList<Section> scheduleSections = new ArrayList<>();
//
//        System.out.println(getSectionRow(availableSections.get(0)));

    }
    public static ArrayList<Section> getSections() throws IOException {
        FileReader offeringCSV = new FileReader("files/CourseOffering.csv");

        ArrayList<Section> offering;
        try (BufferedReader readOffering = new BufferedReader(offeringCSV);) {
            offering = new ArrayList<Section>();
            String line = "";

            readOffering.readLine(); // not needed line
            while ((line = readOffering.readLine()) != null) {
                String[] splitOffering = line.split(","); //split everything but Name and sections are together!
                String[] splitNameSection = splitOffering[0].split("-"); //split name and section
                offering.add(new Section(splitNameSection[0], splitNameSection[1], splitOffering[1], Integer.parseInt(splitOffering[2]), splitOffering[4], splitOffering[5], splitOffering[6], splitOffering[7], splitOffering[8], splitOffering[9]));
            }
        }
        return offering;
    }
    public static ArrayList<Course> getDegreePlan() throws IOException{
        FileReader degreeCSV = new FileReader("files/DegreePlan.csv");
        ArrayList<Course> degreePlan = new ArrayList<Course>();

        try (BufferedReader readDegreePlan = new BufferedReader(degreeCSV)){
            String line = "";
            readDegreePlan.readLine(); // not needed line

            while((line = readDegreePlan.readLine()) != null){
                String[] splitDegreePlan = line.split(",");
                degreePlan.add(new Course(splitDegreePlan[0], Integer.parseInt(splitDegreePlan[1]), splitDegreePlan[2], splitDegreePlan[3]));
            }
        }
        return degreePlan;
    }
    public static ArrayList<Course> getFinishedCourses() throws IOException{
        FileReader finshedCSV = new FileReader("files/FinishedCourses.csv");
        ArrayList<Course> finishedCourses = new ArrayList<Course>();

        try (BufferedReader readFinished = new BufferedReader(finshedCSV)){
            String line = "";

            while((line = readFinished.readLine()) != null){
                String[] splitFinished = line.split(","); // split name term and grade
                finishedCourses.add(new Course(splitFinished[0] , Integer.parseInt(splitFinished[1]), splitFinished[2]));
            }
        }
        return finishedCourses;
    }
    public static boolean isValidCourse(String courseName) throws IOException{
        ArrayList<Course> finishedCourses = getFinishedCourses();
        ArrayList<Course> degreePlan = getDegreePlan();
        int counter = 0;

        for(int i = 0; i < degreePlan.size(); i++){
            if(degreePlan.get(i).getName().equals(courseName)){
                String[] pre = degreePlan.get(i).getPre().split(";");
                for(int j = 0; j < pre.length; j++){
                    for(int k = 0; k < finishedCourses.size(); k++){
                        if(pre[j].equals(finishedCourses.get(k).getName()) && !(finishedCourses.get(k).getGrade().equals("F")) && !(finishedCourses.get(k).getGrade().equals("W"))){
                            counter++;
                        }
                    }
                }
                if(counter == pre.length) // if the sizes are equal all the pre requested courses are taken
                    return true;
            }
        }
        //default return
        return false;
    }
    public static int[] getSectionTime(String  time){

        String[] times = time.split("-");
        int[] intTime = new int[2];
        for(int i = 0; i < times.length; i++ ){
            if(Character.toString(times[i].charAt(0)).equals("0")){
                intTime[i] = Integer.parseInt(times[i].substring(1,3));
            } else{
                intTime[i] = Integer.parseInt(times[i]);
            }
        }
        return intTime;
    }
    public static int getTime(Section section){
        int[] times = getSectionTime(section.getTime());
        int totalTime = times[1] -times[0];
        return totalTime;
    }
    public static boolean isConflict(Section section, ArrayList<Section> scheduleSections){
        int[] times = getSectionTime(section.getTime());
        int start = times[0];
        int end = times[1];
        String[] days = section.getDays().split("");
        ArrayList <String> commonDays = new ArrayList<String>();
        for(int i = 0 ; i< scheduleSections.size() ; i++){
            if(section.getCourseName().equals(scheduleSections.get(i).getCourseName()) && section.getType().equals(scheduleSections.get(i).getType())){
                return  true;
            }
            int[] anotherSectionTimes = getSectionTime(scheduleSections.get(i).getTime()); // time as integer
            String[] anotherSectionDays = scheduleSections.get(i).getDays().split(""); //days array
            for(int k = 0; k < days.length; k++){
                for(int l = 0; l < anotherSectionDays.length; l++) {
                    if (days[k].equals(anotherSectionDays[l])) {
                        commonDays.add(days[k]);
                    }

                }
            }

            for(int j = 0; j < commonDays.size() ; j++){
                // if the time is between the beggning or the ending of the class and it's the same day it's a conflict
                if((start >= anotherSectionTimes[0] && start <= anotherSectionTimes[1]) || (end >= anotherSectionTimes[0] && end <= anotherSectionTimes[1])){
                    return true;
                }
                else if(start < anotherSectionTimes[0] && end > anotherSectionTimes[0]){
                    return true;
                }
            }
            commonDays.clear();
        }
        return  false;
    }

    public static String[] getAllTimes(Section section){

        String[] times = new String[5];
        int row  = getSectionRow(section);
        boolean[] columns = getSectionColumns(section);

        for(int i = 0 ; i < columns.length ; i++){
            if(columns[i]){
                times[i] = (i+1) + "-" + row;
            }
            else {
                times[i] = "0-0";
            }
        }


        return times;
    }
    public static void saveSchedual(ArrayList<Section> finished, String name) throws FileNotFoundException, IOException{

        try ( ObjectOutputStream out = new ObjectOutputStream( new FileOutputStream("files/"+"saved/" +name))) {
            out.writeObject(finished);
        }
    }

    public static ArrayList<Section> getSchedual(String name) throws FileNotFoundException, IOException, ClassNotFoundException{
        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream("files/"+"saved/"+ name))) {
            ArrayList<Section> get =  ((ArrayList<Section>)in.readObject());
            return get;
        }
    }



}
