package factory;

import controller.*;


public class CLIFactory extends GraphicalFactory {


    @Override
    public DoReportController CreateReportController() {
        // Restituisce l'implementazione CLI di DoReportController
        return new CLIReportController();
    }

    @Override
    public ChooseMunicipalityController CreateMunicipalityController() {
        // Restituisce l'implementazione CLI di ChooseMunicipalityController
        return new CLIChooseMunicipalityController();
    }

    @Override
    public GraphicLoginController createLoginController() {
        // Restituisce l'implementazione CLI di GraphicLoginController
        return new CLILoginController();
    }

    @Override
    public RegisterController createRegisterController() {
        return new CLIRegisterController();


    }


    @Override
    public HomeController createHomeController(){
        return new CLIHomeController();
    }

    @Override
    public HomeEmployeeController createHomeEmployeeController() {
        return new CLIEmployeeHomeController();
    }

    @Override
    public ShowReportsController createShowReportsController() {
        return new CLIShowReportsController();
    }

    @Override
    public ReportDetailsController createReportDetailsController() {
        return new CLIReportDetailsController();
    }

    @Override
    public MyAccController createMyAccController() {
        return new CLIMyAccController();
    }


}

