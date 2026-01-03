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


        ShowReportsController controller = GraphicalFactory.getInstance().createShowReportsController();
        SceneManager.switchScene(
                dynamicAnchorPane,                        
                "/fxml/reportsMunicipality-view.fxml",
                controller,
                null
        );


    }

    @Override
    @FXML
    public void addEvent() {
        // non implementato
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


        MyAccController controller = GraphicalFactory.getInstance().createMyAccController();
        SceneManager.switchScene(
                dynamicAnchorPane,
                "/fxml/MyAcc-view.fxml",
                controller,
                "startMyAcc"
        );

    }






}
