package frontend;

import BackEnd.Attendee;
import BackEnd.Event;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import static frontend.AttendeeGUI.tempAttendee;

public class AttendeeBookedEvents {
    public static void show(Attendee attendee) {
        Button backBtn = new Button("Back");

        VBox root = new VBox();
        ScrollPane scrollPane = new ScrollPane(root);
        scrollPane.setFitToWidth(true);

        Scene scene = new Scene(scrollPane, 600, 800);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.show();
        backBtn.setOnAction(e -> {
            stage.close();
            AttendeeGUI.show(tempAttendee);
        });
    }

}
