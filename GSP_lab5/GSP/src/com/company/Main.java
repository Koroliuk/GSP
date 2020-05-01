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
    public static ArrayList<MyEdge> listEdgesOfTree = new ArrayList<>();
    public static ArrayList<MyEdge> edges;
    public static int[][] matrixOfTree;

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
        Label labelOfStep = new Label("Алгоритм за варіантом:\n" + "\n" + "        -Пріма"+" \n"+" \n");
        labelOfStep.setFont(new Font(15));
        Label labelOfEmpty = new Label("\n");
        labelOfEmpty.setFont(new Font(15));
        Button startButton = new Button("Побудувати граф");
        Button stepButton = new Button("Крок алгоритму");
        Button finishButton = new Button("Мінімальний кістяк");
        startButton.setPrefSize(176, 50);
        stepButton.setPrefSize(176, 50);
        finishButton.setPrefSize(176, 50);
        vBox1.getChildren().addAll(labelOfNodes, textFieldOfNodes, labelOfPosition, labelOfStep, labelOfEmpty);
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
        vBox2.setLayoutX(1550);
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
            MyEdge minEdge = null;
            int minWeight = 10000;
            ArrayList<Text> numbersOfWeight = new ArrayList<>();
            for (MyEdge edg : edges) {
                matrixEdges[edg.startNode-1][edg.finishNode-1] = edg;
                matrixEdges[edg.finishNode-1][edg.startNode-1] = edg;
                if (edg.weightOfEdge < minWeight && edg.startNode != edg.finishNode) {
                    minWeight = edg.weightOfEdge;
                    minEdge = edg;
                }
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
            assert minEdge != null;
            CalculateMath.queue.add(minEdge);
            for (Text text : numbersOfWeight) {
                root.getChildren().add(text);
            }
            for (int i = 0; i < numberOfNodes; i++) {
                for (int j = 0; j < numberOfNodes; j++) {
                    matrix[i][j] = Math.abs(matrix[i][j]);
                }
            }
        });

        stepButton.setOnAction(actionEvent -> {

            CalculateMath.buildTreeStep();
        });

        finishButton.setOnAction(actionEvent -> {
            for (MyEdge edge : listEdgesOfTree) {
                matrixOfTree[edge.startNode-1][edge.finishNode-1] = 1;
                matrixOfTree[edge.finishNode-1][edge.startNode-1] = 1;
            }
            VBox vBox = new VBox();
            Label labelOfTree = new Label("Матриця кістяка:");
            labelOfTree.setFont(new Font(15));
            vBox.getChildren().add(labelOfTree);
            for (int i = 0; i < numberOfNodes; i++) {
                StringBuilder line = new StringBuilder("    ");
                for (int j = 0; j < numberOfNodes; j++) {
                    line.append(matrixOfTree[i][j]).append(" ");
                }
                Label labelOfLine = new Label(String.valueOf(line));
                labelOfLine.setFont(new Font(15));
                vBox.getChildren().add(labelOfLine);
            }
            vBox.setLayoutX(1330);
            vBox.setLayoutY(550);
            root.getChildren().add(vBox);
        });

        root.getChildren().addAll(exitHBox,vBox1,vBox2);

        stage.setTitle("GSP");
        stage.setScene(scene);
        stage.show();

    }
}