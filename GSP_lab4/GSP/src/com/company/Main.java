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
    public static boolean orientation = true;
    public static int[][] matrix;
    public static MyNode[] nodeList;
    public static ArrayList<MyNode> queue = new ArrayList<>();
    public static int[][] matrixOfNodes;
    public static ArrayList<MyEdge> edges;
    public static int[][] matrixOfTreeOld;
    public static int[] numeration;

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
        textFieldOfNodes.setPrefColumnCount(9);
        Label labelOfPosition = new Label("Розміщення за варіантом:\n" + "\n" + "        -трикутник"+" \n"+" \n");
        labelOfPosition.setFont(new Font(15));
        Label labelOfStep = new Label("Обхід за варіантом:\n" + "\n" + "        -в ширину"+" \n"+" \n");
        labelOfStep.setFont(new Font(15));
        vBox1.getChildren().addAll(labelOfNodes, textFieldOfNodes, labelOfPosition, labelOfStep);
        vBox1.setSpacing(18);
        vBox1.setLayoutX(1330);
        vBox1.setLayoutY(20);

        VBox vBox2 = new VBox();
        Label labelOfMatrix = new Label("Матриця суміжності напрямленого графа:");
        labelOfMatrix.setFont(new Font(15));
        TextArea textAreaOfMatrix = new TextArea();
        textAreaOfMatrix.setPrefColumnCount(12);
        textAreaOfMatrix.setPrefRowCount(13);
        vBox2.getChildren().addAll(labelOfMatrix, textAreaOfMatrix);
        vBox2.setSpacing(10);
        vBox2.setLayoutX(1550);
        vBox2.setLayoutY(20);

        HBox hBox3 = new HBox();
        Button startButton = new Button("Побудувати граф");
        Button stepButton = new Button("           Крок \n обходу в ширину");
        Button finishButton = new Button(" Дерево та матриця \n    відповідностей  \n          вершин");
        startButton.setPrefSize(176, 80);
        stepButton.setPrefSize(176, 80);
        finishButton.setPrefSize(176, 80);

        startButton.setOnAction(actionEvent -> {
            Control.setNumberOfNodes(textFieldOfNodes.getCharacters());
            Control.setMatrix(textAreaOfMatrix.getText());
            MyNode[] nodes = CalculateGraphic.makeNodes(numberOfNodes);
            for (MyNode node : nodes) {
                root.getChildren().add(node.circle);
                root.getChildren().add(node.textOfNode);
            }
            nodeList = nodes;
            edges = CalculateGraphic.makeEdge(numberOfNodes, matrix, nodes, orientation);
            for (MyEdge edg : edges) {
                if (edg.edge instanceof ArrowArc) {
                    root.getChildren().add(((ArrowArc) edg.edge).polyline);
                    root.getChildren().add(((ArrowArc) edg.edge).quadCurve);
                } else if (edg.edge instanceof ArrowLine) {
                    root.getChildren().add(((ArrowLine) edg.edge).polyline);
                    root.getChildren().add(((ArrowLine) edg.edge).line);
                } else {
                    root.getChildren().add(edg.edge);
                }
            }
            for (int i = 0; i < numberOfNodes; i++) {
                for (int j = 0; j < numberOfNodes; j++) {
                    matrix[i][j] = Math.abs(matrix[i][j]);
                }
            }

            for (int i = 0; i < numberOfNodes; i++) {
                int su = 0;
                for (int el : matrix[i]) {
                    su+=el;
                }
                if (su > 0) {
                    queue.add(nodeList[i]);
                    break;
                }
            }
            CalculateMath.queuePred.add(-1);
        });

        stepButton.setOnAction(actionEvent -> {
            CalculateMath.bfsStep();
        });

        finishButton.setOnAction(actionEvent -> {
            Rectangle graphFone2 = new Rectangle(10, 10, 1300, 1000);
            graphFone2.setFill(Color.WHITE);
            root.getChildren().add(graphFone2);
            for (MyNode node : nodeList) {
                root.getChildren().removeAll(node.circle, node.textOfNode);
            }

            MyNode[] nodeListBFS = CalculateGraphic.makeNodes(11);
            for (MyNode node : nodeListBFS) {
                int num = 0;
                for (int j = 0; j < numberOfNodes; j++) {
                    if (matrixOfNodes[node.numberOfNode-1][j] > 0) {
                        num = j+1;
                    }
                }
                if (num > 0) {
                    double textX = node.circle.getCenterX() - 5.0;
                    double textY = node.circle.getCenterY() + 5.0;
                    if (num > 9) {
                        textX-=5.0;
                    }
                    node.textOfNode = new Text(textX, textY, String.valueOf(num));
                    node.textOfNode.setFont(new Font(17));
                }
                root.getChildren().addAll(node.circle, node.textOfNode);
            }

            VBox vBox = new VBox();
            Label label0 = new Label("Одержана нумерація:");
            label0.setFont(new Font(16));
            vBox.getChildren().add(label0);
            StringBuilder line1 = new StringBuilder("    ");
            for (int j = 0; j < numberOfNodes; j++) {
                line1.append(j+1).append(" ");
            }
            Label labelOfLine1 = new Label(String.valueOf(line1));
            labelOfLine1.setFont(new Font(15));
            vBox.getChildren().add(labelOfLine1);
            StringBuilder line2 = new StringBuilder("    ");
            for (int j = 0; j < numberOfNodes; j++) {
                for (int k = 0; k < numberOfNodes; k++) {
                    if (j == numeration[k]) {
                        line2.append(k+1).append(" ");
                    }
                }
            }
            Label labelOfLine2 = new Label(String.valueOf(line2));
            labelOfLine2.setFont(new Font(15));
            vBox.getChildren().add(labelOfLine2);

            Label label = new Label("Матриця відповідностей вершин:");
            label.setFont(new Font(16));
            vBox.getChildren().add(label);
            for (int i = 0; i < numberOfNodes; i++) {
                StringBuilder line = new StringBuilder("    ");
                for (int j = 0; j < numberOfNodes; j++) {
                    line.append(matrixOfNodes[i][j]).append(" ");
                }
                Label labelOfLine = new Label(String.valueOf(line));
                labelOfLine.setFont(new Font(15));
                vBox.getChildren().add(labelOfLine);
            }

            ArrayList<MyEdge> edgesListOfTree = CalculateGraphic.makeEdge(numberOfNodes, matrixOfTreeOld, nodeListBFS, true);
            for (MyEdge edg : edgesListOfTree) {
                if (edg.edge instanceof ArrowArc) {
                    root.getChildren().add(((ArrowArc) edg.edge).polyline);
                    root.getChildren().add(((ArrowArc) edg.edge).quadCurve);
                } else if (edg.edge instanceof ArrowLine) {
                    root.getChildren().add(((ArrowLine) edg.edge).polyline);
                    root.getChildren().add(((ArrowLine) edg.edge).line);
                } else {
                    root.getChildren().add(edg.edge);
                }
            }

            int[][] matrixOfTreeNew = new int[numberOfNodes][numberOfNodes];
            for (int i = 0; i < numberOfNodes; i++) {
                for (int j = 0; j < numberOfNodes; j++) {
                    int st = 0;
                    int fn = 0;
                    for (int k = 0; k < numberOfNodes; k++) {
                        if (matrixOfNodes[i][k] == 1) {
                            st = k;
                        }
                    }
                    for (int k = 0; k < numberOfNodes; k++) {
                        if (matrixOfNodes[j][k] == 1) {
                            fn = k;
                        }
                    }
                    if (matrixOfTreeOld[i][j] == -1) {
                        matrixOfTreeNew[st][fn] = 1;
                    }
                }
            }

            Label label1 = new Label("Матриця дерева:");
            label1.setFont(new Font(16));
            vBox.getChildren().add(label1);
            for (int i = 0; i < numberOfNodes; i++) {
                StringBuilder line = new StringBuilder("    ");
                for (int j = 0; j < numberOfNodes; j++) {
                    line.append(matrixOfTreeNew[i][j]).append(" ");
                }
                Label labelOfLine = new Label(String.valueOf(line));
                labelOfLine.setFont(new Font(15));
                vBox.getChildren().add(labelOfLine);
            }
            vBox.setLayoutX(1330);
            vBox.setLayoutY(380);
            root.getChildren().add(vBox);
        });

        hBox3.getChildren().addAll(startButton, stepButton, finishButton);
        hBox3.setSpacing(15);
        hBox3.setLayoutX(1330);
        hBox3.setLayoutY(290);

        root.getChildren().addAll(exitHBox,vBox1,vBox2,hBox3);

        stage.setTitle("GSP");
        stage.setScene(scene);
        stage.show();

    }
}