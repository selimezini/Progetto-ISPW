package Controller;

import Beans.BeanReport;
import com.jfoenix.controls.JFXListView;
import javafx.fxml.FXML;



import Beans.BeanReport;
import  Controller.ReportController;   // Assicurati di usare il package giusto
import com.jfoenix.controls.JFXListView;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.ListCell;
import javafx.scene.control.Tooltip;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class GUIShowReportsController extends ShowReportsController {

    @FXML
    private JFXListView<String> listReports;
    @FXML
    private AnchorPane contentPane;


    // 1) Campo per il controller applicativo
    private final ReportController reportController = new ReportController();

    // 2) Lista interna di BeanReport
    private final List<BeanReport> reports = new ArrayList<>();

    @FXML
    public void initialize() {
        System.out.println("Entrato nella GUISHOWCONTROLLER");
        // 3) Imposta la cell factory (tooltip)
        listReports.setCellFactory(lv -> new ListCell<>() {
            @Override
            protected void updateItem(String item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setText(null);
                    setTooltip(null);
                } else {
                    setText(item);
                    setTooltip(new Tooltip(item));
                }
            }
        });

        // 4) Listener sul doppio click
        listReports.setOnMouseClicked(this::onReportClicked);

        // 5) Carica subito i report appena la GUI viene inizializzata
        showReports();
    }

    /**
     * Questo è il metodo “pubblico” chiamato da initialize() (o da un bottone se preferisci)
     * che recupera i dati dal ReportController e passa la lista a loadReports().
     */
    @FXML
    public void showReports() {
        // 6) CHIAMATA AL CONTROLLER APPLICATIVO PER OTTENERE I BeanReport
        List<BeanReport> fetched = reportController.getReportsForCurrentMunicipality();

        // 7) Passa la lista recuperata a loadReports() per popolare la ListView
        loadReports(fetched);
    }

    /**
     * Popola la ListView con le stringhe di anteprima (Titolo | Data | Urgenza | Stato).
     */
    private void loadReports(List<BeanReport> reportList) {
        // 8) Aggiorna la lista interna
        reports.clear();
        reports.addAll(reportList);

        // 9) Costruisci le stringhe di preview
        List<String> previews = reportList.stream()
                .map(r -> String.format(
                        "Titolo: %s | Data: %s |  %s | Stato: %s",
                        r.getTitle(),
                        r.getDate().toString(),
                        r.getUrgencyType(),   // o r.getUrgency() se usi un getter diverso
                        r.getStatus()
                ))
                .collect(Collectors.toList());

        // 10) Assegna le preview alla ListView
        listReports.getItems().setAll(previews);
    }

    /**
     * Gestisce il doppio click su una riga: apre la schermata di dettaglio passando il ReportBean.
     */

    private void onReportClicked(MouseEvent event) {
        if (event.getClickCount() == 2) {
            int index = listReports.getSelectionModel().getSelectedIndex();
            if (index >= 0 && index < reports.size()) {
                openReportDetails(reports.get(index));
            }
        }
    }

    /**
     * Carica il FXML di report-details e passa il BeanReport selezionato al suo controller.
     */
    private void openReportDetails(BeanReport selectedReport) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/reportDetails-view.fxml"));
            AnchorPane detailsPane = loader.load();

            // Ora JavaFX avrà già iniettato tutti i Label/ImageView in GUIReportDetailsController
            GUIReportDetailsController controller = loader.getController();
            controller.setReport(selectedReport);

            // Sostituisco il contenuto del contentPane
            contentPane.getChildren().setAll(detailsPane);
            AnchorPane.setTopAnchor(detailsPane,    0.0);
            AnchorPane.setBottomAnchor(detailsPane, 0.0);
            AnchorPane.setLeftAnchor(detailsPane,   0.0);
            AnchorPane.setRightAnchor(detailsPane,  0.0);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
