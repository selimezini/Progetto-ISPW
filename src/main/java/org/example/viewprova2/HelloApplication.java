package org.example.viewprova2;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class HelloApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("hello-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 800, 600);
        stage.setTitle("CivisAlert");
        stage.setScene(scene);
        stage.show();
        System.out.println("sadax");
        System.out.println("asdetwqfrgfdgd");

    }

    public static void main(String[] args) {
        launch();
    }



}