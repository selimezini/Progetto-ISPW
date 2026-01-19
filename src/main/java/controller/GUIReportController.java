package controller;

import beans.BeanReport;
import exceptions.ValidationException;
import javafx.animation.PauseTransition;
import javafx.scene.layout.AnchorPane;
import javafx.util.Duration;
import model.ProblemType;
import model.UrgencyType;
import com.jfoenix.controls.JFXButton;
import exceptions.ApplicationException;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;

public class GUIReportController extends DoReportController{

    @FXML
    private JFXButton cancelReportButton;

    @FXML
    private JFXButton confirmReportButton;

    @FXML
    private TextField descriptionTxt;

    @FXML
    private JFXButton insertPhotoButton;

    @FXML
    private TextField titleTxt;

    @FXML
    private ComboBox<ProblemType> typeOfProblem;

    @FXML
    private ComboBox<UrgencyType> urgency;

    @FXML
    private TextField viaDelProblemaTxt;


    @FXML
    private ImageView previewImage;

    @FXML
    private Label msgLabel;

    @FXML
    private AnchorPane dynamicContentPane;

    private String selectedImagePath;

    private Image  selectedImage;


    @FXML
    public void initialize() {

        typeOfProblem.getItems().addAll(ProblemType.values());

        typeOfProblem.setCellFactory(comboBox -> new javafx.scene.control.ListCell<>() {
            @Override
            protected void updateItem(ProblemType item, boolean empty) {
                super.updateItem(item, empty);
                setText(empty || item == null ? null : item.getDescription());
            }
        });

        typeOfProblem.setButtonCell(new javafx.scene.control.ListCell<>() {
            @Override
            protected void updateItem(ProblemType item, boolean empty) {
                super.updateItem(item, empty);
                setText(empty || item == null ? null : item.getDescription());
            }
        });


        urgency.getItems().addAll(UrgencyType.values());

        urgency.setCellFactory(comboBox -> new javafx.scene.control.ListCell<>() {
            @Override
            protected void updateItem(UrgencyType item, boolean empty) {
                super.updateItem(item, empty);
                setText(empty || item == null ? null : item.getDescription());
            }
        });

        urgency.setButtonCell(new javafx.scene.control.ListCell<>() {
            @Override
            protected void updateItem(UrgencyType item, boolean empty) {
                super.updateItem(item, empty);
                setText(empty || item == null ? null : item.getDescription());
            }
        });
    }


    @FXML
    private void onInsertPhoto() {
        FileChooser chooser = new FileChooser();
        chooser.setTitle("Seleziona foto del problema");
        chooser.getExtensionFilters().add(
                new FileChooser.ExtensionFilter("Immagini", "*.png","*.jpg","*.jpeg")
        );
        File file = chooser.showOpenDialog(insertPhotoButton.getScene().getWindow());
        if (file != null) {
            try (InputStream in = new FileInputStream(file)) {
                selectedImage     = new Image(in);
                selectedImagePath = file.toURI().toString();
                previewImage.setImage(selectedImage);
            } catch (Exception e) {
                msgLabel.setText("errore nell'inserimento della foto");
            }
        }
    }





    @Override
    public void createReport() {
        String title       = titleTxt.getText();
        String description = descriptionTxt.getText();
        String via         = viaDelProblemaTxt.getText();

        ProblemType    selProb = typeOfProblem.getValue();
        UrgencyType    selUrg  = urgency.getValue();
        String problemDesc = (selProb == null) ? null : selProb.getDescription();
        String urgencyDesc = (selUrg  == null) ? null : selUrg.getDescription();

        BeanReport bean = new BeanReport();
        bean.setTitle(title);
        bean.setDescription(description);
        bean.setViaDelProblema(via);
        bean.setProblemType(problemDesc);
        bean.setUrgencyType(urgencyDesc);
        bean.setStatus("APERTO");
        bean.setImagePath(selectedImagePath);
        bean.setImage(selectedImage);

        try {
            bean.validate();

            new ReportController().submitReport(bean);

            msgLabel.setText("Segnalazione inviata con successo!");
            SceneManager.switchScene(
                    dynamicContentPane,
                    "/fxml/confirm-view.fxml",
                    null,
                    null
            );

            PauseTransition pause = new PauseTransition(Duration.seconds(4));
            pause.setOnFinished(evt ->
                    SceneManager.switchScene(
                            dynamicContentPane,
                            "/fxml/homeDashboard-view.fxml",
                            null,
                            null
                    )
            );
            pause.play();

        } catch (ValidationException ve) {
            msgLabel.setText(ve.getMessage());

        } catch (ApplicationException ae) {
            msgLabel.setText(ae.getMessage());
        }
    }


    @Override
    public void deleteReport() {
    //non implementato


    }


}
