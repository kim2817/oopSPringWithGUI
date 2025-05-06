package frontend;

import BackEnd.Attendee;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

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

        HBox navBar = new HBox(10, userBox, profileIcon);
        navBar.setAlignment(Pos.CENTER_RIGHT);
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
        Label resultLabel = new Label();

        searchBtn.setOnAction(e->{

            System.out.println(searchEvents(searchField.getText()));

        });


        //intrests
        Label intrestsLabel = new Label("Events you may like");
        intrestsLabel.setStyle("-fx-font-size: 18px; -fx-font-weight: bold;");

        //layout
        VBox root = new VBox(20, navBar, searchSection, intrestsLabel);
        root.setPadding(new Insets(20));


        ScrollPane scrollPane = new ScrollPane(root);
        scrollPane.setFitToWidth(true);

        Scene scene = new Scene(scrollPane, 600, 800);
        stage.setScene(scene);
        stage.show();
    }


}
