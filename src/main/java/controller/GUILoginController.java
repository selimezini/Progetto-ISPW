package controller;

import beans.LoginBean;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXCheckBox;
import exceptions.ApplicationException;
import exceptions.ValidationException;
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
    public void initialize() {
        txtMunicipalCode.setDisable(true);

        txtAmIEmployee.selectedProperty().addListener((obs, wasSelected, isSelected) -> {
            txtMunicipalCode.setDisable(!isSelected);
        });
    }

    @Override
    @FXML
    public void login() {
        try {

            LoginBean loginBean = new LoginBean(
                    txtUsername.getText(),
                    txtPassword.getText(),
                    txtAmIEmployee.isSelected() ? "Employee" : "Citizen",
                    txtMunicipalCode.getText()
            );


            loginBean.validate();


            new LoginController().authenticateUser(loginBean);


            if (txtAmIEmployee.isSelected()) {
                SceneManager.switchScene(
                        loginPane,
                        "/fxml/homeEmployee-view.fxml",
                        GraphicalFactory.getInstance().createHomeEmployeeController(),
                        "loadHome"
                );
            } else {
                SceneManager.switchScene(
                        loginPane,
                        "/fxml/home-view.fxml",
                        GraphicalFactory.getInstance().createHomeController(),
                        "loadHome"
                );
            }

        } catch (ValidationException ve) {

            lblError.setText(ve.getMessage());

        } catch (ApplicationException ae) {

            lblError.setText(ae.getMessage());
        }
    }


    @Override
    public void  register() {
        RegisterController controller = GraphicalFactory.getInstance().createRegisterController();
        SceneManager.switchScene(
                loginPane,
                "/fxml/register-view.fxml",
                controller,
                "startRegister"
        );

    }
}





