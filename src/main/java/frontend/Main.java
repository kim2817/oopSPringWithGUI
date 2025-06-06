package frontend;

import BackEnd.*;
import javafx.application.Application;
import javafx.stage.Stage;

import java.io.File;

import static BackEnd.Gender.FEMALE;

public class Main extends Application{

    public static void main(String[] args) {
        Database.scanInput(new File("DataToInput.txt"));
        Database.create(new Category("standup comedy"));
        Database.create(new Attendee("j", "j", "123", new DateTime("16/02/2008"),FEMALE, Integer.parseInt("17"),
                "address",Double.parseDouble("50.5"), "standup comedy", "plays","workshops"));
        Database.create(new Organizer("k","k","123",new DateTime("28/11/2007"),FEMALE,Double.parseDouble("550.6")));
        Database.create(new Admin("e","e","123", new DateTime("01/01/0001"), Gender.MALE, "role", "9 to 5"));
        Database.create(new Event("champions", Database.findCat("plays"),new Room() , (Organizer) Database.findUser("k", "123"),12.5, new DateTime("16/02/2008") ));
        Database.create(new Event("weeee", Database.findCat("plays"),new Room() , (Organizer) Database.findUser("k", "123"),12.5, new DateTime("16/02/2008") ));
        Database.create(new Event("reeee", Database.findCat("plays"),new Room() , (Organizer) Database.findUser("k", "123"),12.5, new DateTime("16/02/2008") ));
        Database.create(new Event("seeeeeee", Database.findCat("plays"),new Room() , (Organizer) Database.findUser("k", "123"),12.5, new DateTime("16/02/2008") ));
        Database.create(new Event("noice", Database.findCat("plays"),new Room() , (Organizer) Database.findUser("k", "123"),12.5, new DateTime("16/02/2008") ));
        Database.create(new Attendee("j", "j", "123", new DateTime("16/02/2008"),FEMALE, Integer.parseInt("17"),
                "address",Double.parseDouble("50.5"), "standup comedy", "plays","workshops"));
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {
        RegisterLogin.show();

    }
}

