package com.ldnel;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;

import java.io.*;
import java.util.ArrayList;

/**
 * Created by ldnel_000 on 2015-10-25.
 */
public class Node implements Drawable {
    /*
    Represents the node in a graph
     */
    //PARSING VARIALBLES=====================================
    //Tags used for XML export of node objects

    final public static String startTag = "<node>";
    final public static String endTag = "</node>";
    final public static String IDStartTag = "<id>";
    final public static String IDEndTag = "</id>";
    final public static String labelStartTag = "<label>";
    final public static String labelEndTag = "</label>";
    final public static String locationStartTag = "<location>";
    final public static String locationEndTag = "</location>";

    final public static String selectedTag = "<selected/>"; //node is in selected state
    //END PARSING TAGS


    public static Font NODE_LABEL_FONT = Font.font("Courier New", FontWeight.BOLD, 30);
    public final static int SMALL_NODE_RADIUS = 7;
    public final static int MEDIUM_NODE_RADIUS = 20;
    public final static int LARGE_NODE_RADIUS = 40;
    public static double NODE_RADIUS = 30;
    public static Color DEFAULT_NODE_COLOR = Color.CORNFLOWERBLUE;
    public static Color NODE_LABEL_COLOR = Color.BLACK;

    //instance variables
    private int id; //used to represent the node in an data text file
    //id's are set just before node is written to data file
    private String label;
    private Location location; //location of center of node
    private boolean isSelected = false;
    private ArrayList<Edge> edges;

    //construction
    public Node(double x, double y) {
        //TO DO: should check that x and y are positive
        //otherwise throw an exception
        location = new Location(x, y);
        edges = new ArrayList<Edge>();
    }

    public Node(Location aLocation) {
        //TO DO: should throw exception if aLocation is null
        location = aLocation;
        edges = new ArrayList<Edge>();
    }

    public Node(Location aLocation, String aLabel) {
        //TO DO:  should throw exception if aLoaction is null
        location = aLocation;
        label = aLabel;
        edges = new ArrayList<Edge>();
    }

    public Node(double x, double y, String aNodeLabel) {
        //TO DO: should check that x and y are postive
        location = new Location(x, y);
        if (aNodeLabel != null && aNodeLabel.trim().length() > 0)
            label = aNodeLabel;
        edges = new ArrayList<Edge>();
    }

    //get and set methods
    public int getId() {
        return id;
    }

    public void setID(int anID) {
        id = anID;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String aLabel) {
        label = aLabel.trim();
    }

    public ArrayList<Edge> getEdges() {
        return edges;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selectedIfTrue) {
        isSelected = selectedIfTrue;
    }

    public void toggleSelected() {
        isSelected = !isSelected;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location aLocation) {
        if (aLocation != null) location = aLocation;
    }

    boolean containsLocation(Location aLocation) {
        //answer whether aLocation is withing the drawing area of this node
        if (aLocation == null) return false;
        return Location.distanceBetween(location, aLocation) < NODE_RADIUS;

    }

    public void moveDelta(double deltaX, double deltaY) {
        //move location by deltaX,deltaY
        location.moveDelta(deltaX, deltaY);
    }


    public void drawWith(GraphicsContext thePen) {

        thePen.setFill(DEFAULT_NODE_COLOR);
        if (isSelected) thePen.setFill(Drawable.highlightColor);

        //draw the node shape
        thePen.fillOval(location.getX() - NODE_RADIUS, //upper left X
                location.getY() - NODE_RADIUS, //upper left Y
                2 * NODE_RADIUS, //width
                2 * NODE_RADIUS); //height

        //draw label if there is one
        if (label != null && label.trim().length() > 0) {
            Text theText = new Text(label); //create text object that can be measured
            theText.setFont(NODE_LABEL_FONT); //set font of the text object
            //get the width and height of the text object
            double textWidth = theText.getLayoutBounds().getWidth();
            double textHeight = theText.getLayoutBounds().getHeight();

            //draw the node label centered on the node
            thePen.setFill(NODE_LABEL_COLOR);
            thePen.setFont(NODE_LABEL_FONT);
            thePen.fillText(label, location.getX() - textWidth / 2, location.getY() + textHeight / 4);
        }
    }

    public void writeToFile(String baseIndent, PrintWriter outputFile) {
        //Write the node to a file in XML style tag-delimited data that
        //baseIndent is current indentation white space to
        // added in front of the tags for formatting

        String tab = "   ";
        String indent = baseIndent + tab;

        //write class start tag
        outputFile.println(baseIndent + startTag);

        //nodes are expected to have a unique ID tag in the XML data
        outputFile.println(indent + IDStartTag + id + IDEndTag);

        if (this.label != null && !this.label.isEmpty())
            outputFile.println(indent + labelStartTag + getLabel() + labelEndTag);

        outputFile.println(indent + locationStartTag + location.getX() + "," + location.getY() + locationEndTag);

        if (this.isSelected()) outputFile.println(baseIndent + tab + selectedTag);

        //write class end tag
        outputFile.println(baseIndent + endTag);

    }

    public static Node parseFromFile(BufferedReader graphXMLDataStream) {
        //Parse node data from xml file
        // input expected to have the following from
        // The start tag will have been stripped off already
        //parse until the closing tag is found
        /*
            <node>
               <id>1000</id>
               <label>A</label>
               <location>100.0,100.0</location>
            </node>
        */

        if (graphXMLDataStream == null) return null;

        Node parsedNode = new Node(0, 0);  //dummy location to be replaced

        String inputLine; //current input line
        String dataString = null;

        BufferedReader inputFile;

        try {
            //parse until we get to the closing tag
            while ((inputLine = graphXMLDataStream.readLine()) != null) {
                //System.out.println(inputLine);
                dataString = inputLine.trim();

                if (dataString.startsWith(endTag)) return parsedNode;
                else if (dataString.startsWith(IDStartTag) &&
                        dataString.endsWith(IDEndTag)) {
                    String theIDString = dataString.substring(
                            IDStartTag.length(),
                            dataString.length() - IDEndTag.length()
                    );
                    parsedNode.setID(Integer.parseInt(theIDString));
                } else if (dataString.startsWith(labelStartTag) &&
                        dataString.endsWith(labelEndTag)) {
                    String theLabelString = dataString.substring(
                            labelStartTag.length(),
                            dataString.length() - labelEndTag.length()
                    );
                    parsedNode.setLabel(theLabelString.trim());
                } else if (dataString.startsWith(locationStartTag) &&
                        dataString.endsWith(locationEndTag)) {
                    String theLocationString = dataString.substring(
                            locationStartTag.length(),
                            dataString.length() - locationEndTag.length()
                    );
                    String coordinates[] = theLocationString.split(",");
                    Location theLocation = new Location(Double.parseDouble(coordinates[0].trim()),
                            Double.parseDouble(coordinates[1].trim()));

                    parsedNode.setLocation(theLocation);
                } else if (dataString.startsWith(selectedTag)) {
                    parsedNode.setSelected(true);
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
