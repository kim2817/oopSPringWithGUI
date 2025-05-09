package frontend;
import BackEnd.Admin;
import BackEnd.Attendee;
import BackEnd.Organizer;
import BackEnd.User;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import static BackEnd.Entrance.login;

public class LoginWindow {
    public static void show(){
        Stage stage = new Stage();

        Image icon = new Image("Logo.png");
        stage.getIcons().add(icon);

        stage.setTitle("Eventra Login");

        Label greeting = new Label("Welcome Back");
        greeting.getStyleClass().add("h1");
        greeting.setWrapText(false);
        greeting.setMaxWidth(Double.MAX_VALUE);
        greeting.setAlignment(Pos.CENTER);

        VBox purplePanel = new VBox(greeting);
        purplePanel.setPadding(new Insets(20, 30, 20, 30)); // top, right, bottom, left

        purplePanel.setAlignment(Pos.CENTER);
        purplePanel.setPrefWidth(300);
        purplePanel.getStyleClass().add("lilacSquare-panel");


        Label username = new Label("Username");
        TextField fieldUser = new TextField();

        Label password = new Label("Password:");
        PasswordField fieldPass = new PasswordField();

        VBox userField = new VBox(5, username, fieldUser);
        VBox passField = new VBox(5, password, fieldPass);

        userField.setAlignment(Pos.TOP_LEFT);
        passField.setAlignment(Pos.TOP_LEFT);


        Button loginBtn = new Button("Login");
        Button cancelBtn = new Button("Cancel");
        loginBtn.getStyleClass().add("purple-button");
        cancelBtn.getStyleClass().add("purple-button");


        HBox btns = new HBox(10, loginBtn, cancelBtn);
        btns.setAlignment(Pos.CENTER);

        VBox.setMargin(btns, new Insets(20, 0, 0, 0));

        fieldUser.setMaxWidth(250);
        fieldPass.setMaxWidth(250);


        username.getStyleClass().add("body-text");
        password.getStyleClass().add("body-text");

        VBox.setMargin(username, new Insets(10, 0, 0, 0));
        VBox.setMargin(password, new Insets(10, 0, 0, 0));



        cancelBtn.setOnAction(e -> {
            stage.close();
            RegisterLogin.show();
        });

        Label notFound= new Label();
        loginBtn.setOnAction(e -> {

            User Person;
            String user = fieldUser.getText();
            String pass = fieldPass.getText();
             User role = login(user,pass);

             if(role != null) {
                 if (role instanceof Attendee) {
                     System.out.println("attendee dashboard");
                     AttendeeGUI.show((Attendee) role);
                     notFound.setText("Account found");
                     stage.close();
                 }
                 if (role instanceof Admin) {
                     System.out.println("admin dashboard");
                     AdminInterface.show((Admin) role);
                     notFound.setText("Account found");
                     stage.close();
                 }
                 if (role instanceof Organizer) {
                     System.out.println("organizer dashboard");
                     OrganizerUI.show((Organizer) role);
                     notFound.setText("Account found");
                     stage.close();
                 }
             }
             else{
                 notFound.setText("Account is not registered");
             }

        });


        VBox subroot = new VBox(15, userField, passField, notFound, btns);

        subroot.setAlignment(Pos.CENTER); // Keeps everything centered as a block
        subroot.setPadding(new Insets(30));
        subroot.setAlignment(Pos.CENTER);
        subroot.setPadding(new Insets(30));
        subroot.setPrefWidth(300);

        purplePanel.setPrefWidth(300);
        purplePanel.setPrefHeight(450);

        HBox lastSubRoot = new HBox(purplePanel, subroot);
        lastSubRoot.setPadding(new Insets(20));
        lastSubRoot.setAlignment(Pos.CENTER_LEFT);
        lastSubRoot.setSpacing(0);

        StackPane root = new StackPane(lastSubRoot);


        Scene scene = new Scene(root, 800, 450);
        scene.getStylesheets().add(LoginWindow.class.getResource("/styles.css").toExternalForm());


        stage.setResizable(false);

        stage.setScene(scene);
        stage.show();

    }
}
