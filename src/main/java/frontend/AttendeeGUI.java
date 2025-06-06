package frontend;

import BackEnd.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
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

import java.util.ArrayList;
import java.util.List;

import static BackEnd.Admin.searchEvents;
import static BackEnd.DateTime.displayTime;

public class AttendeeGUI {
    public static Attendee tempAttendee;
    private static FlowPane SearchResult;
    private static Label FoundCond;
    public static Stage curStage;
    public static void show(Attendee attendee) {
        tempAttendee = attendee;
        Stage stage = new Stage();
        Image icon = new Image("Logo.png");
        stage.getIcons().add(icon);
        stage.setTitle("Eventra - Attendee Dashboard");

        Image profileImage = new Image("profile.png");
        ImageView profileIcon = new ImageView(profileImage);
        profileIcon.setFitWidth(60);
        profileIcon.setFitHeight(60);

        BorderPane sidebarRoot = new BorderPane();


        VBox sidebar = new VBox(10);
        sidebar.getStyleClass().add("lilacSidebar-panel");
        sidebar.setPadding(new Insets(10));
        sidebar.setPrefWidth(200);

        Button myAccBtn = new Button("My Account");
        Button myEventBtn = new Button("My Events");
        Button logoutBtn = new Button("Logout");
        myAccBtn.getStyleClass().add("purple-button");
        myEventBtn.getStyleClass().add("purple-button");
        logoutBtn.getStyleClass().add("purple-button");
        myAccBtn.setMaxWidth(150);
        myEventBtn.setMaxWidth(150);
        logoutBtn.setMaxWidth(150);

        myEventBtn.setOnAction(e->{
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

        sidebar.getChildren().addAll(myAccBtn, myEventBtn, logoutBtn);

        // 2. Create the toggle button (top left)
        Button toggleBtn = new Button("☰");
        toggleBtn.getStyleClass().add("transparent-lilac-button");

        toggleBtn.setFocusTraversable(false);
        toggleBtn.setOnAction(e -> {
            if (sidebarRoot.getLeft() == null) {
                sidebarRoot.setLeft(sidebar);
            } else {
                sidebarRoot.setLeft(null);
            }
        });




        Label greeting = new Label("Hello " + tempAttendee.getGen().getTitle() + " " + attendee.getUsername() + ",");
        Label balanceLabel = new Label("Balance: $" + attendee.getBalance());
        greeting.getStyleClass().add("h2");
        balanceLabel.getStyleClass().add("h3");

        VBox userBox = new VBox(5, greeting, balanceLabel);
        userBox.setAlignment(Pos.CENTER_LEFT);

        HBox navBar = new HBox(10, profileIcon,userBox);
        navBar.setAlignment(Pos.CENTER_LEFT);
        navBar.setPadding(new Insets(10));
        navBar.setStyle("-fx-background-color: transparent;");

        HBox topBar = new HBox(10,toggleBtn,navBar);
        topBar.getStyleClass().add("lilacSquare-panel");

        topBar.setAlignment(Pos.CENTER_LEFT);
        topBar.setPadding(new Insets(10));



        //searching
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
            ArrayList<Button> buttons = eventToButton(searchEvents(searchField.getText()));
            SearchResult.getChildren().clear();
            if(buttons.isEmpty()){
                FoundCond.setText("No event found");
                SearchResult.getChildren().add(FoundCond);
            }
            else{
                FoundCond.setText("");
                SearchResult.getChildren().addAll(buttons);
            }
        });

        Separator separator = new Separator();

        //categories searching..
        Label or = new Label("OR");
        or.getStyleClass().add("h3");
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
            ArrayList<Button> buttons = eventToButton(cat.getEvents());
            SearchResult.getChildren().clear();
            if(buttons.isEmpty()){
                FoundCond.setText("No event found");
                SearchResult.getChildren().add(FoundCond);
            }
            else{
                FoundCond.setText("");
                SearchResult.getChildren().addAll(buttons);
            }
        });

        HBox catSearching = new HBox(10,CatsCombo,Catssearch);
        catSearching.setAlignment(Pos.CENTER);
        SearchResult.setAlignment(Pos.CENTER);


        //intrests
        Label intrestsLabel = new Label("Events you may like");
        intrestsLabel.getStyleClass().add("h2");
        Category[] interests = tempAttendee.getInterest();
        TitledPane[] panes = new TitledPane[3];
        for(int i=0;i<3;i++){
            HBox hBox = new HBox(10);
            hBox.setAlignment(Pos.CENTER);
            panes[i] = new TitledPane();
            panes[i].setText(interests[i].getCatName());
            panes[i].setContent(hBox);
            ArrayList<Button> buttons = eventToButton(interests[i].getEvents());
            if(buttons.size()>5) hBox.getChildren().addAll(buttons.subList(0,5));
            else hBox.getChildren().addAll(buttons);
        }
        Accordion accordion = new Accordion();
        accordion.getStyleClass().add("custom-accordion");
        accordion.getPanes().addAll(panes);
        //layout
        VBox centerContent = new VBox(20, searchSection,otherOption,catSearching,SearchResult,separator,intrestsLabel,accordion);
        centerContent.setPadding(new Insets(20));

        centerContent.setAlignment(Pos.TOP_CENTER);
        VBox root = new VBox(sidebarRoot,centerContent);

        sidebarRoot.setTop(topBar);
        sidebarRoot.setCenter(centerContent);
        sidebarRoot.minHeightProperty().bind(stage.heightProperty().subtract(topBar.heightProperty()));
        ScrollPane scrollPane = new ScrollPane(root);
        scrollPane.setFitToWidth(true);

        Scene scene = new Scene(scrollPane, 800, 450);
        scene.getStylesheets().add(AttendeeGUI.class.getResource("/styles.css").toExternalForm());

        stage.setScene(scene);
        curStage=stage;
        stage.show();
    }
    public static ArrayList<Button> eventToButton(List<Event> events){
        SearchResult.getChildren().clear();
        ArrayList<Button> buttons = new ArrayList<>();
        if(events!=null && !(events.isEmpty())) {
            for (Event event : events) {
                Button eventButton = new Button(event.getEventName() + "\n" + displayTime(event));
                eventButton.getStyleClass().add("rounded-soft-button");
                eventButton.setOnAction(ee -> {
                    String eventName = eventButton.getText().substring(0, eventButton.getText().indexOf("\n"));
                    EventDetailsAttendee.show(Database.findEvent(eventName).getFirst());
                    AttendeeGUI.curStage.close();
                });
                buttons.add(eventButton);
            }
        }
        return buttons;
    }
    public static class myAccount {
        public static void show() {
            Button backBtn = new Button("Back");
            backBtn.getStyleClass().add("filled-button");

            Image pfp = new Image("profile.png");
            ImageView pfpView = new ImageView(pfp);


            pfpView.setFitWidth(80);
            pfpView.setFitHeight(80);
            HBox imageHpane = new HBox(20, pfpView);
            imageHpane.setAlignment(Pos.TOP_CENTER);

            Line separatorLine = new Line(0, 50, 275, 50);
            separatorLine.setStroke(Color.web("#a074e2"));
            separatorLine.setStrokeWidth(2);

            Label text = new Label("MY ACCOUNT");
            text.getStyleClass().add("h2");
            text.setPadding(new Insets(20));
            String interests = tempAttendee.showInterest();
            Label details = new Label(
                    "Username:      " + tempAttendee.getUsername() + "\n" +
                            "Email:         " + tempAttendee.getEmail() + "\n" +
                            "ID:            " + tempAttendee.getID() + "\n" +
                            "Age:           " + tempAttendee.getAge() + "\n" +
                            "Date of Birth: " + tempAttendee.getDateOfBirth().DOBToString() + "\n" +
                            "Interests:\n" + interests
            );


            details.setTextFill(Color.web("#47276d"));
            details.setPadding(new Insets(10));
            VBox detailsVpane = new VBox(details);
            detailsVpane.setPadding(new Insets(10));

            VBox Vpane = new VBox(5, text, separatorLine, imageHpane, detailsVpane, backBtn);
            Vpane.setAlignment(Pos.TOP_CENTER);
            Vpane.setPadding(new Insets(10));

            ScrollPane scrollPane = new ScrollPane(Vpane);
            Scene scene = new Scene(scrollPane, 400, 450);
            scene.getStylesheets().add(myAccount.class.getResource("/styles.css").toExternalForm());

            Stage stage = new Stage();
            stage.getIcons().add(pfp);

            stage.setScene(scene);
            stage.show();
            backBtn.setOnAction(e -> {
                stage.close();
                AttendeeGUI.show(tempAttendee);
            });
        }



    }
}
