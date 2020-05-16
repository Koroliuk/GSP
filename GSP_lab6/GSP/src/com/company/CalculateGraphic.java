package com.company;

import javafx.scene.Node;
import javafx.scene.paint.Color;
import javafx.scene.shape.Arc;
import javafx.scene.shape.ArcType;
import javafx.scene.shape.Line;
import javafx.scene.shape.QuadCurve;

import java.util.ArrayList;

public class CalculateGraphic {
    public static MyNode[] makeNodes(int n) {
        MyNode[] nodes = new MyNode[n];
        if (n == 1) {
            nodes[0] = new MyNode(660, 500, 25, 1, 1);
        } else if (n == 2) {
            nodes[0] = new MyNode(400, 350, 25, 1, 1);
            nodes[1] = new MyNode(800, 650, 25, 2, 2);
        } else {
            int kilAB, kilBC, kilCA;
            if (n % 3 == 0) {
                kilAB = (n / 3) + 1;
                kilBC = (n / 3) + 1;
                kilCA = (n / 3) + 1;
            } else if (n % 3 == 1) {
                kilAB = (n / 3) + 1;
                kilBC = (n / 3) + 2;
                kilCA = (n / 3) + 1;
            } else {
                kilAB = (n / 3) + 2;
                kilBC = (n / 3) + 1;
                kilCA = (n / 3) + 2;
            }
            int AB = kilAB;
            int BC = kilBC;
            int CA = kilCA;
            double tAB = 1 / (kilAB - 1.0);
            double tBC = 1 / (kilBC - 1.0);
            double tCA = 1 / (kilCA - 1.0);
            int i = 0;
            int side = 1; //1 to AB, 2 to BC, 3 to CA
            while (kilAB+ kilBC +kilCA > 0) {
                if (kilAB > 0) {
                    double xCircleI = -560*(AB-kilAB)*tAB+660.0;
                    double yCircleI = 800*(AB-kilAB)*tAB+100.0;
                    nodes[i] = new MyNode(xCircleI, yCircleI, 25, i+1, (i==0)?13:1);
                    i++;
                    kilAB--;
                } else if (kilBC > 0) {
                    if (side == 1) {
                        kilBC--;
                        side = 2;
                        nodes[i-1].sideOfNode = 12;
                    }
                    double xCircleI = 1100*(BC- kilBC)*tBC+100.0;
                    double yCircleI = 900.0;
                    nodes[i] = new MyNode(xCircleI, yCircleI, 25, i+1, side);
                    i++;
                    kilBC--;
                } else {
                    if (side == 2) {
                        kilCA--;
                        side = 3;
                        nodes[i-1].sideOfNode = 23;
                    }
                    if (kilCA == 1) {
                        break;
                    }
                    double xCircleI = -540*(CA-kilCA)*tCA+1200.0;
                    double yCircleI = -800*(CA-kilCA)*tCA+900.0;
                    nodes[i] = new MyNode(xCircleI, yCircleI, 25, i+1, side);
                    i++;
                    kilCA--;
                }
            }
        }
        return nodes;
    }

    public static ArrayList<MyEdge> makeEdge(int n, int[][] matrix, MyNode[] nodes, boolean orientation) {
        ArrayList<MyEdge> edgeList = new ArrayList<>();
        int[][] a = matrix.clone();
        for (int i = 0; i < a.length; i++){
            for (int j = 0; j < a[i].length; j++){
                if (orientation) {
                    if (a[i][j] == 1 && a[j][i] == 1 && i!=j) {
                        nodes[i].isHaveDoubleEdge = "Creater";
                        nodes[j].isHaveDoubleEdge = "Inheriter";
                        MyEdge forward = new MyEdge(i+1, j+1, makeNoLoopEdge(n, nodes[i], nodes[j], true));
                        MyEdge back = new MyEdge(j+1, i+1, makeNoLoopEdge(n, nodes[j], nodes[i], true));
                        edgeList.add(forward);
                        edgeList.add(back);
                        nodes[i].isHaveDoubleEdge = "No";
                        nodes[j].isHaveDoubleEdge = "No";
                        a[i][j] = -1;
                        a[j][i] = -1;
                    } else if (a[i][j] > 0 &&  i == j) {
                        MyEdge forwardAndBack = new MyEdge(i+1, i+1, makeLoopEdge(nodes[i]));
                        edgeList.add(forwardAndBack);
                        a[i][j] = -1;
                    } else {
                        if (a[i][j] == 1) {
                            MyEdge forward = new MyEdge(j+1, i+1, makeNoLoopEdge(n, nodes[j], nodes[i], true));
                            edgeList.add(forward);
                            a[i][j] = -1;
                        }
                    }
                } else {
                    if (a[i][j] == 1 && a[j][i] == 1 && i == j) {
                        MyEdge forwardAndBack = new MyEdge(i+1, i+1, makeLoopEdge(nodes[i]));
                        edgeList.add(forwardAndBack);
                        a[i][j] = -1;
                    } else {
                        if (a[i][j] == 1 && a[j][i] == 1) {
                            MyEdge forwardAndBack = new MyEdge(i+1, j+1, makeNoLoopEdge(n, nodes[i], nodes[j], false));
                            edgeList.add(forwardAndBack);
                            a[i][j] = -1;
                            a[j][i] = -1;
                        }
                    }
                }
            }
        }
        return edgeList;
    }

