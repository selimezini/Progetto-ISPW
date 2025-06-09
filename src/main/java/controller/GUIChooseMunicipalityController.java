package controller;

import beans.MunicipalityBean;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXListView;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import org.example.viewprova2.session.SessionManager;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class GUIChooseMunicipalityController extends ChooseMunicipalityController {

    @FXML private Label MsgLabel;
    @FXML private JFXListView<String> MunicipalitiesList;
    @FXML private JFXButton SearchMunicipalityButton;
    @FXML private TextField MunicipalityNameTxt;


    @FXML private AnchorPane contentPane;

    private List<MunicipalityBean> lastResults = new ArrayList<>();
    private final ReportController reportController = new ReportController();



    @FXML
    @Override
    public void searchMunicipality() {
        String name = MunicipalityNameTxt.getText().trim();

        if (name.isEmpty()) {
            MunicipalitiesList.getItems().clear();
            lastResults.clear();
            return;
        }

        MunicipalityBean request = new MunicipalityBean(name, null,null);
        List<MunicipalityBean> beans = reportController.searchMunicipality(request);

        lastResults = beans;
        MunicipalitiesList.getItems().setAll(
                beans.stream()
                        .map(MunicipalityBean::toString)
                        .collect(Collectors.toList())
        );
    }

    @Override
    public List<MunicipalityBean> showMunicipalityList() {
        return lastResults;
    }

    @Override
    public void chooseMunicipality() {
        int idx = MunicipalitiesList.getSelectionModel().getSelectedIndex();
        if (idx < 0 || idx >= lastResults.size()) return;

        MunicipalityBean chosen = lastResults.get(idx);

        SessionManager.getInstance().setCurrentMunicipalityReport(chosen);
        System.out.println("siamo su GUICHOOSECONTROLLER: ho scritto" + chosen.getName() + chosen.getRegion() + "CODICE" + chosen.getCode());

        try {
            FXMLLoader loader = new FXMLLoader(
                    getClass().getResource("/fxml/report-view.fxml")
            );
            AnchorPane reportForm = loader.load();

            contentPane.getChildren().setAll(reportForm);
            AnchorPane.setTopAnchor(reportForm,    0.0);
            AnchorPane.setBottomAnchor(reportForm, 0.0);
            AnchorPane.setLeftAnchor(reportForm,   0.0);
            AnchorPane.setRightAnchor(reportForm,  0.0);

        } catch (IOException e) {
            MsgLabel.setText("Errore nel caricamento del form di segnalazione.");

        }
    }

    @FXML
    private void onListClicked(MouseEvent event) {
        if (event.getClickCount() == 2) {
            chooseMunicipality();
        }
    }
}
