package frontend;

import BackEnd.Attendee;
import javafx.application.Application;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

public class AttendeeGUI {
    public static void show(Attendee attendee) {

        Stage stage = new Stage();
        stage.setTitle("Eventra - Attendee Dashboard");

        Image profileImage = new Image("profile.png"); // Replace with your actual image
        ImageView profileIcon = new ImageView(profileImage);
        profileIcon.setFitWidth(40);
        profileIcon.setFitHeight(40);

        Label greeting = new Label("Hello Mr/Ms " + username + ",");
        Label balanceLabel = new Label("Balance: $" );




    }


}
