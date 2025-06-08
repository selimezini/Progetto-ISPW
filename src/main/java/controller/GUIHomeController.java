package controller;

import factory.GraphicalFactory;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.layout.AnchorPane;
import com.jfoenix.controls.JFXButton;

import java.io.IOException;

public class GUIHomeController extends  HomeController{

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
    private JFXButton homeButton;

    @FXML
    public void initialize() {
        loadHome();
    }

    @FXML
    private void loadHome(){
        try {
            Parent pane = FXMLLoader
                    .load(getClass().getResource("/fxml/homeDashboard-view.fxml"));
            DinamicContentPane.getChildren().setAll(pane);
            AnchorPane.setTopAnchor(pane,    0.0);
            AnchorPane.setBottomAnchor(pane, 0.0);
            AnchorPane.setLeftAnchor(pane,   0.0);
            AnchorPane.setRightAnchor(pane,  0.0);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }




    @Override
    @FXML
    public void onNewReport() {

        loadIntoContentPane("/fxml/ChooseAMunicipality-view.fxml");
    }



    private void loadIntoContentPane(String fxmlPath) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlPath));


            if (fxmlPath.endsWith("ChooseAMunicipality-view.fxml")) {
                GraphicalFactory factory = GraphicalFactory.getInstance();
                ChooseMunicipalityController controller = factory.CreateMunicipalityController();

                loader.setController(controller); // Imposta controller creato dalla factory
            }else if(fxmlPath.endsWith("Myacc-view.fxml")) {
                GraphicalFactory factory = GraphicalFactory.getInstance();
                MyAccController controller = factory.createMyAccController();
                loader.setController(controller);
            }else if(fxmlPath.endsWith("reportsMunicipality-view.fxml")){

                GUIUserShowReportsController contr = new GUIUserShowReportsController();
                loader.setController(contr);
            }

            Parent pane = loader.load();

            DinamicContentPane.getChildren().setAll(pane);
            AnchorPane.setTopAnchor(pane, 0.0);
            AnchorPane.setBottomAnchor(pane, 0.0);
            AnchorPane.setLeftAnchor(pane, 0.0);
            AnchorPane.setRightAnchor(pane, 0.0);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    @FXML
    public void showMyAcc() {
        loadIntoContentPane("/fxml/MyAcc-view.fxml");
    }

    @FXML
    public void showMyReports() {
        loadIntoContentPane("/fxml/reportsMunicipality-view.fxml");
    }


}