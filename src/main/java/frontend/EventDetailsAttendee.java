package frontend;

import BackEnd.Event;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class EventDetailsAttendee {
    public static void show(Event event) {
        Stage stage = new Stage();
        stage.setTitle("Event Details");
        Label events = new Label((event.AttendeeToString()));

        VBox root = new VBox(events);
        ScrollPane scrollPane = new ScrollPane(root);
        scrollPane.setFitToWidth(true);

        Scene scene = new Scene(scrollPane, 600, 800);
        stage.setScene(scene);
        stage.show();

    }
}
