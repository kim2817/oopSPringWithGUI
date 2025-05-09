package frontend;


import BackEnd.*;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import javafx.scene.text.Font;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static BackEnd.DateTime.displayTime;


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
    private static FlowPane SearchResult;
    private static Label FoundCond;
    public static void show(Organizer u){
        Stage stage = new Stage();
        HBox layoutx1 = new HBox(10);
        VBox layouty1 = new VBox(10, layoutx1);
        VBox layouty2 = new VBox(10);
        HBox layoutx2 = new HBox(10, layouty2);
        HBox emptySpace = new HBox();
        emptySpace.setAlignment(Pos.CENTER);
        SearchResult = new FlowPane(10,10);
        emptySpace.getChildren().add(SearchResult);
        VBox layout = new VBox(20);
        layout.getChildren().addAll(layouty1,layoutx2,emptySpace);
        Scene s = new Scene(layout,800,450);
        stage.setScene(s);
        FoundCond = new Label();
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
        viewOrganisedEvents.setOnAction(e ->{
            System.out.println(CatsCombo1.getValue());
            eventToButton(u.getOrganizedEvents(Database.findCat(CatsCombo1.getValue())));
        });
        stage.show();
    }
    public static void eventToButton(List<Event> events) {
        SearchResult.getChildren().clear();
        System.out.println(events);
        if (events != null && !(events.isEmpty())) {
            for (Event event : events) {
                Button eventButton = new Button(event.getEventName() + "\n" + displayTime(event));
                eventButton.setOnAction(ee -> {
                    String eventName = eventButton.getText().substring(0, eventButton.getText().indexOf("\n"));
                    EventDetailsAttendee.show(Database.findEvent(eventName).getFirst());
                });
                SearchResult.getChildren().add(eventButton);
            }
            FoundCond.setText("");
            SearchResult.getChildren().add(FoundCond);
        }
        else{
            FoundCond.setText("No events found");
            SearchResult.getChildren().add(FoundCond);
        }
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

        layouty3.getChildren().addAll(new Label("Create New Event"),layoutx1,layoutx2);

        Button rentRoom = new Button("Rent Room");

        layouty4.getChildren().addAll(layouty3,rentRoom);
        layouty4.setAlignment(Pos.BOTTOM_LEFT);

        String name = nameField.getText();

        rentRoom.setOnAction(e->{
            Category selectedCategory = Database.findCat(CatsCombo.getValue());
            System.out.println(selectedCategory);
//            stage.close();
//            RentRoomUI.show(name,selectedCategory,u);
        });

        stage.show();





    }


}

class RentRoomUI {
    public static void show(String n, Category C, Organizer u){


        Stage stage = new Stage();
        VBox layouty1 = new VBox(10);
        HBox layoutx1 = new HBox(10);
        VBox layouty2 = new VBox(10);
        HBox layoutx2 = new HBox(10);
        HBox layoutx3 = new HBox(10);
        VBox layouty3 = new VBox(10);
        HBox layoutx4 = new HBox(10);
        VBox layouty4 = new VBox(10);

        VBox layout = new VBox(layoutx1,layoutx2,new Label("\nBalance "+u.getBalance().getBalance()),layoutx3,layoutx4);
        Scene s = new Scene(layout , 800,450);
        layout.setPadding(new Insets(20));
        stage.setScene(s);

        DatePicker date = new DatePicker();
        layoutx1.getChildren().addAll(new Label("Date   "),date,new Label("\n\n"));
        layoutx1.setAlignment(Pos.TOP_LEFT);


        TimeSlot[]  times = {TimeSlot.translate(0),TimeSlot.translate(1),TimeSlot.translate(2)};
        ComboBox timeSlot = new ComboBox(FXCollections.observableArrayList(times));

        layoutx2.getChildren().addAll(new Label("Time   "),timeSlot);
        layoutx2.setAlignment(Pos.TOP_LEFT);


        LocalDate dateValue = date.getValue();
        int day = dateValue.getDayOfMonth();
        int month = dateValue.getMonthValue();
        int year = dateValue.getYear();





        stage.show();
    }


}

class RentRoomDetailsUI {
    public static void show(Room room,String eventName,LocalDate date,TimeSlot timeSlot,Organizer u){

        int day = date.getDayOfMonth();
        int month = date.getMonthValue();
        int year = date.getYear();

        Stage stage = new Stage();
        VBox layouty1 = new VBox(10);
        HBox layoutx1 = new HBox(10);
        VBox layouty2 = new VBox(10);
        HBox layoutx2 = new HBox(10);
        HBox layoutx3 = new HBox(10);
        VBox layouty3 = new VBox(10);
        HBox layoutx4 = new HBox(10);
        VBox layouty4 = new VBox(10);

        HBox layout = new HBox(10,layouty1,layouty2,layouty3,layouty4);
        Scene s = new Scene(layout , 800,450);
        layout.setPadding(new Insets(20));
        stage.setScene(s);

        layouty1.getChildren().addAll(
                new Label("Room Name  "+room.getRoomName()),
                new Label("Event Name  "+eventName),
                new Label("Date  "+day + "/"+month+"/"+year + "In The "+timeSlot.toString(timeSlot)),
                new Label("Room Capacity  "+room.getRoomCapacity()),
                new Label("Price  "+room.getRentPrice()),
                new Label("New Balance:  "+(u.getBalance().getBalance()-room.getRentPrice()))
        );
        layouty1.setAlignment(Pos.TOP_LEFT);


        Button rent = new Button("Rent");
        layouty2.getChildren().addAll(rent);

        rent.setOnAction(e->{



        });

        stage.show();
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