package frontend;

import BackEnd.Admin;
import BackEnd.Category;
import BackEnd.Database;
import BackEnd.Event;
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

import java.util.ArrayList;
import java.util.List;

import static BackEnd.DateTime.displayTime;


public class AdminInterface {
    public static Admin tempAdmin;

    public static void show(Admin q) {
        tempAdmin = q;

        Stage stage = new Stage();
        stage.setTitle("Admin Interface");
        stage.getIcons().add(new Image("img.png"));


        HBox sidebar = new HBox();
        sidebar.setStyle("-fx-background-color: #333; -fx-padding: 10;");
        sidebar.setPrefWidth(200);
        stage.setResizable(false);

        Button toggleButton = new Button("☰");
        toggleButton.setAlignment(Pos.CENTER_RIGHT);
        HBox fullSidebar = new HBox(sidebar, toggleButton);
        toggleButton.setStyle("-fx-font-size: 16;");

        toggleButton.setOnAction(e -> {
            double targetX = sidebar.getTranslateX() == 0 ? -250 : 0;

            TranslateTransition transition = new TranslateTransition(Duration.millis(300), sidebar);
            transition.setToX(targetX);
            transition.play();

        });


        //sidebar buttons
        Button accDetails = new Button("View Account");
        Button logout = new Button("Log out");

        accDetails.setOnAction(e -> {


        });
        logout.setOnAction(e -> {
            stage.close();
            tempAdmin = null;
            LoginWindow.show(); //should be replaced with main.Start
        });

        accDetails.setOnAction(e -> {
            stage.close();
            myAccount.show();
        });


        sidebar.getChildren().addAll(accDetails, logout);
        sidebar.setPadding(new Insets(5));
        sidebar.setTranslateX(-250);

        Label greeting = new Label("Hello Mr/Mrs: " + q.getUsername());
        greeting.setFont(Font.font("Arial", FontWeight.BOLD, 16));
        Line line = new Line();

        HBox greetingHbox = new HBox(greeting);
        greetingHbox.setAlignment(Pos.TOP_LEFT);

        Button eventsBtn = new Button("Events");
        Button usersBtn = new Button("Users");
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
            managerooms.show();
        });

        ScrollPane scrollPane = new ScrollPane();
        scrollPane.setFitToWidth(true);


        VBox Vpane = new VBox(20,greetingHbox, fullSidebar, toggleButton, buttons);

        Scene scene = new Scene(Vpane, 800, 450);
        stage.setScene(scene);
        stage.show();
        Vpane.setPadding(new Insets(20));
        Vpane.setAlignment(Pos.TOP_LEFT);

    }


    public static class showEvent {
        public static void show() {
            Stage stage = new Stage();
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

                for(Event q: bwoah){
                    System.out.println(q);
                }

            });

            VBox Vpane = new VBox(10, tempHbox, backBtn);
            Vpane.setAlignment(Pos.BOTTOM_LEFT);

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
                        Button eventButton = new Button (events.get(i).getEventName() + "\n" + displayTime(events.get(i)));
                        SearchResult.getChildren().add(eventButton);
                    }
                    SearchResult.getChildren().add(FoundCond);
                }
                else{
                    FoundCond.setText("No events found in this Category");
                    SearchResult.getChildren().add(FoundCond);
                }
            });

            HBox catSearching = new HBox(10,CatsCombo,Catssearch);
            catSearching.setAlignment(Pos.CENTER);
            SearchResult.setAlignment(Pos.CENTER);



            backBtn.setOnAction(e -> {
                stage.close();
                AdminInterface.show(tempAdmin);
            });


            VBox root = new VBox(20, Hpane,otherOption, catSearching, SearchResult, Vpane);
            Scene scene = new Scene(root, 800, 300);
            stage.setScene(scene);





            ScrollPane scrollPane = new ScrollPane(Vpane);
            scrollPane.setFitToWidth(true);

            Vpane.setPadding(new Insets(20));
        }
    }

    public static class showUsers {
        public static void show() {
            Button backBtn = new Button("Back");
            Stage stage = new Stage();
            ComboBox<String> usersCombo = new ComboBox<>();
            usersCombo.setPromptText("Select User Type");
            usersCombo.getItems().add(0, "Attendee");
            usersCombo.getItems().add(1, "Organizer");

            Button list = new Button("List");

            HBox Hpane1 = new HBox(usersCombo, list);
            Hpane1.setPadding(new Insets(10));
            Hpane1.setAlignment(Pos.TOP_CENTER);
            VBox Vpane = new VBox(20, Hpane1, backBtn);
            Scene scene = new Scene(Vpane, 500, 300);
            stage.setScene(scene);
            stage.show();

            //Hpane1.setPadding(new Insets(20));

            list.setOnAction(e -> {
                if (usersCombo.getValue().equals("Oragnizer")) {
                    //Organizer List
                } else {
                    //Attende List
                }
            });

            Vpane.setAlignment(Pos.TOP_CENTER);

            backBtn.setOnAction(e -> {
                stage.close();
                AdminInterface.show(tempAdmin);
            });
            Vpane.setPadding(new Insets(20));
        }
    }


    public static class managerooms {
        public static void show() {
            Stage stage = new Stage();
            ToggleGroup group = new ToggleGroup();
            RadioButton roomChoice = new RadioButton("Room");
            Button createRoom = new Button("Create Room");
            Button readRoom = new Button("List Rooms");
            Button updateRoom = new Button("Update Rooms");
            Button deleteRoom = new Button("Delete Room");

            RadioButton catChoice = new RadioButton("Category");
            Button createCat = new Button("Create Category");
            Button readCat = new Button("List Category");
            Button updateCat = new Button("Update Category");
            Button deleteCat = new Button("Delete Category");
            HBox Hpane1 = new HBox(10, roomChoice, catChoice);
            Hpane1.setAlignment(Pos.CENTER);
            Text text = new Text("Please choose room or catgeory");
            Button backBtn = new Button("Back");
            HBox Hpane2 = new HBox(10, backBtn);
            Hpane2.setAlignment(Pos.BOTTOM_LEFT);

            backBtn.setOnAction(e -> {
                stage.close();
                AdminInterface.show(tempAdmin);
            });

            HBox roomCRUD = new HBox(10, createRoom, readRoom, updateRoom, deleteRoom);
            roomCRUD.setAlignment(Pos.CENTER);
            HBox catCRUD = new HBox(10, createCat, readCat, updateCat, deleteCat);
            catCRUD.setAlignment(Pos.CENTER);

            VBox Vpane = new VBox(20, Hpane1, roomCRUD, catCRUD, Hpane2);
            Vpane.setAlignment(Pos.CENTER);
            Scene scene = new Scene(Vpane, 500, 200);
            roomChoice.setToggleGroup(group);
            catChoice.setToggleGroup(group);

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
            Button backBtn = new Button("Back");
            Image pfp = new Image("pfp.png");
            ImageView pfpView = new ImageView(pfp);
            pfpView.setFitWidth(80);
            pfpView.setFitHeight(80);
            HBox imageHpane = new HBox(20, pfpView);
            imageHpane.setAlignment(Pos.TOP_CENTER);

            Line seperatorline = new Line(0,50,275,50);
            seperatorline.setStroke(Color.BLACK);
            seperatorline.setStrokeWidth(4);

            Label text = new Label("MY ACCOUNT");
            text.setFont(Font.font("Arial", FontWeight.EXTRA_BOLD, 18));
            text.setPadding(new Insets(20));
//            text.setStyle("-fx-border-color: black; -fx-border-width: 2; -fx-padding: 5;");
            Label details = new Label("Username: " + tempAdmin.getUsername() + "\n"
                    + "Email: " + tempAdmin.getEmail() + "\n"
                    + "ID: " + tempAdmin.getID() + "\n"
                    + "Role: " + tempAdmin.getRole() + "\n"
                    + "Date of Birth: " + tempAdmin.getDateOfBirth());
            details.setFont(Font.font("Arial", FontWeight.BOLD, 12));
            VBox detailsVpane = new VBox(details);
            detailsVpane.setPadding(new Insets(10));

            VBox Vpane = new VBox(5,text,seperatorline, imageHpane, detailsVpane, backBtn);
            Vpane.setAlignment(Pos.TOP_CENTER);
            Vpane.setPadding(new Insets(10));

            Scene scene = new Scene(Vpane, 275, 350);
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.show();
            backBtn.setOnAction(e -> {
                stage.close();
                AdminInterface.show(tempAdmin);
            });


        }
    }
}
