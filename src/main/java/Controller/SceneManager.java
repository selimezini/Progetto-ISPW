package Controller;

import Factory.GraphicalFactory;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class SceneManager {

    private static Stage primaryStage;


    private static final GraphicalFactory factory = GraphicalFactory.getInstance();


    public static void setStage(Stage stage) {
        primaryStage = stage;
    }


    public static void changeScene(String fxmlPath, String title) {
        try {

            FXMLLoader loader = new FXMLLoader(SceneManager.class.getResource(fxmlPath));


            loader.setControllerFactory(controllerClass -> {
                if (GraphicLoginController.class.isAssignableFrom(controllerClass)) {
                    return factory.createLoginController();
                }
                if (DoReportController.class.isAssignableFrom(controllerClass)) {
                    return factory.CreateReportController();
                }
                if (ChooseMunicipalityController.class.isAssignableFrom(controllerClass)) {
                    return factory.CreateMunicipalityController();
                }
                if (GUIRegisterController.class.isAssignableFrom(controllerClass)) {
                    return factory.createRegisterController();
                }

                try {
                    return controllerClass.getDeclaredConstructor().newInstance();
                } catch (Exception e) {

                    throw new RuntimeException("Impossibile creare controller per: " + controllerClass, e);
                }
            });

            Parent root = loader.load();


            primaryStage.setScene(new Scene(root));

            primaryStage.setTitle(title);

            primaryStage.show();
        } catch (IOException e) {

            e.printStackTrace();
        }
    }
}
