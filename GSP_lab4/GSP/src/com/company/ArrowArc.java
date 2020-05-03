package com.company;

import javafx.scene.paint.Color;
import javafx.scene.shape.Path;
import javafx.scene.shape.Polyline;
import javafx.scene.shape.QuadCurve;

public class ArrowArc extends Path {
    private static final double defaultArrowHeadSize = 10.0;
    public QuadCurve quadCurve;
    public Polyline polyline;
    ArrowArc(double startX, double startY, double controlX, double controlY, double endX, double endY, double arrowHeadSize) {
        quadCurve = new QuadCurve(startX, startY, controlX, controlY, endX, endY);
        quadCurve.setFill(Color.TRANSPARENT);
        quadCurve.setStroke(Color.BLACK);
        quadCurve.setStrokeWidth(2);

        polyline = new Polyline();
        double angle = Math.atan2((endY - controlY), (endX - controlX)) - Math.PI / 2.0;
        double sin = Math.sin(angle);
        double cos = Math.cos(angle);
        //point1
        double x1 = (- 1.0 / 2.0 * cos + Math.sqrt(3) / 2 * sin) * arrowHeadSize + endX;
        double y1 = (- 1.0 / 2.0 * sin - Math.sqrt(3) / 2 * cos) * arrowHeadSize + endY;
        //point2
        double x2 = (1.0 / 2.0 * cos + Math.sqrt(3) / 2 * sin) * arrowHeadSize + endX;
        double y2 = (1.0 / 2.0 * sin - Math.sqrt(3) / 2 * cos) * arrowHeadSize + endY;

        polyline.getPoints().addAll(
                x1, y1,
                x2, y2,
                endX, endY);
        polyline.setFill(Color.BLACK);
        polyline.setStroke(Color.BLACK);
    }
    ArrowArc(double startX, double startY, double controlX, double controlY, double endX, double endY) {
        this(startX, startY, controlX, controlY, endX, endY, defaultArrowHeadSize);
    }
}