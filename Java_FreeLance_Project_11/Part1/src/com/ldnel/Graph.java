package com.ldnel;

import javafx.scene.canvas.GraphicsContext;

import java.io.*;
import java.util.*;

public class Graph implements Drawable {
    /*
    Implements a graph consisting of nodes and edges
     */
    //PARSING VARIALBLES=====================================
    //Tags used for XML like export of graph objects

    final public static String startTag = "<graph>";
    final public static String endTag = "</graph>";
    final public static String labelStartTag = "<label>";
    final public static String labelEndTag = "</label>";

    final public static String XMLDocumentTypeTag = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>";
    final public static String XMLCommentTag = "<!--";
    final public static String XMLCommentEndTag = "-->";

    //END OF PARSING TAGS

    private String label; //name of the graph
    private ArrayList<Node> nodes; //nodes of the graph
    private ArrayList<Edge> edges; //edges of the graph

    public Graph() {
        nodes = new ArrayList<Node>();
        edges = new ArrayList<Edge>();
    }

    public Graph(String aGraphName) {
        nodes = new ArrayList<Node>();
        edges = new ArrayList<Edge>();
        if (aGraphName != null && aGraphName.trim().length() > 0)
            label = aGraphName;
    }

    public void addNode(Node aNode) {
        //add a node to the graph
        if (aNode == null) return;
        if (nodes.contains(aNode)) return; //don't allow node to be added twice
        nodes.add(aNode);
    }

    public void removeNode(Node aNode) {
        if (aNode == null) return;
        removeEdges(aNode.getEdges());
        nodes.remove(aNode);
    }

    public void addEdge(Edge anEdge) {
        //add an edge to the graph
        if (anEdge == null) return;
        if (edges.contains(anEdge)) return; //don't allow edges more than once
        if (hasEdgeBetween(anEdge.getHead(), anEdge.getTail())) return; //don't allow multi-edge
        edges.add(anEdge);
        anEdge.getHead().getEdges().add(anEdge);
        anEdge.getTail().getEdges().add(anEdge);
    }

    void removeEdge(Edge anEdge) {
        if (anEdge == null) return;
        anEdge.getHead().getEdges().remove(anEdge);
        anEdge.getTail().getEdges().remove(anEdge);
        edges.remove(anEdge);
    }

    void removeEdges(ArrayList<Edge> theEdges) {
        if (theEdges == null || theEdges.isEmpty()) return;
        //make copy of theEdges collection so we are iteration over a copy
        ArrayList<Edge> edgesToRemove = new ArrayList<Edge>();
        edgesToRemove.addAll(theEdges);
        for (Edge e : edgesToRemove) removeEdge(e);
    }

    public boolean hasEdgeBetween(Node n1, Node n2) {
        for (Edge e : edges) {
            if (e.getHead() == n1 && e.getTail() == n2) return true;
            if (e.getTail() == n1 && e.getHead() == n2) return true;
        }
        return false;
    }

    public Node nodeAtLocation(Location aLocation) {
        //answer the node a location aLocation if any
        if (aLocation == null) return null;
        for (Node n : nodes)
            if (n.containsLocation(aLocation)) return n;
        return null;

    }

    public Edge edgeAtLocation(Location aLocation) {
        //answer the node a location aLocation if any
        if (aLocation == null) return null;
        for (Edge e : edges) {
            Location headLocation = e.getHead().getLocation();
            Location tailLocation = e.getTail().getLocation();
            double edgeLength = Location.distanceBetween(headLocation, tailLocation);
            double fromHeadLength = Location.distanceBetween(headLocation, aLocation);
            double fromTailLength = Location.distanceBetween(tailLocation, aLocation);
            double tolerance = 2;
            if (fromHeadLength + fromTailLength - edgeLength < tolerance) return e;
        }

        return null;

    }

    public void clearSelectedNodes() {
        //clear the isSelected value of any selected nodes
        for (Node n : nodes) n.setSelected(false);
    }

    public void clearSelectedEdges() {
        //clear the isSelected value of any selected nodes
        for (Edge e : edges) e.setSelected(false);
    }

    public void clearSelections() {
        clearSelectedEdges();
        clearSelectedNodes();
    }

    public ArrayList<Node> getSelectedNodes() {
        //answer a collection of all the selected nodes in the graph
        ArrayList<Node> selectedNodes = new ArrayList<Node>();
        for (Node n : nodes) if (n.isSelected()) selectedNodes.add(n);
        return selectedNodes;
    }

    public ArrayList<Edge> getSelectedEdges() {
        //answer a collection of all the selected nodes in the graph
        ArrayList<Edge> selectedEdges = new ArrayList<Edge>();
        for (Edge e : edges) if (e.isSelected()) selectedEdges.add(e);
        return selectedEdges;
    }

