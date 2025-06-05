package Controller;

import Beans.LoginBean;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXCheckBox;
import exceptions.ApplicationException;
import javafx.animation.PauseTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.util.Duration;

import java.net.URL;
import java.util.ResourceBundle;

public class GUIRegisterController extends RegisterController implements Initializable {

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

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // Disabilita il campo codice comune finché non si seleziona checkbox
        regMunicipalCode.setDisable(true);
        regEmployeeCheck.selectedProperty().addListener((obs, oldV, newV) -> {
            regMunicipalCode.setDisable(!newV);
            if (!newV) {
                regMunicipalCode.clear();
            }
        });
    }

    /** Metodo astratto ereditato da RegisterController */
    @Override
    public void register() {
        // Estraggo valori dai campi
        String user = regUsername.getText().trim();
        String pass = regPassword.getText().trim();
        boolean isEmp = regEmployeeCheck.isSelected();
        String code = regMunicipalCode.getText().trim();

        // Validazione
        if (user.isEmpty() || pass.isEmpty() || (isEmp && code.isEmpty())) {
            lblRegError.setText("Compila tutti i campi richiesti.");
            return;
        }

        // Preparo il bean per la registrazione
        LoginBean bean = new LoginBean(user, pass, isEmp ? "Employee" : "Citizen", code);

        try {
           LoginController loginController = new LoginController();
           loginController.registerUser(bean);
           successLbl.setText("Registrazione avvenuta con successo");
            PauseTransition pause = new PauseTransition(Duration.seconds(1));
            pause.setOnFinished(evt -> {
                SceneManager.changeScene("/fxml/login-view.fxml", "CivisAlert - Login");
            });
            pause.play();

        } catch (ApplicationException e) {
            // Se fallisce, mostro l'errore
            lblRegError.setText(e.getMessage());
        }
    }


    /** Invocato da onAction="#handleBackToLogin" */
    @FXML
    private void handleBackToLogin() {
        SceneManager.changeScene("/fxml/login-view.fxml", "CivisAlert - Login");
    }
}
