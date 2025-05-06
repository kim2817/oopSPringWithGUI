package frontend;

import BackEnd.*;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.scene.text.Font;




class OrganizerUI {
    static Label lbl(String s , double top , double left){

        Label Dname = new Label(s);
        AnchorPane.setTopAnchor(Dname,top);
        AnchorPane.setLeftAnchor(Dname, left);

        return Dname;
    }
    static TextField fld(double top , double left){

        TextField Dname = new TextField();
        AnchorPane.setTopAnchor(Dname,top);
        AnchorPane.setLeftAnchor(Dname, left);

        return Dname;
    }
    static Button btn(String s , double top , double left){

        Button Dname = new Button(s);
        AnchorPane.setTopAnchor(Dname,top);
        AnchorPane.setLeftAnchor(Dname, left);

        return Dname;
    }


    public static void show(Organizer u) {
        Stage stage = new Stage();
        stage.setResizable(false);
        AnchorPane layout = new AnchorPane();
        Scene s = new Scene(layout , 500,250);
        stage.setScene(s);
        String name = "Hello Mr/Ms: " + u.getUsername();

        layout.setPadding(new Insets(10));

        layout.getChildren().add(lbl(name,10.0,10.0));
        layout.getChildren().add(lbl("Balance: " + u.getBalance().getBalance(),30.0, 10.0 ));
        layout.getChildren().add(lbl("--------------------------------------------------------",70.0, 100.0 ));
        Button viewEvents = btn("View Events",90.0,100.0);
        Button myEvents = btn("My Events",90.0,300.0);
        Button logout = btn("Logout",150.0,210.0);
        layout.getChildren().addAll(viewEvents,myEvents,logout);
        stage.show();


        //viewEvents.setOnAction(e->);
        //myEvents.setOnAction(e->);
        logout.setOnAction(e-> {
            stage.close();
            LoginWindow.show();
        });
    }
}

class ViewEventsUI {



}

class MyEventsUI {



}

class CreateNewEventUI {



}

class RentRoomUI {



}

class RentRoomDetailsUI {



}

class RentRoomConfirmationUI {



}

class ManageOrganizedEventUI {



}

class EditEventDetailsUI {



}

class DeleteEventConfirmationUI {



}

class EventSearchResultUI {



}

class EventFilterResultUI {



}

class ViewEventDetailsUI {



}