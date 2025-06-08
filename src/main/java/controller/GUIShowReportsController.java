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

public class GUIShowReportsController extends ShowReportsController {

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

        // 4) Listener sul doppio click
        listReports.setOnMouseClicked(this::onReportClicked);

        // 5) Carica subito i report appena la GUI viene inizializzata
        showReports();
    }

    @Override
    @FXML
    public void showReports() {
        List<BeanReport> fetched = reportController.getReportsForCurrentMunicipality();


        loadReports(fetched);
    }


    private void loadReports(List<BeanReport> reportList) {

        reports.clear();
        reports.addAll(reportList);


        List<String> previews = reportList.stream()
                .map(r -> String.format(
                        "Titolo: %s | Data: %s |  %s | Stato: %s",
                        r.getTitle(),
                        r.getDate().toString(),
                        r.getUrgencyType(),   // o r.getUrgency() se usi un getter diverso
                        r.getStatus()
                ))
                .collect(Collectors.toList());


        listReports.getItems().setAll(previews);
    }


    private void onReportClicked(MouseEvent event) {
        if (event.getClickCount() == 2) {
            int index = listReports.getSelectionModel().getSelectedIndex();
            if (index >= 0 && index < reports.size()) {
                openReportDetails(reports.get(index));
            }
        }
    }

    private void openReportDetails(BeanReport selectedReport) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/reportDetails-view.fxml"));
            AnchorPane detailsPane = loader.load();

            GUIReportDetailsController controller = loader.getController();
            controller.setReport(selectedReport);


            contentPane.getChildren().setAll(detailsPane);
            AnchorPane.setTopAnchor(detailsPane,    0.0);
            AnchorPane.setBottomAnchor(detailsPane, 0.0);
            AnchorPane.setLeftAnchor(detailsPane,   0.0);
            AnchorPane.setRightAnchor(detailsPane,  0.0);

        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }
}
