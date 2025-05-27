
package Factory;

import Controller.ChooseMunicipalityController;
import Controller.DoReportController;
import Controller.GraphicLoginController;
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

}

