module oopSpring25GUI {
    requires javafx.fxml;
    requires javafx.controls;
    requires java.naming;
    requires java.sql;
    requires java.desktop;

    opens frontend to javafx.fxml;
    exports frontend;
}
