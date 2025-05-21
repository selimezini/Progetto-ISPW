package Controller;

import Beans.LoginBean;
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
        String username = this.txtUsername.getText();
        String password = this.txtPassword.getText();
        String municipalCode = this.txtMunicipalCode.getText();
        boolean isEmployee = this.txtAmIEmployee.isSelected();
        String role = "";

        if(isEmployee == true) {
            role = "employee";
        }
        // Validazione
        if (username == null || username.isEmpty() ||
                password == null || password.isEmpty() ||
                (isEmployee && (municipalCode == null || municipalCode.isEmpty()))) {

            lblError.setText("Non ci possono essere campi vuoti. Perfavore riprovare.");
            return;
        }

        // Se è tutto ok, creo e riempio il bean
        LoginBean loginBean = new LoginBean(username,password,municipalCode,role);
        LoginController loginController = new LoginController();
        try{
            loginController.authenticateUser(loginBean);
            SceneManager.changeScene("/fxml/home-view.fxml","CivisAlert-Home");

        }catch(ApplicationException e){
            lblError.setText(e.getMessage());
        }


    }


    public void setReportController(ReportController reportController) {
        this.reportController = reportController;
    }




    @Override
    public void  register() {

        SceneManager.changeScene("/fxml/register-view.fxml","CivisAlert-Register");

    }
}





