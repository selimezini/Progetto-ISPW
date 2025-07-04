package controller;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.lang.reflect.Method;

public class SceneManager {

    public static Stage primaryStage;

    public static void setStage(Stage stage) {
        primaryStage = stage;
    }


    public static void switchScene(AnchorPane targetPane, String fxmlPath, Object controller, String methodToCall) {
        try {
            FXMLLoader loader = new FXMLLoader(SceneManager.class.getResource(fxmlPath));
            loader.setController(controller);
            Parent content = loader.load();

            if (methodToCall != null && !methodToCall.isBlank()) {
                Method method = controller.getClass().getMethod(methodToCall);
                method.invoke(controller);
            }

            targetPane.getChildren().setAll(content);
            AnchorPane.setTopAnchor(content, 0.0);
            AnchorPane.setBottomAnchor(content, 0.0);
            AnchorPane.setLeftAnchor(content, 0.0);
            AnchorPane.setRightAnchor(content, 0.0);

        } catch (IOException | ReflectiveOperationException e) {
            e.printStackTrace();
        }
    }
}

