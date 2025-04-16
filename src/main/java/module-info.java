module org.example.viewprova2 {
    requires javafx.fxml;

    requires com.dlsc.formsfx;
    requires com.jfoenix;
    requires javafx.controls;

    opens org.example.viewprova2 to javafx.fxml;
    exports org.example.viewprova2;
    exports Controller;
    opens Controller to javafx.fxml;
}