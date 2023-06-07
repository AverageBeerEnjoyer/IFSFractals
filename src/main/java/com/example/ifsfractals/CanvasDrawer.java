package com.example.ifsfractals;

import javafx.geometry.Rectangle2D;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.util.Pair;

import java.util.ArrayList;
import java.util.List;

public class CanvasDrawer {
    private final Canvas canvas;
    private final GraphicsContext gc;
    private final double x0, y0;
    private final double width,height;
    private Rectangle2D bounds;
    private double initInterval;
    private int initIntervalToDraw;
    private String ordinateName = "y";
    private String abscissaName = "x";
    private List<Dot> data;

    public CanvasDrawer(Canvas canvas) {
        data = new ArrayList<>();
        this.canvas = canvas;
        this.gc = canvas.getGraphicsContext2D();
        this.width = canvas.getWidth();
        this.height = canvas.getHeight();
        this.x0 = width / 5;
        this.y0 = height * 4 / 5;
        initInterval = 20;
        setGCProperties();
        countBounds();
        draw();
    }
    private void setGCProperties(){
        gc.setFill(Color.rgb(117, 227, 104, 0.5));
        gc.setFont(new Font("Verdana",14));
    }
    private void countBounds() {
        Dot bl = convertCoordsCanvasToGraph(new Dot(0, height));
        bounds = new Rectangle2D(bl.x(), bl.y(), width / initInterval, height / initInterval);
        initIntervalToDraw = (int) ((bounds.getMaxX() - bounds.getMinX()) / 10);
    }
    public void setData(List<Dot> dots){
        this.data = dots;
        draw();
    }
    private void drawDots(){
        data.forEach(this::drawPixel);
    }

    private void drawCoordinateSystem() {
        gc.setLineWidth(4);
        drawLine(new Straight(1,0,0));
        drawLine(new Straight(0,1,0));
        gc.setLineWidth(1);

        gc.strokeText(abscissaName, width - 15, y0 - 5);
        gc.strokeText(ordinateName, x0 + 5, 15);
        double l = 16 / initInterval;
        for (int i = initIntervalToDraw; i < bounds.getMaxY() || i < bounds.getMaxX() || -i > bounds.getMinX() || -i > bounds.getMinY(); i += initIntervalToDraw) {
            if (i < bounds.getMaxX()) {
                drawLine(new Dot(i, bounds.getMinY()), new Dot(i, bounds.getMaxY()));
                drawText("" + i, new Dot(i+l/10, -l));
            }
            if (-i > bounds.getMinX()) {
                drawLine(new Dot(-i, bounds.getMinY()), new Dot(-i, bounds.getMaxY()));
                drawText("" + (-i), new Dot(-i+l/10, -l));
            }
            if (i < bounds.getMaxY()) {
                drawLine(new Dot(bounds.getMinX(), i), new Dot(bounds.getMaxX(), i));
                drawText("" + i, new Dot(-l, i+l/10));
            }
            if (i > bounds.getMinY()) {
                drawLine(new Dot(bounds.getMinX(), -i), new Dot(bounds.getMaxX(), -i));
                drawText("" + (-i), new Dot(-l, -i+l/10));
            }
            if(initIntervalToDraw==0) break;
        }
    }

    private void drawLine(Dot d1, Dot d2) {
        Dot dot1 = convertCoordsGraphToCanvas(d1);
        Dot dot2 = convertCoordsGraphToCanvas(d2);
        gc.strokeLine(dot1.x(), dot1.y(), dot2.x(), dot2.y());
    }

    private void drawLine(Straight straight) {
        Pair<Dot, Dot> dots = straight.recount(bounds);
        if (dots == null) return;
        drawLine(dots.getKey(), dots.getValue());
    }

    private void drawText(String s, Dot d) {
        Dot canvasCoords = convertCoordsGraphToCanvas(d);
        gc.strokeText(s, canvasCoords.x(), canvasCoords.y());
    }

    private void clearGC() {
        gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
    }

    private void draw() {
        clearGC();
        drawCoordinateSystem();
        drawDots();
    }

    private void drawPixel(Dot dot){
        Dot canvasCoords = convertCoordsGraphToCanvas(dot);
        gc.fillRect(canvasCoords.x(),canvasCoords.y(),1,1);
    }




    public void increaseInitInterval() {
        initInterval *= 2;
        countBounds();
        draw();
    }

    public void decreaseInitInterval() {
        initInterval /= 2;
        countBounds();
        draw();
    }

    private Dot convertCoordsCanvasToGraph(Dot canvasCoords) {
        double x = (canvasCoords.x() - x0) / initInterval;
        double y = (y0 - canvasCoords.y()) / initInterval;
        return new Dot(x, y);
    }


    private Dot convertCoordsGraphToCanvas(Dot graphCoords) {
        double x = graphCoords.x() * initInterval + x0;
        double y = y0 - graphCoords.y() * initInterval;
        return new Dot(x, y);
    }
}
