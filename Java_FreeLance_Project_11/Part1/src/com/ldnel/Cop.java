package com.ldnel;

import javafx.scene.paint.Color;

import java.util.ArrayList;

public class Cop extends Player {

    public Cop (Graph aGraph, ArrayList<Player> aListOfPlayers) {
        super("COP", aGraph, aListOfPlayers);
        color = Color.BLUE;
    }

    @Override
    protected void decideNextNode() {
        currentNode = nextNode;
        nextNode = graph.closestWitLabel(currentNode, "robber");
    }
}
