package com.assignment6;

import javafx.scene.paint.Color;

import java.util.ArrayList;
import java.util.List;

public class Cop extends Player {

    public static List<Cop> cops;
    protected int robbersCaught;

    static {
        cops = new ArrayList<>();
    }

    public Cop(Graph aGraph, ArrayList<Player> aListOfPlayers) {
        super("COP", aGraph, aListOfPlayers);
        color = Color.BLUE;
        cops.add(this);
    }

    @Override
    protected void decideNextNode() {
        currentNode = nextNode;
        nextNode = graph.closestWitLabel(currentNode, "robber");
        if (nextNode == null) {
            if (Math.random() > .5)
                nextNode = currentNode.getEdges().get((int) (currentNode.getEdges().size() * Math.random())).getTail();
            else
                nextNode = currentNode.getEdges().get((int) (currentNode.getEdges().size() * Math.random())).getHead();

        }
    }

    public void catchRobber() {
        robbersCaught++;
    }
}
