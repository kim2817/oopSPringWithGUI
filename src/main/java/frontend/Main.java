package frontend;

import BackEnd.*;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import static BackEnd.Gender.FEMALE;
import static BackEnd.Gender.MALE;
import static javafx.application.Application.launch;

public class Main extends Application{

    public static void main(String[] args) {
        Database.create(new Attendee("j", "j", "123", new DateTime("16/02/2008"),FEMALE, Integer.parseInt("17"),
                "address",Double.parseDouble("50.5"), "standup comedy", "plays","workshops"));
        Database.create(new Organizer("k","k","123",new DateTime("28/11/2007"),FEMALE,Double.parseDouble("550.6")));
        Database.create(new Admin("e","e","123", new DateTime("01/01/0001"), Gender.MALE, "role", "9 to 5"));
        Database.create(new Category("standup comedy"));
        Database.create(new Category("plays"));
        Database.create(new Category("workshops"));
        Database.create(new Event("champions", Database.findCat("plays"),new Room() , (Organizer) Database.findUser("k", "123"),12.5, new DateTime("16/02/2008") ));
        Database.create(new Event("weeee", Database.findCat("plays"),new Room() , (Organizer) Database.findUser("k", "123"),12.5, new DateTime("16/02/2008") ));
        Database.create(new Event("reeee", Database.findCat("plays"),new Room() , (Organizer) Database.findUser("k", "123"),12.5, new DateTime("16/02/2008") ));
        Database.create(new Event("seeeeeee", Database.findCat("plays"),new Room() , (Organizer) Database.findUser("k", "123"),12.5, new DateTime("16/02/2008") ));
        Database.create(new Event("noice", Database.findCat("plays"),new Room() , (Organizer) Database.findUser("k", "123"),12.5, new DateTime("16/02/2008") ));


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