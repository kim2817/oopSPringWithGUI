package frontend;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.RadioButton;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class managerooms{
    public static void show(){
        Stage stage = new Stage();
        FlowPane pane = new FlowPane();
        pane.setPadding(new Insets(10));
        Scene scene = new Scene(pane, 300, 300);
        RadioButton roomChoice = new RadioButton("Room");
        RadioButton catChoice = new RadioButton("Category");

        pane.getChildren().addAll(roomChoice, catChoice);
        stage.setScene(scene);
        stage.show();
    }


}