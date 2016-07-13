package com.ldnel;

import javafx.scene.paint.Color;

import java.util.ArrayList;

public class Robber extends Player {

    public Robber(Graph aGraph, ArrayList<Player> aListOfPlayers) {
        super("ROBBER", aGraph, aListOfPlayers);
        color = Color.RED;
    }

    @Override
    protected void decideNextNode() {
        currentNode.setLabel("");
        nextNode.setLabel("");
        super.decideNextNode();
        currentNode.setLabel("Robber");
    }
}
