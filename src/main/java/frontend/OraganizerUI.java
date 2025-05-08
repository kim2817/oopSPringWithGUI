package frontend;


import BackEnd.*;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import javafx.scene.text.Font;

import java.util.List;


class OrganizerUI {
    static Organizer sessionOrg;
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
        sessionOrg = u;

        Stage stage = new Stage();
        HBox layoutx1 = new HBox(10);
        VBox layouty1 = new VBox(10, layoutx1);
        HBox layoutx2 = new HBox(10);
        VBox layouty2 = new VBox(10, layoutx2);
        HBox layoutx3 = new HBox(10);
        VBox layouty3 = new VBox(10, layoutx3);

        VBox layout = new VBox(layouty1,layouty2,layouty3);
        Scene s = new Scene(layout , 800,450);
        stage.setScene(s);

        layout.setPadding(new Insets(20));

        layouty1.getChildren().add(lbl("Hello Mr/Ms: " + u.getUsername(),10.0,10.0));
        layouty1.getChildren().add(lbl("Balance: " + u.getBalance().getBalance(),30.0, 10.0 ));
        layouty1.setAlignment(Pos.TOP_LEFT);
        layouty2.getChildren().add(lbl("----------------------------------------------------------------------------------------------------------------------------------------------------------",70.0, 100.0 ));
        layouty2.setAlignment(Pos.CENTER);
        Button viewEvents = btn("View Events",90.0,100.0);
        Button myEvents = btn("My Events",90.0,300.0);
        Button logout = btn("Logout",150.0,210.0);
        layoutx3.getChildren().addAll(viewEvents,myEvents,logout);
        layoutx3.setAlignment(Pos.CENTER);
        stage.show();

        //viewEvents.setOnAction(e->);
        myEvents.setOnAction(e->{
            stage.close();
            MyEventsUI.show(u);


        });
        logout.setOnAction(e-> {
            stage.close();
            LoginWindow.show();
        });
    }
}

class ViewEventsUI {
    public static void show(){






    }


}

class MyEventsUI {
    public static void show(Organizer u){
        Stage stage = new Stage();
        HBox layoutx1 = new HBox(10);
        VBox layouty1 = new VBox(10, layoutx1);
        VBox layouty2 = new VBox(10);
        HBox layoutx2 = new HBox(10,layouty2);
        HBox layoutx3 = new HBox(10);
        VBox layouty3 = new VBox(10, layoutx3);

        VBox layout = new VBox(layouty1,layoutx2,layouty3);
        Scene s = new Scene(layout , 800,450);
        stage.setScene(s);

        layout.setPadding(new Insets(20));

        ObservableList<String> items = FXCollections.observableArrayList(Category.listAllCategories());
        ComboBox<String> CatsCombo1 = new ComboBox<>();
        CatsCombo1.setPromptText("Select Category");
        CatsCombo1.getItems().addAll(items);
        layouty1.getChildren().addAll(new Label("My Events"),CatsCombo1);
        layouty1.setAlignment(Pos.TOP_LEFT);

        Button createNewEvent = new Button("Create New Event");
        Button viewOrganisedEvents = new Button("View Organised Events");
        Button manageEvent = new Button("Manage Events");
        layoutx2.getChildren().addAll(createNewEvent,viewOrganisedEvents,manageEvent);
        layoutx2.setAlignment(Pos.CENTER);

        createNewEvent.setOnAction(e->{
            stage.close();
            CreateNewEventUI.show(u);
        });

        stage.show();





    }


}

class CreateNewEventUI {
    public static void show(Organizer u){

        Stage stage = new Stage();
        VBox layouty1 = new VBox(10);
        HBox layoutx1 = new HBox(10);
        VBox layouty2 = new VBox(10);
        HBox layoutx2 = new HBox(10);
        HBox layoutx3 = new HBox(10);
        VBox layouty3 = new VBox(10, layoutx3);
        HBox layoutx4 = new HBox(10);
        VBox layouty4 = new VBox(10, layoutx4);
        //cat combo\\
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
                    Button eventButton = new Button (events.get(i).getEventName() + "\n" );
                    SearchResult.getChildren().add(eventButton);
                }
                SearchResult.getChildren().add(FoundCond);
            }
            else{
                FoundCond.setText("No events found in this Category");
                SearchResult.getChildren().add(FoundCond);
            }
        });


        VBox layout = new VBox(layoutx1,layoutx2,layouty3,layouty4);
        Scene s = new Scene(layout , 800,450);
        stage.setScene(s);

        layout.setPadding(new Insets(20));

        TextField nameField = new TextField();
        layoutx1.getChildren().addAll(new Label("Name     "),nameField);
        layoutx1.setAlignment(Pos.TOP_LEFT);

        layoutx2.getChildren().addAll(new Label("Category"),CatsCombo,new Label("\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n"));
        layoutx2.setAlignment(Pos.TOP_LEFT);

        layouty3.getChildren().addAll(layoutx1,layoutx2);

        Button rentRoom = new Button("Rent Room");
        layouty4.getChildren().addAll(layouty3,rentRoom);
        layouty4.setAlignment(Pos.BOTTOM_LEFT);

        stage.show();





    }


}

class RentRoomUI {
    public static void show(){
        Stage stage = new Stage();
        stage.setResizable(false);
        AnchorPane layout = new AnchorPane();
        Scene s = new Scene(layout , 800,450);
        stage.setScene(s);





    }


}

class RentRoomDetailsUI {
    public static void show(){
        Stage stage = new Stage();
        stage.setResizable(false);
        AnchorPane layout = new AnchorPane();
        Scene s = new Scene(layout , 800,450);
        stage.setScene(s);





    }


}

class RentRoomConfirmationUI {
    public static void show(){
        Stage stage = new Stage();
        stage.setResizable(false);
        AnchorPane layout = new AnchorPane();
        Scene s = new Scene(layout , 800,450);
        stage.setScene(s);





    }


}

class ManageOrganizedEventUI {
    public static void show(){
        Stage stage = new Stage();
        stage.setResizable(false);
        AnchorPane layout = new AnchorPane();
        Scene s = new Scene(layout , 800,450);
        stage.setScene(s);





    }


}

class EditEventDetailsUI {
    public static void show(){
        Stage stage = new Stage();
        stage.setResizable(false);
        AnchorPane layout = new AnchorPane();
        Scene s = new Scene(layout , 800,450);
        stage.setScene(s);





    }


}

class DeleteEventConfirmationUI {
    public static void show(){
        Stage stage = new Stage();
        stage.setResizable(false);
        AnchorPane layout = new AnchorPane();
        Scene s = new Scene(layout , 800,450);
        stage.setScene(s);





    }


}

class EventSearchResultUI {
    public static void show(){
        Stage stage = new Stage();
        stage.setResizable(false);
        AnchorPane layout = new AnchorPane();
        Scene s = new Scene(layout , 800,450);
        stage.setScene(s);





    }


}

class EventFilterResultUI {
    public static void show(){
        Stage stage = new Stage();
        stage.setResizable(false);
        AnchorPane layout = new AnchorPane();
        Scene s = new Scene(layout , 800,450);
        stage.setScene(s);





    }


}

class ViewEventDetailsUI {
    public static void show(){
        Stage stage = new Stage();
        stage.setResizable(false);
        AnchorPane layout = new AnchorPane();
        Scene s = new Scene(layout , 800,450);
        stage.setScene(s);





    }


}