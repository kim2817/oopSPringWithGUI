package frontend;

import BackEnd.Admin;
import javafx.animation.TranslateTransition;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.scene.text.Font;
import javafx.util.Duration;


public class AdminInterface{
    public static void show(Admin q){


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
            LoginWindow.show(); //should be replaced with main.Start
        });


        sidebar.getChildren().addAll(accDetails, logout);

        sidebar.setTranslateX(-250);
        Scene scene = new Scene(grid, 600, 400);

        Text greeting = new Text("Hello Mr/Mrs: Admin");
        greeting.setFont(Font.font("Arial"));
        Line line = new Line();

//        line.setStartX(0);
//        line.setEndX(2000);
//        line.setStroke(Color.BLACK);
        Button eventsBtn = new Button("Events");
        Button usersBtn = new Button("Users");
        Button mngDataBtn = new Button("Manage Data");


        eventsBtn.setOnAction(e->{
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


}
