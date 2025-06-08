module org.example.viewprova2 {
    requires javafx.fxml;

    requires com.dlsc.formsfx;
    requires com.jfoenix;
    requires javafx.controls;
    requires java.sql;


    opens org.example.viewprova2 to javafx.fxml;
    exports org.example.viewprova2;
    exports controller;
    opens controller to javafx.fxml;
}