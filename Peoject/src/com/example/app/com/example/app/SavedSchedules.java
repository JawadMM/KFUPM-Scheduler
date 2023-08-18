package com.example.app;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import static com.example.app.Functions.getSchedual;
import static com.example.app.GridPaneSchedual.*;
import static com.example.app.HomePage.courseBasketPut;

public class SavedSchedules extends Application {
    @Override
    public void start(Stage stage) throws Exception {
//        VBox main = makeSchedual();
//
//        Scene scene = new Scene(main);
//        stage.setScene(scene);
//        stage.show();

    }
    public static void main(String[] args) {
        launch();
    }

    public static Node makeSchedual(ArrayList<Section> addedSections , ArrayList<Section> scheduleSections, GridPane schedualArea, VBox BasketCourses) throws IOException, ClassNotFoundException {
        VBox main = new VBox(20);
        File schedualsFile = new File("files/saved");
        ArrayList<ArrayList<Section>> allScheduals = getSavedSchduals(schedualsFile);
        System.out.println(allScheduals);
        for(int i = 0 ; i< allScheduals.size() ; i++){
            main.getChildren().add(showSchdual(allScheduals.get(i), i, addedSections, scheduleSections,schedualArea, BasketCourses  ));
        }
        ScrollPane root = new ScrollPane(main);
        root.setMinWidth(1220);
        root.setPadding(new Insets(100, 100, 100, 100));
        return  root;

    }
    public static  ArrayList<ArrayList<Section>> getSavedSchduals(File file) throws IOException, ClassNotFoundException {

        File[] savedScheduals = file.listFiles();
        ArrayList<ArrayList<Section>> allScheduals = new ArrayList<>();

        for(int i = 0 ; i < savedScheduals.length ; i++){
            allScheduals.add(getSchedual(savedScheduals[i].getName()));
        }

        return allScheduals;
    }
    public  static HBox showSchdual(ArrayList<Section> savedSchedual, int i, ArrayList<Section> addedSections , ArrayList<Section> scheduleSections, GridPane schedualArea, VBox BasketCourses){
        HBox card = new HBox(10);
        Text scheduleNumber = new Text("Schedule number " + i);
        HBox.setMargin(scheduleNumber, new Insets(0, 20, 0, 0));
        scheduleNumber.setStyle("-fx-font-weight: bold;");
        
        Text contains = new Text("Contains:");
        HBox.setMargin(contains, new Insets(0, 10, 0, 0));
        
        card.getChildren().add(scheduleNumber);

        card.getChildren().add(contains);

        savedSchedual.forEach(N -> card.getChildren().add(new Text(N.getCourseName())));
        
        Button getScedual = new Button("get This shedual");
        HBox.setMargin(getScedual, new Insets(0, 0, 0, 500));
        
        
        getScedual.setStyle("-fx-border-color: #4C8157; -fx-background-color: #4C8157; -fx-background-radius: 12; -fx-border-radius: 16; -fx-text-fill: white; -fx-font-weight: bold;");
        
        getScedual.setOnAction(event -> {
            BasketCourses.getChildren().removeAll(BasketCourses.getChildren());
            addedSections.clear();
            scheduleSections.clear();
            clearGrid(schedualArea);
            for(int j = 0 ; j < savedSchedual.size() ; j++){
                addedSections.add(savedSchedual.get(j));
                scheduleSections.add(savedSchedual.get(j));
            }
            for(int j = 0 ; j < addedSections.size(); j++){
                
                VBox basketCard =  courseBasketPut(addedSections.get(j), scheduleSections , schedualArea );
                BasketCourses.getChildren().add(basketCard);
                
            }
            for(int j = 0 ; j< scheduleSections.size() ; j++){
                
                try {
                    putSectionNoConflict(scheduleSections.get(j) , schedualArea);
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }
            System.out.println("saved schedual is" + savedSchedual);
            System.out.println("saved addedSections is" + addedSections);
            System.out.println("saved scedualSections is" + scheduleSections);
            
            
        });
        
        card.getChildren().add(getScedual);
        card.setPadding(new Insets(20,700,20,20));
        card.maxWidth(720);
        card.setStyle("-fx-background-color: #DBE8CE");
        HBox.setMargin(card, new Insets(0, 0,20, 0));
        return  card;
    }
}
