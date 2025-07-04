package controller;

import beans.LoginBean;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXCheckBox;
import exceptions.ApplicationException;
import factory.GraphicalFactory;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

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

    @FXML
    private AnchorPane loginPane;

    private ReportController reportController;


    @FXML
    void handleLogin(ActionEvent event) {

        login();


    }

    @FXML
    public void initialize() {
        txtMunicipalCode.setDisable(true);

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

        String role = isEmployee ? "Employee" : "Citizen";

        if (username == null || username.isEmpty() ||
                password == null || password.isEmpty() ||
                (isEmployee && (municipalCode == null || municipalCode.isEmpty()))) {
            lblError.setText("Non ci possono essere campi vuoti. Per favore riprova.");
            return;
        }

        LoginBean loginBean = new LoginBean(username, password, role, municipalCode);
        System.out.println( "GUILOgin:" + role);
        LoginController loginController = new LoginController();
        try {
            loginController.authenticateUser(loginBean);

            if (isEmployee) {
                Stage stage = (Stage) loginPane.getScene().getWindow();
                HomeEmployeeController homeController = GraphicalFactory.getInstance().createHomeEmployeeController();
                SceneManager.switchScene(
                        loginPane,
                        "/fxml/homeEmployee-view.fxml",
                        homeController,
                        "loadHome"          // metodo di GUIHomeController che inizializza la vista
                );
            } else {
                Stage stage = (Stage) loginPane.getScene().getWindow();
                HomeController homeController = GraphicalFactory.getInstance().createHomeController();
                SceneManager.switchScene(
                        loginPane,
                        "/fxml/home-view.fxml",
                        homeController,
                        "loadHome"          // metodo di GUIHomeController che inizializza la vista
                );
            }

        } catch (ApplicationException e) {
            lblError.setText(e.getMessage());
        }
    }



    @Override
    public void  register() {
        Stage stage = (Stage) loginPane.getScene().getWindow();
        RegisterController controller = GraphicalFactory.getInstance().createRegisterController();
        SceneManager.switchScene(
                loginPane,
                "/fxml/register-view.fxml",
                controller,
                "startRegister"          // metodo di GUIHomeController che inizializza la vista
        );

    }
}





