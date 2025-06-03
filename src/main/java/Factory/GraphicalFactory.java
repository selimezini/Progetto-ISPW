
package Factory;

import Controller.*;
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


    // === Abstract factory methods ===
    public abstract DoReportController CreateReportController();
    public abstract ChooseMunicipalityController CreateMunicipalityController();
    public abstract GraphicLoginController createLoginController();
    public abstract RegisterController createRegisterController();
    public abstract HomeController createHomeController();
    public abstract HomeEmployeeController createHomeEmployeeController();
    public abstract ShowReportsController createShowReportsController();
    public abstract ReportDetailsController createReportDetailsController();

}

