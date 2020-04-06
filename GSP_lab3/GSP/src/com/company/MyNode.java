package com.company;

import javafx.scene.Node;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

public class MyNode extends Node {
    String isHaveDoubleEdge = "No";
    double newControlX;
    double newConrtolY;
    int numberOfNode;
    int sideOfNode;
    Circle circle;
    Text textOfNode;

    MyNode(double centerX,double centerY,double radius, int numOfNode, int side) {
        circle = new Circle(centerX, centerY, radius);
        circle.setFill(Color.WHITE);
        circle.setStroke(Color.BLACK);
        circle.setStrokeWidth(2);

        double textX = centerX-5.0;
        double textY = centerY+5.0;
        if (numOfNode > 9) {
            textX-=5.0;
        }
        textOfNode = new Text(textX, textY, String.valueOf(numOfNode));
        textOfNode.setFont(new Font(17));

        numberOfNode = numOfNode;
        sideOfNode = side;
    }
}
