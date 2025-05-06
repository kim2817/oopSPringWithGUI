package frontend;
import BackEnd.Admin;
import BackEnd.Attendee;
import BackEnd.Organizer;
import BackEnd.User;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

import static BackEnd.Entrance.login;

public class LoginWindow {
    public static void show(){
        Stage stage = new Stage();

        Image icon = new Image("img.png");
        stage.getIcons().add(icon);

        stage.setTitle("Eventra Login");

        Label username = new Label("Username");
        TextField fieldUser = new TextField();

        Label password = new Label("Password:");
        PasswordField fieldPass = new PasswordField();

        Button loginBtn = new Button("Login");
        Button cancelBtn = new Button("Cancel");

        GridPane grid = new GridPane();
        grid.setVgap(10);
        grid.setHgap(10);
        grid.add(username, 0, 0);
        grid.add(fieldUser, 1, 0);
        grid.add(password, 0, 1);
        grid.add(fieldPass, 1, 1);
        grid.add(loginBtn, 0, 2);
        grid.add(cancelBtn, 1, 2);

        cancelBtn.setOnAction(e -> stage.close());

        loginBtn.setOnAction(e -> {
            User Person;
            String user = fieldUser.getText();
            String pass = fieldPass.getText();
             User role = login(user,pass);
             if(role != null) {
                 if (role instanceof Attendee) {
                     System.out.println("attendee dashboard");
                     AttendeeGUI.show((Attendee) role);
                 }
                 if (role instanceof Admin) {
                     System.out.println("admin dashboard");
                     AdminInterface.show((Admin) role);
                 }
                 if (role instanceof Organizer) {
                     System.out.println("organizer dashboard");
                     OrganizerUI.show((Organizer) role);
                 }
             }

                // 1 not found, 2 attendee, 3 organizer, 4 admin
            System.out.println("Logging in. . .");
        });


        grid.setPadding(new Insets(20));

        StackPane root = new StackPane(grid);

        Scene scene = new Scene(root, 400, 300);

        stage.setScene(scene);
        stage.show();

    }
}
