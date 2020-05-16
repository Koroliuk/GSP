package com.company;


public class Control {

    public static void setNumberOfNodes(CharSequence numberOfNodes) {
        Main.numberOfNodes = Integer.parseInt(numberOfNodes.toString());
        Main.matrixEdges = new MyEdge[Main.numberOfNodes][Main.numberOfNodes];
        CalculateMath.length = new int[Main.numberOfNodes];
        for (int i = 0; i < Main.numberOfNodes; i++) {
            CalculateMath.length[i] = Main.infinity;
        }
        CalculateMath.mark = new char[Main.numberOfNodes];
        for (int i = 0; i < Main.numberOfNodes; i++) {
            CalculateMath.mark[i] = 'T';
        }
        CalculateMath.prev = new int[Main.numberOfNodes];
        for (int i = 0; i < Main.numberOfNodes; i++) {
            CalculateMath.prev[i] = -1;
        }
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
        Main.matrix = CalculateMath.createMatrixOfUnOrientedGraph(Main.numberOfNodes, matrix);
    }

    public static void setMatrixW(String text) {
        char[] s = text.toCharArray();
        int[][] matrix = new int[Main.numberOfNodes][Main.numberOfNodes];
        int column = 0;
        int row = 0;
        char pred = ' ';
        StringBuilder num = new StringBuilder();
        for (char c : s) {
            if (c != '\n' && c != ' ') {
                num.append(c);
            }
            if (c == ' ') {
                matrix[row][column] = Integer.parseInt(String.valueOf(num));
                column++;
                num = new StringBuilder();
            }
            if (c == '\n'){
                if (pred != ' ') {
                    matrix[row][column] = Integer.parseInt(String.valueOf(num));
                }
                num = new StringBuilder();
                row++;
                column = 0;
            }
            pred = c;
        }
        if (!String.valueOf(num).equals("")) {
            matrix[Main.numberOfNodes-1][Main.numberOfNodes-1] = Integer.parseInt(String.valueOf(num));
        }
        Main.matrixW = matrix;
    }
}
