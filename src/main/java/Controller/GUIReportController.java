package Controller;

import Beans.BeanReport;
import Beans.MunicipalityBean;
import Model.ProblemType;
import Model.UrgencyType;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import exceptions.ApplicationException;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import org.example.viewprova2.session.SessionManager;

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


    /** Apre un FileChooser per selezionare un’immagine */
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
                e.printStackTrace();
            }
        }
    }





    @Override
    public void createReport() {

        String title = TitleTxt.getText();
        String description = DescriptionTxt.getText();
        String via = ViaDelProblemaTxt.getText();
        ProblemType probType = TypeOfProblem.getValue();
        UrgencyType urgType = Urgency.getValue();
        if(title.isEmpty() || description.isEmpty() || via.isEmpty() || probType == null || urgType == null) {
            msgLabel.setText("Perfavore inserire tutti i campi");
            return;
        }

        BeanReport bean = new BeanReport(title,description,probType.getDescription(),urgType.getDescription(),"APERTO",selectedImagePath,selectedImage,via);
        ReportController reportController = new ReportController();

        try {
            reportController.submitReport(bean);
        }catch (ApplicationException e) {
            msgLabel.setText(e.getMessage());
        }

        }

    @Override
    public void deleteReport() {

    }

    @Override
    public void updateReport() {

    }
}
