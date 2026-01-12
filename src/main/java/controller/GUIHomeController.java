package controller;

import dao.FactoryDao;
import factory.GraphicalFactory;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.layout.AnchorPane;
import com.jfoenix.controls.JFXButton;
import javafx.stage.Stage;

import java.io.IOException;

public class GUIHomeController extends  HomeController{

    @FXML
    private AnchorPane dinamicContentPane;

    @FXML
    private JFXButton newReportButton;

    @FXML
    private JFXButton myReportsButton;

    @FXML
    private JFXButton eventsButton;


    @FXML
    private JFXButton myAccButton;

    @FXML
    private JFXButton homeButton;

    @FXML
    public void initialize() {
        loadHome();
    }

    @FXML
    public void loadHome(){

        SceneManager.switchScene(
                dinamicContentPane,
                "/fxml/homeDashboard-view.fxml",
                null,
               null
        );
    }




    @Override
    @FXML
    public void onNewReport() {

        ChooseMunicipalityController controller =
                GraphicalFactory.getInstance().CreateMunicipalityController();
        SceneManager.switchScene(
                dinamicContentPane,
                "/fxml/ChooseAMunicipality-view.fxml",
                controller,
                "startSearch"
        );

    }





    @FXML
    public void showMyAcc() {

        MyAccController controller = GraphicalFactory.getInstance().createMyAccController();
        SceneManager.switchScene(
                dinamicContentPane,
                "/fxml/MyAcc-view.fxml",
                controller,
                "startMyAcc"
        );

    }

    @FXML
    public void showMyReports() {
        MyAccController controller = GraphicalFactory.getInstance().createMyAccController();
        SceneManager.switchScene(
                dinamicContentPane,
                "/fxml/reportsMunicipality-view.fxml",
                controller,
                "startRegister"
        );



    }


}