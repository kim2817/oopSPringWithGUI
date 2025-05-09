package frontend;

import BackEnd.Attendee;
import BackEnd.Category;
import BackEnd.Database;
import BackEnd.Event;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;

import javax.naming.directory.SearchResult;
import java.util.ArrayList;
import java.util.List;

import static BackEnd.Admin.searchEvents;
import static BackEnd.DateTime.displayTime;

public class AttendeeGUI {
    public static Attendee tempAttendee;
    private static FlowPane SearchResult;
    private static Label FoundCond;
    public static void show(Attendee attendee) {
        tempAttendee = attendee;
        Stage stage = new Stage();
        stage.setTitle("Eventra - Attendee Dashboard");

        Image profileImage = new Image("profile.png"); // Replace with your actual image
        ImageView profileIcon = new ImageView(profileImage);
        profileIcon.setFitWidth(40);
        profileIcon.setFitHeight(40);

        BorderPane sidebarRoot = new BorderPane();

        // 1. Create the sidebar
        VBox sidebar = new VBox(10);
        sidebar.setPadding(new Insets(10));
        sidebar.setStyle("-fx-background-color: #eeeeee;");
        sidebar.setPrefWidth(200);

        Button myAccBtn = new Button("My Account");
        Button logoutBtn = new Button("Logout");
        Button MyEvent = new Button("My events");

        MyEvent.setOnAction(e->{
            stage.close();
            AttendeeBookedEvents.show(tempAttendee);
        });
        logoutBtn.setOnAction(e -> {
            stage.close();
            tempAttendee = null;
            LoginWindow.show();
        });

        myAccBtn.setOnAction(e -> {
            stage.close();
            AttendeeGUI.myAccount.show();
        });

        sidebar.getChildren().addAll(myAccBtn, logoutBtn);

        // 2. Create the toggle button (top left)
        Button toggleBtn = new Button("☰");
        toggleBtn.setFocusTraversable(false);

        toggleBtn.setOnAction(e -> {
            if (sidebarRoot.getLeft() == null) {
                sidebarRoot.setLeft(sidebar);
            } else {
                sidebarRoot.setLeft(null);
            }
        });




        Label greeting = new Label("Hello Mr/Ms " + attendee.getUsername() + ",");
        Label balanceLabel = new Label("Balance: $" + attendee.getBalance());

        VBox userBox = new VBox(5, greeting, balanceLabel);
        userBox.setAlignment(Pos.CENTER_LEFT);

        HBox navBar = new HBox(10, profileIcon,userBox);
        navBar.setAlignment(Pos.CENTER_LEFT);
        navBar.setPadding(new Insets(10));
        navBar.setStyle("-fx-background-color: #dddddd;");

        HBox topBar = new HBox(10,toggleBtn,navBar);
        topBar.setAlignment(Pos.CENTER_LEFT);
        topBar.setPadding(new Insets(10));
        topBar.setStyle("-fx-background-color: #dddddd;");

        //searching
        TextField searchField = new TextField();
        searchField.setPromptText("Search for an event");
        Button searchBtn = new Button("Search");
        HBox searchBox = new HBox(10, searchField, searchBtn);
        searchBox.setAlignment(Pos.CENTER);
        VBox searchSection = new VBox(10, searchBox);
        searchSection.setAlignment(Pos.CENTER);
        searchSection.setPadding(new Insets(10));

        SearchResult = new FlowPane();
        SearchResult.setVgap(10);
        SearchResult.setHgap(10);
        FoundCond = new Label("");
        searchBtn.setOnAction(e->{
            eventToButton(searchEvents(searchField.getText()));
        });

        Separator separator = new Separator();

        //categories searching..
        Label or = new Label("OR");
        ObservableList<String> items = FXCollections.observableArrayList(Category.listAllCategories());
        ComboBox<String> CatsCombo = new ComboBox<>();
        CatsCombo.setPromptText("Select a Category");
        CatsCombo.getItems().addAll(items);
        VBox otherOption = new VBox(20,or,CatsCombo);
        otherOption.setAlignment(Pos.CENTER);

        Button Catssearch = new Button("Search");

        Catssearch.setOnAction(e -> {
            eventToButton(Database.findCat(CatsCombo.getValue()).getEvents());
        });

        HBox catSearching = new HBox(10,CatsCombo,Catssearch);
        catSearching.setAlignment(Pos.CENTER);
        SearchResult.setAlignment(Pos.CENTER);


        //intrests
        Label intrestsLabel = new Label("Events you may like");
        intrestsLabel.setStyle("-fx-font-size: 18px; -fx-font-weight: bold;");

        //layout
        VBox centerContent = new VBox(20, searchSection,otherOption,catSearching, separator,SearchResult,intrestsLabel);
        centerContent.setPadding(new Insets(20));

        centerContent.setAlignment(Pos.CENTER);
        VBox root = new VBox(sidebarRoot,centerContent);

        sidebarRoot.setTop(topBar);
        sidebarRoot.setLeft(sidebar); // visible by default
        sidebarRoot.setCenter(centerContent);

        ScrollPane scrollPane = new ScrollPane(root);
        scrollPane.setFitToWidth(true);

        Scene scene = new Scene(scrollPane, 600, 800);
        stage.setScene(scene);
        stage.show();
    }
    public static void eventToButton(List<Event> events){
        SearchResult.getChildren().clear();
        if(events!=null && !(events.isEmpty())) {
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
    public static class myAccount {
        public static void show() {
            Button backBtn = new Button("Back");
            Image pfp = new Image("pfp.png");
            ImageView pfpView = new ImageView(pfp);
            pfpView.setFitWidth(80);
            pfpView.setFitHeight(80);
            HBox imageHpane = new HBox(20, pfpView);
            imageHpane.setAlignment(Pos.TOP_CENTER);

            Line seperatorline = new Line(0, 50, 275, 50);
            seperatorline.setStroke(Color.BLACK);
            seperatorline.setStrokeWidth(4);

            Label text = new Label("MY ACCOUNT");
            text.setFont(Font.font("Arial", FontWeight.EXTRA_BOLD, 18));
            text.setPadding(new Insets(20));
            String interests = tempAttendee.showInterest();
            Label details = new Label("Username: " + tempAttendee.getUsername() + "\n"
                    + "Email: " + tempAttendee.getEmail() + "\n"
                    + "ID: " + tempAttendee.getID() + "\n"
                    + "Age: " + tempAttendee.getAge() + "\n"
                    + "Date of Birth: " + tempAttendee.getDateOfBirth().DOBToString() + "\n" +
                    "Address: " + tempAttendee.getAddress() +"\n" +
                    "Interests: " + "\n" + interests);
            details.setFont(Font.font("Arial", FontWeight.BOLD, 12));
            VBox detailsVpane = new VBox(details);
            detailsVpane.setPadding(new Insets(10));

            VBox Vpane = new VBox(5, text, seperatorline, imageHpane, detailsVpane, backBtn);
            Vpane.setAlignment(Pos.TOP_CENTER);
            Vpane.setPadding(new Insets(10));

            Scene scene = new Scene(Vpane, 275, 350);
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.show();
            backBtn.setOnAction(e -> {
                stage.close();
                AttendeeGUI.show(tempAttendee);
            });
        }



    }
}
