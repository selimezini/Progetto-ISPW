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
    public void loadHome(){

        SceneManager.switchScene(
                DinamicContentPane,                        // **il tuo AnchorPane di “placeholder”**
                "/fxml/homeDashboard-view.fxml",     // il FXML da caricare dentro
                null,                                // il controller custom
               null                             // (opzionale) metodo di init da invocare
        );
    }




    @Override
    @FXML
    public void onNewReport() {

       // loadIntoContentPane("/fxml/ChooseAMunicipality-view.fxml");
        ChooseMunicipalityController controller =
                GraphicalFactory.getInstance().CreateMunicipalityController();
        SceneManager.switchScene(
                DinamicContentPane,                        // **il tuo AnchorPane di “placeholder”**
                "/fxml/ChooseAMunicipality-view.fxml",     // il FXML da caricare dentro
                controller,                                // il controller custom
                "startSearch"                              // (opzionale) metodo di init da invocare
        );

    }





    @FXML
    public void showMyAcc() {
        //loadIntoContentPane("/fxml/MyAcc-view.fxml");
       // loadIntoContentPane("/fxml/MyAcc-view.fxml");
        MyAccController controller = GraphicalFactory.getInstance().createMyAccController();
        SceneManager.switchScene(
                DinamicContentPane,
                "/fxml/MyAcc-view.fxml",
                controller,
                "startMyAcc"          // metodo di GUIHomeController che inizializza la vista
        );

    }

    @FXML
    public void showMyReports() {
       // loadIntoContentPane("/fxml/reportsMunicipality-view.fxml");
        MyAccController controller = GraphicalFactory.getInstance().createMyAccController();
        SceneManager.switchScene(
                DinamicContentPane,
                "/fxml/reportsMunicipality-view.fxml",
                controller,
                "startRegister"          // metodo di GUIHomeController che inizializza la vista
        );



    }


}