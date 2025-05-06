package frontend;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;


public class OraganizerUI extends Application {
    @Override
    public void start(Stage primaryStage) {
        // Get the scene from another class
        Scene scene =  new OrganizerUI().show();
        primaryStage.setResizable(false);
        primaryStage.setTitle("Decoupled JavaFX App");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}

class OrganizerUI {
    Scene show(String name , double balance , String ID) {

        AnchorPane layout = new AnchorPane();
        layout.setPadding(new Insets(10));
        Label Dname = new Label("Hello Mr/Ms " + name);
        AnchorPane.setTopAnchor(Dname,10.0);
        AnchorPane.setLeftAnchor(Dname, 10.0);
        layout.getChildren().add(Dname);



        Scene s = new Scene(layout , 500,500);

    return s;
    }
}
