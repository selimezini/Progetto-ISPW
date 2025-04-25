package Factory;

import Controller.*;


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
}

