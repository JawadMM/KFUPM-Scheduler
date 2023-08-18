package com.example.app;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.VPos;
import javafx.scene.Scene;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;

import static com.example.app.Functions.getSections;
import static com.example.app.Functions.getSectionTime;
import static com.example.app.Functions.isConflict;

public class GridPaneSchedual extends Application {

    @Override
    public void start(Stage stage) throws Exception {

        GridPane scheduleGrid = getGridPane();

        ArrayList<Section> Offering = getSections();
        List<Section> availableSections = Offering.subList(1, 3);
        ArrayList<Section> scudelae = new ArrayList<Section>();


        for(int i = 0 ; i < availableSections.size() ; i++){
            putSection(availableSections.get(i) , scheduleGrid, scudelae);
        }
        Scene scene = new Scene(scheduleGrid, 1000, 1000);
        stage.setScene(scene);
        stage.show();
    }
    public static boolean[] getSectionColumns(Section section){
        boolean[] columns = new boolean[5];

        if(section.getDays().contains("U")) columns[0] = true;
        if(section.getDays().contains("M")) columns[1] = true;
        if(section.getDays().contains("T")) columns[2] = true;
        if(section.getDays().contains("W")) columns[3] = true;
        if(section.getDays().contains("R")) columns[4] = true;

        return columns;
    }
    public static int getSectionRow(Section section){

        int sectionsTimes = getSectionTime(section.getTime())[0];
        if(sectionsTimes <= 100) sectionsTimes *= 10;
        int row = (int) (1 + (Math.floor(sectionsTimes  - 700) / 100));

        System.out.println(sectionsTimes);
        System.out.println((Math.floor(sectionsTimes  - 700)));
        return  row;
    }
    public static GridPane getGridPane(){
        GridPane gridPane = new GridPane();
        gridPane.setHgap(60);
        gridPane.setVgap(20);

        gridPane.getColumnConstraints().addAll(new ColumnConstraints(), 
        new ColumnConstraints(160), 
        new ColumnConstraints(160), 
        new ColumnConstraints(160), 
        new ColumnConstraints(160), 
        new ColumnConstraints(160));

        gridPane.getRowConstraints().addAll(
        new RowConstraints(160), 
        new RowConstraints(160), 
        new RowConstraints(160), 
        new RowConstraints(160), 
        new RowConstraints(160), 
        new RowConstraints(160), 
        new RowConstraints(160), 
        new RowConstraints(160), 
        new RowConstraints(160), 
        new RowConstraints(160), 
        new RowConstraints(160), 
        new RowConstraints(160));


        Text SevenAM = new Text("7:00");
        Text eigthAM = new Text("8:00");
        Text nineAM = new Text("9:00");
        Text tenAM = new Text("10:00");
        Text elevenAM = new Text("11:00");
        Text twelveAM = new Text("12:00");
        Text onePM = new Text("13:00");
        Text twoPM = new Text("14:00");
        Text threePM = new Text("15:00");
        Text fourPM = new Text("16:00");
        Text fivePM = new Text("17:00");


        gridPane.add(SevenAM, 0, 1, 1, 1);
        gridPane.add(eigthAM, 0, 2, 1, 1);
        gridPane.add(nineAM, 0, 3, 1, 1);
        gridPane.add(tenAM, 0, 4, 1, 1);
        gridPane.add(elevenAM, 0, 5, 1, 1);
        gridPane.add(twelveAM, 0, 6, 1, 1);
        gridPane.add(onePM, 0, 7, 1, 1);
        gridPane.add(twoPM, 0, 8, 1, 1);
        gridPane.add(threePM, 0, 9, 1, 1);
        gridPane.add(fourPM, 0, 10, 1, 1);
        gridPane.add(fivePM, 0, 11, 1, 1);

        gridPane.setValignment(SevenAM, VPos.TOP);
        gridPane.setValignment(eigthAM, VPos.TOP);
        gridPane.setValignment(nineAM, VPos.TOP);
        gridPane.setValignment(tenAM, VPos.TOP);
        gridPane.setValignment(elevenAM, VPos.TOP);
        gridPane.setValignment(twelveAM, VPos.TOP);
        gridPane.setValignment(onePM, VPos.TOP);
        gridPane.setValignment(twoPM, VPos.TOP);
        gridPane.setValignment(threePM, VPos.TOP);
        gridPane.setValignment(fourPM, VPos.TOP);
        gridPane.setValignment(fivePM, VPos.TOP);

        Text sundayText = new Text("Sunday");
        Text mondayText = new Text("Monday");
        Text tuesdayText = new Text("Tuesday");
        Text wednesdayText = new Text("Wednesday");
        Text thursdayText = new Text("Thursday");

        gridPane.add(sundayText, 1, 0, 1, 1);
        gridPane.add(mondayText, 2, 0, 1, 1);
        gridPane.add(tuesdayText, 3, 0, 1, 1);
        gridPane.add(wednesdayText, 4, 0, 1, 1);
        gridPane.add(thursdayText, 5, 0, 1, 1);

        gridPane.setGridLinesVisible(false);
        gridPane.setPadding(new Insets(100, 0, 20, 10));
        return gridPane;
    }
    public static Boolean putSection(Section section,GridPane schedualGrid , ArrayList<Section> scheduleSections ) throws Exception {
        boolean[] columns = getSectionColumns(section);
        int  row = getSectionRow(section);
        if(isConflict(section, scheduleSections)){
            return false;
        }
        for(int i =0 ; i < columns.length ; i++){
            if(columns[i]){
                schedualGrid.add(createSection(section),i+1 , row,1,1);
            }
        }
        return true;
    }
    public static void putSectionNoConflict(Section section,GridPane schedualGrid  ) throws Exception {
        boolean[] columns = getSectionColumns(section);
        int  row = getSectionRow(section);
        for(int i =0 ; i < columns.length ; i++){
            if(columns[i]){
                schedualGrid.add(createSection(section),i+1 , row,1,1);
            }
        }
    }
    public static VBox createSection(Section section){
        VBox sectionContainer = new VBox();
        Text sectionName = new Text(section.getCourseName());
        Text sectionCrn = new Text(section.getCrn() + "");
        Text sectionTime = new Text(section.getTime());
        Text sectionInstructor = new Text(section.getInstructor());
        Text sectionBuilding = new Text(section.getLocation());

        sectionContainer.getChildren().add(sectionName);
        sectionContainer.getChildren().add(sectionCrn);
        sectionContainer.getChildren().add(sectionTime);
        sectionContainer.getChildren().add(sectionInstructor);
        sectionContainer.getChildren().add(sectionBuilding);

        
        if (section.getTime().substring(0,4).contains("30")) {
            sectionContainer.setMargin(sectionName, new Insets(70, 0, 0, 0));
        }
        
        return sectionContainer;
    }
    public static void clearGrid(GridPane schedualArea){
        for(int i = 1 ; i <= 11 ; i++ ){
            int finalI = i;
            for(int j = 1 ; j <= 5; j++){
                int finalJ = j;
                schedualArea.getChildren().removeIf(node -> GridPane.getColumnIndex(node) == finalJ && GridPane.getRowIndex(node) == finalI);
            }
        }
    }
    public static void main(String[] args) {
        Application.launch(args);
    }
}
