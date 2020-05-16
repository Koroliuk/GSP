package com.company;


public class CalculateMath {
    public static int[] length;
    public static char[] mark;
    public static int[] prev;
    public static int countOfP = 0;
    public static boolean flag = false;

    public static int[][] createMatrixOfUnOrientedGraph(int n, int[][] a) {
        int[][] newMatrix = new int[n][n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                newMatrix[i][j] = (Math.abs(a[i][j]+a[j][i])>0?1:0);
            }
        }
        return newMatrix;
    }

    public static void dijkstraAlgorithm(MyNode startNode) {
        if (flag) {
            return;
        }
        int amount = Main.numberOfNodes;
        for (int i = 0; i < amount; i++) {
            if (mark[i] == 'P') {
                for (int j = 0; j < amount; j++) {
                    if (i == j) {
                        continue;
                    }
                    MyEdge edge = Main.matrixEdges[i][j];
                    if (edge == null) {
                        continue;
                    }
                    if (mark[edge.startNode-1] == 'T' || mark[edge.finishNode-1] == 'T') {
                        if (edge.startNode-1 == i) {
                            int t = length[edge.startNode-1]+edge.weightOfEdge;
                            if (t < length[edge.finishNode-1]) {
                                length[edge.finishNode-1] = t;
                                prev[edge.finishNode-1] = edge.startNode-1;
                            }
                        } else {
                            int t = length[edge.finishNode-1]+edge.weightOfEdge;
                            if (t < length[edge.startNode-1]) {
                                length[edge.startNode-1] = t;
                                prev[edge.startNode-1] = edge.finishNode-1;
                            }
                        }
                    }
                }
            }
        }
        int minNumberOfNode = 0;
        int minLength = Main.infinity;
        for (int i = 0; i < amount; i++) {
            if (i != startNode.numberOfNode-1) {
                if (length[i] < minLength && mark[i] == 'T') {
                    minLength = length[i];
                    minNumberOfNode = i;
                }
            }
        }
        mark[minNumberOfNode] = 'P';
        countOfP+=1;
        if (countOfP == amount) {
            flag = true;
        }
    }
}