    private static Node makeLoopEdge(MyNode node) {
        double centerX = node.circle.getCenterX();
        double centerY = node.circle.getCenterY();
        int startAngle, loopLength;
        if (node.sideOfNode == 1 || node.sideOfNode == 13 || node.sideOfNode == 12) {
            centerX-=25;
            centerY-=25;
            startAngle = 0;
            loopLength = 270;
        } else if (node.sideOfNode == 2 || node.sideOfNode == 23) {
            centerY+=30;
            startAngle = -210;
            loopLength = 245;
        } else {
            centerX+=25;
            centerY-=25;
            startAngle = -90;
            loopLength = 265;
        }
        Arc loopEdge = new Arc(centerX, centerY, 20, 20, startAngle, loopLength);
        loopEdge.setType(ArcType.OPEN);
        loopEdge.setFill(Color.TRANSPARENT);
        loopEdge.setStroke(Color.SLATEGRAY);
        loopEdge.setStrokeWidth(2);
        return loopEdge;
    }

    private static Node makeNoLoopEdge(int n, MyNode ot, MyNode ku, boolean flag) {
        int numOfNbrL = ot.numberOfNode+1 > n ? 1 : ot.numberOfNode+1;
        int numOfNbrR = ot.numberOfNode-1 == 0 ? n : ot.numberOfNode-1;
        if ((ot.isHaveDoubleEdge.equals("No")) && (numOfNbrL == ku.numberOfNode || numOfNbrR == ku.numberOfNode)
                || (ot.isHaveDoubleEdge.equals("No") && ot.sideOfNode < 10 && ku.sideOfNode != ot.sideOfNode && ku.sideOfNode < 10)
                || (ot.isHaveDoubleEdge.equals("No") && ot.sideOfNode == 1 && ku.sideOfNode == 23)
                || (ot.isHaveDoubleEdge.equals("No") && ot.sideOfNode == 2 && ku.sideOfNode == 13)
                || (ot.isHaveDoubleEdge.equals("No") && ot.sideOfNode == 3 && ku.sideOfNode == 12)
                || (ot.isHaveDoubleEdge.equals("No") && ot.sideOfNode == 13 && ku.sideOfNode == 2)
                || (ot.isHaveDoubleEdge.equals("No") && ot.sideOfNode == 12 && ku.sideOfNode == 3)
                || (ot.isHaveDoubleEdge.equals("No") && ot.sideOfNode == 23 && ku.sideOfNode == 1)) {
            double otX, otY, kuX, kuY;
            double[] equalOfLine = equationOfLine(ot.circle.getCenterX(), ot.circle.getCenterY(), ku.circle.getCenterX(), ku.circle.getCenterY());
            double k = equalOfLine[0];
            double b = equalOfLine[1];
            double[] lineOtIntersection = lineCircleIntersection(k, b, ot.circle.getCenterX(), ot.circle.getCenterY(), ku.circle.getCenterX(), ku.circle.getCenterY(), 25);
            otX = lineOtIntersection[0];
            otY = lineOtIntersection[1];
            double[] lineKuIntersection = lineCircleIntersection(k, b, ku.circle.getCenterX(), ku.circle.getCenterY(), ot.circle.getCenterX(), ot.circle.getCenterY(), 25);
            kuX = lineKuIntersection[0];
            kuY = lineKuIntersection[1];
            if (flag) {
                return new ArrowLine(kuX, kuY, otX, otY);
            }
            else {
                Line edge = new Line(otX, otY, kuX, kuY);
                edge.setStroke(Color.SLATEGRAY);
                edge.setStrokeWidth(2);
                return edge;
            }
        } else {
            double controlX, controlY, otX, otY, kuX, kuY, kOt, bOt, kKu, bKu;
            double[] controlXY = (ot.isHaveDoubleEdge.equals("No"))?findControlXY(ot, ku):findControlXYoriented(ot, ku);
            controlX = controlXY[0];
            controlY = controlXY[1];

            if (ot.isHaveDoubleEdge.equals("Inheriter")) {
                controlX = ot.newControlX;
                controlY = ot.newConrtolY;
            }
            double[] eqaulOflineOt = equationOfLine(ot.circle.getCenterX(), ot.circle.getCenterY(), controlX, controlY);
            kOt = eqaulOflineOt[0];
            bOt = eqaulOflineOt[1];

            double[] otXotY = lineCircleIntersection(kOt, bOt, ot.circle.getCenterX(), ot.circle.getCenterY(), controlX, controlY, 25);
            double[] eqaulOflineKu = equationOfLine(ku.circle.getCenterX(), ku.circle.getCenterY(), controlX, controlY);
            kKu = eqaulOflineKu[0];
            bKu = eqaulOflineKu[1];

            double[] kuXkuY = lineCircleIntersection(kKu, bKu, ku.circle.getCenterX(), ku.circle.getCenterY(), controlX, controlY, 25);
            otX = otXotY[0];
            otY = otXotY[1];
            kuX = kuXkuY[0];
            kuY = kuXkuY[1];
            if (flag) {
                if (ot.isHaveDoubleEdge.equals("Creater")) {
                    double midX = (ot.circle.getCenterX()+ku.circle.getCenterX())/2d;
                    double midY = (ot.circle.getCenterY()+ku.circle.getCenterY())/2d;
                    ku.newControlX = 2*midX - controlX;
                    ku.newConrtolY = 2*midY - controlY;

                }
                return new ArrowArc(kuX, kuY, controlX, controlY, otX, otY);
            } else {
                QuadCurve edge = new QuadCurve(otX, otY, controlX, controlY, kuX, kuY);
                edge.setStrokeWidth(2);
                edge.setFill(Color.TRANSPARENT);
                edge.setStroke(Color.SLATEGRAY);
                return edge;
            }

        }
    }

