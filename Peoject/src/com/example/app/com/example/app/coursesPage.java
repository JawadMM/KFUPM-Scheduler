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
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.LinkedList;
import java.util.ArrayList;
import java.util.List;

import static com.example.app.GridPaneSchedual.clearGrid;
import static com.example.app.HomePage.courseBasketPut;
import static com.example.app.Functions.getSections;

public class coursesPage extends Application  {
    public static void main(String[] args) {
        launch(args);
    }

    public void start(Stage primaryStage) throws IOException {

    }

    public static Node makeCoursesPage(List<Section> availableSections , ArrayList<Section> addedSections , ArrayList<Section> scheduleSections, VBox basketCourses, GridPane schedualArea) throws IOException {


        ScrollPane SectionsScroller = new ScrollPane();
        VBox root = new VBox();
        HBox header = new HBox();
        VBox sections = new VBox();
        HBox section = new HBox();

//        show secions to user

        for(int i = 0 ; i< availableSections.size() ; i++){
            HBox card =  courseCard(availableSections.get(i), addedSections , availableSections, i, basketCourses ,schedualArea , scheduleSections);
            sections.getChildren().add(card);
        }
//        header things
        Button clearBasket = new Button("clear Basket and schedual");
        clearBasket.setVisible(false);
        Text mainHeader = new Text("Add section to Basket");
        mainHeader.setVisible(false);
        Button saveSchedual = new Button("start with saved scedual");
        saveSchedual.setVisible(false);
        header.getChildren().add(mainHeader);
        header.getChildren().add((saveSchedual));
        header.getChildren().add((clearBasket));
        header.setSpacing(100);

//        sections area

        root.getChildren().add(header);
        SectionsScroller.setContent(sections);
        root.getChildren().add(SectionsScroller);

        saveSchedual.setOnAction(event -> {

        });


        clearBasket.setOnAction(event -> {
            System.out.println(addedSections);
            basketCourses.getChildren().removeAll(basketCourses.getChildren());
            addedSections.clear();
            scheduleSections.clear();
            clearGrid(schedualArea);
        });

        sections.setSpacing(10);
        root.setSpacing(100);
        root.setPadding(new Insets(20, 100, 100, 10));

        ScrollPane coursesPage = new ScrollPane(root);
        coursesPage.setPadding(new Insets(100,10,20,100));
        coursesPage.setMinWidth(1220);
        coursesPage.setVbarPolicy(javafx.scene.control.ScrollPane.ScrollBarPolicy.NEVER);
        return  coursesPage;
    }

    public static HBox courseCard(Section section, ArrayList<Section> addedSections,List<Section> availableSections ,int i , VBox basketCourses, GridPane schedualArea ,ArrayList<Section> scheduleSections) {

        Text courseCodeText = new Text("Course Code");
        Text crnText = new Text("CRN");
        Text crnNumberText = new Text(section.getCrn() +"");

        VBox courseCodeVBox = new VBox(courseCodeText, crnText, crnNumberText);

        Text courseNameText = new Text("Course Text");
        Text courseName = new Text(section.getCourseName());


        VBox courseNameVBox = new VBox(courseNameText, courseName);

        Text courseInsutrctorText = new Text("Course Instrustor");
        Text courseInstructor = new Text(section.getInstructor());

        VBox courseInstructorVBox = new VBox(courseInsutrctorText, courseInstructor);
        courseInstructorVBox.setMaxWidth(60);

        Text daysText = new Text("Days");
        Text days = new Text(section.getDays());

        VBox daysVBox = new VBox(daysText, days);

        Text timeText = new Text("Time");
        Text time = new Text(section.getTime());

        VBox timeVBox = new VBox(timeText, time);

        Text buildingText = new Text("Building");
        Text building = new Text(section.getLocation());

        VBox buildingVBox = new VBox(buildingText, building);

        Text activityText = new Text("Activity");
        Text activity = new Text(section.getType());

        VBox activityVBox = new VBox(activityText, activity);

        Text statusText = new Text("Status");
        Text waitlistText = new Text("Waitlist");


        Text statusSection = new Text(section.getStatus());
        Text waitlistSection = new Text(section.getWaitList());

        VBox statusAndWaitlist = new VBox(statusText, statusSection);
        statusAndWaitlist.setSpacing(12);

        VBox statusIndicator = new VBox(waitlistText, waitlistSection);
        statusIndicator.setSpacing(12);

        HBox statusHBox = new HBox(statusAndWaitlist, statusIndicator);
        statusHBox.setSpacing(30);
        statusHBox.setAlignment(Pos.CENTER);

        Button addButton = new Button("Add");
        addButton.setStyle("-fx-background-color: #4C8157; -fx-background-radius: 16; -fx-text-fill: white; -fx-font-weight: bold");
        addButton.setMinWidth(65);
        addButton.setMinHeight(30);

        Button removebButton = new Button("Remove");
        removebButton.setStyle("-fx-border-color: #4C8157; -fx-background-radius: 12; -fx-border-radius: 16; -fx-background-color: transperant; -fx-font-weight: bold");
        removebButton.setMinWidth(65);
        System.out.println(removebButton.getWidth());
        removebButton.setMinHeight(30);

        HBox buttons = new HBox(removebButton, addButton);
        buttons.setSpacing(12);
        buttons.setAlignment(Pos.BOTTOM_RIGHT);

//        removebButton.setDisable(true);
        addButton.setOnAction(event -> {
//            removebButton.setDisable(false);
//            addButton.setDisable(true);
            if(!addedSections.contains(availableSections.get(i))){
                addedSections.add(availableSections.get(i));
                System.out.println("Added");
                basketCourses.getChildren().removeAll(basketCourses.getChildren());

                for(int j = 0 ; j< addedSections.size() ; j++){
                    VBox card =  courseBasketPut(addedSections.get(j), scheduleSections , schedualArea );
                    basketCourses.getChildren().add(card);
                }
            }
        });
        removebButton.setOnAction(event -> {
//            addButton.setDisable(false);
//            removebButton.setDisable(true);
//            if(addedSections.contains())
            addedSections.removeIf(obj -> obj.getCrn() == (Integer.parseInt(crnNumberText.getText())));
            basketCourses.getChildren().removeAll(basketCourses.getChildren());
            for(int j = 0 ; j< addedSections.size() ; j++){
                VBox card =  courseBasketPut(addedSections.get(j), scheduleSections , schedualArea );
                basketCourses.getChildren().add(card);
            }
            System.out.println("Removed");
        });

        VBox container = new VBox(statusHBox, buttons);
        container.setSpacing(12);
        HBox card = new HBox(courseCodeVBox, courseNameVBox, courseInstructorVBox, daysVBox, timeVBox, buildingVBox, activityVBox, container);
        card.setSpacing(40);
        card.setStyle("-fx-background-color: #DBE8CE; -fx-background-radius: 12;");
        card.setPadding(new Insets(15, 20, 15, 20));
        card.setMaxHeight(109);
        card.setMaxWidth(1000);
        card.setMinWidth(1000);


        return  card;
    }


}
