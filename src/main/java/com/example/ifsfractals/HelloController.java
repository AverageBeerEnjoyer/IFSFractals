package com.example.ifsfractals;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.canvas.Canvas;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;

public class HelloController implements Initializable {
    @FXML
    private Canvas canvas;

    private CanvasDrawer canvasDrawer;

    @FXML
    private void open() {
        FileChooser fileChooser = new FileChooser();
        Stage stage = new Stage();
        File file = fileChooser.showOpenDialog(stage);
        if (file == null) return;
        setFractal(new Fractal(file));
    }

    @FXML
    private void increaseScale(){
        canvasDrawer.increaseInitInterval();
    }

    @FXML
    private void decreaseScale(){
        canvasDrawer.decreaseInitInterval();
    }

    private void setFractal(Fractal fractal){
        canvasDrawer.setData(fractal.getDots());
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        canvasDrawer = new CanvasDrawer(canvas);
    }
}