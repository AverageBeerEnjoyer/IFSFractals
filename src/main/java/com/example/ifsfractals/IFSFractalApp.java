package com.example.ifsfractals;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Locale;

public class IFSFractalApp extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        Locale.setDefault(Locale.ENGLISH);
        FXMLLoader fxmlLoader = new FXMLLoader(IFSFractalApp.class.getResource("main-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 1288.0, 801);
        stage.setTitle("Hello!");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}