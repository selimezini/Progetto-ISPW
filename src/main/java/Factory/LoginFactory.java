package Factory;

import Controller.CLILoginController;
import Controller.GUILoginController;
import Controller.LoginGraphicController;

public class LoginFactory {

    private static LoginFactory instance;

    private LoginFactory() {
    }

    public static LoginFactory getInstance() {

        if(instance == null) {
            instance = new LoginFactory();
        }

            return instance;
    }

    public LoginGraphicController getController(int choice) throws Exception {

        switch(choice) {

            case 1: return new GUILoginController();
            case 2: return new CLILoginController();
            default : throw new Exception("Invalide Type");
        }
    }



}
