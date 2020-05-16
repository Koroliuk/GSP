package com.company;

import javafx.scene.Node;
import javafx.scene.shape.Line;
import javafx.scene.shape.QuadCurve;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;

public class MyEdge extends Node {
    int startNode;
    int finishNode;
    int weightOfEdge;
    Node edge;
    Text textOfWeight;

    MyEdge(int start, int finish, Node edg) {
        startNode = finish; //counting
        finishNode = start; //from 1
        edge = edg;
        if (start == 0) {
            weightOfEdge = 0;
            return;
        }
        int weight = Main.matrixW[finish-1][start-1];
        weightOfEdge = weight;
        double textX = 0, textY = 0;
        if (edge instanceof ArrowArc) {
            double startX, startY, endX, endY, controlX, controlY;
            startX = ((ArrowArc) edge).quadCurve.getStartX();
            startY = ((ArrowArc) edge).quadCurve.getStartY();
            endX = ((ArrowArc) edge).quadCurve.getEndX();
            endY = ((ArrowArc) edge).quadCurve.getEndY();
            controlX = ((ArrowArc) edge).quadCurve.getControlX();
            controlY = ((ArrowArc) edge).quadCurve.getControlY();
            double midX = (startX+endX)/2d;
            double midY = (startY+endY)/2d;
            textX = (midX+controlX)/2d;
            textY = (midY+controlY)/2d;
            if (startY-endY < 10) {
                textY-=5;
            }
            if ((startX-endX)*(startY-endY) < 0) {
                textX+=10;
                textY+=5;
            }
        } else if (edge instanceof ArrowLine) {
            double startX, startY, endX, endY;
            startX = ((ArrowLine) edge).line.getStartX();
            startY = ((ArrowLine) edge).line.getStartY();
            endX = ((ArrowLine) edge).line.getEndX();
            endY = ((ArrowLine) edge).line.getEndY();
            textX = (startX+endX)/2d;
            textY = (startY+endY)/2d;
        } else if (edge instanceof QuadCurve){
            double startX, startY, endX, endY, controlX, controlY;
            startX = ((QuadCurve) edge).getStartX();
            startY = ((QuadCurve) edge).getStartY();
            endX = ((QuadCurve) edge).getEndX();
            endY = ((QuadCurve) edge).getEndY();
            controlX = ((QuadCurve) edge).getControlX();
            controlY = ((QuadCurve) edge).getControlY();
            double midX = (startX+endX)/2d;
            double midY = (startY+endY)/2d;
            textX = (midX+controlX)/2d;
            textY = (midY+controlY)/2d;
            if (startY-endY < 10) {
                textY-=5;
            }
            if ((startX-endX)*(startY-endY) < 0) {
                textX+=10;
                textY+=5;
            }
        } else {
            if (edge instanceof Line){
                double startX, startY, endX, endY;
                startX = ((Line) edge).getStartX();
                startY = ((Line) edge).getStartY();
                endX = ((Line) edge).getEndX();
                endY = ((Line) edge).getEndY();
                textX = (startX+endX)/2d;
                textY = (startY+endY)/2d;
            }
        }
        textOfWeight = new Text(textX, textY, String.valueOf(weight));
        textOfWeight.setFont(Font.font("veranda", FontWeight.BOLD, FontPosture.REGULAR, 15));
    }

}