    private static double[] equationOfLine(double x1, double y1, double x2, double y2) {
        return new double[]{(y2-y1)/(x2-x1), (y1*x2 - x1*y2)/(x2-x1)};
    }

    private static double[] lineCircleIntersection(double k, double b, double otX, double otY, double kuX, double kuY, double r) {
        double d =(Math.pow((2*k*b-2*otX-2*otY*k),2)-(4+4*k*k)*(b*b-r*r+otX*otX+otY*otY-2*otY*b));
        double x1=((-(2*k*b-2*otX-2*otY*k)-Math.sqrt(d))/(2+2*k*k));
        double x2=((-(2*k*b-2*otX-2*otY*k)+Math.sqrt(d))/(2+2*k*k));
        double y1=k*x1+b;
        double y2=k*x2+b;
        if (lenOfLine(x1, y1, kuX, kuY) > lenOfLine(x2, y2, kuX, kuY)) {
            return new double[]{x2, y2};
        } else {
            return new double[]{x1, y1};
        }
    }

    private static double[] findControlXY(MyNode ot, MyNode ku) {
        double x01 = ot.circle.getCenterX();
        double y01 = ot.circle.getCenterY();
        double x02 = ku.circle.getCenterX();
        double y02 = ku.circle.getCenterY();
        double midX = (x01+x02)/2d;
        double midY = (y01+y02)/2d;
        double l = Math.sqrt(Math.pow((x01-x02), 2)+Math.pow((y01-y02), 2));
        if ((ot.sideOfNode == 13 || ot.sideOfNode == 1 || ot.sideOfNode == 12)
                && (ku.sideOfNode == 13 || ku.sideOfNode == 1 || ku.sideOfNode == 12)) {
            return new double[]{midX-4.5/13d*l, midY-l/8};
        }
        if ((ot.sideOfNode == 12 || ot.sideOfNode == 2 || ot.sideOfNode == 23)
                && (ku.sideOfNode == 12 || ku.sideOfNode == 2 || ku.sideOfNode == 23)) {
            return new double[]{midX, midY-4.5d/13d*l};
        }
        if ((ot.sideOfNode == 23 || ot.sideOfNode == 3 || ot.sideOfNode == 13)
                && (ku.sideOfNode == 23 || ku.sideOfNode == 3 || ku.sideOfNode == 13)) {
            return new double[]{midX+4.5d/13d*l, midY-l/8};
        }
        return new double[]{0, 0};
    }

    private static double[] findControlXYoriented(MyNode ot, MyNode ku) {
        double x01 = ot.circle.getCenterX();
        double y01 = ot.circle.getCenterY();
        double x02 = ku.circle.getCenterX();
        double y02 = ku.circle.getCenterY();
        double midX = (x01+x02)/2d;
        double midY = (y01+y02)/2d;
        double k = (y01 - y02) / ( x01 - x02 );
        if (k == 0) {
            return new double[]{(x01+x02)/2d, y01+80};
        } else {
            k = -1 / k;
            double b = midY - k * midX;
            double x = midX;
            double y = midY;
            while (Math.sqrt(Math.pow((x - midX), 2) + (Math.pow((y - midY), 2))) < 90) {
                x++;
                y = k * x + b;
            }
            return new double[]{x, y};
        }
    }

    private static double lenOfLine(double x1, double y1, double x2, double y2) {
        return Math.sqrt(Math.pow((x1-x2), 2)+Math.pow((y1-y2), 2));
    }
}