module oopSpring25GUI {
    requires javafx.fxml;
    requires javafx.controls;

    opens frontend to javafx.fxml;
    exports frontend;
}
