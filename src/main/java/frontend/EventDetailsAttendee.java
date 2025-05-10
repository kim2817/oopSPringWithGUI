package frontend;

import BackEnd.Event;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class EventDetailsAttendee {
    public static void show(Event event) {
        Label eventName = new Label("Event Name: " + event.getEventName());
        eventName.getStyleClass().add("h2");

        Label eventDate = new Label("Date: " + event.getEventDate().DOBToString());
        eventDate.getStyleClass().add("h3");
        VBox roomDetails = new VBox(10, new Label("Room Name: " + event.getEventRoom().getRoomName()),
                new Label("Room ID: " + event.getEventRoom().getID()),
                new Label("Room Capacity: " + event.getEventRoom().getRoomCapacity()));
        roomDetails.setPadding(new Insets(0, 0, 0, 20));
        Label Room = new Label("Room");
        Room.getStyleClass().add("h2");
        VBox roomVBox = new VBox(10,Room, roomDetails);



        Label status = new Label();
        status.setPadding(new Insets(0, 0, 0, 20));
        int x = event.getEventRoomCap() - event.getEventAttendees();
        if(x == 0){
            status.setText("Fully Booked");
        }
        else{
            status.setText(x + " Tickets Left");
        }
        Button bookNow = new Button("Book Now");
        Button backBtn = new Button("Back");
        backBtn.getStyleClass().add("filled-button");
        bookNow.getStyleClass().add("filled-button");

        Label statuss = new Label("Status");
        statuss.getStyleClass().add("h2");
        VBox root = new VBox(15, eventName, new Separator(),
                eventDate, new VBox(10, statuss, status), roomVBox
        , new Label("Ticket Price: " + event.getTicketPrice()), bookNow, backBtn);
        Stage stage = new Stage();
        stage.setTitle("Event Details");
        root.setPadding(new Insets(20));
        ScrollPane scrollPane = new ScrollPane(root);
        scrollPane.setFitToWidth(true);
        Scene scene = new Scene(scrollPane, 600, 800);
        scene.getStylesheets().add(EventDetailsAttendee.class.getResource("/styles.css").toExternalForm());

        stage.setScene(scene);
        stage.show();
        bookNow.setOnAction(e->{
            stage.close();
            BookEventTickets.show(event);
        });
        backBtn.setOnAction(e->{
            stage.close();
            AttendeeGUI.show(AttendeeGUI.tempAttendee);
        });
    }
}

class BookEventTickets{
    public static void show(Event event) {
        Label eventName = new Label("Event Name: " + event.getEventName());
        Label balance = new Label();
        eventName.getStyleClass().add("h2");

        HBox balanceHBox = new HBox(10, new Label("Balance: "), balance);
        TextField ticketCount = new TextField();
        ticketCount.getStyleClass().add("filled-textfield");

        HBox ticketCountHBox = new HBox(10, new Label("No. of Tickets: "), ticketCount);
        TextField totalPrice = new TextField();
        totalPrice.getStyleClass().add("filled-textfield");

        totalPrice.setEditable(false);
        HBox totalPriceHBox = new HBox(10, new Label("Total Price: "), totalPrice);
        Button button = new Button("Book");
        Button backBtn = new Button("Back");
        backBtn.getStyleClass().add("filled-button");
        button.getStyleClass().add("filled-button");

        ticketCount.setOnKeyTyped(e->{
            if(ticketCount.getText().isEmpty()) ticketCount.setText("0");
            totalPrice.setText(String.valueOf((event.getTicketPrice()*Integer.parseInt(ticketCount.getText()))));
        });
        Label err = new Label("");
        ticketCount.setText("0");
        balance.setText(String.valueOf(AttendeeGUI.tempAttendee.getBalance()));
        VBox root = new VBox(10, eventName, new Separator(), balanceHBox, ticketCountHBox, totalPriceHBox, err, button);
        Stage stage = new Stage();
        stage.setTitle("Event Details");
        root.setPadding(new Insets(20));
        ScrollPane scrollPane = new ScrollPane(root);
        scrollPane.setFitToWidth(true);
        Scene scene = new Scene(scrollPane, 600, 800);
        scene.getStylesheets().add(BookEventTickets.class.getResource("/styles.css").toExternalForm());

        stage.setScene(scene);
        stage.show();
        button.setOnAction(e->{
            double bal = Double.parseDouble(balance.getText());
            double price = Double.parseDouble(totalPrice.getText());
            if(bal < price){
                err.setText("Insufficient Funds");
            }
            else{
                err.setText("");
                stage.hide();
                TicketPurchaseConfirmation.show(event,Integer.parseInt(ticketCount.getText()),stage);
            }
        });
        backBtn.setOnAction(e->{
            stage.close();
            EventDetailsAttendee.show(event);
        });
    }
}

class TicketPurchaseConfirmation{
    public static void show(Event event, int ticketCount, Stage stage){
        Stage st = new Stage();
        Button btn1 = new Button("yes");
        Button btn2 = new Button("no");
        btn1.getStyleClass().add("filled-button");
        btn2.getStyleClass().add("filled-button");

        Label newlabel= new Label("ARE YOU SURE???????");
        HBox hb1= new HBox(20,btn1,btn2);
        VBox vb1= new VBox(20,newlabel,hb1);
        Scene sets= new Scene(vb1,200,100);
        sets.getStylesheets().add(TicketPurchaseConfirmation.class.getResource("/styles.css").toExternalForm());

        hb1.setAlignment(Pos.CENTER);
        vb1.setAlignment(Pos.CENTER);
        st.setResizable(false);
        st.setScene(sets);
        btn1.setOnAction(e-> {
            AttendeeGUI.tempAttendee.bookTickets(event,AttendeeGUI.tempAttendee,ticketCount);
            PaymentSuccessful.show(event,ticketCount);
            stage.close();
            st.close();
        });
        btn2.setOnAction(e-> {
            st.close();
            stage.show();
        });
        st.show();
    }
}

class PaymentSuccessful{
    public static void show(Event event, int ticketCount){
        Label success = new Label("Success");
        success.getStyleClass().add("h3");

        HBox hBox1 = new HBox(success);
        hBox1.setAlignment(Pos.CENTER);
        Button ok = new Button("Ok");
        ok.getStyleClass().add("filled-button");

        VBox root = new VBox(10.0, hBox1, new Separator(),
                new Label("Number of Purchased Tickets: " + ticketCount),
                new Label("Event ID:    " + event.getID()),
                new Label("Event Name:  " + event.getEventName()),
                new Label("Event Room:  " + event.getEventRoom().getRoomName()),
                new Label("Price Payed: " + event.getTicketPrice()*ticketCount),
                new Label("Balance:     " + AttendeeGUI.tempAttendee.getBalance()),
                ok);
        Stage stage = new Stage();
        stage.setTitle("Event Details");
        root.setPadding(new Insets(20));
        ScrollPane scrollPane = new ScrollPane(root);
        scrollPane.setFitToWidth(true);
        Scene scene = new Scene(scrollPane, 250, 450);
        scene.getStylesheets().add(PaymentSuccessful.class.getResource("/styles.css").toExternalForm());

        stage.setScene(scene);
        stage.show();
        ok.setOnAction(e->{
            stage.close();
            AttendeeGUI.show(AttendeeGUI.tempAttendee);
        });
    }
}