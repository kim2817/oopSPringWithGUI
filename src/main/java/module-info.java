module oopSpring25GUI {
    requires javafx.fxml;
    requires javafx.controls;
    requires java.naming;

    opens frontend to javafx.fxml;
    exports frontend;
}
