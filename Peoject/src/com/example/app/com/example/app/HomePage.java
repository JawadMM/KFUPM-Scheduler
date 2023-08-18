package com.example.app;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.ScrollPane.ScrollBarPolicy;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import static com.example.app.Functions.*;
import static com.example.app.GridPaneSchedual.getGridPane;
import static com.example.app.GridPaneSchedual.putSection;
import static com.example.app.GridPaneSchedual.clearGrid;
import static com.example.app.SavedSchedules.makeSchedual;
import static com.example.app.coursesPage.makeCoursesPage;


/**
 * JavaFX App
 */
public class HomePage extends Application {

    static HBox primaryPane;
    static Node[] areas = new Node[3];


    
    @Override
    public void start(Stage stage) throws IOException {
        ArrayList<Section> offering = getSections();

        List<Section> availableSections = new ArrayList<>();

        ArrayList<Section> addedSections = new ArrayList<Section>();
        ArrayList<Section> scheduleSections = new ArrayList<>();

        for(int i = 0 ; i < offering.size() ; i++ ){
            if(isValidCourse(offering.get(i).getCourseName())){
                availableSections.add(offering.get(i));
            }
        }

        
        Scene scene = homePage(availableSections , addedSections, scheduleSections);
        stage.setScene(scene);
        stage.show();
        

        stage.setResizable(false);


    }
    public static VBox courseBasketPut(Section section, ArrayList<Section> scheduleSections, GridPane schedualGrid){

        Text crnNumberText = new Text(section.getCrn() +"");
        crnNumberText.setFill(Color.WHITE);
        Text courseName = new Text(section.getCourseName());
        courseName.setFill(Color.WHITE);
        Text courseInstructor = new Text(section.getInstructor());
        courseInstructor.setFill(Color.WHITE);
        Text days = new Text(section.getDays());
        days.setFill(Color.WHITE);
        Text time = new Text(section.getTime());
        time.setFill(Color.WHITE);
        Text building = new Text(section.getLocation());
        building.setFill(Color.WHITE);
        Text type = new Text(section.getType());
        type.setFill(Color.WHITE);

        Button addButton = new Button("Add");
        addButton.setStyle("-fx-border-color: #DBE8CE; -fx-background-color: #DBE8CE; -fx-background-radius: 12; -fx-border-radius: 16; -fx-text-fill: #4C8157; -fx-font-weight: bold;");
        Button RemoveButton = new Button("remove");
        RemoveButton.setStyle("-fx-border-color: #DBE8CE; -fx-background-color: #4C8157; -fx-background-radius: 12; -fx-border-radius: 16; -fx-text-fill: #DBE8CE; -fx-font-weight: bold;");
//        RemoveButton.setDisable(true);
        addButton.setOnAction(event -> {
            if(!scheduleSections.contains(section)){
                try {
                    if(putSection(section , schedualGrid , scheduleSections) && !scheduleSections.contains(section)){
                        scheduleSections.add(section);

                    }
                    else {
                        System.out.println("CONFLICT!");
                    }
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }

        });
        RemoveButton.setOnAction(event -> {
            if(scheduleSections.contains(section)){
                String[] times = getAllTimes(section);

                for(int i = 0 ; i < times.length ; i++ ){
                    if(!times[i].equals("0-0")){
                        int finalI = i;
                        schedualGrid.getChildren().removeIf(node -> GridPane.getColumnIndex(node) == Integer.parseInt(times[finalI].split("-")[0]) && GridPane.getRowIndex(node) == Integer.parseInt(times[finalI].split("-")[1]));

                    }
                }
                scheduleSections.remove(section);
            }

        });
        HBox buttons = new HBox(addButton,RemoveButton);
        buttons.setSpacing(20);
        VBox courseBasket = new VBox(crnNumberText,courseName ,courseInstructor  ,days ,time ,building, type  ,buttons);
        VBox.setMargin(buttons, new Insets(10, 0, 0, 0));
        VBox.setMargin(courseBasket, new Insets(0, 0, 20, 0));
        return  courseBasket;
    }

    public static Scene homePage( List<Section> availableSections , ArrayList<Section> addedSections , ArrayList<Section> scheduleSections) throws IOException{
        

        GridPane schedualArea = getGridPane();



        //Save Button area
        Button saveButton = new Button("Save Schedule");
        saveButton.setMinHeight(42);
        saveButton.setMinWidth(190);
        saveButton.setStyle("-fx-background-color: #F5FFEA; -fx-background-radius: 16; -fx-text-fill: black");
        
        AtomicInteger i = new AtomicInteger();
        saveButton.setOnAction(event -> {
            try {
                if(scheduleSections.size() > 0){
                    saveSchedual(scheduleSections,i+ "" +".dat");
                    i.getAndIncrement();
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
        //the button needs to be in a container to make proper padding
        VBox buttonContainer = new VBox(saveButton);
        buttonContainer.setSpacing(20);
        //Putting the putton over the schedule
        
        
        VBox.setMargin(saveButton, new Insets(0, 0, 0, 50));
        buttonContainer.setStyle("-fx-background-color: #4C8157;");
        buttonContainer.setMaxHeight(100);
        buttonContainer.setPadding(new Insets(70, 0, 0, 0));
        
        //basket area
        VBox basketCourses = new VBox(10);
        ScrollPane basketCoursesArea = new ScrollPane(basketCourses);
        basketCoursesArea.setMinWidth(285);
        basketCoursesArea.setMinHeight(1000);
        basketCoursesArea.setStyle("-fx-background-color: transperant;");
        
        Button clearBasket = new Button("Clear Basket and schedual");
        clearBasket.setMinHeight(42);
        clearBasket.setMinWidth(190);
        clearBasket.setStyle("-fx-border-color: #DBE8CE; -fx-background-color: #4C8157; -fx-background-radius: 12; -fx-border-radius: 16; -fx-text-fill: #DBE8CE; -fx-font-weight: bold;");
        VBox.setMargin(clearBasket, new Insets(0, 0, 0, 50));
        
        

        clearBasket.setOnAction(event -> {
            System.out.println(addedSections);
            basketCourses.getChildren().removeAll(basketCourses.getChildren());
            addedSections.clear();
            scheduleSections.clear();
            clearGrid(schedualArea);
        });
        
        buttonContainer.getChildren().addAll(clearBasket);
//        basketCourses.getChildren().add(buttonContainer);
        VBox basketSaveArea = new VBox(buttonContainer,basketCoursesArea );
        

//        for(int i = 0 ; i< addedSections.size() ; i++){
//            VBox card =  courseBasketPut(addedSections.get(i), scheduleSections,schedualArea );
//            basketCourses.getChildren().add(card);
//        }
        basketCoursesArea.widthProperty().addListener((o) -> {
            Node vp = basketCoursesArea.lookup(".viewport");
            vp.setStyle("-fx-background-color:#4C8157;");
        });

        basketCoursesArea.setPadding(new Insets(0, 0, 100, 0));
        basketCourses.setPadding(new Insets(20, 0, 100, 51.5));
        basketCoursesArea.setVbarPolicy(ScrollBarPolicy.NEVER);


        ScrollPane schedualScrollPane = new ScrollPane(schedualArea);
        schedualScrollPane.setMinWidth(1220);
        schedualScrollPane.setVbarPolicy(ScrollBarPolicy.NEVER);

        //Putting the areas in a list of nodes
        //for the purpose of changing the middle area only when the user navigates by pressing the navigation buttons
        
        areas[0] = makeNav(availableSections, addedSections, scheduleSections, primaryPane, schedualArea, basketCourses, schedualScrollPane);
        areas[1] = schedualScrollPane;
        areas[2] = basketSaveArea;

        //putting the areas inside the primary pane
        primaryPane = new HBox(areas);
        Scene scene = new Scene(primaryPane , 1700, 1024);
        return scene;
    }

    public static void main(String[] args) {
        launch();
    }
    
    public static VBox makeNav(List<Section> availableSections , ArrayList<Section> addedSections , ArrayList<Section> scheduleSections, Node courseHomePage, GridPane schedual, VBox BasketCourses, ScrollPane scheduleScrollPane) {
        
        //Creating buttons for the navigation area pn the left
        Button homeButton = new Button("Home");
        homeButton.setMinHeight(32);
        homeButton.setMinWidth(145);
        homeButton.setAlignment(Pos.CENTER_LEFT);
        homeButton.setStyle("-fx-background-color: #3C6945; -fx-background-radius: 30; -fx-text-fill: white");
        homeButton.setId("homeButton");
        homeButton.setDisable(false);

        homeButton.setOnAction(event -> {
            areas[1] = scheduleScrollPane;
            primaryPane.getChildren().setAll(areas);
        });


        Button courseOfferingButton = new Button("Course Offering");
        courseOfferingButton.setMinHeight(32);
        courseOfferingButton.setMinWidth(145);
        courseOfferingButton.setAlignment(Pos.CENTER_LEFT);
        courseOfferingButton.setStyle("-fx-background-color: #3C6945; -fx-background-radius: 30; -fx-text-fill: white");
        courseOfferingButton.setId("courseOfferingButton");

        courseOfferingButton.setOnAction(event -> {
            try {
                areas[1] = makeCoursesPage(availableSections ,  addedSections , scheduleSections, BasketCourses, schedual);
                primaryPane.getChildren().setAll(areas);

            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        });

    


        Button savedSchedulesButton = new Button("Saved Schedules");
        savedSchedulesButton.setMinHeight(32);
        savedSchedulesButton.setMinWidth(145);
        savedSchedulesButton.setAlignment(Pos.CENTER_LEFT);
        savedSchedulesButton.setStyle("-fx-background-color: #3C6945; -fx-background-radius: 30; -fx-text-fill: white");
        savedSchedulesButton.setId("savedSchedulesButton");

        savedSchedulesButton.setOnAction(event -> {
                    try {
                        areas[1] = makeSchedual(addedSections, scheduleSections, schedual, BasketCourses);
                        primaryPane.getChildren().setAll(areas);

                    } catch (IOException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    } catch (ClassNotFoundException e) {
                        throw new RuntimeException(e);
                    }
                    ;
                });


        //putting the buttons above each other
        VBox NavigationButtons = new VBox(homeButton, courseOfferingButton, savedSchedulesButton );
        NavigationButtons.setSpacing(75);
        NavigationButtons.setStyle("-fx-background-color: #4C8157");
        NavigationButtons.setPadding(new Insets(192, 25.5, 0, 25.5));
        return NavigationButtons;
    }

}

