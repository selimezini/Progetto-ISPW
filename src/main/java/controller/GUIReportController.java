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
    private JFXButton CancelReportButton;

    @FXML
    private JFXButton ConfirmReportButton;

    @FXML
    private TextField DescriptionTxt;

    @FXML
    private JFXButton InsertPhotoButton;

    @FXML
    private TextField TitleTxt;

    @FXML
    private ComboBox<ProblemType> TypeOfProblem;

    @FXML
    private ComboBox<UrgencyType> Urgency;

    @FXML
    private TextField ViaDelProblemaTxt;


    @FXML
    private ImageView PreviewImage;

    @FXML
    private Label msgLabel;

    @FXML
    private AnchorPane dynamicContentPane;

    private String selectedImagePath;

    private Image  selectedImage;


    @FXML
    public void initialize() {
        // Popola il ComboBox TypeOfProblem
        TypeOfProblem.getItems().addAll(ProblemType.values());

        TypeOfProblem.setCellFactory(comboBox -> new javafx.scene.control.ListCell<>() {
            @Override
            protected void updateItem(ProblemType item, boolean empty) {
                super.updateItem(item, empty);
                setText(empty || item == null ? null : item.getDescription());
            }
        });

        TypeOfProblem.setButtonCell(new javafx.scene.control.ListCell<>() {
            @Override
            protected void updateItem(ProblemType item, boolean empty) {
                super.updateItem(item, empty);
                setText(empty || item == null ? null : item.getDescription());
            }
        });


        Urgency.getItems().addAll(UrgencyType.values());

        Urgency.setCellFactory(comboBox -> new javafx.scene.control.ListCell<>() {
            @Override
            protected void updateItem(UrgencyType item, boolean empty) {
                super.updateItem(item, empty);
                setText(empty || item == null ? null : item.getDescription());
            }
        });

        Urgency.setButtonCell(new javafx.scene.control.ListCell<>() {
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
        File file = chooser.showOpenDialog(InsertPhotoButton.getScene().getWindow());
        if (file != null) {
            try (InputStream in = new FileInputStream(file)) {
                selectedImage     = new Image(in);
                selectedImagePath = file.toURI().toString();
                PreviewImage.setImage(selectedImage);
            } catch (Exception e) {
              System.out.println(e.getMessage());
            }
        }
    }





    @Override
    public void createReport() {
        String title       = TitleTxt.getText();
        String description = DescriptionTxt.getText();
        String via         = ViaDelProblemaTxt.getText();

        ProblemType    selProb = TypeOfProblem.getValue();
        UrgencyType    selUrg  = Urgency.getValue();
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



    }


}
