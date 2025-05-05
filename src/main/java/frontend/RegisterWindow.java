package frontend;

import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class RegisterWindow {
    public static void show(){
        Stage stage = new Stage();
        stage.setTitle("Eventra Registration");

        Image icon = new Image("img.png");
        stage.getIcons().add(icon);

        Label role = new Label("Role");
        RadioButton Organizer = new RadioButton("Organizer");
        RadioButton Attendee = new RadioButton("Attendee");

        ToggleGroup Roles = new ToggleGroup();
        Organizer.setToggleGroup(Roles);
        Attendee.setToggleGroup(Roles);

        HBox roleGaps = new HBox(10, role, Organizer, Attendee);

        Label usernameLabel = new Label("Username:");
        TextField usernameField = new TextField();

        Label emailLabel = new Label("Email:");
        TextField emailField = new TextField();

        Label passwordLabel = new Label("Password:");
        PasswordField passwordField = new PasswordField();

        Label confirmLabel = new Label("Confirm Password:");
        PasswordField confirmField = new PasswordField();

        Label genderLabel = new Label("Gender:");
        RadioButton male = new RadioButton("Male");
        RadioButton female = new RadioButton("Female");
        ToggleGroup genderChoice = new ToggleGroup();
        male.setToggleGroup(genderChoice);
        female.setToggleGroup(genderChoice);
        HBox genderBox = new HBox(10, male, female);

        Label dobLabel = new Label("Date of Birth:");
        DatePicker dobPicker = new DatePicker();

        Label balanceLabel = new Label("Wallet Balance:");
        TextField balanceField = new TextField();

        //attendee fields only!!!!!!! -------------------------------------
        Label citylabel = new Label("City:");
        TextField cityField = new TextField();

        Label agelabel = new Label("Age:");
        TextField ageField = new TextField();

        ComboBox<String> CatsCombo1 = new ComboBox<>();
        CatsCombo1.setPromptText("Select Interest 1");
        CatsCombo1.getItems().addAll(Categories.listAllCategories());

        ComboBox<String> CatsCombo2 = new ComboBox<>();
        CatsCombo2.setPromptText("Select interest 2");
        CatsCombo2.getItems().addAll(Categories.listAllCategories());

        ComboBox<String> CatsCombo3 = new ComboBox<>();
        CatsCombo3.setPromptText("Select interest 3 ");
        CatsCombo3.getItems().addAll(Categories.listAllCategories());

        VBox attendeeExtraBox = new VBox(10);
        attendeeExtraBox.getChildren().addAll(
                citylabel, cityField,
                agelabel, ageField,
                CatsCombo1, CatsCombo2, CatsCombo3
        );
        attendeeExtraBox.setVisible(false);
        attendeeExtraBox.setManaged(false);

        Roles.selectedToggleProperty().addListener((obs, oldToggle, newToggle) -> {
            if (newToggle != null) {
                RadioButton chose = (RadioButton) newToggle;
                boolean isAttendee = chose == Attendee;

                attendeeExtraBox.setVisible(isAttendee);
                attendeeExtraBox.setManaged(isAttendee);
            }
        });
        GridPane grid = new GridPane();
        grid.setVgap(10);
        grid.setHgap(10);

        grid.add(role, 0, 0);
        grid.add(Organizer, 1, 0);
        grid.add(Attendee, 2, 0);

        grid.add(usernameLabel, 0, 1);
        grid.add(usernameField, 1, 1, 2, 1);

        grid.add(emailLabel, 0, 2);
        grid.add(emailField, 1, 2, 2, 1);

        grid.add(passwordLabel, 0, 3);
        grid.add(passwordField, 1, 3, 2, 1);

        grid.add(confirmLabel, 0, 4);
        grid.add(confirmField, 1, 4, 2, 1);

        grid.add(genderLabel, 0, 5);
        grid.add(genderBox, 1, 5, 2, 1);

        grid.add(dobLabel, 0, 6);
        grid.add(dobPicker, 1, 6, 2, 1);

        grid.add(balanceLabel, 0, 7);
        grid.add(balanceField, 1, 7, 2, 1);

        grid.add(attendeeExtraBox, 0, 8, 3, 1);

        Button registerBtn = new Button("Register");
        Button cancelBtn = new Button("Cancel");

        grid.add(registerBtn, 1, 9);
        grid.add(cancelBtn, 2, 9);


        registerBtn.setOnAction(e -> {
            System.out.println("Collecting data . . .");
        });

        cancelBtn.setOnAction(e -> {
            stage.close();
        });

        ScrollPane scrollPane = new ScrollPane(grid);
        scrollPane.setFitToWidth(true);

        grid.setPadding(new Insets(20));

        Scene scene = new Scene(scrollPane, 500, 700);

        stage.setScene(scene);
        stage.show();
    }
}
