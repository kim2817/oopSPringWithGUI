package frontend;


import BackEnd.*;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.scene.text.Font;
import javafx.stage.StageStyle;
import javafx.util.converter.LocalDateStringConverter;
import javafx.util.converter.LocalDateTimeStringConverter;

import javax.swing.*;
import java.time.LocalDate;
import java.util.*;

import static BackEnd.Admin.searchEvents;
import static BackEnd.DateTime.displayTime;
import static BackEnd.TimeSlot.*;
import static frontend.AttendeeGUI.curStage;
import static frontend.AttendeeGUI.eventToButton;
import static javafx.application.Application.launch;


class OrganizerUI {
   static Organizer sessionOrg;
    static Label lbl(String s , double top , double left){

        Label Dname = new Label(s);
        Dname.getStyleClass().add("h2");
        AnchorPane.setTopAnchor(Dname,top);
        AnchorPane.setLeftAnchor(Dname, left);

        return Dname;
    }
    static TextField fld(double top , double left){

        TextField Dname = new TextField();
        Dname.getStyleClass().add("filled-textfield");
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
        VBox layouty3 = new VBox(30);
        layouty3.setPadding(new Insets(20));
        VBox layout = new VBox(layouty1,layouty2,layouty3);
        Scene s = new Scene(layout , 800,450);
        s.getStylesheets().add(OrganizerUI.class.getResource("/styles.css").toExternalForm());
        stage.setScene(s);

        layout.setPadding(new Insets(20));

        layouty1.getChildren().add(lbl("Hello Mr/Ms: " + u.getUsername(),10.0,10.0));
        layouty1.getChildren().add(lbl("Balance: " + u.getBalance().getBalance(),30.0, 10.0 ));
        Label email = new Label("Email: "+u.getEmail());
        email.getStyleClass().add("h3");
        Label gender = new Label("Gender: "+u.getGen());
        gender.getStyleClass().add("h3");
        layouty1.getChildren().addAll(email,gender);
        layouty1.setAlignment(Pos.TOP_LEFT);
        Line separatorLine = new Line(0, 50, 275, 50);
        separatorLine.setStroke(Color.web("#a074e2"));
        separatorLine.setStrokeWidth(2);
        layouty2.getChildren().add(separatorLine);
        layouty2.setAlignment(Pos.CENTER);
        Button viewEvents = btn("View Events",90.0,100.0);
        viewEvents.getStyleClass().add("filled-button");
        viewEvents.setMaxWidth(150);
        Button myEvents = btn("My Events",90.0,300.0);
        myEvents.getStyleClass().add("filled-button");
        myEvents.setMaxWidth(150);
        Button logout = btn("Logout",150.0,210.0);
        logout.getStyleClass().add("filled-button");
        logout.setMaxWidth(150);
        layouty3.getChildren().addAll(viewEvents,myEvents,logout);
        layouty3.setAlignment(Pos.CENTER);
        Image icon = new Image("Logo.png");
        stage.getIcons().add(icon);
        stage.show();

        viewEvents.setOnAction(e->{
            stage.close();
            ViewEventsUI.show(u);
        });
        myEvents.setOnAction(e->{
            stage.close();
            MyEventsUI.show(u);
        });
        logout.setOnAction(e-> {
            stage.close();
            RegisterLogin.show();
        });
    }
}

class ViewEventsUI {

