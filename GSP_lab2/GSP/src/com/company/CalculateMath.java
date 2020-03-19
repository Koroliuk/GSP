package com.company;

public class CalculateMath {
    public static int[][] createMatrixOfUnOrientedGraph(int n, int[][] a) {
        int[][] newMatrix = new int[n][n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                newMatrix[i][j] = (Math.abs(a[i][j]+a[j][i])>0?1:0);
            }
        }
        return newMatrix;
    }

    public static int[][] degreesOfNodesOr(int n, int[][] matrix) {
        int[][] ans = new int[n][2];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                ans[i][0]+=matrix[i][j];//out
                ans[j][1]+=matrix[i][j];//in
            }
        }
        return ans;
    }

    public static int[] degreesOfNodesUn(int n, int[][] matrix) {
        int[] ans = new int[n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                ans[i]+=matrix[i][j];
                if (i == j) {
                    ans[i]+=matrix[i][j];
                }
            }
        }
        return ans;
    }

    public static int setGraphRegularOr(int n, int[][] matrix) {
        int ans = Math.abs(matrix[0][0]);
        int someDegree = Math.abs(matrix[0][0]);
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < 2; j++) {
                if (Math.abs(matrix[i][j]) != someDegree) {
                    ans = -1;
                    break;
                }
            }
        }
        return ans;
    }

    public static int setGraphRegularUn(int n, int[] matrix) {
        int ans = matrix[0];
        int someDegree = matrix[0];
        for (int i = 0; i < n; i++) {
            if (matrix[i] != someDegree) {
                ans = -1;
                break;
            }
        }
        return ans;
    }
}
