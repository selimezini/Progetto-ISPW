package controller;

import beans.LoginBean;
import exceptions.ApplicationException;
import javafx.animation.PauseTransition;
import javafx.beans.binding.Bindings;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.util.Duration;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXCheckBox;
import org.example.viewprova2.session.SessionManager;


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
    private Label errorLbl;

    @FXML
    private Label succcessLbl;



    private final LoginController loginController = new LoginController();

    @FXML
    public void initialize() {

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


        succcessLbl.setText("Modifiche salvate con successo.");


        newUsername.clear();
        newPassword.clear();
        changeUsername.setSelected(false);
        changePassword.setSelected(false);
        showPassword.setSelected(false);


        PauseTransition pause = new PauseTransition(Duration.seconds(1));
        pause.setOnFinished(e -> exitButton.getScene().getWindow().hide());
        pause.play();
    }

    @FXML
    public void exit() {
        SessionManager sessionManager  = SessionManager.getInstance();
        sessionManager.setCurrentUser(null);
        SceneManager.changeScene("/fxml/login-view.fxml", "Login - CivisAlert");


    }
}
