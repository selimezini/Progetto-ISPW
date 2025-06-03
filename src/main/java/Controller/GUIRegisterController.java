package Controller;

import Beans.LoginBean;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXCheckBox;
import exceptions.ApplicationException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ResourceBundle;

public class GUIRegisterController extends RegisterController implements Initializable {

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
        LoginBean bean = new LoginBean(user, pass, isEmp ? "employee" : "citizen", code);

        try {
            // Invoco il controller applicativo (supponendo esista un RegisterController non-grafico)
            //RegisterController appCtrl = /* es. new ApplicationRegisterController() */;
           // appCtrl.register(bean);

            // Dopo il successo, torno al login
            SceneManager.changeScene("/fxml/login-view.fxml", "CivisAlert - Login");

        } catch (ApplicationException e) {
            // Se fallisce, mostro l'errore
            lblRegError.setText(e.getMessage());
        }
    }

    /** Invocato da onAction="#handleRegister" */
    @FXML
    private void handleRegister(ActionEvent event) {
        // Faccio il vero lavoro nel metodo astratto register()
        register();
    }

    /** Invocato da onAction="#handleBackToLogin" */
    @FXML
    private void handleBackToLogin() {
        SceneManager.changeScene("/fxml/login-view.fxml", "CivisAlert - Login");
    }
}