    public void deleteSelected() {
        //delete selected elements in the graph.
        //delete selected edges first TO DO
        ArrayList<Edge> selectedEdges = getSelectedEdges();
        for (Edge e : selectedEdges) removeEdge(e);
        //delete selected nodes
        ArrayList<Node> selectedNodes = getSelectedNodes();
        for (Node n : selectedNodes) removeNode(n);
    }


    public void drawWith(GraphicsContext thePen) {

        //draw edges first so they will be behind nodes
        for (Edge e : edges) e.drawWith(thePen);
        //draw nodes
        for (Node n : nodes) n.drawWith(thePen);

    }

    public void writeToFile(PrintWriter outputFile) {
        //Write the chart to a file in XML style tag-delimited data

        //Assign unique ID's to each node to identify them in data file
        int IDcounter = 1000;
        for (Node n : nodes) n.setID(IDcounter++);

        String indent = "   ";
        outputFile.println(XMLDocumentTypeTag);

        //write class start tag
        outputFile.println(startTag);
        if (label != null && label.trim().length() > 0)
            outputFile.println(indent + labelStartTag + label + labelEndTag);

        // Output the nodes
        for (int i = 0; i < nodes.size(); i++)
            nodes.get(i).writeToFile(indent, outputFile);


        // Output the edges
        for (int i = 0; i < edges.size(); i++)
            ((Edge) edges.get(i)).writeToFile(indent, outputFile);


        //write class end tag
        outputFile.println(endTag);

    }

    public static Graph parseFromFile(File graphXMLDataFile) {
        //Parse in a version from the input file
        //The file is expected to have the following version information
        // The file is expected to have the following outer XML structure
        /*
     <?xml version="1.0" encoding="UTF-8"?>
     <graph>
        <node>
           <id>1000</id>
           <label>A</label>
           <location>100.0,100.0</location>
           <selected/>
        </node>
        <node>
           <id>1001</id>
           <label>B</label>
           <location>400.0,300.0</location>
        </node>
        <node>
           <id>1002</id>
           <label>C</label>
           <location>100.0,400.0</location>
        </node>
        <edge>
           <head>1000</head>
           <tail>1001</tail>
           <selected/>
        </edge>
        <edge>
           <head>1000</head>
           <tail>1002</tail>
        </edge>
        <edge>
           <head>1001</head>
           <tail>1002</tail>
        </edge>
     </graph>
        */

        if (graphXMLDataFile == null) return null;

        Graph parsedModel = null;
        Graph graphToReturn = null;

        String inputLine; //current input line
        String dataString = null;

        BufferedReader inputFile;

        try {

            inputFile = new BufferedReader(new FileReader(graphXMLDataFile));

            //parse until we get to the closing tag
            while ((inputLine = inputFile.readLine()) != null) {
                //System.out.println(inputLine);
                dataString = inputLine.trim();

                if (dataString.startsWith(Graph.startTag)) {
                    //parse a graph
                    parsedModel = new Graph();
                } else if (dataString.startsWith(Graph.endTag)) {
                    //parse a graph
                    graphToReturn = parsedModel;
                    parsedModel = null;
                } else if (dataString.startsWith(Node.startTag)) {
                    Node node = Node.parseFromFile(inputFile);
                    if (node != null) parsedModel.addNode(node);
                } else if (dataString.startsWith(Edge.startTag)) {
                    Edge edge = Edge.parseFromFile(inputFile, parsedModel.nodes);
                    if (edge != null) parsedModel.addEdge(edge);
                }

            }
            //closing tag reached
            inputFile.close();


            //catch file IO errors
        } catch (EOFException e) {
            System.out.println("VERSION PARSE Error: EOF encountered, file may be corrupted.");
            return null;
        } catch (IOException e) {
            System.out.println("VERSION PARSE Error: Cannot read from file.");
            return null;
        }

        if (graphToReturn == null) {
            System.out.println("ERROR: Graph: Parsed graph not created");
            return null;
        }
        return graphToReturn;
    }

    public Node getRandomNode() {
        return nodes.get((int) (Math.random() * nodes.size()));
    }

    public Node closestWitLabel(Node startingLocation, String label) {
        Queue<Node> queue = new LinkedList<>();
        Set<Node> seen = new HashSet<>();
        for (Edge node : startingLocation.getEdges()) {
            queue.add(node.getHead());
            queue.add(node.getTail());
        }
        while (!queue.isEmpty()) {
            Node t = queue.poll();
            if (seen.contains(t))
                continue;
            seen.add(t);
            if (t.getLabel() != null)
                if (t.getLabel().equalsIgnoreCase(label))
                    return t;
            for (Edge node : t.getEdges()) {
                queue.add(node.getHead());
                queue.add(node.getTail());
            }
        }
        return startingLocation.getEdges().get(0).getTail();
    }
}
