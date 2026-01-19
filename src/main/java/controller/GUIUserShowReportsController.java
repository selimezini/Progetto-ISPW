package controller;

import beans.BeanReport;
import com.jfoenix.controls.JFXListView;
import exceptions.DataLoadException;
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


    private final ReportController reportController = new ReportController();


    private final List<BeanReport> reports = new ArrayList<>();

    @FXML
    public void initialize() {
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


        listReports.setOnMouseClicked(this::onReportClicked);


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
                .toList();

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
            throw new DataLoadException("Impossibile caricare la schermata", e);
        }
    }
}
