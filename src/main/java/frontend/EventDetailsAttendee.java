package frontend;

import BackEnd.Attendee;
import BackEnd.DateTime;
import BackEnd.Event;
import BackEnd.Wallet;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import javax.crypto.SealedObject;

public class EventDetailsAttendee {
    public static void show(Event event) {
        Label eventName = new Label("Event Name: " + event.getEventName());
        Label eventDate = new Label("Date: " + event.getEventDate().DOBToString());
        VBox roomDetails = new VBox(10, new Label("Room Name: " + event.getEventRoom().getRoomName()),
                new Label("Room ID: " + event.getEventRoom().getID()),
                new Label("Room Capacity: " + event.getEventRoom().getRoomCapacity()));
        roomDetails.setPadding(new Insets(0, 0, 0, 20));
        VBox roomVBox = new VBox(10, new Label("Room"), roomDetails);
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
        VBox root = new VBox(15, eventName, new Separator(),
                eventDate, new VBox(10, new Label("Status"), status), roomVBox
        , new Label("Ticket Price: " + event.getTicketPrice()), bookNow);
        Stage stage = new Stage();
        stage.setTitle("Event Details");
        root.setPadding(new Insets(20));
        ScrollPane scrollPane = new ScrollPane(root);
        scrollPane.setFitToWidth(true);
        Scene scene = new Scene(scrollPane, 600, 800);
        stage.setScene(scene);
        stage.show();
        bookNow.setOnAction(e->{
            stage.close();
            BookEventTickets.show(event);
        });
    }
}

class BookEventTickets{
    public static void show(Event event) {
        Label eventName = new Label("Event Name: " + event.getEventName());
        Label balance = new Label();
        HBox balanceHBox = new HBox(10, new Label("Balance: "), balance);
        TextField ticketCount = new TextField();
        HBox ticketCountHBox = new HBox(10, new Label("No. of Tickets: "), ticketCount);
        TextField totalPrice = new TextField();
        HBox totalPriceHBox = new HBox(10, new Label("Total Price: "), totalPrice);
        Button button = new Button("Book");
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
    }
}

class TicketPurchaseConfirmation{
    public static void show(Event event, int ticketCount, Stage stage){
        Stage st = new Stage();
        Button btn1 = new Button("yes");
        Button btn2 = new Button("no");
        Label newlabel= new Label("ARE YOU SURE???????");
        HBox hb1= new HBox(20,btn1,btn2);
        VBox vb1= new VBox(20,newlabel,hb1);
        Scene sets= new Scene(vb1,200,100);
        hb1.setAlignment(Pos.CENTER);
        vb1.setAlignment(Pos.CENTER);
        st.setResizable(false);
        st.setScene(sets);
        btn1.setOnAction(e-> {
            AttendeeGUI.tempAttendee.bookTickets(event,AttendeeGUI.tempAttendee,ticketCount);
            AttendeeGUI.show(AttendeeGUI.tempAttendee);
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