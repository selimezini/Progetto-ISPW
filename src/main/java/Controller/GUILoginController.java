package Controller;

import Beans.LoginBean;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXCheckBox;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class GUILoginController implements LoginGraphicController {

    @FXML
    private JFXButton loginButton;

    @FXML
    private JFXButton regButton;

    @FXML
    private JFXCheckBox txtAmIEmployee;

    @FXML
    private PasswordField txtMunicipalCode;

    @FXML
    private PasswordField txtPassword;

    @FXML
    private TextField txtUsername;

    @FXML
    private Label lblError;

    @FXML
    void handleLogin(ActionEvent event) {
        // Qui chiami il controller applicativo
        LoginBean loginBean = login(); // <--- usa il tuo metodo

        if (loginBean == null) {
            // Vuol dire che c'erano errori, e hai già settato il messaggio di errore
            return;
        }

        // Qui invochi il controller applicativo, passando il Bean
        LoginController appLoginController = new LoginController();
        appLoginController.login(loginBean);
    }

    @FXML
    public void initialize() {
        // Disabilita inizialmente il campo codice
        txtMunicipalCode.setDisable(true);

        // Listener per abilitare/disabilitare in base alla checkbox
        txtAmIEmployee.selectedProperty().addListener((obs, wasSelected, isSelected) -> {
            txtMunicipalCode.setDisable(!isSelected);
        });
    }

    @Override
    public LoginBean login() {
        String username = this.txtUsername.getText();
        String password = this.txtPassword.getText();
        String municipalCode = this.txtMunicipalCode.getText();
        boolean isEmployee = this.txtAmIEmployee.isSelected();

        // Validazione
        if (username == null || username.isEmpty() ||
                password == null || password.isEmpty() ||
                (isEmployee && (municipalCode == null || municipalCode.isEmpty()))) {

            lblError.setText("Non ci possono essere campi vuoti. Perfavore riprovare.");
            return null;
        }

        // Se è tutto ok, creo e riempio il bean
        LoginBean loginBean = new LoginBean();
        loginBean.setUsername(username);
        loginBean.setPassword(password);
        loginBean.setRole(isEmployee);
        if (isEmployee) {
            loginBean.setMunicipalityCode(municipalCode);
        }

        return loginBean;
    }

    @Override
    public LoginBean register() {
        return null; // Per ora
    }
}





