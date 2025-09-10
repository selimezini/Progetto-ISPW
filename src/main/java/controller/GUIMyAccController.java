package controller;

import beans.LoginBean;
import exceptions.ApplicationException;
import factory.GraphicalFactory;
import javafx.animation.PauseTransition;
import javafx.beans.binding.Bindings;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.util.Duration;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXCheckBox;
import org.example.viewprova2.session.SessionManager;

import java.io.IOException;


public class GUIMyAccController extends MyAccController {

    @FXML
    private TextField     myUsername;
    @FXML
    private JFXCheckBox   changeUsername;

    @FXML
    private TextField     newUsername;

    @FXML
    private PasswordField cleanPassword;

    @FXML
     private TextField     passwordField;

    @FXML
    private JFXCheckBox   showPassword;


    @FXML
    private JFXCheckBox   changePassword;

    @FXML
    private PasswordField newPassword;

    @FXML
    private JFXButton     confirmChangesButton;
    @FXML
    private JFXButton     exitButton;

    @FXML
    private AnchorPane dynamicContentPane;

    @FXML
    private Label errorLbl;

    @FXML
    private Label successLbl;



    private final LoginController loginController = new LoginController();

    @FXML
    @Override
    public void startMyAcc() {

        LoginBean currentBean = loginController.getUserCredentials();
        String currentUser = (currentBean != null ? currentBean.getUsername() : "");
        String currentPass = (currentBean != null ? currentBean.getPassword() : "");

        myUsername.setText(currentUser);
        myUsername.setEditable(false);

        cleanPassword.setText(currentPass);
        cleanPassword.setEditable(false);


        passwordField.setVisible(false);
        passwordField.setManaged(false);

        showPassword.selectedProperty().addListener((obs, was, isNow) -> {
            if (isNow) {
                passwordField.setText(cleanPassword.getText());
                passwordField.setVisible(true);
                passwordField.setManaged(true);
                cleanPassword.setVisible(false);
                cleanPassword.setManaged(false);
            } else {
                cleanPassword.setText(passwordField.getText());
                cleanPassword.setVisible(true);
                cleanPassword.setManaged(true);
                passwordField.setVisible(false);
                passwordField.setManaged(false);
            }
        });


        newUsername.setDisable(true);
        changeUsername.selectedProperty().addListener((obs, was, isNow) -> {
            newUsername.setDisable(!isNow);
            if (!isNow) {
                newUsername.clear();
            }
        });


        newPassword.setDisable(true);
        changePassword.selectedProperty().addListener((obs, was, isNow) -> {
            newPassword.setDisable(!isNow);
            if (!isNow) {
                newPassword.clear();
            }
        });


        confirmChangesButton.disableProperty().bind(
                Bindings.and(
                        newUsername.textProperty().isEmpty(),
                        changePassword.selectedProperty().not()
                )
        );


    }

    @FXML
    public void confirmChanges() {
        // Ricarica credenziali correnti
        LoginBean currentBean = loginController.getUserCredentials();
        if (currentBean == null) {
            errorLbl.setText("Errore interno: utente non trovato.");
            return;
        }

        boolean anyChange = false;

        // Cambio username?
        if (changeUsername.isSelected()) {
            String nuovoUser = newUsername.getText().trim();
            if (nuovoUser.isEmpty()) {
                errorLbl.setText("Inserisci il nuovo username oppure deseleziona la casella.");
                return;
            }
            LoginBean beanPerUsername = new LoginBean(
                    nuovoUser,
                    currentBean.getPassword(),
                    currentBean.getRole(),
                    null
            );
            try {
                loginController.changeUsername(beanPerUsername);
                myUsername.setText(nuovoUser);
                anyChange = true;
            } catch (ApplicationException ex) {
                errorLbl.setText("Errore cambio username: " + ex.getMessage());
                return;
            }
        }

        // Cambio password?
        if (changePassword.isSelected()) {
            String nuovaPass = newPassword.getText().trim();
            if (nuovaPass.isEmpty()) {
               errorLbl.setText("Inserisci la nuova password oppure deseleziona la casella.");
                return;
            }
            LoginBean beanPerPassword = new LoginBean(
                    currentBean.getUsername(),
                    nuovaPass,
                    currentBean.getRole(),
                    null
            );
            try {
                loginController.changePassword(beanPerPassword);
                cleanPassword.setText(nuovaPass);
                passwordField.setText(nuovaPass);
                anyChange = true;
            } catch (ApplicationException ex) {
               errorLbl.setText("Errore cambio password: " + ex.getMessage());
                return;
            }
        }

        if (!anyChange) {
            errorLbl.setText("Nessuna modifica da salvare.");
            return;
        }


        successLbl.setText("Modifiche salvate con successo.");


        newUsername.clear();
        newPassword.clear();
        changeUsername.setSelected(false);
        changePassword.setSelected(false);
        showPassword.setSelected(false);



    }

    @FXML
    public void exit() {
        loginController.logout();
        Stage stage = (Stage) exitButton.getScene().getWindow();

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
            System.err.println("Errore durante il caricamento della schermata di login: " + e.getMessage());
        }
    }
}
