module oopSpring25GUI {
    requires javafx.fxml;
    requires javafx.controls;
    requires java.naming;
    requires java.sql;

    opens frontend to javafx.fxml;
    exports frontend;
}
