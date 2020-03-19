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
import javafx.stage.Stage;

import java.util.ArrayList;

public class Main extends Application {
    public static int numberOfNodes;
    public static boolean orientation = false;
    public static int[][] matrix1;
    public static int[][] matrix2;
    public static MyNode[] nodeList;

    public static void main(String[] args) {
        Application.launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {
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
        //textFieldOfNodes.setOnAction(event -> Control.setNumberOfNodes(textFieldOfNodes.getCharacters()));
        Label labelOfPosition = new Label("\n" + "Розміщення за варіантом:\n" + "\n" + "        -трикутник"+" \n"+" \n");
        labelOfPosition.setFont(new Font(15));
        RadioButton isGraphOriented = new RadioButton("Напрямлений граф");
        isGraphOriented.setFont(new Font(15));
        isGraphOriented.setOnAction(event -> Control.setGraphOrientation());
        vBox1.getChildren().addAll(labelOfNodes, textFieldOfNodes, labelOfPosition, isGraphOriented);
        vBox1.setSpacing(20);
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
        Button startButton = new Button("  Побудувати \n напрямлений \n       граф");
        Button nextButton = new Button("   Перетворити \nу ненапрямлений\n         граф");
        Button infoButton = new Button("Вивести інформацію");
        startButton.setPrefSize(170, 70);
        nextButton.setPrefSize(170, 70);
        infoButton.setPrefSize(170, 70);
        root.getChildren().addAll(exitHBox,vBox1,vBox2, hBox3);

        startButton.setOnAction(actionEvent -> {
            Control.setNumberOfNodes(textFieldOfNodes.getCharacters());
            Control.setMatrix(textAreaOfMatrix.getText());
            MyNode[] nodes = CalculateGraphic.makeNodes(numberOfNodes);
            for (MyNode node : nodes) {
                root.getChildren().add(node.circle);
                root.getChildren().add(node.textOfNode);
            }
            nodeList = nodes;
            ArrayList<Node> edges = CalculateGraphic.makeEdge(numberOfNodes, matrix1, nodes, orientation);
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
                    matrix1[i][j] = Math.abs(matrix1[i][j]);
                }
            }
        });

        nextButton.setOnAction(actionEvent -> {
            Rectangle graphFone2 = new Rectangle(10, 10, 1300, 1000);
            graphFone2.setFill(Color.WHITE);
            root.getChildren().add(graphFone2);

            for (MyNode node : nodeList) {
                root.getChildren().removeAll(node.circle, node.textOfNode);
            }
            for (MyNode node : nodeList) {
                root.getChildren().addAll(node.circle, node.textOfNode);
            }

            matrix2 = CalculateMath.createMatrixOfUnOrientedGraph(numberOfNodes, matrix1);
            ArrayList<Node> edges = CalculateGraphic.makeEdge(numberOfNodes, matrix2, nodeList, false);
            for (int i = 0; i < numberOfNodes; i++) {
                for (int j = 0; j < numberOfNodes; j++) {
                    matrix2[i][j] = Math.abs(matrix2[i][j]);
                }
            }
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
        });

