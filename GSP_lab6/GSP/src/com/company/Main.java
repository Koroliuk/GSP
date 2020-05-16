package com.company;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.util.ArrayList;

public class Main extends Application {
    public static int numberOfNodes;
    public static int[][] matrix;
    public static int[][] matrixW;
    public static MyEdge[][] matrixEdges;
    public static MyNode[] nodeList;
    public static ArrayList<MyEdge> edges;
    public static MyNode startNode;
    public static int infinity = 1000000000;
    private boolean flag = false;
    private static int result1Y = 370;
    public static boolean showFlag = true;

    public static void main(String[] args) {
        Application.launch(args);
    }

    @Override
    public void start(Stage stage) {
        Group root = new Group();
        Scene scene = new Scene(root, 1920, 1080);
        scene.setFill(Color.LIGHTGRAY);

        Rectangle graphFone = new Rectangle(10, 10, 1300, 1000);
        graphFone.setFill(Color.WHITE);
        root.getChildren().add(graphFone);

        HBox exitHBox = new HBox();
        exitHBox.setPadding(new Insets(15, 12, 15, 12));
        Button exitButton = new Button("Вихід");
        exitButton.setOnAction(event -> stage.close());
        exitButton.setPrefSize(70, 20);
        exitHBox.getChildren().add(exitButton);
        exitHBox.setLayoutX(1810);
        exitHBox.setLayoutY(960);

        VBox vBox1 = new VBox();
        Label labelOfNodes = new Label("Кількість вершин:");
        labelOfNodes.setFont(new Font(15));
        TextField textFieldOfNodes = new TextField();
        textFieldOfNodes.setPrefColumnCount(11);
        Label labelOfPosition = new Label("Розміщення за варіантом:\n        -трикутник");
        labelOfPosition.setFont(new Font(15));
        Button startButton = new Button("Побудувати граф");
        Button stepButton = new Button("Крок алгоритму");
        Button finishButton = new Button("Результат");
        startButton.setPrefSize(190, 20);
        stepButton.setPrefSize(190, 20);
        finishButton.setPrefSize(190, 20);
        vBox1.getChildren().addAll(labelOfNodes, textFieldOfNodes, labelOfPosition);
        vBox1.getChildren().addAll(startButton, stepButton, finishButton);
        vBox1.setSpacing(18);
        vBox1.setLayoutX(1330);
        vBox1.setLayoutY(20);

        VBox vBox2 = new VBox();
        Label labelOfMatrix = new Label("Матриця суміжності напрямленого графа:");
        labelOfMatrix.setFont(new Font(15));
        TextArea textAreaOfMatrix = new TextArea();
        textAreaOfMatrix.setPrefColumnCount(12);
        textAreaOfMatrix.setPrefRowCount(13);
        Label labelOfMatrixW = new Label("Матриця ваг графа:");
        labelOfMatrixW.setFont(new Font(15));
        TextArea textAreaOfMatrixW = new TextArea();
        textAreaOfMatrixW.setPrefColumnCount(12);
        textAreaOfMatrixW.setPrefRowCount(13);
        vBox2.getChildren().addAll(labelOfMatrix, textAreaOfMatrix, labelOfMatrixW, textAreaOfMatrixW);
        vBox2.setSpacing(10);
        vBox2.setLayoutX(1570);
        vBox2.setLayoutY(20);

        startButton.setOnAction(actionEvent -> {
            Control.setNumberOfNodes(textFieldOfNodes.getCharacters());
            Control.setMatrix(textAreaOfMatrix.getText());
            Control.setMatrixW(textAreaOfMatrixW.getText());

            MyNode[] nodes = CalculateGraphic.makeNodes(numberOfNodes);
            for (MyNode node : nodes) {
                root.getChildren().add(node.circle);
                root.getChildren().add(node.textOfNode);
            }
            nodeList = nodes;
            edges = CalculateGraphic.makeEdge(numberOfNodes, matrix, nodes, false);
            ArrayList<Text> numbersOfWeight = new ArrayList<>();
            for (MyEdge edg : edges) {
                matrixEdges[edg.startNode-1][edg.finishNode-1] = edg;
                matrixEdges[edg.finishNode-1][edg.startNode-1] = edg;
                if (edg.edge instanceof ArrowArc) {
                    root.getChildren().add(((ArrowArc) edg.edge).polyline);
                    root.getChildren().add(((ArrowArc) edg.edge).quadCurve);
                } else if (edg.edge instanceof ArrowLine) {
                    root.getChildren().add(((ArrowLine) edg.edge).polyline);
                    root.getChildren().add(((ArrowLine) edg.edge).line);
                } else {
                    root.getChildren().add(edg.edge);
                }
                numbersOfWeight.add(edg.textOfWeight);
            }
            for (Text text : numbersOfWeight) {
                root.getChildren().add(text);
            }
            for (int i = 0; i < numberOfNodes; i++) {
                for (int j = 0; j < numberOfNodes; j++) {
                    matrix[i][j] = Math.abs(matrix[i][j]);
                }
            }
            for (int i = 0; i < numberOfNodes; i++) {
                boolean isNodeStart = false;
                for (int j = 0; j < numberOfNodes; j++) {
                    if (matrix[i][j] > 0) {
                        isNodeStart = true;
                        break;
                    }
                }
                if (isNodeStart) {
                    startNode = nodes[i];
                    break;
                }
            }
        });

        stepButton.setOnAction(actionEvent -> {
            if (flag) {
                CalculateMath.dijkstraAlgorithm(startNode);
            } else {
                for (MyNode node : nodeList) {
                    root.getChildren().remove(node.textOfNode);
                    double nodeX = node.textOfNode.getX() - 5;
                    double nodeY = node.textOfNode.getY();
                    node.textOfNode = new Text(nodeX, nodeY, String.valueOf(node.numberOfNode));
                    root.getChildren().add(node.textOfNode);
                }
                int start = startNode.numberOfNode-1;
                CalculateMath.length[start] = 0;
                CalculateMath.mark[start] = 'P';
                CalculateMath.prev[start] = -1;
                CalculateMath.countOfP+=1;
                flag = true;
                VBox vBox = new VBox();
                Label labelText = new Label("Зміна відстаней до вершин\nпротягом виконання\nалгоритму:");
                vBox.getChildren().add(labelText);
                labelText.setFont(new Font(14));
                StringBuilder line1 = new StringBuilder();
                for (int i = 0; i < numberOfNodes; i++) {
                    line1.append(i+1);
                    if (i != numberOfNodes-1) {
                        line1.append(" ");
                    }
                    if (i+1 < 10) {
                        line1.append(" ");
                    }
                }
                Label label1 = new Label(String.valueOf(line1));
                label1.setFont(new Font(13));
                label1.setUnderline(true);
                vBox.getChildren().add(label1);
                StringBuilder line2 = new StringBuilder();
                for (int i = 0; i < numberOfNodes; i++) {
                    String element = "";
                    if (CalculateMath.length[i] == infinity) {
                        element="∞";
                    } else {
                        element = String.valueOf(CalculateMath.length[i]);
                    }
                    line2.append(element).append(" ");
                    if (element.length() < 2 && element.equals("∞")) {
                        line2.append(" ");
                    }
                }
                Label label2 = new Label(String.valueOf(line2));
                label2.setFont(new Font(13));
                vBox.getChildren().add(label2);
                vBox.setLayoutX(1330);
                vBox.setLayoutY(300);
                root.getChildren().add(vBox);
            }
            for (MyNode node : nodeList) {
                root.getChildren().remove(node.textOfNode);
                double nodeX = node.textOfNode.getX();
                double nodeY = node.textOfNode.getY();
                node.textOfNode = new Text(nodeX, nodeY,node.numberOfNode + String.valueOf(CalculateMath.mark[node.numberOfNode-1]));
                root.getChildren().add(node.textOfNode);
            }
            if (showFlag) {
                StringBuilder line2 = new StringBuilder();
                VBox vBox = new VBox();
                for (int i = 0; i < numberOfNodes; i++) {
                    String element = "";
                    if (CalculateMath.length[i] == infinity) {
                        element="∞";
                    } else {
                        element = String.valueOf(CalculateMath.length[i]);
                    }
                    line2.append(element).append(" ");
                    if (element.length() < 2 && element.equals("∞")) {
                        line2.append(" ");
                    }
                }
                Label label = new Label(String.valueOf(line2));
                label.setFont(new Font(13));
                vBox.getChildren().add(label);
                vBox.setLayoutX(1330);
                vBox.setLayoutY(result1Y);
                result1Y+=15;
                root.getChildren().add(vBox);
                if (CalculateMath.flag) {
                    showFlag = false;
                }
            }
        });

        finishButton.setOnAction(actionEvent -> {
            for (int i = 0; i < numberOfNodes; i++) {
                System.out.print(CalculateMath.length[i]+" ");
            }
            System.out.println();
            for (int i = 0; i < numberOfNodes; i++) {
                System.out.print(CalculateMath.mark[i]+" ");
            }
            System.out.println();
            for (int i = 0; i < numberOfNodes; i++) {
                System.out.print(CalculateMath.prev[i]+" ");
            }
            System.out.println();
            VBox vBoxAns = new VBox();
            for (int i = 0; i < numberOfNodes; i++) {
                if (i == startNode.numberOfNode-1) {
                    Label label = new Label("Вершина №"+String.valueOf(i+1)+", від якої ми шукаємо мінімальні шляхи до інших");
                    label.setFont(new Font(14));
                    vBoxAns.getChildren().add(label);
                } else {
                    StringBuilder line = new StringBuilder("Мінімальний шлях ");
                    System.out.println(line);
                    StringBuilder line1 = new StringBuilder();
                    if (i+1 > 9) {
                        StringBuilder num = new StringBuilder(String.valueOf(i+1));
                        line1.append(num.reverse()).append("-");
                    } else {
                        line1.append(i+1).append("-");
                    }
                    int node = i;
                    while (CalculateMath.prev[node] != -1) {
                        if (node != i) {
                            line1.append("-");
                        }
                        if (CalculateMath.prev[node]+1 > 9) {
                            StringBuilder num = new StringBuilder(String.valueOf(CalculateMath.prev[node]+1));
                            line1.append(num.reverse());
                        } else {
                            line1.append(CalculateMath.prev[node]+1);
                        }
                        node = CalculateMath.prev[node];
                    }
                    line1.reverse();
                    line.append(line1);
                    System.out.println(line);
                    line.append(" ").append("між вершинами №").append(startNode.numberOfNode);
                    line.append(" та №").append(i+1);
                    System.out.println(line);
                    line.append(" має довжину ").append(CalculateMath.length[i]);
                    System.out.println(line);
                    Label label = new Label(String.valueOf(line));
                    label.setFont(new Font(14));
                    vBoxAns.getChildren().add(label);
                }
            }
            vBoxAns.setLayoutX(1330);
            vBoxAns.setLayoutY(550);
            root.getChildren().add(vBoxAns);
        });

        root.getChildren().addAll(exitHBox,vBox1,vBox2);

        stage.setTitle("GSP");
        stage.setScene(scene);
        stage.show();

    }
}