package com.company;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.Node;
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
    public static ArrayList<ArrayList<Integer>> listOfComponents;
    public static MyNode[] nodeList;

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
        vBox1.getChildren().addAll(labelOfNodes, textFieldOfNodes, labelOfPosition);
        vBox1.setSpacing(10);
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

        VBox vBox3 = new VBox();
        Button startButton = new Button("Побудувати граф");
        Button infoButton = new Button("Вивести інформацію");
        Button buildConGraph = new Button("Граф конденсації");
        startButton.setPrefSize(190, 25);
        infoButton.setPrefSize(190, 25);
        buildConGraph.setPrefSize(190, 25);

        startButton.setOnAction(actionEvent -> {
            Control.setNumberOfNodes(textFieldOfNodes.getCharacters());
            Control.setMatrix(textAreaOfMatrix.getText());
            MyNode[] nodes = CalculateGraphic.makeNodes(numberOfNodes);
            for (MyNode node : nodes) {
                root.getChildren().add(node.circle);
                root.getChildren().add(node.textOfNode);
            }
            nodeList = nodes;
            ArrayList<Node> edges = CalculateGraphic.makeEdge(numberOfNodes, matrix, nodes, orientation);
            for (Node edge : edges) {
                if (edge instanceof ArrowArc) {
                    root.getChildren().add(((ArrowArc) edge).polyline);
                    root.getChildren().add(((ArrowArc) edge).quadCurve);
                } else if (edge instanceof ArrowLine) {
                    root.getChildren().add(((ArrowLine) edge).polyline);
                    root.getChildren().add(((ArrowLine) edge).line);
                } else {
                    root.getChildren().add(edge);
                }
            }
            for (int i = 0; i < numberOfNodes; i++) {
                for (int j = 0; j < numberOfNodes; j++) {
                    matrix[i][j] = Math.abs(matrix[i][j]);
                }
            }
        });

        infoButton.setOnAction(actionEvent -> {
            int[][] listOfDegreesOfGraph = CalculateMath.degreesOfNodesOr(numberOfNodes, matrix);
            ArrayList<ArrayList<ArrayList<Integer>>> listWays23 = CalculateMath.waysLength23(numberOfNodes, matrix);
            ArrayList<ArrayList<Integer>> listWays2 = listWays23.get(0);
            ArrayList<ArrayList<Integer>> listWays3 = listWays23.get(1);
            int[][] closure = CalculateMath.TRClosure(numberOfNodes, matrix);
            int[][] components = CalculateMath.getMatrixOfComponents(numberOfNodes, closure);
            listOfComponents = CalculateMath.getListOfComponents(numberOfNodes, components);

            HBox hBoxAll = new HBox();

            VBox vBoxL = new VBox();

            Label labelDegrees = new Label("Півстепені вузлів:");
            labelDegrees.setFont(new Font(13));
            vBoxL.getChildren().add(labelDegrees);

            HBox hBoxDegrees = new HBox();
            VBox vBoxDegreesOut = new VBox();
            Label labelDegreeOut = new Label(" Напівстепені\n  заходу");
            labelDegreeOut.setFont(new Font(13));
            vBoxDegreesOut.getChildren().add(labelDegreeOut);
            for (int i = 0; i < numberOfNodes; i++) {
                Label labelOfDegreeOfNodeOr = new Label("    В.№"+(i+1)+": "+listOfDegreesOfGraph[i][1]);
                labelOfDegreeOfNodeOr.setFont(new Font(12));
                vBoxDegreesOut.getChildren().add(labelOfDegreeOfNodeOr);
            }
            hBoxDegrees.getChildren().add(vBoxDegreesOut);

            VBox vBoxDegreesIn = new VBox();
            Label labelDegreeIn = new Label("  Напівстепені\n  виходу");
            labelDegreeIn.setFont(new Font(13));
            vBoxDegreesIn.getChildren().add(labelDegreeIn);
            for (int i = 0; i < numberOfNodes; i++) {
                Label labelOfDegreeOfNodeOr = new Label("    В.№"+(i+1)+": "+listOfDegreesOfGraph[i][0]);
                labelOfDegreeOfNodeOr.setFont(new Font(12));
                vBoxDegreesIn.getChildren().add(labelOfDegreeOfNodeOr);
            }
            hBoxDegrees.getChildren().add(vBoxDegreesIn);
            vBoxL.getChildren().add(hBoxDegrees);

            VBox vBoxOfMatrixConnectivity = new VBox();
            Label labelTextOfMatrixConnectivity = new Label("Матриця зв'язності:");
            labelTextOfMatrixConnectivity.setFont(new Font(13));
            vBoxOfMatrixConnectivity.getChildren().add(labelTextOfMatrixConnectivity);
            for (int i = 0; i < numberOfNodes; i++) {
                StringBuilder line = new StringBuilder();
                line.append("   ");
                for (int j = 0; j < numberOfNodes; j++) {
                    line.append(" ").append(components[i][j]);
                }
                Label labelLine = new Label(String.valueOf(line));
                labelLine.setFont(new Font(12));
                vBoxOfMatrixConnectivity.getChildren().add(labelLine);
            }
            vBoxL.getChildren().add(vBoxOfMatrixConnectivity);

            VBox vBoxComponents = new VBox();
            for (int i = 0; i < listOfComponents.size(); i++) {
                StringBuilder line = new StringBuilder("Компонента зв'язності №");
                line.append(i+1).append("\n").append(" вершини:");
                for (int j = 0; j < listOfComponents.get(i).size(); j++) {
                    line.append(" ").append(listOfComponents.get(i).get(j));
                }
                Label labelLine = new Label(String.valueOf(line));
                labelLine.setFont(new Font(13));
                vBoxComponents.getChildren().add(labelLine);
            }
            vBoxL.getChildren().add(vBoxComponents);

            VBox vBoxMatrixClosure = new VBox();
            Label labelMatrixClosure = new Label("Матриця досяжності:");
            labelMatrixClosure.setFont(new Font(13));
            vBoxMatrixClosure.getChildren().add(labelMatrixClosure);
            for (int i = 0; i < numberOfNodes; i++) {
                StringBuilder line = new StringBuilder("   ");
                for (int j = 0; j < numberOfNodes; j++) {
                    line.append(" ").append(closure[i][j]);
                }
                Label labelLine = new Label(String.valueOf(line));
                labelLine.setFont(new Font(12));
                vBoxMatrixClosure.getChildren().add(labelLine);
            }
            vBoxL.getChildren().add(vBoxMatrixClosure);

            VBox vBoxR = new VBox();

            HBox hBoxWays = new HBox();
            VBox vBoxWays2 = new VBox();
            Label labelWays2 = new Label("Шляхи\nдовжини 2:");
            labelWays2.setFont(new Font(13));
            vBoxWays2.getChildren().add(labelWays2);
            for (ArrayList<Integer> integers : listWays2) {
                StringBuilder line = new StringBuilder("   ");
                for (int j = 0; j < 3; j++) {
                    if (j == 2) {
                        line.append(integers.get(j));
                    } else {
                        line.append(integers.get(j)).append("-");
                    }
                }
                Label labelLine = new Label(String.valueOf(line));
                labelLine.setFont(new Font(12));
                vBoxWays2.getChildren().add(labelLine);
            }
            hBoxWays.getChildren().add(vBoxWays2);

            VBox vBoxWays3 = new VBox();
            Label labelWays3 = new Label("Шляхи\nдовжини 3:");
            labelWays3.setFont(new Font(13));
            vBoxWays3.getChildren().add(labelWays3);
            for (ArrayList<Integer> integers : listWays3) {
                StringBuilder line = new StringBuilder("   ");
                for (int j = 0; j < 4; j++) {
                    if (j == 3) {
                        line.append(integers.get(j));
                    } else {
                        line.append(integers.get(j)).append("-");
                    }
                }
                Label labelLine = new Label(String.valueOf(line));
                labelLine.setFont(new Font(12));
                vBoxWays3.getChildren().add(labelLine);
            }
            hBoxWays.getChildren().add(vBoxWays3);
            hBoxWays.setSpacing(10);
            vBoxR.getChildren().add(hBoxWays);

            hBoxAll.getChildren().addAll(vBoxL, vBoxR);
            hBoxAll.setSpacing(70);
            hBoxAll.setLayoutX(1330);
            hBoxAll.setLayoutY(280);
            root.getChildren().add(hBoxAll);
        });

        buildConGraph.setOnAction(actionEvent -> {
            Rectangle graphFone2 = new Rectangle(10, 10, 1300, 1000);
            graphFone2.setFill(Color.WHITE);
            root.getChildren().add(graphFone2);

            for (MyNode node : nodeList) {
                root.getChildren().removeAll(node.circle, node.textOfNode);
            }

            int n = listOfComponents.size();
            MyNode[] nodeListCon = CalculateGraphic.makeNodes(n);
            for (MyNode node : nodeListCon) {
                double x = node.textOfNode.getX()-5;
                double y = node.textOfNode.getY();
                node.textOfNode = new Text(x, y, "K"+String.valueOf(node.numberOfNode));
                node.textOfNode.setFont(new Font(15));
                root.getChildren().addAll(node.circle, node.textOfNode);
            }
            int[][] matrixCon = CalculateMath.getEdgesOfConGraph(listOfComponents, matrix);
            ArrayList<Node> edgesOfConGraph = CalculateGraphic.makeEdge(n, matrixCon, nodeListCon, true);
            for (Node edge : edgesOfConGraph) {
                if (edge instanceof ArrowArc) {
                    root.getChildren().add(((ArrowArc) edge).polyline);
                    root.getChildren().add(((ArrowArc) edge).quadCurve);
                } else if (edge instanceof ArrowLine) {
                    root.getChildren().add(((ArrowLine) edge).polyline);
                    root.getChildren().add(((ArrowLine) edge).line);
                } else {
                    root.getChildren().add(edge);
                }
            }
        });

        vBox3.getChildren().addAll(startButton, infoButton, buildConGraph);
        vBox3.setSpacing(15);
        vBox3.setLayoutX(1330);
        vBox3.setLayoutY(160);

        root.getChildren().addAll(exitHBox,vBox1,vBox2,vBox3);

        stage.setTitle("GSP");
        stage.setScene(scene);
        stage.show();

    }
}