        infoButton.setOnAction(actionEvent -> {
            int[][] degreesListOr = CalculateMath.degreesOfNodesOr(numberOfNodes, matrix1);
            int[] degreesListUn = CalculateMath.degreesOfNodesUn(numberOfNodes, matrix2);
            int isGraphRegularOr = CalculateMath.setGraphRegularOr(numberOfNodes, degreesListOr);
            int isGraphRegularUn = CalculateMath.setGraphRegularUn(numberOfNodes, degreesListUn);
            //Isolated and pendant node

            VBox vBoxAll = new VBox();
            vBoxAll.setSpacing(15);

            HBox hBoxAll = new HBox();
            hBoxAll.setSpacing(20);

            VBox vBoxOfOreintedGraph = new VBox();
            Label labelOfOreintedGraph = new Label("Степені вершин напрямленого\nграфу:");
            labelOfOreintedGraph.setFont(new Font(15.5));
            vBoxOfOreintedGraph.getChildren().add(labelOfOreintedGraph);
            vBoxOfOreintedGraph.setSpacing(10);

            HBox hBoxDegreesOr = new HBox();

            VBox vBoxDegreesOr = new VBox();
            Label labelDegreesOr = new Label("Степені\nвершин");
            labelDegreesOr.setFont(new Font(14));
            vBoxDegreesOr.getChildren().add(labelDegreesOr);
            for (int i = 0; i < numberOfNodes; i++) {
                Label labelOfDegreeOfNodeOr = new Label(" В.№"+(i+1)+": "+(degreesListOr[i][0]+degreesListOr[i][1]));
                labelOfDegreeOfNodeOr.setFont(new Font(14));
                vBoxDegreesOr.getChildren().add(labelOfDegreeOfNodeOr);
            }
            hBoxDegreesOr.getChildren().add(vBoxDegreesOr);

            VBox vBoxDegreesOut = new VBox();
            Label labelDegreeOut = new Label(" Напівстепені\n виходу");
            labelDegreeOut.setFont(new Font(14));
            vBoxDegreesOut.getChildren().add(labelDegreeOut);
            for (int i = 0; i < numberOfNodes; i++) {
                Label labelOfDegreeOfNodeOr = new Label("    В.№"+(i+1)+": "+degreesListOr[i][1]);
                labelOfDegreeOfNodeOr.setFont(new Font(14));
                vBoxDegreesOut.getChildren().add(labelOfDegreeOfNodeOr);
            }
            hBoxDegreesOr.getChildren().add(vBoxDegreesOut);

            VBox vBoxDegreesIn = new VBox();
            Label labelDegreeIn = new Label("  Напівстепені\n  заходу");
            labelDegreeIn.setFont(new Font(14));
            vBoxDegreesIn.getChildren().add(labelDegreeIn);
            for (int i = 0; i < numberOfNodes; i++) {
                Label labelOfDegreeOfNodeOr = new Label("    В.№"+(i+1)+": "+degreesListOr[i][0]);
                labelOfDegreeOfNodeOr.setFont(new Font(14));
                vBoxDegreesIn.getChildren().add(labelOfDegreeOfNodeOr);
            }
            hBoxDegreesOr.getChildren().add(vBoxDegreesIn);

            vBoxOfOreintedGraph.getChildren().add(hBoxDegreesOr);

            Label labelOfRegularOr = new Label();
            if (isGraphRegularOr > -1) {
                labelOfRegularOr.setText("  Граф однорідний. "+"Степінь однорідності "+String.valueOf(isGraphRegularOr)+".");
            } else {
                labelOfRegularOr.setText("  Цей граф не є однорідним.");
            }
            labelOfRegularOr.setFont(new Font(14));
            vBoxOfOreintedGraph.getChildren().add(labelOfRegularOr);

            hBoxAll.getChildren().add(vBoxOfOreintedGraph);

            //Unoriented
            VBox vBoxOfUnorientedGraph = new VBox();
            Label labelOfUnorientedGraph = new Label("Степені вершин ненапрямленого\nграфу:");
            labelOfUnorientedGraph.setFont(new Font(15.5));
            vBoxOfUnorientedGraph.getChildren().add(labelOfUnorientedGraph);
            vBoxOfUnorientedGraph.setSpacing(8);

            VBox vBoxDegreesUn = new VBox();
            Label labelDegreesUn = new Label("  Степені вершин");
            labelDegreesUn.setFont(new Font(15));
            vBoxDegreesUn.getChildren().add(labelDegreesUn);
            for (int i = 0; i < numberOfNodes; i++) {
                Label labelOfDegreeOfNodeUn = new Label("    В.№"+(i+1)+": "+degreesListUn[i]);
                labelOfDegreeOfNodeUn.setFont(new Font(14));
                vBoxDegreesUn.getChildren().add(labelOfDegreeOfNodeUn);
            }
            vBoxOfUnorientedGraph.getChildren().add(vBoxDegreesUn);

            Label labelOfregularUn = new Label();
            if (isGraphRegularUn > -1) {
                labelOfregularUn.setText("  Граф однорідний. "+"\n  Степінь однорідності "+String.valueOf(isGraphRegularUn)+".");
            } else {
                labelOfregularUn.setText("  Цей граф не є однорідним.");
            }
            labelOfregularUn.setFont(new Font(14));
            vBoxOfUnorientedGraph.getChildren().add(labelOfregularUn);

            hBoxAll.getChildren().add(vBoxOfUnorientedGraph);

            vBoxAll.getChildren().add(hBoxAll);

            StringBuilder isolatedNodes = new StringBuilder("Ізольовані вершини графа:");
            StringBuilder pendantNodes = new StringBuilder("Висячі вершини графа:");
            boolean isHaveNotIsolatedNodes = true;
            boolean isHaveNotPendantNodes = true;
            for (int i = 0; i < numberOfNodes; i++) {
                if (degreesListUn[i] == 0) {
                    isolatedNodes.append(" ");
                    isolatedNodes.append(Integer.toString((i + 1)));
                    isHaveNotIsolatedNodes = false;
                }
                if (degreesListUn[i] == 1) {
                    pendantNodes.append(" ");
                    pendantNodes.append(Integer.toString((i + 1)));
                    isHaveNotPendantNodes = false;
                }
            }
            if (isHaveNotIsolatedNodes) {
                isolatedNodes = new StringBuilder("У графа немає ізольованих вершин.");
            }
            if (isHaveNotPendantNodes) {
                pendantNodes = new StringBuilder("У графа немає висячих вершин.");
            }

            VBox vBoxIsolatedAndPendantNodes = new VBox();
            Label labelOfIsolatedNodes = new Label(isolatedNodes.toString());
            Label labelOfPendantNodes = new Label(pendantNodes.toString());
            labelOfIsolatedNodes.setFont(new Font(14));
            labelOfPendantNodes.setFont(new Font(14));
            vBoxIsolatedAndPendantNodes.getChildren().addAll(labelOfIsolatedNodes, labelOfPendantNodes);
            vBoxAll.getChildren().add(vBoxIsolatedAndPendantNodes);

            vBoxAll.setLayoutX(1330);
            vBoxAll.setLayoutY(400);
            root.getChildren().add(vBoxAll);
        });

        hBox3.getChildren().addAll(startButton, nextButton, infoButton);
        hBox3.setSpacing(20);
        hBox3.setLayoutX(1330);
        hBox3.setLayoutY(300);

        stage.setTitle("GSP");
        stage.setScene(scene);
        stage.show();

    }
}