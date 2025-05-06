package frontend;

import javafx.application.Application;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

public class AttendeeGUI extends Application {
    public static void main(String[] args) {
        launch(args);
    }
    @Override
    public void start(Stage stage) throws Exception {
        stage.setTitle("Eventra - Attendee Dashboard");

        //profile pic
        Image profileImage = new Image("profile.png"); // Use actual path or file
        ImageView profileIcon = new ImageView(profileImage);
        profileIcon.setFitWidth(40);
        profileIcon.setFitHeight(40);





    }
}
