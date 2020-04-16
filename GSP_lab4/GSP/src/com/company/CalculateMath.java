package com.company;

import javafx.scene.paint.Color;

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
