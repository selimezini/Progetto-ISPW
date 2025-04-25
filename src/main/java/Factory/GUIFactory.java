package Factory;

import Controller.GUIReportController;
import Controller.GUIChooseMunicipalityController;
import Controller.GUILoginController;
import Controller.ChooseMunicipalityController;
import Controller.DoReportController;
import Controller.GraphicLoginController;


public class GUIFactory extends GraphicalFactory {



    @Override
    public DoReportController CreateReportController() {
        return new GUIReportController();
    }

    @Override
    public ChooseMunicipalityController CreateMunicipalityController() {
        return new GUIChooseMunicipalityController();
    }

    @Override
    public GraphicLoginController createLoginController() {
        return new GUILoginController();
    }
}
