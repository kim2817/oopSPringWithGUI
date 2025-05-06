package frontend;

import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.scene.control.TextField;
import javafx.scene.control.Label;

import java.awt.*;

public class showEvent {
    public static void show(){
        Stage stage = new Stage();
        Label label = new Label("Search: ");
        TextField search = new TextField();
        search.setText("Enter the event name");
        FlowPane pane = new FlowPane();
        pane.setAlignment(Pos.TOP_CENTER);

        pane.getChildren().addAll(label, search);
        Scene scene = new Scene(pane,800, 300);
        stage.setScene(scene);
        stage.show();

    }
}
