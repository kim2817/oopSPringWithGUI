package frontend;

import BackEnd.Attendee;
import BackEnd.Event;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Separator;
import javafx.scene.image.Image;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.stage.Stage;

import static frontend.AttendeeGUI.tempAttendee;

public class AttendeeBookedEvents {
    public static void show(Attendee attendee) {

        Button backBtn = new Button("Back");
        backBtn.getStyleClass().add("filled-button");
        Line separatorLine = new Line(0, 50, 275, 50);
        separatorLine.setStroke(Color.web("#a074e2"));
        separatorLine.setStrokeWidth(2);

        Label text = new Label("My Events");
        text.getStyleClass().add("h2");
        text.setPadding(new Insets(20));

        VBox vBox = new VBox(20);
        vBox.getChildren().addAll(text,separatorLine);
        for(Event event:attendee.getBookedEvents()){
            Label label = new Label("Event Name: " + event.getEventName() + "\nDate: " + event.getEventDate().DOBToString() + "\nRoom Name: " + event.getEventRoom().getRoomName());
            vBox.getChildren().addAll(label, new Separator());
        }
        VBox root = new VBox(20, vBox, backBtn);
        root.setPadding(new Insets(20));
        ScrollPane scrollPane = new ScrollPane(root);
        scrollPane.setFitToWidth(true);

        Scene scene = new Scene(scrollPane, 400, 450);
        scene.getStylesheets().add(AttendeeBookedEvents.class.getResource("/styles.css").toExternalForm());

        Stage stage = new Stage();
        Image icon = new Image("Logo.png");
        stage.getIcons().add(icon);
        stage.setScene(scene);
        stage.show();
        backBtn.setOnAction(e -> {
            stage.close();
            AttendeeGUI.show(tempAttendee);
        });
    }

}
