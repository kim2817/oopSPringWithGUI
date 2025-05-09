package frontend;

import BackEnd.*;
import javafx.animation.TranslateTransition;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.scene.text.Font;
import javafx.util.Duration;

import javax.net.ssl.SNIHostName;
import java.util.ArrayList;
import java.util.List;

import static BackEnd.DateTime.displayTime;


public class AdminInterface {
    public static Admin tempAdmin;

    public static void show(Admin q) {
        tempAdmin = q;


        Text greeting = new Text("Welcome Mr/Mrs: " + q.getUsername());
        greeting.setFont(Font.font("Arial", FontWeight.BOLD, 16));


        BorderPane sidebarRoot = new BorderPane();

        VBox sidebar = new VBox(10);
        sidebar.setPadding(new Insets(10));
        sidebar.setStyle("-fx-background-color: #eeeeee;");
        sidebar.setPrefWidth(200);

        Button accDetails = new Button("My Account");
        Button logout = new Button("Logout");

        sidebar.getChildren().addAll(accDetails, logout);

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


        Stage stage = new Stage();
        stage.setTitle("Admin Interface");
        stage.getIcons().add(new Image("Logo.png"));

        Image profileImage = new Image("profile.png"); // Replace with your actual image
        ImageView profileIcon = new ImageView(profileImage);
        profileIcon.setFitWidth(40);
        profileIcon.setFitHeight(40);

        HBox navBar = new HBox(10, profileIcon, greeting);
        navBar.setAlignment(Pos.CENTER_LEFT);
        navBar.setPadding(new Insets(10));
        navBar.setStyle("-fx-background-color: #dddddd;");


        HBox topBar = new HBox(10, toggleBtn, navBar);
        topBar.setAlignment(Pos.TOP_LEFT);
        topBar.setPadding(new Insets(10));
        topBar.setStyle("-fx-background-color: #dddddd;");
        logout.setOnAction(e -> {
            stage.close();
            tempAdmin = null;
            LoginWindow.show();
        });

        accDetails.setOnAction(e -> {
            stage.close();
            myAccount.show();
        });


        Button eventsBtn = new Button("Events");
        Button usersBtn = new Button("Show Attendees");
        Button mngDataBtn = new Button("Manage Data");
        HBox buttons = new HBox(10, eventsBtn, usersBtn, mngDataBtn);
        buttons.setAlignment(Pos.CENTER);

        eventsBtn.setOnAction(e -> {
            stage.close();
            showEvent.show();
        });

        usersBtn.setOnAction(e -> {
            stage.close();
            showUsers.show();
        });
        mngDataBtn.setOnAction(e -> {
            stage.close();
            manageData.show();
        });


        VBox centerContent = new VBox(20, buttons);

        centerContent.setPadding(new Insets(20));
        centerContent.setAlignment(Pos.CENTER);


        VBox root = new VBox(topBar, sidebarRoot, centerContent);

        // 4. Assemble layout
        sidebarRoot.setTop(topBar);
        sidebarRoot.setLeft(sidebar); // visible by default
        sidebarRoot.setCenter(centerContent);

        ScrollPane scrollPane = new ScrollPane(root);
        scrollPane.setFitToWidth(true);

        Scene scene = new Scene(scrollPane, 800, 450);
        stage.setScene(scene);
        stage.show();
        centerContent.setPadding(new Insets(20));
        centerContent.setAlignment(Pos.TOP_LEFT);

    }


