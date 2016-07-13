package com.assignment6;

import javafx.scene.paint.Color;

import java.util.ArrayList;
import java.util.List;

public class Robber extends Player {

    public static List<Robber> robbers;

    static {
        robbers = new ArrayList<>();
    }

    public Robber(Graph aGraph, ArrayList<Player> aListOfPlayers) {
        super("ROBBER", aGraph, aListOfPlayers);
        color = Color.RED;
        robbers.add(this);
    }

    @Override
    protected void decideNextNode() {
        currentNode.setLabel("");
        nextNode.setLabel("");
        super.decideNextNode();
        currentNode.setLabel("Robber");
    }
}
