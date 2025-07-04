package controller;

import factory.GraphicalFactory;
import com.jfoenix.controls.JFXButton;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
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
    private JFXButton myAccButton;






    @Override
    @FXML
    public void showReports() {

        //loadIntoContentPane("/fxml/reportsMunicipality-view.fxml");
        ShowReportsController controller = GraphicalFactory.getInstance().createShowReportsController();
        SceneManager.switchScene(
                dynamicAnchorPane,                        // **il tuo AnchorPane di “placeholder”**
                "/fxml/reportsMunicipality-view.fxml",     // il FXML da caricare dentro
                controller,                                // il controller custom
                null                             // (opzionale) metodo di init da invocare
        );


    }

    @Override
    @FXML
    public void addEvent() {

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
            System.out.println(e.getMessage());
        }



    }

    @FXML
    public void showMyAcc() {

        //loadIntoContentPane("/fxml/myAcc-view.fxml");
        MyAccController controller = GraphicalFactory.getInstance().createMyAccController();
        SceneManager.switchScene(
                dynamicAnchorPane,
                "/fxml/MyAcc-view.fxml",
                controller,
                "startMyAcc"          // metodo di GUIHomeController che inizializza la vista
        );

    }






}
