package frontend;

import BackEnd.Attendee;
import BackEnd.Event;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Separator;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import static frontend.AttendeeGUI.tempAttendee;

public class AttendeeBookedEvents {
    public static void show(Attendee attendee) {
        Button backBtn = new Button("Back");
        VBox vBox = new VBox(20);
        for(Event event:attendee.getBookedEvents()){
            Label label = new Label("Event Name: " + event.getEventName() + "\nDate: " + event.getEventDate().DOBToString() + "\nRoom Name: " + event.getEventRoom().getRoomName());
            vBox.getChildren().addAll(label, new Separator());
        }
        VBox root = new VBox(20, vBox, backBtn);
        root.setPadding(new Insets(20));
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
