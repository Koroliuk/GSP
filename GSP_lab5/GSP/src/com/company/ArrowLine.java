package com.company;

import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.Path;
import javafx.scene.shape.Polyline;

public class ArrowLine extends Path {
    private static final double defaultArrowHeadSize = 10.0;
    public Line line;
    public Polyline polyline;
    ArrowLine(double startX, double startY, double endX, double endY, double arrowHeadSize) {
        line = new Line(startX, startY, endX, endY);
        line.setStroke(Color.SLATEGRAY);
        line.setStrokeWidth(2);

        polyline = new Polyline();
        double angle = Math.atan2((endY - startY), (endX - startX)) - Math.PI / 2.0;
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
                endX, endY );
        polyline.setFill(Color.SLATEGRAY);
        polyline.setStroke(Color.SLATEGRAY);
    }
    ArrowLine(double startX, double startY, double endX, double endY) {
        this(startX, startY, endX, endY, defaultArrowHeadSize);
    }
}