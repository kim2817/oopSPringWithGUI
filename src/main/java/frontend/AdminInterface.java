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

//        line.setStartX(0);
//        line.setEndX(2000);
//        line.setStroke(Color.BLACK);
        Button eventsBtn = new Button("Events");
        Button usersBtn = new Button("Users");
        Button mngDataBtn = new Button("Manage Data");


        eventsBtn.setOnAction(e->{
            stage.close();
            showEvent.show();
        });

        usersBtn.setOnAction(e->{
            showUsers.show();
        });
        mngDataBtn.setOnAction(e->{
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

        Scene scene1 = new Scene(scrollPane, 500, 700);
        grid.setPadding(new Insets(20));

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
            searchBtn.setOnAction(e->{
                if (search.getText() == null){
//                    System.out.println();
                }
                else{
                    answer.setText((Admin.searchEvents(search.getText())));
                    answer.setLayoutX(400);
                    answer.setLayoutY(200);
                }

            });
            VBox Vpane = new VBox(10, answer, backBtn);
            Vpane.setAlignment(Pos.BOTTOM_LEFT);
            VBox root = new VBox(Hpane,Vpane);
            Scene scene = new Scene(root,800, 300);
            stage.setScene(scene);

            backBtn.setOnAction(e ->{
                stage.close();
                AdminInterface.show(tempAdmin);
            });



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
            FlowPane pane = new FlowPane();
            pane.setHgap(100);
            Scene scene = new Scene(pane, 300, 300);
            ToggleGroup group = new ToggleGroup();
            RadioButton roomChoice = new RadioButton("Room");
            RadioButton catChoice = new RadioButton("Category");
            Text text = new Text("Please choose room or catgeory");
            Button backBtn = new Button("Back");
            pane.getChildren().add(backBtn);



            pane.setPadding(new Insets(20));
            pane.getChildren().addAll(roomChoice, catChoice, text);
            stage.setScene(scene);
            stage.show();

            roomChoice.setToggleGroup(group);
            catChoice.setToggleGroup(group);

        }


    }
}