    public static class showEvent {
        public static void show() {
            Image icon = new Image("profile.png");
            Stage stage = new Stage();
            stage.setTitle("Search events");
            stage.getIcons().add(icon);
            Button searchBtn = new Button("Search");
            Label resultLabel = new Label();
            TextField search = new TextField();

            Button backBtn = new Button("Back");
            stage.show();
            HBox Hpane = new HBox(10, search, searchBtn);
            Hpane.setAlignment(Pos.TOP_CENTER);
            Hpane.setSpacing(5);

            Text answer = new Text();
            HBox tempHbox = new HBox(answer);
            tempHbox.setAlignment(Pos.CENTER);
            searchBtn.setOnAction(e -> {
                ArrayList<Event> bwoah = Admin.searchEvents(search.getText());

                for (Event q : bwoah) {
                    answer.setText(q.toString());
                }

            });
            VBox backVpane = new VBox(10, backBtn);
            backVpane.setAlignment(Pos.BOTTOM_LEFT);

            Label or = new Label("OR");
            ObservableList<String> items = FXCollections.observableArrayList(Category.listAllCategories());
            ComboBox<String> CatsCombo = new ComboBox<>();
            CatsCombo.setPromptText("Select a Category");
            CatsCombo.getItems().addAll(items);
            VBox otherOption = new VBox(20, or, CatsCombo);
            otherOption.setAlignment(Pos.CENTER);

            final VBox SearchResult = new VBox(10);
            final Label FoundCond = new Label("");
            Button Catssearch = new Button("Search");

            Catssearch.setOnAction(e -> {
                SearchResult.getChildren().clear();

                Category selectedCategory = Database.findCat(CatsCombo.getValue());
                if (!((selectedCategory.getEvents()).isEmpty())) {
                    FoundCond.setText("Events in this Category : ");
                    List<Event> events = selectedCategory.getEvents();
                    for (int i = 0; i < events.size(); i++) {
                        Button eventButton = new Button(events.get(i).getEventName() + "\n" + displayTime(events.get(i)));
                        SearchResult.getChildren().add(eventButton);
                    }
                    SearchResult.getChildren().add(FoundCond);
                } else {
                    FoundCond.setText("No events found in this Category");
                    SearchResult.getChildren().add(FoundCond);
                }
            });

            HBox catSearching = new HBox(10, CatsCombo, Catssearch);
            catSearching.setAlignment(Pos.CENTER);
            SearchResult.setAlignment(Pos.CENTER);


            backBtn.setOnAction(e -> {
                stage.close();
                AdminInterface.show(tempAdmin);
            });


            VBox root = new VBox(20, Hpane, tempHbox, otherOption, catSearching, SearchResult, backBtn);
            ScrollPane scrollPane = new ScrollPane(root);
            scrollPane.setFitToWidth(true);
            Scene scene = new Scene(scrollPane, 800, 300);
            stage.setScene(scene);



            root.setPadding(new Insets(20));

        }
    }

    public static class showUsers {
        public static void show() {
            Image icon = new Image("profile.png");
            Stage stage = new Stage();
            stage.setTitle("Show Attendees");
            stage.getIcons().add(icon);
            Label text = new Label("Click on an attendee to show more details:");
            HBox Hpane1 = new HBox(10, text);
            Hpane1.setAlignment(Pos.CENTER);

            Button backBtn = new Button("Back");
            HBox Hpane2 = new HBox(10, backBtn);
            Hpane2.setAlignment(Pos.BOTTOM_LEFT);
            VBox attendeeHbox = new VBox(10);
            VBox Vpane = new VBox(20, Hpane1, attendeeHbox, Hpane2);


            ArrayList<Attendee> output = Admin.viewAttendee();
            for(Attendee q: output) {
                Label answer = new Label(q.toString());
                attendeeHbox.getChildren().add(answer);
            }


            Vpane.setAlignment(Pos.TOP_CENTER);

            backBtn.setOnAction(e -> {
                stage.close();
                AdminInterface.show(tempAdmin);
            });
            Vpane.setPadding(new Insets(20));
            Scene scene = new Scene(Vpane, 500, 300);
            stage.setScene(scene);
            stage.show();

        }
    }


