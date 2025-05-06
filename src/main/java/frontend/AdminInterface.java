package frontend;

import BackEnd.Admin;
import javafx.animation.TranslateTransition;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.scene.shape.Line;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.scene.text.Font;
import javafx.util.Duration;


public class AdminInterface{
    public static Admin tempAdmin;

    public static void show(Admin q){
        tempAdmin = q;

        Stage stage = new Stage();
        stage.setTitle("Admin Interface");
        stage.getIcons().add(new Image("img.png"));


        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);

        HBox sidebar = new HBox();
        sidebar.setStyle("-fx-background-color: #333; -fx-padding: 10;");
        sidebar.setPrefWidth(200);
        stage.setResizable(false);

        Button toggleButton = new Button("☰");

        toggleButton.setStyle("-fx-font-size: 16;");
        grid.add(toggleButton,0,1);
        grid.add(sidebar,0,0 );

        toggleButton.setOnAction(e ->{
            double targetX = sidebar.getTranslateX()==0? -250: 0;

            TranslateTransition transition = new TranslateTransition(Duration.millis(300), sidebar);
            transition.setToX(targetX);
            transition.play();

        });





        //sidebar buttons
        Button accDetails = new Button("View Account");
        Button logout = new Button("Log out");

        accDetails.setOnAction(e->{

        });
        logout.setOnAction(e->{
            stage.close();
            tempAdmin = null;
            LoginWindow.show(); //should be replaced with main.Start
        });


        sidebar.getChildren().addAll(accDetails, logout);

        sidebar.setTranslateX(-250);
        Scene scene = new Scene(grid, 600, 400);

        Text greeting = new Text("Hello Mr/Mrs: " + q.getUsername());
        greeting.setFont(Font.font("Arial"));
        Line line = new Line();

        Button eventsBtn = new Button("Events");
        Button usersBtn = new Button("Users");
        Button mngDataBtn = new Button("Manage Data");


        eventsBtn.setOnAction(e->{
            stage.close();
            showEvent.show();
        });

        usersBtn.setOnAction(e->{
            stage.close();
            showUsers.show();
        });
        mngDataBtn.setOnAction(e->{
            stage.close();
            managerooms.show();
        });

        grid.add(greeting,1,1);
        grid.add(eventsBtn, 0,4);
        grid.add(usersBtn, 0,5);
        grid.add(mngDataBtn, 0,6);
        stage.setScene(scene);
        stage.show();

        ScrollPane scrollPane = new ScrollPane(grid);
        scrollPane.setFitToWidth(true);

        grid.setPadding(new Insets(20));
        Scene scene1 = new Scene(scrollPane, 500, 700);

    }


    public static class showEvent {
        public static void show(){
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
            searchBtn.setOnAction(e->{

                    answer.setText((Admin.searchEvents(search.getText())));
//                answer.setLayoutY(200);
//                answer.setLayoutX(400);


            });
            VBox Vpane = new VBox(10, tempHbox, backBtn);
            Vpane.setAlignment(Pos.BOTTOM_LEFT);
            VBox root = new VBox(Hpane,Vpane);
            Scene scene = new Scene(root,800, 300);
            stage.setScene(scene);

            backBtn.setOnAction(e ->{
                stage.close();
                AdminInterface.show(tempAdmin);
            });


            ScrollPane scrollPane = new ScrollPane(Vpane);
            scrollPane.setFitToWidth(true);

            Vpane.setPadding(new Insets(20));
        }
    }

    public static class showUsers {
        public static void show(){
            Stage stage = new Stage();
            Pane pane = new Pane();
            Scene scene = new Scene(pane);
            stage.setScene(scene);
            stage.show();
        }
    }

    public static class managerooms{
        public static void show(){
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

            backBtn.setOnAction(e ->{
                stage.close();
                AdminInterface.show(tempAdmin);
            });

            HBox roomCRUD = new HBox(10, createRoom, readRoom, updateRoom, deleteRoom);
            roomCRUD.setAlignment(Pos.CENTER);
            HBox catCRUD = new HBox(10, createCat, readCat, updateCat, deleteCat);
            catCRUD.setAlignment(Pos.CENTER);

            VBox Vpane = new VBox(20, Hpane1,roomCRUD, catCRUD, Hpane2);
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

                    if(isCat) {
                        roomCRUD.setVisible(false);
                        roomCRUD.setManaged(false);
                        catCRUD.setVisible(true);
                        catCRUD.setManaged(true);
                    }
                    else{
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
}
