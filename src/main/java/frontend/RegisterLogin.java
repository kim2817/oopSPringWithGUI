package frontend;

import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Separator;
import javafx.scene.image.Image;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class RegisterLogin {
    public static void show() {

        Stage regLog = new Stage();
        Image icon = new Image("Logo.png");
        regLog.getIcons().add(icon);
        regLog.setTitle("Eventra regLog");

        Button loginButton = new Button("Login");
        Button registerButton = new Button("Register");
        loginButton.getStyleClass().add("purple-button");
        registerButton.getStyleClass().add("purple-button");
        loginButton.setMaxWidth(150);
        registerButton.setMaxWidth(150);


        Label greeting = new Label("Welcome \nto Eventra");
        VBox greetingBox = new VBox(greeting);
        greetingBox.setAlignment(Pos.CENTER);
        greetingBox.setPrefHeight(450);



        Label choose = new Label("Login or Register");
        VBox.setMargin(choose, new Insets(60, 0, 20, 0));
        VBox choices = new VBox(30,choose,loginButton,registerButton);
        choices.setAlignment(Pos.CENTER);
        VBox.setMargin(choose, new Insets(0, 0, 40, 0));


        //CSS STUFF
        greeting.getStyleClass().add("h1");
        choose.getStyleClass().add("h2");
        greetingBox.getStyleClass().add("lilac-panel");


        Separator verticalSeparator = new Separator();
        verticalSeparator.setOrientation(Orientation.VERTICAL);

        HBox flow = new HBox(greetingBox, verticalSeparator, choices);
        flow.setAlignment(Pos.CENTER);
        flow.setPrefHeight(450);

        verticalSeparator.prefHeightProperty().bind(flow.heightProperty());
        greetingBox.setPrefWidth(400);
        choices.setPrefWidth(400);


        Color lilac = Color.web("#ac98df");

        loginButton.setOnAction(e->{
            regLog.close();
            LoginWindow.show();
        });
        registerButton.setOnAction(e->{
            regLog.close();
            RegisterWindow.show();
        });

        StackPane root = new StackPane(new HBox(20, flow));
        Scene scene = new Scene(root, 800, 450);
        scene.getStylesheets().add(RegisterLogin.class.getResource("/styles.css").toExternalForm());


        root.setPadding(new Insets(20));


        regLog.setResizable(false);
        regLog.setScene(scene);
        regLog.show();
    }
}
