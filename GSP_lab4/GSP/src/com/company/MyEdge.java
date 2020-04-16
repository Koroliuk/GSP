package com.company;

import javafx.scene.Node;

public class MyEdge extends Node {
    int startNode;
    int finishNode;
    Node edge;

    MyEdge(int start, int finish, Node edg) {
        startNode = finish; //counting
        finishNode = start; //from 1
        edge = edg;
    }

}
