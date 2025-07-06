package controller;

import beans.LoginBean;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXCheckBox;
import exceptions.ApplicationException;
import exceptions.ValidationException;
import factory.GraphicalFactory;
import javafx.animation.PauseTransition;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.util.Duration;

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

            // 3) registra l’utente
            new LoginController().registerUser(bean);

            // 4) notifico successo
            successLbl.setText("Registrazione avvenuta con successo");

            // 5) dopo 1 sec pulisco i campi
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
            // mostra l’errore di validazione
            lblRegError.setText(ve.getMessage());
        } catch (ApplicationException ae) {
            lblRegError.setText(ae.getMessage());
        }
    }

    @FXML
    private void handleBackToLogin() {
        GraphicLoginController loginController = GraphicalFactory.getInstance().createLoginController();
        SceneManager.switchScene(
                registerPane,
                "/fxml/login-view.fxml",
                null,
               null
        );

    }
}
