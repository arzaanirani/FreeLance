package com.ldnel;

import java.io.BufferedReader;
import java.io.EOFException;
import java.io.IOException;
import java.io.PrintWriter;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

/**
 * Created by ldnel_000 on 2015-10-25.
 */
public class Edge implements Drawable{
	
	final public static String startTag = "<edge>";
    final public static String endTag = "</edge>";
    final public static String HeadStartTag = "<head>";
    final public static String HeadEndTag = "</head>";
    final public static String SelectedStartTag = "<selected>";
    final public static String SelectedEndTag = "</selected>";
    final public static String TailStartTag = "<tail>";
    final public static String TailEndTag = "</tail>";
	
	private Node head, tail; //The head and tail of the edge
	private boolean isSelected; //Whether the edge has been selected
	
	/** Represents an edge between two nodes in the graph.
	 * 
	 * @param head The head Node
	 * @param tail The tail Node
	 */
    public Edge(Node head, Node tail){
    	this.head = head;
    	this.tail = tail;
    }
    
    /** Represents an edge between two nodes in the graph.
     * 
     * @param head The head Node
     * @param tail The tail Node
     * @param selected Whether the edge has been selected
     */
    public Edge(Node head, Node tail, boolean selected) {
    	this(head, tail);
    	isSelected = selected;
    }
    
    public void drawWith(GraphicsContext thePen){
    	if (!isSelected) //If the edge isn't selected
    		thePen.setStroke(Node.DEFAULT_NODE_COLOR); //set to the node color (CornBlue)
    	else //if it's selected
    		thePen.setStroke(Color.YELLOW); //set to Yellow
    	thePen.setLineWidth(5); //increase line width
    	thePen.strokeLine(head.getLocation().getX(), head.getLocation().getY(), tail.getLocation().getX(), tail.getLocation().getY()); //draw stroke from head to tail
    }
    
    /** Whether the Edge contains a specific location
     * 
     * @param aLocation The location to check
     * @return Whether the location is contained within the Edge
     */
    public boolean containsLocation(Location aLocation){
        //answer whether aLocation is withing the drawing area of this edge
        if(aLocation == null) return false;
        return Location.distanceBetween(head.getLocation(), aLocation) + Location.distanceBetween(tail.getLocation(), aLocation ) - Location.distanceBetween(head.getLocation(), tail.getLocation()) < 3;

    }
    
    /**
     * @return If the edge is selected
     */
    public boolean isSelected() {
    	return isSelected;
    }
    
    /**
     * Switch the value of isSelected (button toggle)
     */
    public void invertSelected() {
    	isSelected = !isSelected;
    }
    
    /** Writes the Edge in XML format to the file
     * 
     * @param baseIndent The indent level to start the XML at
     * @param outputFile The file to write to
     */
    public void writeToFile(String baseIndent, PrintWriter outputFile){
        //Write the edge to a file in XML style tag-delimited data that
        //baseIndent is current indentation white space to
        // added in front of the tags for formatting

        String tab = "   ";
        String indent = baseIndent + tab;

        //write class start tag
        outputFile.println(baseIndent + startTag);

        outputFile.println(indent + HeadStartTag + head.getId() + HeadEndTag); //add head
        outputFile.println(indent + TailStartTag + tail.getId() + TailEndTag); //add tail
        outputFile.println(indent + SelectedStartTag + isSelected + SelectedEndTag); //add isSelected

        //write class end tag
        outputFile.println(baseIndent + endTag);
    }

    public static Edge parseFromFile(Graph graph, BufferedReader graphXMLDataStream) {
        //Parse node data from xml file
        // input expected to have the following from
        // The start tag will have been stripped off already
        //parse until the closing tag is found
        /*
            <edge>
               <head>1001</head>
               <tail>1002</tail>
            </edge>
        */

        if (graphXMLDataStream == null) return null;

        Node head = null, tail = null; //head and tail
        boolean selected = false;
        String inputLine; //current input line
        String dataString = null;

        try {
            //parse until we get to the closing tag
            while ((inputLine = graphXMLDataStream.readLine()) != null) {
                //System.out.println(inputLine);
                dataString = inputLine.trim();

                if (dataString.startsWith(endTag)) //if end tag return the edge
                	return new Edge(head, tail, selected);
                else if(dataString.startsWith(HeadStartTag) && //if head tag get the head node ID
                        dataString.endsWith(HeadEndTag)){
                        String idString = dataString.substring(
                                HeadStartTag.length(),
                                dataString.length() - HeadEndTag.length()
                        );
                        head = graph.getNode(Integer.parseInt(idString)); //get node by ID
                }
                else if(dataString.startsWith(TailStartTag) && //if the tail tag get the tail node ID
                        dataString.endsWith(TailEndTag)){
                        String idString = dataString.substring(
                                TailStartTag.length(),
                                dataString.length() - TailEndTag.length()
                        );
                        tail = graph.getNode(Integer.parseInt(idString)); //get the node by ID
                }
                else if(dataString.startsWith(SelectedStartTag) && //if the isSelected tag get the boolean value
                        dataString.endsWith(SelectedEndTag)){
                        String selectedString = dataString.substring(
                                SelectedStartTag.length(),
                                dataString.length() - SelectedEndTag.length()
                        );
                        selected = Boolean.parseBoolean(selectedString); //parse value to boolean
                }
            }

            //catch file IO errors
        } catch (EOFException e) {
            System.out.println("VERSION PARSE Error: EOF encountered, file may be corrupted.");
            return null;
        } catch (IOException e) {
            System.out.println("VERSION PARSE Error: Cannot read from file.");
            return null;
        }
        return null;
    }

    /**
     * @return the head Node
     */
    public Node getHead() {
    	return head;
    }

    /**
     * @return the tail Node
     */
    public Node getTail() {
    	return tail;
    }
}
