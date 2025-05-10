package frontend;

import BackEnd.Category;
import BackEnd.Database;
import javafx.beans.binding.Bindings;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
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
        Label wnabyCreateAcc = new Label("Create a New Account");
        wnabyCreateAcc.getStyleClass().add("h1");


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

        Label messageLabel = new Label("");




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
        int hgap=10;
        VBox hb1= new VBox(hgap, role,Organizer,Attendee );
        VBox hb2= new VBox(hgap,usernameLabel,usernameField );
        VBox hb3= new VBox(hgap, emailLabel, emailField);
        VBox hb4= new VBox(hgap,passwordLabel, passwordField);
        VBox hb5= new VBox(hgap,confirmLabel, confirmField);
        VBox hb51= new VBox(messageLabel);
        VBox hb6= new VBox(hgap,genderLabel,genderBox);
        VBox hb7= new VBox(hgap,dobLabel,dobPicker);
        VBox hb8= new VBox(hgap,balanceLabel,balanceField);
        VBox hb9= new VBox(hgap,attendeeExtraBox);

        Button registerBtn = new Button("Register");
        Button cancelBtn = new Button("Cancel");
        HBox hb10= new HBox(hgap,registerBtn,cancelBtn);

        VBox vb= new VBox(10,wnabyCreateAcc,hb1,hb2,hb3,hb4,hb5,hb6,hb7,hb8,hb9,hb51,hb10);
        vb.setAlignment(Pos.CENTER);
        vb.setPadding(new Insets(20));
        vb.setAlignment(Pos.CENTER);
        vb.setMaxWidth(450);

        VBox Moraba3Eswed = new VBox(vb);
        Moraba3Eswed.setMaxWidth(500);
        Moraba3Eswed.setAlignment(Pos.CENTER);
        Moraba3Eswed.setStyle("-fx-background-color: white; -fx-background-radius: 20; -fx-padding: 30; "
                + "-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.1), 10, 0.3, 0, 4);");

        StackPane centerHebabPane = new StackPane(Moraba3Eswed);
        centerHebabPane.setAlignment(Pos.CENTER);
        centerHebabPane.getStyleClass().add("lilacSquare-panel");

        ScrollPane scrollPane = new ScrollPane(centerHebabPane);
        scrollPane.setFitToWidth(true);
        scrollPane.setFitToHeight(true);




        registerBtn.setDisable(true);



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
            try {
                boolean flag = true;

                String username = usernameField.getText();
                String password = passwordField.getText();
                String confirm = confirmField.getText();
                String balance = balanceField.getText();

                if (username.contains(" ")) {
                    messageLabel.setText("Username can't contain spaces.");
                } else if(Database.checkUser(username)){
                    messageLabel.setText("Username already found.");
                } else if (!password.equals(confirm)) {
                    messageLabel.setText("Passwords do not match.");
                } else if (password.length() < 8) {
                    messageLabel.setText("Password must be at least 8 characters.");
                } else if (Double.parseDouble(balance) <= 0) {
                    messageLabel.setText("Balance should be +ve.");
                } else {
                    flag = false;
                }
                if (flag) return;
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

                if (Organizer.isSelected()) {
                    registerOrganizer(emailField.getText(), usernameField.getText(), passwordField.getText(), dobPicker.getValue().format(formatter), female.isSelected(), balanceField.getText());
                } else {
                    flag = true;
                    String age = ageField.getText();
                    String address = ageField.getText();
                    if(age.isEmpty() || address.isEmpty()){
                        messageLabel.setText("Age/Address can't be empty.");
                    }
                    else if(CatsCombo1.getValue() == null || CatsCombo2.getValue() == null || CatsCombo3.getValue() == null){
                        messageLabel.setText("Choose your interests");
                    }
                    else if(Integer.parseInt(age) <= 0){
                        messageLabel.setText("Age should be +ve");
                    }
                    else flag=false;
                    if(flag) return;
                    registerAttendee(emailField.getText(), usernameField.getText(), passwordField.getText(), dobPicker.getValue().format(formatter), female.isSelected(), ageField.getText(), cityField.getText(), balanceField.getText(), CatsCombo1.getValue(), CatsCombo2.getValue(), CatsCombo3.getValue());
                }
                Stage currentStage = (Stage) registerBtn.getScene().getWindow();
                currentStage.close();
                LoginWindow.show();
            } catch (RuntimeException ex) {
                messageLabel.setText("Invalid Input");
            }
        });

        cancelBtn.setOnAction(e -> {
            stage.close();
            RegisterLogin.show();
        });

        Scene scene = new Scene(scrollPane, 800, 450);
        scene.getStylesheets().add(LoginWindow.class.getResource("/styles.css").toExternalForm());


        stage.setResizable(false);

        stage.setScene(scene);
        stage.show();
        stage.setScene(scene);
        stage.show();




    }
}
