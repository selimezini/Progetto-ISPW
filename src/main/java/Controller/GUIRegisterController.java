package Controller;

import Beans.LoginBean;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXCheckBox;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Controller per register-view.fxml
 */
public class GUIRegisterController implements Initializable {

    @FXML private TextField regUsername;
    @FXML private TextField regPassword;
    @FXML private JFXCheckBox regEmployeeCheck;
    @FXML private TextField regMunicipalCode;
    @FXML private Label lblRegError;
    @FXML private JFXButton confirmRegButton;
    @FXML private JFXButton backButton;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // Disabilita il campo codice comune finché non si seleziona checkbox
        regMunicipalCode.setDisable(true);
        regEmployeeCheck.selectedProperty().addListener((obs, oldV, newV) -> {
            regMunicipalCode.setDisable(!newV);
            if (!newV) regMunicipalCode.clear();
        });
    }

    /** Invocato da onAction="#handleRegister" */
    @FXML
    private void handleRegister(ActionEvent event) {
        String user = regUsername.getText().trim();
        String pass = regPassword.getText().trim();
        boolean isEmp = regEmployeeCheck.isSelected();
        String code = regMunicipalCode.getText().trim();

        if (user.isEmpty() || pass.isEmpty() || (isEmp && code.isEmpty())) {
            lblRegError.setText("Compila tutti i campi richiesti.");
            return;
        }

        LoginBean bean = new LoginBean(user, pass, isEmp ? "employee" : "citizen", code);
        // TODO: passa `bean` al controller applicativo per la registrazione

        // dopo registrazione, torna al login
        SceneManager.changeScene("/fxml/login-view.fxml", "CivisAlert - Login");
    }

    /** Invocato da onAction="#handleBackToLogin" */
    @FXML
    private void handleBackToLogin(ActionEvent event) {
        SceneManager.changeScene("/fxml/login-view.fxml", "CivisAlert - Login");
    }
}

