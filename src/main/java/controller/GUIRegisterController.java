package controller;

import beans.LoginBean;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXCheckBox;
import exceptions.ApplicationException;
import exceptions.DataLoadException;
import exceptions.ValidationException;
import factory.GraphicalFactory;
import javafx.animation.PauseTransition;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class GUIRegisterController extends RegisterController  {

    @FXML
    private TextField regUsername;

    @FXML
    private TextField regPassword;

    @FXML
    private JFXCheckBox regEmployeeCheck;

    @FXML
    private TextField regMunicipalCode;

    @FXML
    private Label lblRegError;

    @FXML
    private JFXButton confirmRegButton;

    @FXML
    private JFXButton backButton;

    @FXML
    private Label successLbl;


    @FXML
    private AnchorPane registerPane;

    @FXML
    public void startRegister(){
        regMunicipalCode.setDisable(true);
        regEmployeeCheck.selectedProperty().addListener((obs, oldV, newV) -> {
            regMunicipalCode.setDisable(!newV);
            if (!newV) {
                regMunicipalCode.clear();
            }
        });
    }


    @Override
    public void register() {
        // 1) costruisco il bean
        String user = regUsername.getText().trim();
        String pass = regPassword.getText().trim();
        boolean isEmp = regEmployeeCheck.isSelected();
        String code = regMunicipalCode.getText().trim();

        LoginBean bean = new LoginBean(
                user,
                pass,
                isEmp ? "Employee" : "Citizen",
                code
        );

        try {

            bean.validate();


            new LoginController().registerUser(bean);


            successLbl.setText("Registrazione avvenuta con successo");


            PauseTransition pause = new PauseTransition(Duration.seconds(1));
            pause.setOnFinished(evt -> {
                regUsername.clear();
                regPassword.clear();
                regMunicipalCode.clear();
                regEmployeeCheck.setSelected(false);
                regMunicipalCode.setDisable(true);

                lblRegError.setText("");
                successLbl.setText("");
            });
            pause.play();

        } catch (ValidationException ve) {

            lblRegError.setText(ve.getMessage());
        } catch (ApplicationException ae) {
            lblRegError.setText(ae.getMessage());
        }
    }

    @FXML
    private void handleBackToLogin() {
        Stage stage = (Stage) registerPane.getScene().getWindow();

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/login-view.fxml"));

            GraphicalFactory factory = GraphicalFactory.getInstance();
            GraphicLoginController loginController = factory.createLoginController();
            loader.setController(loginController);

            Parent loginRoot = loader.load();
            stage.setScene(new Scene(loginRoot));
            stage.setTitle("CivisAlert – Login");
            stage.show();



        } catch (IOException e) {
            throw new DataLoadException("Impossibile caricare homeEmployeeDashboard-view.fxml", e);

        }
    }

}
