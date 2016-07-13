package com.ldnel;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class Player implements Drawable, Movable {

    protected Location location;
    protected String label;
    protected Color color;
    protected Graph graph; //graph on which player is playing
    private ArrayList<Player> players; //container that holds all the games players
    private Set<Node> visited = new HashSet<Node>();
    private double speed;
    private final double RADIUS;
    public static Font NODE_LABEL_FONT = Font.font("Courier New", FontWeight.BOLD, 30);

    protected Node currentNode;
    protected Node nextNode;


    public Player (String aLabel, Graph aGraph, ArrayList<Player> aListOfPlayers) {
        label = aLabel;
        graph = aGraph;
        players = aListOfPlayers;
        speed = 4;
        color = new Color(Math.random(), Math.random(), Math.random(), 1);
        RADIUS = 10;

        currentNode = aGraph.getRandomNode();
        nextNode =  currentNode.getEdges().get((int) (Math.random() * currentNode.getEdges().size())).getTail();
        location = new Location(currentNode.getLocation().getX(), currentNode.getLocation().getY());

    }

    protected void decideNextNode () {
        currentNode = nextNode;
        ArrayList<Edge> edgesToChooseFrom = currentNode.getEdges();
        ArrayList<Node> nodesToChooseFrom = new ArrayList<Node>();
        for (Edge edge : edgesToChooseFrom) {
            if (!edge.getHead().equals(currentNode))
                nodesToChooseFrom.add(edge.getHead());
            if (!edge.getTail().equals(currentNode))
                nodesToChooseFrom.add(edge.getTail());
        }
        nextNode = nodesToChooseFrom.get((int)(Math.random() * nodesToChooseFrom.size()));
    }

    @Override
    public void drawWith (GraphicsContext thePen) {
        thePen.setFill(color);
        thePen.fillOval(location.getX() - RADIUS, //upper left X
                location.getY() - RADIUS, //upper left Y
                2 * RADIUS, //width
                2 * RADIUS); //height
        if (label != null && label.trim().length() > 0) {
            Text theText = new Text(label); //create text object that can be measured
            theText.setFont(NODE_LABEL_FONT); //set font of the text object
            //get the width and height of the text object
            double textWidth = theText.getLayoutBounds().getWidth();
            double textHeight = theText.getLayoutBounds().getHeight();

            //draw the node label centered on the node
            thePen.setFill(color.invert());
            thePen.setFont(NODE_LABEL_FONT);
            thePen.fillText(label, location.getX() - textWidth / 2, location.getY() + textHeight / 4);
        }
    }

    @Override
    public void advanceInArea (double topLeftX, double topLeftY, double width, double height) {
        Location toLocation = nextNode.getLocation();
        double theta = Math.atan2(toLocation.getY() - location.getY(), toLocation.getX() - location.getX());
        double deltaY = Math.sin(theta) * speed;
        double deltaX = Math.cos(theta) * speed;
        location.moveDelta(deltaX, deltaY);
        if (nextNode.containsLocation(location)) {
            location = new Location(nextNode.getLocation().getX(), nextNode.getLocation().getY());
            decideNextNode();
        }
    }
}
