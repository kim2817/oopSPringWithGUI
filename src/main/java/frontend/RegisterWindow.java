package frontend;

import BackEnd.Category;
import javafx.beans.binding.Bindings;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.time.format.DateTimeFormatter;

import static BackEnd.Entrance.registerAttendee;
import static BackEnd.Entrance.registerOrganizer;

public class RegisterWindow {
    public static void show(){
        //stage
        Stage stage = new Stage();
        stage.setTitle("Eventra Registration");

        //icon
        Image icon = new Image("Logo.png");
        stage.getIcons().add(icon);

        //role radiobutton
        Label role = new Label("Role");
        RadioButton Organizer = new RadioButton("Organizer");
        RadioButton Attendee = new RadioButton("Attendee");

        ToggleGroup Roles = new ToggleGroup();
        Organizer.setToggleGroup(Roles);
        Attendee.setToggleGroup(Roles);

        HBox roleGaps = new HBox(10, role, Organizer, Attendee);

        //username
        Label usernameLabel = new Label("Username:");
        TextField usernameField = new TextField();

        //email
        Label emailLabel = new Label("Email:");
        TextField emailField = new TextField();

        //password
        Label passwordLabel = new Label("Password:");
        PasswordField passwordField = new PasswordField();

        //confirm password
        Label confirmLabel = new Label("Confirm Password:");
        PasswordField confirmField = new PasswordField();

        Label messageLabel = new Label();




        //gender radiobutton
        Label genderLabel = new Label("Gender:");
        RadioButton male = new RadioButton("Male");
        RadioButton female = new RadioButton("Female");
        ToggleGroup genderChoice = new ToggleGroup();
        male.setToggleGroup(genderChoice);
        female.setToggleGroup(genderChoice);
        HBox genderBox = new HBox(10, male, female);

        //date of birth
        Label dobLabel = new Label("Date of Birth:");
        DatePicker dobPicker = new DatePicker();

        //enter balance
        Label balanceLabel = new Label("Wallet Balance:");
        TextField balanceField = new TextField();

        //attendee fields only!!!!!!! -------------------------------------
        Label citylabel = new Label("Address:");
        TextField cityField = new TextField();

        Label agelabel = new Label("Age:");
        TextField ageField = new TextField();

        //intrests
        ObservableList<String> items = FXCollections.observableArrayList(Category.listAllCategories());
        ComboBox<String> CatsCombo1 = new ComboBox<>();
        CatsCombo1.setPromptText("Select Interest 1");
        CatsCombo1.getItems().addAll(items);

        ComboBox<String> CatsCombo2 = new ComboBox<>();
        CatsCombo2.setPromptText("Select interest 2");
        CatsCombo2.getItems().addAll(items);

        ComboBox<String> CatsCombo3 = new ComboBox<>();
        CatsCombo3.setPromptText("Select interest 3 ");
        CatsCombo3.getItems().addAll(items);

        //collecting all attendee only data
        VBox attendeeExtraBox = new VBox(10);
        attendeeExtraBox.getChildren().addAll(
                citylabel, cityField,
                agelabel, ageField,
                CatsCombo1, CatsCombo2, CatsCombo3
        );


        attendeeExtraBox.setVisible(false);
        attendeeExtraBox.setManaged(false);

        // to show extra field when attendee only
        Roles.selectedToggleProperty().addListener((obs, oldToggle, newToggle) -> {
            if (newToggle != null) {
                RadioButton chose = (RadioButton) newToggle;
                boolean isAttendee = chose == Attendee;

                attendeeExtraBox.setVisible(isAttendee);
                attendeeExtraBox.setManaged(isAttendee);
            }
        });

        //Alligning stuff
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

        grid.add(messageLabel, 0, 8, 3, 1);

        grid.add(registerBtn, 1, 10);
        grid.add(cancelBtn, 2, 10);


        registerBtn.setDisable(true); // initially disabled



        registerBtn.disableProperty().bind(
                Bindings.createBooleanBinding(() ->
                                passwordField.getText().isEmpty()
                                        || confirmField.getText().isEmpty()
                                        || usernameField.getText().isEmpty() || emailField.getText().isEmpty()
                        || (!male.isSelected() && !female.isSelected()) || (!Attendee.isSelected() && !Organizer.isSelected())
                        || (dobPicker.getValue() == null) || balanceField.getText().isEmpty(),
                        passwordField.textProperty(),
                        confirmField.textProperty(),Roles.selectedToggleProperty()
                        ,usernameField.textProperty(), emailField.textProperty(), balanceField.textProperty(),dobPicker.valueProperty(),genderChoice.selectedToggleProperty()

                )
        );

        registerBtn.setOnAction(e -> {
            String password = passwordField.getText();
            String confirm = confirmField.getText();


            if (!password.equals(confirm)) {
                messageLabel.setText("Passwords do not match.");
            } else if (password.length() < 8) {
                messageLabel.setText("Password must be at least 8 characters.");
            } else {
                messageLabel.setText("Password is valid!");
            }
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            String formattedDob = dobPicker.getValue().format(formatter);

            if(Organizer.isSelected()){
                registerOrganizer(emailField.getText(),usernameField.getText(),passwordField.getText(), dobPicker.getValue().format(formatter), female.isSelected(), balanceField.getText());

            }
            else{
                registerAttendee(emailField.getText(),usernameField.getText(),passwordField.getText(), dobPicker.getValue().format(formatter), female.isSelected(), ageField.getText(), cityField.getText(), balanceField.getText(), CatsCombo1.getValue(),CatsCombo2.getValue(),CatsCombo3.getValue());
            }
            Stage currentStage = (Stage) registerBtn.getScene().getWindow();
            currentStage.close();
            LoginWindow.show();

        });

        cancelBtn.setOnAction(e -> {
            stage.close();
            RegisterLogin.show();
        });




        ScrollPane scrollPane = new ScrollPane(grid);
        scrollPane.setFitToWidth(true);

        grid.setPadding(new Insets(20));

        Scene scene = new Scene(scrollPane, 500, 700);


        stage.setScene(scene);
        stage.show();
    }
}
