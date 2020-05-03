package com.company;


import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.QuadCurve;

import java.util.ArrayList;


public class CalculateMath {
    public static ArrayList<Integer> queuePred = new ArrayList<>();
    public static int[] visited = new int[Main.numberOfNodes];
    public static int count = 0;

    public static void bfsStep() {
        int su = 0;
        for (int i = 0; i < Main.numberOfNodes; i++) {
            su+=visited[i];
        }
        if (Main.queue.size() == 0 || su == Main.numberOfNodes) {
            return;
        }
        MyNode curr = Main.queue.remove(0);
        Integer pred = queuePred.remove(0);
        curr.circle.setFill(Color.BLACK);
        curr.textOfNode.setFill(Color.WHITE);
        int i = curr.numberOfNode-1;
        Main.matrixOfNodes[i][count] = 1;
        Main.numeration[count] = i;
        count++;
        visited[i] = 1;
        if (pred != -1) {
            Main.matrixOfTreeOld[pred][i] = 1;
            MyEdge edg = Main.matrixOfEdges[pred][i];
            if (edg.edge instanceof ArrowArc){
                ((ArrowArc) edg.edge).quadCurve.setStroke(Color.PALEVIOLETRED);
                ((ArrowArc) edg.edge).polyline.setStroke(Color.PALEVIOLETRED);
                ((ArrowArc) edg.edge).polyline.setFill(Color.PALEVIOLETRED);
            } else if (edg.edge instanceof QuadCurve) {
                ((QuadCurve) edg.edge).setStroke(Color.PALEVIOLETRED);
            } else if (edg.edge instanceof ArrowLine) {
                ((ArrowLine) edg.edge).line.setStroke(Color.PALEVIOLETRED);
                ((ArrowLine) edg.edge).polyline.setStroke(Color.PALEVIOLETRED);
                ((ArrowLine) edg.edge).polyline.setFill(Color.PALEVIOLETRED);
            } else {
                if (edg.edge instanceof Line) {
                    ((Line) edg.edge).setStroke(Color.PALEVIOLETRED);
                }
            }
        }
        for (int j = 0; j < Main.numberOfNodes; j++) {
            if (Main.matrix[i][j] > 0 && visited[j] == 0) {
                MyNode nbr = Main.nodeList[j];
                queuePred.add(curr.numberOfNode-1);
                nbr.circle.setFill(Color.GRAY);
                Main.queue.add(Main.nodeList[j]);
            }
        }
    }
}
