package Controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.layout.AnchorPane;
import com.jfoenix.controls.JFXButton;

import java.io.IOException;

public class GUIHomeController {

    @FXML
    private AnchorPane DinamicContentPane;

    @FXML
    private JFXButton newReportButton;

    @FXML
    private JFXButton myReportsButton;

    @FXML
    private JFXButton EventsButton;

    @FXML
    private JFXButton myAccButton;

    @FXML
    public void initialize() {
        // (opzionale) carica una home iniziale
    }

    /** Collega in home-view.fxml: onAction="#onNewReport" */
    @FXML
    private void onNewReport() {
        loadIntoContentPane("/fxml/ChooseAMunicipality-view.fxml");
    }

    /** Metodo generico per caricare un FXML dentro DinamicContentPane */
    private void loadIntoContentPane(String fxmlPath) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlPath));
            Parent pane = loader.load();

            // Pulisce e inserisce
            DinamicContentPane.getChildren().setAll(pane);

            // Ancoraggi per riempire tutto lo spazio
            AnchorPane.setTopAnchor(pane, 0.0);
            AnchorPane.setBottomAnchor(pane, 0.0);
            AnchorPane.setLeftAnchor(pane, 0.0);
            AnchorPane.setRightAnchor(pane, 0.0);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
