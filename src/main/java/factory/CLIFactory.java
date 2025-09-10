package factory;

import controller.*;


public class CLIFactory extends GraphicalFactory {


    @Override
    public DoReportController CreateReportController() {

        return new CLIReportController();
    }

    @Override
    public ChooseMunicipalityController CreateMunicipalityController() {

        return new CLIChooseMunicipalityController();
    }

    @Override
    public GraphicLoginController createLoginController() {

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

