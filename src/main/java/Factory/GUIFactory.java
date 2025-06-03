package Factory;

import Controller.*;



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

    @Override
    public RegisterController createRegisterController(){
        return new GUIRegisterController();
    }

    @Override
    public HomeController createHomeController() {
        return new GUIHomeController();
    }

    @Override
    public HomeEmployeeController createHomeEmployeeController() {
        return new GUIHomeEmployeeController();
    }

    @Override
    public ShowReportsController createShowReportsController() {
        return new GUIShowReportsController();
    }

    @Override
    public ReportDetailsController createReportDetailsController() {
        return new GUIReportDetailsController();
    }


}
