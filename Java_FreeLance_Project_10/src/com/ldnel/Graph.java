package com.ldnel;

import javafx.scene.canvas.GraphicsContext;

import java.io.*;
import java.util.ArrayList;

/**
 * Created by ldnel_000 on 2015-10-25.
 */
public class Graph implements Drawable{
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

    public Graph(){
        nodes = new ArrayList<Node>();
        edges = new ArrayList<Edge>();
    }
    public Graph(String aGraphName){
        nodes = new ArrayList<Node>();
        edges = new ArrayList<Edge>();
        if(aGraphName != null && aGraphName.trim().length()>0)
            label = aGraphName;
    }

    public void addNode(Node aNode){
        //add a node to the graph
        if(aNode == null)  return;
        nodes.add(aNode);
    }
    
    /**
     * Adds an edge to the graph.
     * 
     * @param edge The Edge to add
     */
    public void addEdge(Edge edge) {
    	edges.add(edge); //add to total edges array
    	edge.getHead().addEdge(edge); //add edge to head
    	edge.getTail().addEdge(edge); //add edge to tail
    }

    public Node nodeAtLocation(Location aLocation){
        //answer the node a location aLocation if any
        if(aLocation == null) return null;
        for(Node n : nodes)
          if(n.containsLocation(aLocation)) return n;
        return null;

    }

    /** Returns whether an edge is located at a specific location.
     * 
     * @param aLocation The location to check
     * @return If the edge is at the location
     */
    public Edge edgeAtLocation(Location aLocation){
        if(aLocation == null) return null; //if it's not null
        for(Edge e : edges) //go through the edges
          if(e.containsLocation(aLocation)) return e; //return if the edge contains the location
        return null; //otherwise return null

    }
    
    /** Remove a Node from the graph. It should remove all edges within it.
     * 
     * @param node The node to remove
     */
    public void removeNode(Node node) {
    	for (Edge e : node.getEdges()) { //go through it's edges
    		Node other = null; //get the node that the Node-to-remove is with
    		if (e.getHead() != node) {
    			other = e.getHead();
    		} else {
    			other = e.getTail();
    		}
    		other.removeEdge(e); //remove edge
    		edges.remove(e); //remove edge from graph
    	}
    	nodes.remove(node);
    }
    
    /** Removes an edge from the graph.
     * 
     * @param e The edge to remove it
     */
    public void removeEdge(Edge e) {
    	e.getHead().removeEdge(e); //remove the edge from the head node
    	e.getTail().removeEdge(e); //remove the edge from the tail node
    	edges.remove(e); //remove the edge from the graph
    }
    
    public void clearSelectedNodes(){
        //clear the isSelected value of any selected nodes
        for(Node n : nodes) n.setSelected(false);
    }
    public ArrayList<Node> getSelectedNodes(){
        //answer a collection of all the selected nodes in the graph
        ArrayList<Node> selectedNodes = new ArrayList<Node>();
        for(Node n : nodes) if(n.isSelected()) selectedNodes.add(n);
        return selectedNodes;
    }
    /** Get all edges that have been selected.
     *
     * @return All selected edges
     */
    public ArrayList<Edge> getSelectedEdges(){
        //answer a collection of all the selected nodes in the graph
        ArrayList<Edge> selectedEdges = new ArrayList<Edge>();
        for(Edge e : edges) if(e.isSelected()) selectedEdges.add(e);
        return selectedEdges;
    }
    public void deleteSelected(){
        //delete selected elements in the graph.
        //delete selected edges first TO DO

        //delete selected nodes
        for (Node node : getSelectedNodes()) {
    		removeNode(node);
    	}
        
    	for (Edge edge : getSelectedEdges()) { //get all selected edges and remove them
    		removeEdge(edge);
    	}
    }

    /** Check if an edge exists between two nodes
     * 
     * @param node1 First Node
     * @param node2 Second Node
     * @return Whether an edge exists
     */
    public boolean edgeExists(Node node1, Node node2) {
    	for (Edge edge : edges) {
    		if ((edge.getHead() == node1 && edge.getTail() == node2) || (edge.getTail() == node1 && edge.getHead() == node2)) {
    			return true;
    		}
    	}
    	return false;
    }
    
    /** Get a Node by it's ID
     * 
     * @param id The ID
     * @return The Node if it exists
     */
    public Node getNode(int id) {
    	for (Node n : nodes) //go through all nodes and find the one with the same ID
    		if (n.getId() == id)
    			return n;
    	return null;
    }

    public void drawWith(GraphicsContext thePen){

        //draw edges first so they will be behind nodes
        for(Edge e : edges) e.drawWith(thePen);
        //draw nodes
        for(Node n : nodes) n.drawWith(thePen);

    }

    public void writeToFile(PrintWriter outputFile){
        //Write the chart to a file in XML style tag-delimited data

        //Assign unique ID's to each node to identify them in data file
        int IDcounter = 1000;
        for(Node n: nodes)  n.setID(IDcounter++);

        String indent = "   ";
        outputFile.println(XMLDocumentTypeTag);

        //write class start tag
        outputFile.println(startTag);
        if(label != null && label.trim().length() > 0)
            outputFile.println(indent + labelStartTag + label + labelEndTag);

        // Output the nodes
        for (int i=0; i<nodes.size(); i++)
            nodes.get(i).writeToFile(indent, outputFile);

        
        // Output the edges
        for (int i=0; i<edges.size(); i++)
            ((Edge)edges.get(i)).writeToFile(indent, outputFile);
        

        //write class end tag
        outputFile.println(endTag);

    }

    public static Graph parseFromFile(File graphXMLDataFile){
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
        </graph>
        */

        if(graphXMLDataFile == null) return null;

        Graph parsedModel = null;
        Graph graphToReturn = null;

        String inputLine; //current input line
        String dataString = null;

        BufferedReader inputFile;

        try {

            inputFile = new BufferedReader(new FileReader(graphXMLDataFile));

            //parse until we get to the closing tag
            while((inputLine = inputFile.readLine()) != null)
            {
                //System.out.println(inputLine);
                dataString = inputLine.trim();

                if(dataString.startsWith(Graph.startTag)){
                    //parse a graph
                    parsedModel = new Graph();
                }
                else if(dataString.startsWith(Graph.endTag)){
                    //parse a graph
                    graphToReturn = parsedModel;
                    parsedModel = null;
                }
                else if(dataString.startsWith(Node.startTag)){
                    Node node = Node.parseFromFile(inputFile);
                    if(node != null) parsedModel.addNode(node);
                } else if (dataString.startsWith(Edge.startTag)) { //check for edge tag
                	Edge edge = Edge.parseFromFile(parsedModel, inputFile); //get edge
                	parsedModel.addEdge(edge); //add to graph
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

        if(graphToReturn == null) {
            System.out.println("ERROR: Graph: Parsed graph not created");
            return null;
        }
        return graphToReturn;

    }
}
