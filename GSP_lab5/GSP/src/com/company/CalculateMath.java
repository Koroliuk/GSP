package com.company;


import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.QuadCurve;

import java.util.ArrayList;


public class CalculateMath {
    public static int[] visited = new int[Main.numberOfNodes];
    public static int[] addingOfEdgesOfNode = new int[Main.numberOfNodes];
    public static ArrayList<MyEdge> queue = new ArrayList<>();

    public static void buildTreeStep() {
        int count = 0;
        for (int el : visited) {
            count+=el;
        }
        if (!(count == visited.length)) {
            for (int i = 0; i < visited.length; i++) {
                if (visited[i] == 1 && addingOfEdgesOfNode[i] == 0) {
                    for (int j = 0; j < Main.numberOfNodes; j++) {
                        MyEdge toJEgde = Main.matrixEdges[i][j];
                        if (toJEgde == null) {
                            continue;
                        }
                        if (!queue.contains(toJEgde)
                                && (toJEgde.startNode != toJEgde.finishNode)
                                && (visited[toJEgde.finishNode-1] == 0 || visited[toJEgde.startNode-1] == 0)) {
                            queue.add(Main.matrixEdges[i][j]);
                        }
                    }
                    addingOfEdgesOfNode[i] = 1;
                }
            }
            MyEdge minEdge = null;
            int minWeight = 1000;
            for (MyEdge edge : queue) {
                if (edge.weightOfEdge < minWeight
                        && (visited[edge.finishNode-1] == 0 || visited[edge.startNode-1] == 0)) {
                    minEdge = edge;
                    minWeight = edge.weightOfEdge;
                }
            }
            Main.listEdgesOfTree.add(minEdge);
            assert minEdge != null;
            minEdge.textOfWeight.setFill(Color.MAROON);
            minEdge.textOfWeight.setStroke(Color.MAROON);
            if (minEdge.edge instanceof ArrowArc){
                ((ArrowArc) minEdge.edge).quadCurve.setStroke(Color.PALEVIOLETRED);
                ((ArrowArc) minEdge.edge).polyline.setStroke(Color.PALEVIOLETRED);
                ((ArrowArc) minEdge.edge).polyline.setFill(Color.PALEVIOLETRED);
            } else if (minEdge.edge instanceof QuadCurve) {
                ((QuadCurve) minEdge.edge).setStroke(Color.PALEVIOLETRED);
            } else if (minEdge.edge instanceof ArrowLine) {
                ((ArrowLine) minEdge.edge).line.setStroke(Color.PALEVIOLETRED);
                ((ArrowLine) minEdge.edge).polyline.setStroke(Color.PALEVIOLETRED);
                ((ArrowLine) minEdge.edge).polyline.setFill(Color.PALEVIOLETRED);
            } else {
                if (minEdge.edge instanceof Line) {
                    ((Line) minEdge.edge).setStroke(Color.PALEVIOLETRED);
                }
            }
            visited[minEdge.startNode-1] = 1;
            visited[minEdge.finishNode-1] = 1;
            queue.remove(minEdge);
        }
    }

    public static int[][] createMatrixOfUnOrientedGraph(int n, int[][] a) {
        int[][] newMatrix = new int[n][n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                newMatrix[i][j] = (Math.abs(a[i][j]+a[j][i])>0?1:0);
            }
        }
        return newMatrix;
    }
}
