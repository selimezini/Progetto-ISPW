package controller;

import beans.LoginBean;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXCheckBox;
import exceptions.ApplicationException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class GUILoginController extends GraphicLoginController  {

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

    private ReportController reportController;


    @FXML
    void handleLogin(ActionEvent event) {

        login();


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
    public void login() {
        String username        = txtUsername.getText();
        String password        = txtPassword.getText();
        String municipalCode   = txtMunicipalCode.getText();
        boolean isEmployee     = txtAmIEmployee.isSelected();

        // assegno sempre role, non solo in caso di dipendente
        String role = isEmployee ? "Employee" : "Citizen";

        // validazione dei campi
        if (username == null || username.isEmpty() ||
                password == null || password.isEmpty() ||
                (isEmployee && (municipalCode == null || municipalCode.isEmpty()))) {
            lblError.setText("Non ci possono essere campi vuoti. Per favore riprova.");
            return;
        }

        // creo il bean e lo passo al controller
        LoginBean loginBean = new LoginBean(username, password, role, municipalCode);
        System.out.println( "GUILOgin:" + role);
        LoginController loginController = new LoginController();
        try {
            loginController.authenticateUser(loginBean);

            // se è dipendente
            if (isEmployee) {
                SceneManager.changeScene("/fxml/homeEmployee-view.fxml", "CivisAlertStaff-Home");
            } else {
                SceneManager.changeScene("/fxml/home-view.fxml", "CivisAlert-Home");
            }

        } catch (ApplicationException e) {
            lblError.setText(e.getMessage());
        }
    }



    @Override
    public void  register() {

        SceneManager.changeScene("/fxml/register-view.fxml","CivisAlert-Register");

    }
}





