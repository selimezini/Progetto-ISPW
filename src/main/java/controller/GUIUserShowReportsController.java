package controller;

import beans.BeanReport;
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

public class GUIUserShowReportsController extends ShowReportsController {

    @FXML
    private JFXListView<String> listReports;
    @FXML
    private AnchorPane contentPane;

    // controller applicativo
    private final ReportController reportController = new ReportController();

    // lista interna di BeanReport
    private final List<BeanReport> reports = new ArrayList<>();

    @FXML
    public void initialize() {
        System.out.println("Sono entrato su GUIUSER");
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

        // doppio click
        listReports.setOnMouseClicked(this::onReportClicked);

        // carico subito i report dell'utente
        showReports();
    }


    @Override
    @FXML
    public void showReports() {
        List<BeanReport> fetched = reportController.getUserReports();
        loadReports(fetched);
    }

    /** Popola la ListView con le anteprime */
    private void loadReports(List<BeanReport> reportList) {
        reports.clear();
        reports.addAll(reportList);

        List<String> previews = reportList.stream()
                .map(r -> String.format(
                        "Titolo: %s | Data: %s | Urgenza: %s | Stato: %s",
                        r.getTitle(),
                        r.getDate().toString(),
                        r.getUrgencyType(),
                        r.getStatus()
                ))
                .collect(Collectors.toList());

        listReports.getItems().setAll(previews);
    }


    private void onReportClicked(MouseEvent event) {
        if (event.getClickCount() == 2) {
            int idx = listReports.getSelectionModel().getSelectedIndex();
            if (idx >= 0 && idx < reports.size()) {
                openReportDetails(reports.get(idx));
            }
        }
    }


    private void openReportDetails(BeanReport selectedReport) {
        try {
            FXMLLoader loader = new FXMLLoader(
                    getClass().getResource("/fxml/reportDetails-view.fxml")
            );
            AnchorPane pane = loader.load();

            GUIReportDetailsController ctrl = loader.getController();
            ctrl.setReport(selectedReport);

            contentPane.getChildren().setAll(pane);
            AnchorPane.setTopAnchor(pane,    0.0);
            AnchorPane.setBottomAnchor(pane, 0.0);
            AnchorPane.setLeftAnchor(pane,   0.0);
            AnchorPane.setRightAnchor(pane,  0.0);

        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }
}
