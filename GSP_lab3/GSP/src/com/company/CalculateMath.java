package com.company;

import java.util.ArrayList;

public class CalculateMath {
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

    public static int[][] composition(int n, int[][] matrix1, int[][] matrix2) {
        int[][] newMatrix = new int[n][n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (matrix1[i][j] == 1) {
                    for (int k = 0; k < n; k++) {
                        if (matrix2[j][k] == 1) {
                            newMatrix[i][k] = 1;
                        }
                    }
                }
            }
        }
        return newMatrix;
    }

    public static int[][] nTimesComposition(int n, int[][] matrix, int a) {
        int[][] newMatrix = new int[n][n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                newMatrix[i][j] = matrix[i][j];
            }
        }
        while (a > 1) {
            a-=1;
            newMatrix=composition(n, matrix, newMatrix);
        }
        return newMatrix;
    }

    public static int[][] TRClosure(int n, int[][] matrix) {
        int[][] newMatrix = new int[n][n];
        int nn = n-1;
        while (nn > 0) {
            int[][] matrix_i = nTimesComposition(n, matrix, nn);
            nn--;
            for (int i = 0; i < n; i++) {
                for (int j = 0; j < n; j++) {
                    newMatrix[i][j]+=matrix_i[i][j];
                }
            }
        }
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (newMatrix[i][j] > 0) {
                    newMatrix[i][j] = 1;
                }
                if (i == j){
                    newMatrix[i][j] = 1;
                }
            }
        }
        return newMatrix;
    }

    public static int[][] getMatrixOfComponents(int n, int[][] r) {
        int[][] rt = new int[n][n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                rt[j][i] = r[i][j];
            }
        }
        int[][] newMatrix = new int[n][n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                newMatrix[i][j] = rt[i][j]*r[i][j];
            }
        }
        return newMatrix;
    }

    public static ArrayList<ArrayList<Integer>> getListOfComponents(int n, int[][] s) {
        System.out.println();
        int[] nodes = new int[n];
        ArrayList<ArrayList<Integer>> ans = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            ArrayList<Integer> ans_i = new ArrayList<>();
            for (int j = 0; j < n; j++) {
                if (s[i][j] == 1) {
                    if (nodes[j] == 1) {
                        break;
                    }
                    ans_i.add(j + 1);
                    nodes[j] = 1;
                }
            }
            if (ans_i.size() != 0) {
                ans.add(ans_i);
            }
        }
        return ans;
    }

    public static int[][] getEdgesOfConGraph(ArrayList<ArrayList<Integer>> arr, int[][] matrix) {
        int n = arr.size();
        int[][] newMatrix = new int[n][n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (i != j) {
                    boolean flag = false;
                    for (int ii = 0; ii < arr.get(i).size(); ii++) {
                        for (int jj = 0; jj < arr.get(j).size(); jj++) {
                            if (matrix[arr.get(i).get(ii) - 1][arr.get(j).get(jj) - 1] == 1) {
                                flag = true;
                                break;
                            }
                        }
                    }
                    if (flag) {
                        newMatrix[i][j] = 1;
                    } else {
                        newMatrix[i][j] = 0;
                    }
                }
            }
        }
        return newMatrix;
    }
}
