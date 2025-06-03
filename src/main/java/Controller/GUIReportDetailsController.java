package Controller;

import Beans.BeanReport;
import Model.ReportStatus;
import exceptions.DataAccessException;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.util.ArrayList;
import java.util.List;

public class GUIReportDetailsController extends  ReportDetailsController {

    @FXML private Label titleLbl;
    @FXML private Label dateLbl;
    @FXML private Label urgencyLbl;
    @FXML private Label problemLbl;
    @FXML private Label descrLbl;
    @FXML private ImageView imageOfProblem;
    @FXML private ComboBox<String> stateBox;
    @FXML private Label viaLbl;
    @FXML private Label errorLbl;

    private BeanReport report;

    public void setReport(BeanReport report) {
        System.out.println("tipo di urgenza:" + report.getProblemType());
        this.report = report;
        titleLbl.setText(report.getTitle());
        dateLbl.setText(report.getDate().toString());
        urgencyLbl.setText(report.getUrgencyType());
        problemLbl.setText(report.getProblemType());
        descrLbl.setText(report.getDescription());
        imageOfProblem.setImage(new Image(report.getImagePath()));
        viaLbl.setText(report.getViaDelProblema());



        // Carica immagine solo se il path non è nullo/empty
        String imgPath = report.getImagePath();
        if (imgPath != null && !imgPath.isBlank()) {
            try {
                imageOfProblem.setImage(new Image(imgPath));
            } catch (Exception e) {
                imageOfProblem.setImage(null);
            }
        }

        // 1) Costruisco una lista di descrizioni a partire da ReportStatus.values()
        List<String> descrizioni = new ArrayList<>();
        for (ReportStatus rs : ReportStatus.values()) {
            descrizioni.add(rs.getDescription());
        }

        // 2) Popolo il ComboBox<String> con quelle descrizioni
        stateBox.getItems().setAll(descrizioni);

        String savedDesc = report.getStatus(); // es. “In corso”
        String currentDesc = null;
        for (ReportStatus rs : ReportStatus.values()) {
            if (rs.getDescription().equals(savedDesc)) {
                currentDesc = rs.getDescription();
                break;
            }
        }
        // Se non trovato, currentDesc resterà null, altrimenti è la descrizione corretta
        stateBox.setValue(currentDesc);



    }

    @Override
    @FXML
    public void confirmChanges() {
        // 1) Leggo la descrizione selezionata
        String selectedDesc = stateBox.getValue();
        BeanReport newReport = report;
        newReport.setStatus(selectedDesc);
        ReportController reportController = new ReportController();
        try{
            reportController.submitUpdate(newReport);
            errorLbl.setText("Cambiamento effettuato con successo");
        }catch(DataAccessException e){
            errorLbl.setText(e.getMessage());
        }





    }
    }


