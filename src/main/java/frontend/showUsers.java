package frontend;

import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class showUsers {
    public static void show(){
        Stage stage = new Stage();
        Pane pane = new Pane();
        Scene scene = new Scene(pane);
        stage.setScene(scene);
        stage.show();
    }
}