package com.assignment6;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.text.Text;

import java.io.BufferedReader;
import java.io.EOFException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

public class Edge implements Drawable{

    //PARSING VARIALBLES=====================================
    //Tags used for XML export of node objects

    final public static String startTag = "<edge>";
    final public static String endTag = "</edge>";
    final public static String headIDStartTag = "<head>";
    final public static String headIDEndTag = "</head>";
    final public static String tailIDStartTag = "<tail>";
    final public static String tailIDEndTag = "</tail>";
    final public static String labelStartTag = "<label>";
    final public static String labelEndTag = "</label>";

    final public static String selectedTag = "<selected/>"; //edge is in selected state
    //END PARSING TAGS

    private static double DEFAULT_EDGE_WIDTH = 5;

    private String label;
    private Node head, tail;
    private boolean isSelected;

    public Edge(Node headNode, Node tailNode){
        head = headNode;
        tail = tailNode;
    }

    public String getLabel(){return label;}
    public void setLabel(String aLabel){
        if(aLabel == null || aLabel.trim().length() == 0) return;
        label = aLabel.trim();
    }
    public boolean isSelected(){return isSelected;}
    public void setSelected(boolean selectedIfTrue){isSelected = selectedIfTrue;}
    public void toggleSelected(){isSelected = !isSelected;}
    public Node getHead(){return head;}
    public Node getTail(){return tail;}

    public void drawWith(GraphicsContext thePen) {

        thePen.setFill(Node.DEFAULT_NODE_COLOR);
        thePen.setStroke(Node.DEFAULT_NODE_COLOR);

        if (isSelected) {
            thePen.setFill(Drawable.highlightColor);
            thePen.setStroke(Drawable.highlightColor);
        }

        //draw the edge
        thePen.setLineWidth(DEFAULT_EDGE_WIDTH);
        thePen.strokeLine(head.getLocation().getX(), head.getLocation().getY(),
                tail.getLocation().getX(), tail.getLocation().getY());


        //draw label if there is one
        if(label != null && label.trim().length()>0) {
            Text theText = new Text(label); //create text object that can be measured
            theText.setFont(Node.NODE_LABEL_FONT); //set font of the text object
            //get the width and height of the text object
            double textWidth = theText.getLayoutBounds().getWidth();
            double textHeight = theText.getLayoutBounds().getHeight();

            //draw the node label centered on the node
            thePen.setFill(Node.NODE_LABEL_COLOR);
            thePen.setFont(Node.NODE_LABEL_FONT);
            double midpointX = (head.getLocation().getX() + tail.getLocation().getX())/2;
            double midpointY = (head.getLocation().getY() + tail.getLocation().getY())/2;
            thePen.fillText(label,
                            midpointX - textWidth / 2,
                            midpointY + textHeight / 4);
        }
    }

    public void writeToFile(String baseIndent, PrintWriter outputFile){
        //Write the edge to a file in XML style tag-delimited data that
        //baseIndent is current indentation white space to
        // added in front of the tags for formatting

        String tab = "   ";
        String indent = baseIndent + tab;

        //write class start tag
        outputFile.println(baseIndent + startTag);

        //edges go between a head node and tail node identified by ID
        outputFile.println(indent + headIDStartTag + head.getId() + headIDEndTag);
        outputFile.println(indent + tailIDStartTag + tail.getId() + tailIDEndTag);

        if(this.label != null && !this.label.isEmpty())
            outputFile.println(indent + labelStartTag + getLabel() + labelEndTag);


        if(this.isSelected()) outputFile.println(baseIndent + tab + selectedTag);

        //write class end tag
        outputFile.println(baseIndent + endTag);

    }

    public static Edge parseFromFile(BufferedReader graphXMLDataStream, ArrayList<Node> theNodes) {
        //Parse edge data from xml file
        // input expected to have the following from
        // The start tag will have been stripped off already
        //parse until the closing tag is found
        /*
            <edge>
               <head>1</head>
               <tail>1</tail>
               <label>A</label>
            </edge>
        */

        if (graphXMLDataStream == null) return null;

        Edge parsedEdge = new Edge(new Node(0,0), new Node(0,0));  //dummy nodes
        Node headNode = null;
        Node tailNode = null;


        String inputLine; //current input line
        String dataString = null;

        BufferedReader inputFile;

        try {
            //parse until we get to the closing tag
            while ((inputLine = graphXMLDataStream.readLine()) != null) {
                //System.out.println(inputLine);
                dataString = inputLine.trim();

                if (dataString.startsWith(endTag)) {
                    //did not find both head and tail node
                    if(headNode == null || tailNode == null) return null;
                    //return the parsed edge
                    return parsedEdge;
                }

                else if(dataString.startsWith(headIDStartTag) &&
                        dataString.endsWith(headIDEndTag)){
                    String theIDString = dataString.substring(
                            headIDStartTag.length(),
                            dataString.length() - headIDEndTag.length()
                    ).trim();
                    for(Node n : theNodes) {
                      if(n.getId() == Integer.parseInt(theIDString)) headNode = n;
                    }
                    if(headNode != null) parsedEdge.head = headNode;
                }
                else if(dataString.startsWith(tailIDStartTag) &&
                        dataString.endsWith(tailIDEndTag)){
                    String theIDString = dataString.substring(
                            tailIDStartTag.length(),
                            dataString.length() - tailIDEndTag.length()
                    ).trim();
                    for(Node n : theNodes) {
                        if(n.getId() == Integer.parseInt(theIDString)) tailNode = n;
                    }
                    if(tailNode != null) parsedEdge.tail = tailNode;
                }
                else if(dataString.startsWith(labelStartTag) &&
                        dataString.endsWith(labelEndTag)){
                    String theLabelString = dataString.substring(
                            labelStartTag.length(),
                            dataString.length() - labelEndTag.length()
                    );
                    parsedEdge.setLabel(theLabelString.trim());
                }
                else if(dataString.startsWith(selectedTag) ){
                    parsedEdge.setSelected(true);
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
}
