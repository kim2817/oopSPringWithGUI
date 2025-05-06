package frontend;

import BackEnd.*;
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
    Label lbl(String s , double top , double left){

        Label Dname = new Label(s);
        AnchorPane.setTopAnchor(Dname,top);
        AnchorPane.setLeftAnchor(Dname, left);



        return Dname;
    }

    Scene show() {
        String name = "dasddsa";
        AnchorPane layout = new AnchorPane();
        layout.setPadding(new Insets(10));


        layout.getChildren().add(lbl("Test",100.0,100.0));





        Scene s = new Scene(layout , 500,500);

    return s;
    }
}
