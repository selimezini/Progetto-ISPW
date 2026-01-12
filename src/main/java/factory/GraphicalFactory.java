
package factory;

import controller.*;
import org.example.viewprova2.Main;


public abstract class GraphicalFactory {


    private static GraphicalFactory instance;

    protected GraphicalFactory() {

    }


    public static GraphicalFactory getInstance() {
        if (instance == null) {
            String executionMode = Main.getExecutionMode();
            if(executionMode.equals("GUI")) {
                instance = new GUIFactory();
            }else if(executionMode.equals("CLI")) {
                instance = new CLIFactory();
            }
        }
        return instance;

    }



    public abstract DoReportController createReportController();
    public abstract ChooseMunicipalityController createMunicipalityController();
    public abstract GraphicLoginController createLoginController();
    public abstract RegisterController createRegisterController();
    public abstract HomeController createHomeController();
    public abstract HomeEmployeeController createHomeEmployeeController();
    public abstract ShowReportsController createShowReportsController();
    public abstract ReportDetailsController createReportDetailsController();
    public abstract MyAccController createMyAccController();
}

