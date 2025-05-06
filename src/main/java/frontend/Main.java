package frontend;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import static javafx.application.Application.launch;

public class Main extends Application{

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {
        Button loginButton = new Button("Login");
        Button registerButton = new Button("Register");
        Color lilac = Color.web("#ac98df");

        loginButton.setOnAction(e->{
           LoginWindow.show();
        });
        registerButton.setOnAction(e->{
            RegisterWindow.show();
        });

        StackPane root = new StackPane(new HBox(20, loginButton, registerButton));
        Scene scene = new Scene(root, 300, 200, lilac);

        Stage login = new Stage();

        Image icon = new Image("img.png");

        root.setPadding(new Insets(20));

        login.getIcons().add(icon);
        login.setTitle("Eventra login");
        login.setScene(scene);
        login.show();

    }



}