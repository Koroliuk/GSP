package com.company;

public class Control {

    public static void setNumberOfNodes(CharSequence numberOfNodes) {
        Main.numberOfNodes = Integer.parseInt(numberOfNodes.toString());
        Main.matrixOfNodes = new int[Main.numberOfNodes][Main.numberOfNodes];
        Main.matrixOfTreeOld = new int[Main.numberOfNodes][Main.numberOfNodes];
        Main.matrixOfEdges = new MyEdge[Main.numberOfNodes][Main.numberOfNodes];
        Main.numeration = new int[Main.numberOfNodes];
    }

    public static void setMatrix(String text) {
        char[] s = text.toCharArray();
        int[][] matrix = new int[Main.numberOfNodes][Main.numberOfNodes];
        int column = 0;
        int row = 0;
        for (char c : s) {
            if (c == '1' || c == '0') {
                matrix[row][column] = c == '1' ? 1 : 0;
                column++;
            }
            if (c == '\n') {
                row++;
                column = 0;
            }
        }
        Main.matrix = matrix;
    }
}
