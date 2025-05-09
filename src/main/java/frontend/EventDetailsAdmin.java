package frontend;

import BackEnd.Event;
import javafx.stage.Stage;

public class EventDetailsAdmin {
    public static void show(Event event) {
        Stage stage = new Stage();
        stage.setTitle("Event Details");
        System.out.println(event.toString());



    }





}
