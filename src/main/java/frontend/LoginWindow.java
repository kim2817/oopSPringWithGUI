package frontend;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
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
            String user = fieldUser.getText();
            String pass = fieldPass.getText();
            System.out.println("Logging in. . .");
        });

        grid.setPadding(new Insets(20));

        StackPane root = new StackPane(grid);

        Scene scene = new Scene(root, 400, 300);

        stage.setScene(scene);
        stage.show();

    }
}
