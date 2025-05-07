package frontend;

import BackEnd.Attendee;
import BackEnd.Category;
import BackEnd.Database;
import BackEnd.Event;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.List;

import static BackEnd.Admin.searchEvents;

public class AttendeeGUI {
    public static void show(Attendee attendee) {

        Stage stage = new Stage();
        stage.setTitle("Eventra - Attendee Dashboard");

        Image profileImage = new Image("profile.png"); // Replace with your actual image
        ImageView profileIcon = new ImageView(profileImage);
        profileIcon.setFitWidth(40);
        profileIcon.setFitHeight(40);

        Label greeting = new Label("Hello Mr/Ms " + attendee.getUsername() + ",");
        Label balanceLabel = new Label("Balance: $" + attendee.getBalance());

        VBox userBox = new VBox(5, greeting, balanceLabel);
        userBox.setAlignment(Pos.CENTER_LEFT);

        HBox navBar = new HBox(10, profileIcon,userBox);
        navBar.setAlignment(Pos.CENTER_LEFT);
        navBar.setPadding(new Insets(10));
        navBar.setStyle("-fx-background-color: #f0f0f0;");

        //searching
        TextField searchField = new TextField();
        searchField.setPromptText("Search for an event");
        Button searchBtn = new Button("Search");
        HBox searchBox = new HBox(10, searchField, searchBtn);
        searchBox.setAlignment(Pos.CENTER);
        VBox searchSection = new VBox(10, searchBox);
        searchSection.setAlignment(Pos.CENTER);
        searchSection.setPadding(new Insets(10));

        searchBtn.setOnAction(e->{

            System.out.println(searchEvents(searchField.getText()));

        });

        //categories searching..
        Label or = new Label("OR");
        ObservableList<String> items = FXCollections.observableArrayList(Category.listAllCategories());
        ComboBox<String> CatsCombo = new ComboBox<>();
        CatsCombo.setPromptText("Select a Category");
        CatsCombo.getItems().addAll(items);
        VBox otherOption = new VBox(20,or,CatsCombo);
        otherOption.setAlignment(Pos.CENTER);

        final VBox SearchResult = new VBox(10);
        final Label FoundCond = new Label("");
        Button Catssearch = new Button("Search");

        Catssearch.setOnAction(e -> {
            SearchResult.getChildren().clear();

            Category selectedCategory = Database.findCat(CatsCombo.getValue());
            if(!((selectedCategory.getEvents()).isEmpty())) {
                FoundCond.setText("Events in this Category : ");
                List<Event> events = selectedCategory.getEvents();
                for(int i=0; i< events.size();i++){
                    Button eventButton = new Button (events.get(i).getEventName());
                    SearchResult.getChildren().add(eventButton);
                }



                SearchResult.getChildren().add(FoundCond);
            }
            else{
                FoundCond.setText("No events found in this Category");
                SearchResult.getChildren().add(FoundCond);
            }
        });


        HBox catSearching = new HBox(10,CatsCombo,Catssearch);








        //intrests
        Label intrestsLabel = new Label("Events you may like");
        intrestsLabel.setStyle("-fx-font-size: 18px; -fx-font-weight: bold;");

        //layout
        VBox root = new VBox(20, navBar, searchSection, otherOption,catSearching, SearchResult,intrestsLabel);
        root.setPadding(new Insets(20));

        ScrollPane scrollPane = new ScrollPane(root);
        scrollPane.setFitToWidth(true);

        Scene scene = new Scene(scrollPane, 600, 800);
        stage.setScene(scene);
        stage.show();
    }


}
