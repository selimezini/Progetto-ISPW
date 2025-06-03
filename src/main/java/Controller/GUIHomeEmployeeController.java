package Controller;

import Controller.HomeEmployeeController;
import Factory.GraphicalFactory;
import com.jfoenix.controls.JFXButton;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;

public class GUIHomeEmployeeController extends HomeEmployeeController  {

    @FXML
    private AnchorPane dynamicAnchorPane;

    @FXML
    private JFXButton homeButton;

    @FXML
    private JFXButton newEventButton;

    @FXML
    private JFXButton showEventsButton;

    @FXML
    private JFXButton viewReportsButton;



    @FXML
    public void initialize(){
        loadHome();
    }

    @FXML
    public void showHome() {
        loadHome();
    }






    @Override
    @FXML
    public void showReports() {

        loadIntoContentPane("/fxml/reportsMunicipality-view.fxml");

    }

    @Override
    @FXML
    public void addEvent() {

    }


    private void loadIntoContentPane(String fxmlPath) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlPath));

            // Se il controller da caricare è ChooseAMunicipality-view.fxml
            if (fxmlPath.endsWith("reportsMunicipality-view.fxml")) {
                GraphicalFactory factory = GraphicalFactory.getInstance();
                 ShowReportsController  controller = factory.createShowReportsController();

                loader.setController(controller); // Imposta controller creato dalla factory
            }

            Parent pane = loader.load();

            dynamicAnchorPane.getChildren().setAll(pane);
            AnchorPane.setTopAnchor(pane, 0.0);
            AnchorPane.setBottomAnchor(pane, 0.0);
            AnchorPane.setLeftAnchor(pane, 0.0);
            AnchorPane.setRightAnchor(pane, 0.0);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void loadHome(){
        try {
            Parent pane = FXMLLoader
                    .load(getClass().getResource("/fxml/homeEmployeeDashboard-view.fxml"));
            dynamicAnchorPane.getChildren().setAll(pane);
            AnchorPane.setTopAnchor(pane,    0.0);
            AnchorPane.setBottomAnchor(pane, 0.0);
            AnchorPane.setLeftAnchor(pane,   0.0);
            AnchorPane.setRightAnchor(pane,  0.0);
        } catch (IOException e) {
            e.printStackTrace();
        }




    }

}