    public static class manageData {
        public static void show() {
            Image icon = new Image("profile.png");
            Stage stage = new Stage();
            stage.setTitle("Manage rooms");
            stage.getIcons().add(icon);
            ToggleGroup group = new ToggleGroup();
            RadioButton roomChoice = new RadioButton("Room");
            Button CRUDRoom = new Button("Show Rooms");


            RadioButton catChoice = new RadioButton("Category");
            Button CRUDCat = new Button("Show Categories");
            HBox Hpane1 = new HBox(10, roomChoice, catChoice);
            Hpane1.setAlignment(Pos.CENTER);
            Text text = new Text("Please choose room or catgeory");
            HBox textHbox = new HBox(10, text);
            textHbox.setAlignment(Pos.CENTER);
            Button backBtn = new Button("Back");
            HBox Hpane2 = new HBox(10, backBtn);
            Hpane2.setAlignment(Pos.BOTTOM_LEFT);


            backBtn.setOnAction(e -> {
                stage.close();
                AdminInterface.show(tempAdmin);
            });

            CRUDRoom.setOnAction(e -> {
                stage.close();
                roomCRUD.show();
            });

            CRUDCat.setOnAction(e -> {
                stage.close();
                catCRUD.show();
            });

            HBox roomCRUD = new HBox(10, CRUDRoom);
            roomCRUD.setAlignment(Pos.CENTER);
            HBox catCRUD = new HBox(10, CRUDCat);
            catCRUD.setAlignment(Pos.CENTER);

            VBox Vpane = new VBox(20, textHbox, Hpane1, roomCRUD, catCRUD, Hpane2);
            Vpane.setAlignment(Pos.CENTER);
            Scene scene = new Scene(Vpane, 500, 200);

            roomChoice.setToggleGroup(group);
            catChoice.setToggleGroup(group);
            roomChoice.setSelected(true);


            catCRUD.setVisible(false);
            catCRUD.setManaged(false);

            group.selectedToggleProperty().addListener((obs, oldToggle, newToggle) -> {
                if (newToggle != null) {
                    RadioButton chose = (RadioButton) newToggle;
                    boolean isCat = chose == catChoice;

                    if (isCat) {
                        roomCRUD.setVisible(false);
                        roomCRUD.setManaged(false);
                        catCRUD.setVisible(true);
                        catCRUD.setManaged(true);
                    } else {
                        roomCRUD.setVisible(true);
                        roomCRUD.setManaged(true);
                        catCRUD.setVisible(false);
                        catCRUD.setManaged(false);
                    }
                }


            });
            ScrollPane scrollPane = new ScrollPane(Vpane);
            scrollPane.setFitToWidth(true);

            Vpane.setPadding(new Insets(20));
            stage.setScene(scene);
            stage.show();


        }

    }

    public static class myAccount {
        public static void show() {
            Image icon = new Image("profile.png");
            Stage stage = new Stage();
            stage.setTitle("My Account");
            stage.getIcons().add(icon);
            Button backBtn = new Button("Back");
            Image pfp = new Image("profile.png");
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
            Label details = new Label("Username: " + tempAdmin.getUsername() + "\n"
                    + "Email: " + tempAdmin.getEmail() + "\n"
                    + "ID: " + tempAdmin.getID() + "\n"
                    + "Role: " + tempAdmin.getRole() + "\n"
                    + "Date of Birth: " + tempAdmin.getDateOfBirth());
            details.setFont(Font.font("Arial", FontWeight.BOLD, 12));
            VBox detailsVpane = new VBox(details);
            detailsVpane.setPadding(new Insets(10));

            VBox Vpane = new VBox(5, text, seperatorline, imageHpane, detailsVpane, backBtn);
            Vpane.setAlignment(Pos.TOP_CENTER);
            Vpane.setPadding(new Insets(10));

            Scene scene = new Scene(Vpane, 275, 350);
            stage.setScene(scene);
            stage.show();
            backBtn.setOnAction(e -> {
                stage.close();
                AdminInterface.show(tempAdmin);
            });


        }
    }

    public static class roomCRUD {
        public static void show() {
            Stage stage = new Stage();
            stage.setTitle("Rooms");
            Label text = new Label("Click on a room to show more details");
            Button createRoom = new Button("Create Room");
            HBox createHBox = new HBox(10, createRoom);
            createHBox.setAlignment(Pos.TOP_LEFT);
            HBox textHbox = new HBox(10, text);
            textHbox.setAlignment(Pos.CENTER);

            Label answer = new Label();
            EventDetailsAdmin.displayrooms(answer);
            VBox Vpane = new VBox(20, createHBox, textHbox);
            Vpane.setAlignment(Pos.TOP_CENTER);
            Vpane.setPadding(new Insets(20));


            Scene scene = new Scene(Vpane, 600, 400);
            stage.setScene(scene);
            stage.show();

        }
    }
    public static class catCRUD {
        public static void show() {
            Stage stage = new Stage();
            stage.setTitle("Categories");
            Label text = new Label("Categories");
            Button createRoom = new Button("Create Category");
            HBox createHBox = new HBox(10, createRoom);
            createHBox.setAlignment(Pos.TOP_LEFT);
            HBox textHbox = new HBox(10, text);
            textHbox.setAlignment(Pos.CENTER);

            VBox Vpane = new VBox(20, createHBox, textHbox);
            Vpane.setAlignment(Pos.TOP_CENTER);
            Vpane.setPadding(new Insets(20));


            Scene scene = new Scene(Vpane, 600, 400);
            stage.setScene(scene);
            stage.show();
        }
    }
}