    public static Attendee tempAttendee;
    private static FlowPane SearchResult;
    private static Label FoundCond;
    public static void show(Organizer u){
        //searching
        Stage stage = new Stage();


        TextField searchField = new TextField();
        searchField.getStyleClass().add("filled-textfield");
        searchField.setPromptText("Search for an event");
        Button searchBtn = new Button("Search");
        searchBtn.getStyleClass().add("filled-button");
        HBox searchBox = new HBox(10, searchField, searchBtn);
        searchBox.setAlignment(Pos.CENTER);
        VBox searchSection = new VBox(10, searchBox);
        searchSection.setAlignment(Pos.CENTER);
        searchSection.setPadding(new Insets(10));


        SearchResult = new FlowPane(10,10);
        FoundCond = new Label("");
        searchBtn.setOnAction(e->{
            ArrayList<Button> buttons = eventToButton(searchEvents(searchField.getText()),u);
            SearchResult.getChildren().clear();
            if(buttons.isEmpty()){
                FoundCond.setText("No event found");
            }
            else{
                FoundCond.setText("");
                SearchResult.getChildren().addAll(buttons);
            }
        });

        Separator separator = new Separator();

        //categories searching..
        Label or = new Label("OR");
        ObservableList<String> items = FXCollections.observableArrayList(Category.listAllCategories());
        ComboBox<String> CatsCombo = new ComboBox<>();
        CatsCombo.getStyleClass().add("custom-combo");
        CatsCombo.setPromptText("Select a Category");
        CatsCombo.getItems().addAll(items);
        VBox otherOption = new VBox(20,or,CatsCombo);
        otherOption.setAlignment(Pos.CENTER);

        Button Catssearch = new Button("Search");
        Catssearch.getStyleClass().add("filled-button");
        Catssearch.setDisable(true);
        CatsCombo.setOnAction(e->{
            Catssearch.setDisable(false);
        });
        Catssearch.setOnAction(e -> {
            Category cat = Database.findCat(CatsCombo.getValue());
            ArrayList<Button> buttons = eventToButton(cat.getEvents(),u);
            SearchResult.getChildren().clear();
            if(buttons.isEmpty()){
                FoundCond.setText("No event found");
            }
            else{
                FoundCond.setText("");
                SearchResult.getChildren().addAll(buttons);
            }
        });

        HBox catSearching = new HBox(10,CatsCombo,Catssearch);
        catSearching.setAlignment(Pos.CENTER);
        SearchResult.setAlignment(Pos.CENTER);
        Button back = new Button("Back");
        back.getStyleClass().add("filled-button");
        VBox layout = new VBox(10,searchSection,otherOption,catSearching,SearchResult,back);
        layout.setAlignment(Pos.CENTER);
        back.setOnAction(e->{
            stage.close();
            OrganizerUI.show(u);
        });
        Scene s = new Scene(layout,800,450);
        s.getStylesheets().add(ViewEventsUI.class.getResource("/styles.css").toExternalForm());
        stage.setScene(s);
        stage.show();
        }
    public static ArrayList<Button> eventToButton(List<Event> events,Organizer u){
        SearchResult.getChildren().clear();
        ArrayList<Button> buttons = new ArrayList<>();
        if(events!=null && !(events.isEmpty())) {
            for (Event event : events) {
                Button eventButton = new Button(event.getEventName() + "\n" + displayTime(event));
                eventButton.getStyleClass().add("filled-button");
                eventButton.setOnAction(ee -> {
                    String eventName = eventButton.getText().substring(0, eventButton.getText().indexOf("\n"));
                    ViewEventDetailsUI.show(Database.findEvent(eventName).getFirst(),u);
                });
                buttons.add(eventButton);
            }
        }
        return buttons;
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
        s.getStylesheets().add(MyEventsUI.class.getResource("/styles.css").toExternalForm());
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
        Button back = new Button("Back");
        Button viewStats = new Button("View BarChart");
        layoutx2.getChildren().addAll(createNewEvent,viewOrganisedEvents,viewStats,back);
        layoutx2.setAlignment(Pos.CENTER);

        back.setOnAction(e->{
            stage.close();
            OrganizerUI.show(u);
        });
        viewStats.setOnAction(e->{
            stage.close();
            ShowBarChart.show(u.getOrganizedEvents(),u);
        });
        createNewEvent.setOnAction(e->{
            stage.close();
            CreateNewEventUI.show(u);
        });
        viewOrganisedEvents.setOnAction(e ->{
            System.out.println(CatsCombo1.getValue());
            eventToButton(u.getOrganizedEvents(Database.findCat(CatsCombo1.getValue())),u);
        });
        stage.show();
    }
    public static void eventToButton(List<Event> events,Organizer u) {
        SearchResult.getChildren().clear();
        System.out.println(events);
        if (events != null && !(events.isEmpty())) {
            for (Event event : events) {
                Button eventButton = new Button(event.getEventName() + "\n" + displayTime(event));
                eventButton.setOnAction(ee -> {
                    String eventName = eventButton.getText().substring(0, eventButton.getText().indexOf("\n"));
                    EditEventDetailsUI.show(event,u);
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
        CatsCombo.getStyleClass().add("custom-combo");
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
        s.getStylesheets().add(CreateNewEventUI.class.getResource("/styles.css").toExternalForm());
        stage.setScene(s);

        layout.setPadding(new Insets(20));

        TextField nameField = new TextField();
        nameField.getStyleClass().add("filled-textfield");
        layoutx1.getChildren().addAll(new Label("Name     "),nameField);
        layoutx1.setAlignment(Pos.TOP_LEFT);
        TextField ticketPrice = new TextField();
        ticketPrice.getStyleClass().add("filled-textfield");
        layoutx2.getChildren().addAll(new Label("Ticket Price"),ticketPrice,new Label("\n\nCategory"),CatsCombo,new Label("\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n"));
        layoutx2.setAlignment(Pos.TOP_LEFT);

        layouty3.getChildren().addAll(new Label("Create New Event"),layoutx1,layoutx2);

        Button rentRoom = new Button("Rent Room");
        Button back = new Button("Back");

        layouty4.getChildren().addAll(layouty3,rentRoom, back);
        layouty4.setAlignment(Pos.BOTTOM_LEFT);

        back.setOnAction(e->{
            stage.close();
            MyEventsUI.show(u);
        });

        rentRoom.setOnAction(e->{
            double price = Double.parseDouble(ticketPrice.getText());
            String name = nameField.getText();
            Category selectedCategory = Database.findCat(CatsCombo.getValue());
            stage.close();
            RentRoomUI.show(name,price,selectedCategory,u);
        });

        stage.show();
    }


}

class RentRoomUI {
    public static void show(String n,double ticketprice, Category C, Organizer u){


        Stage stage = new Stage();
        VBox layouty1 = new VBox(10);
        HBox layoutx1 = new HBox(10);
        VBox layouty2 = new VBox(10);
        HBox layoutx2 = new HBox(10);
        HBox layoutx3 = new HBox(10);
        VBox layouty3 = new VBox(10);
        HBox layoutx4 = new HBox(10);
        VBox layouty4 = new VBox(10);



        DatePicker date = new DatePicker();
        layoutx1.getChildren().addAll(new Label("Date   "),date,new Label("\n\n"));
        layoutx1.setAlignment(Pos.TOP_LEFT);


        ArrayList<String> times = new ArrayList<>();
        times.add("MORNING");
        times.add("EVENING");
        times.add("AFTERNOON");

        ComboBox<String> timeSlot = new ComboBox<>(FXCollections.observableArrayList(times));
        timeSlot.getStyleClass().add("custom-combo");
        layoutx2.getChildren().addAll(new Label("Time   "),timeSlot);
        layoutx2.setAlignment(Pos.TOP_LEFT);
        FlowPane flowPane = new FlowPane();


        Button rentRoom = new Button("Rent Room");
        Button back = new Button("Back");
        back.setOnAction(e->{
            stage.close();
            CreateNewEventUI.show(u);

        });

        rentRoom.setOnAction(e->{
            LocalDate dateValue = date.getValue();
            int day = dateValue.getDayOfMonth();
            int month = dateValue.getMonthValue();
            int year = dateValue.getYear();
            TimeSlot slot = TimeSlot.stringTo(timeSlot.getValue());
            DateTime datetime = new DateTime(day,month,year,slot);
            Object[] rooms = Database.readAll(new Room());
            ArrayList<Room> filteredRooms = new ArrayList<>();
            for(Object o:rooms){
                Room cur = (Room)o;
                if(cur.isAvailable(datetime)) filteredRooms.add(cur);
            }
            ArrayList<Button> buttons = eventToButton(ticketprice,C,filteredRooms, n, dateValue, slot, u);
            flowPane.getChildren().clear();
            flowPane.getChildren().addAll(buttons);
        });
        VBox layout = new VBox(layoutx1,layoutx2,new Label("\nBalance "+u.getBalance().getBalance()),new Label("\n\n\n\n\n\n\n\n\n\n\n\n"),back,rentRoom,flowPane,layoutx3,layoutx4);
        Scene s = new Scene(layout , 800,450);
        s.getStylesheets().add(RentRoomUI.class.getResource("/styles.css").toExternalForm());
        layout.setPadding(new Insets(20));
        stage.setScene(s);


        stage.show();
    }
    public static ArrayList<Button> eventToButton(double ticketprice,Category c,List<Room> rooms, String eventName, LocalDate localDate, TimeSlot slot, Organizer u){
        ArrayList<Button> buttons = new ArrayList<>();
        if(rooms != null && !(rooms.isEmpty())) {
            for (Room room : rooms) {
                Button roomButton = new Button(room.getRoomName() + "\n" + room.getRentPrice());
                roomButton.setOnAction(ee -> {
                    RentRoomDetailsUI.show(ticketprice,c,room,eventName,localDate,slot,u);
                });
                buttons.add(roomButton);
            }
        }
        return buttons;
    }
}

class RentRoomDetailsUI {
    public static void show(double ticketprice,Category c,Room room,String eventName,LocalDate date,TimeSlot timeSlot,Organizer u){

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
                new Label("Ticket Price "+ticketprice),
                new Label("Category  "+c),
                new Label("Event Name  "+eventName),
                new Label("Date  "+day + "/"+month+"/"+year + " In The "+timeSlot.toString()),
                new Label("Room Capacity  "+room.getRoomCapacity()),
                new Label("Price  "+room.getRentPrice()),
                new Label("New Balance:  "+(u.getBalance().getBalance()-room.getRentPrice()))
        );
        layouty1.setAlignment(Pos.TOP_LEFT);


        Button rent = new Button("Rent");
        layouty2.getChildren().addAll(rent);

        rent.setOnAction(e->{
            stage.close();
            Database.create(new Event(eventName,c,room,u,ticketprice,new DateTime(day,month,year,timeSlot)));
            u.getBalance().withdraw(room.getRentPrice());
        });

        stage.show();
    }


}






class EditEventDetailsUI {
    public static void show(Event event,Organizer u) {
        Stage stage = new Stage();
        VBox names = new VBox(30);
        VBox fields = new VBox(20);
        VBox btn = new VBox(20);

        HBox layout = new HBox(50,names,fields,btn);
        TextField name = new TextField(event.getEventName());
        name.getStyleClass().add("filled-textfield");
        ObservableList<String> items = FXCollections.observableArrayList(Category.listAllCategories());
        ComboBox<String> CatsCombo = new ComboBox<>();
        CatsCombo.getStyleClass().add("custom-combo");
        CatsCombo.setPromptText("Select a Category");
        CatsCombo.getItems().addAll(items);
        TextField price = new TextField();
        price.getStyleClass().add("filled-textfield");
        DatePicker date = new DatePicker();
        ArrayList<String> times = new ArrayList<>();
        times.add("MORNING");
        times.add("EVENING");
        times.add("AFTERNOON");
        ComboBox<String> timeSlot = new ComboBox<>(FXCollections.observableArrayList(times));
        timeSlot.getStyleClass().add("custom-combo");
        Button upname = new Button("Update Name");
        Button upcat = new Button("Update Category");
        Button upprice = new Button("Update Price");
        Button update = new Button("Update Date");
        Button back = new Button("Back");
        Button delete = new Button("Delete");


        upname.setOnAction(e->{
            event.setEventName(name.getText());
        });
        upcat.setOnAction(e->{
            event.setEventCat(Database.findCat(CatsCombo.getValue()));
            System.out.println(event);
        });
        upprice.setOnAction(e->{
            event.setTicketPrice(Double.parseDouble(price.getText()));
        });
        update.setOnAction(e->{
            LocalDate dateValue = date.getValue();
            int day = dateValue.getDayOfMonth();
            int month = dateValue.getMonthValue();
            int year = dateValue.getYear();
            TimeSlot slot = TimeSlot.stringTo(timeSlot.getValue());
            DateTime datetime = new DateTime(day,month,year,slot);
            event.setEventDate(datetime);
        });
        DateTime dte = event.getEventDate();



        CatsCombo.setPromptText(event.getEventCat().getCatName());
        price.setText(Double.toString(event.getTicketPrice()));



        fields.getChildren().addAll(name,CatsCombo,price,date,timeSlot);
        names.getChildren().addAll(new Label("Name:"),new Label("Category:"),new Label("Price: "),new Label("Date: "),new Label("Time Slot:"));
        btn.getChildren().addAll(upname,upcat,upprice,update,back,delete);


        back.setOnAction(e->{
            stage.close();
        });

        delete.setOnAction(e->{
            stage.close();
            DeleteEventConfirmationUI.show(event);
        });

        Scene scene = new Scene(layout, 800, 450);
        stage.setScene(scene);
        stage.show();

    }


}

class DeleteEventConfirmationUI {
    public static void show(Event E){
        Stage st = new Stage();
        Button btn1 = new Button("Yes");
        Button btn2 = new Button("No");
        Label newlabel= new Label("ARE YOU SURE???????");
        HBox hb1= new HBox(20,btn1,btn2);
        VBox vb1= new VBox(20,newlabel,hb1);
        Scene sets= new Scene(vb1,200,100);
        hb1.setAlignment(Pos.CENTER);
        vb1.setAlignment(Pos.CENTER);
        st.setResizable(false);
        st.setScene(sets);
        btn1.setOnAction(e-> {
            st.close();
            Database.delete(E);
        });
        btn2.setOnAction(e-> {
            st.close();
        });
        st.show();
    }

}


class ViewEventDetailsUI {
    public static void show(Event event,Organizer u) {
        Stage stage = new Stage();
        stage.setTitle("Event Details");
        Label events = new Label((event.AttendeeToString()));

        Button back = new Button("Back");
        VBox root = new VBox(events,back);
        ScrollPane scrollPane = new ScrollPane(root);
        scrollPane.setFitToWidth(true);

        back.setOnAction(e->{
            stage.close();
        });

        Scene scene = new Scene(scrollPane, 800, 450);
        stage.setScene(scene);
        stage.show();

    }


}

class ShowBarChart {

    public static void show(ArrayList<Event> events,Organizer u) {
        CategoryAxis xAxis = new CategoryAxis();
        NumberAxis yAxis = new NumberAxis();
        xAxis.setLabel("Age Range");
        yAxis.setLabel("Frequency");
        BarChart<String,Number> barChart = new BarChart<>(xAxis,yAxis);
        XYChart.Series<String,Number> male = new XYChart.Series<>();
        XYChart.Series<String,Number> female = new XYChart.Series<>();
        HashMap<String[],Integer> statistics = new HashMap<>();
        for(Event event:events){
            for(String[] key:event.getStatistics().keySet()){
                statistics.putIfAbsent(key, 0);
                statistics.replace(key,statistics.get(key)+event.getStatistics().get(key));
            }
        }
        for(String[] key:statistics.keySet()){
            if(key[1].equals("m")) male.getData().add(new XYChart.Data<>(key[0],statistics.get(key)));
            else female.getData().add(new XYChart.Data<>(key[0],statistics.get(key)));
        }
        barChart.getData().addAll(male,female);
        male.setName("Male");
        female.setName("Female");
        Button back = new Button("Back");

        HBox hbox = new HBox(barChart,back);
        hbox.setAlignment(Pos.CENTER);
        Scene scene = new Scene(hbox, 800, 450);
        Stage stage = new Stage();
        stage.setScene(scene);
        back.setOnAction(e->{
            stage.close();
            MyEventsUI.show(u);
        });
        stage.show();
    }
}