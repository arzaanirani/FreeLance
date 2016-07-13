package com.ldnel;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;

import java.util.Random;

/**
 * Created by ldnel_000 on 2015-09-16.
 */
public class MovingWord implements Drawable, Movable{
    /*
    Represents a movable word
     */

    private String word = "Hello World";
    private Location location;
    private double wordDirectionX = 1;  //1=>right, -1=>left
    private double wordDirectionY = 1; //1=>downward, -1=>upward,
    private Font wordFont = Font.font( "Courier New", FontWeight.BOLD, 40 );
    private boolean isSelected = false; //selected if true

    public MovingWord(String aString, double x, double y){
        word = aString;
        location = new Location(x,y);
    }

    public void setText(String aString){word = aString;}
    public String getText(){return word;}
    public void setSelected(boolean selectedIfTrue){isSelected = selectedIfTrue;}
    public boolean getSelected(){return isSelected;}
    public Font getFont(){return wordFont;}
    public Location getLocation(){return location;}
    public void moveDelta(double deltaX, double deltaY){location.moveDelta(deltaX, deltaY);}
    public double getDirectionX(){return wordDirectionX;}
    public double getDirectionY(){return wordDirectionY;}
    public void setDirectionX(double aDirectionX){wordDirectionX = aDirectionX;}
    public void setDirectionY(double aDirectionY){wordDirectionY = aDirectionY;}

    public boolean containsLocation(Location aLocation){
        Text theText = new Text(getText()); //create text object that can be measured
        theText.setFont(getFont()); //set font of the text object
        double textWidth = theText.getLayoutBounds().getWidth();
        double textHeight = theText.getLayoutBounds().getHeight();
        if(aLocation.isWithinRect(
                new Rectangle(getLocation().getX(),
                        getLocation().getY() - textHeight,
                        textWidth,
                        textHeight)
        )) return true;
        else
            return false;

    }
    public void advanceInArea(double topLeftX,double topLeftY,double width, double height){
        double increment = 3.0; //amount to move word

        moveDelta(increment*wordDirectionX, increment*wordDirectionY);

        //build a text object to represent the actual width of the greeting string
        Text theText = new Text(getText()); //create text object that can be measured
        theText.setFont(getFont()); //set font of the text object
        //get the width and height of the text object
        double textWidth = theText.getLayoutBounds().getWidth();
        double textHeight = theText.getLayoutBounds().getHeight();

        if (location.getX() + textWidth > width) setDirectionX(Movable.LEFT);
        if (location.getX() < 0) setDirectionX(Movable.RIGHT);
        if (location.getY() > height) setDirectionY(Movable.UPWARDS);
        if (location.getY() < textHeight) setDirectionY(Movable.DOWNWARDS);

    }

    public void drawWith(GraphicsContext thePen){

        //set desired pen properties
        if(isSelected)
            thePen.setFill(Drawable.highlightColor);
        else
            thePen.setFill(Drawable.defaultDrawableFillColor);
        thePen.setStroke(Drawable.defaultDrawableStrokeColor);
        thePen.setLineWidth(2);
        thePen.setFont(wordFont);

        //draw the word
        thePen.fillText(word, location.getX(), location.getY());
        thePen.strokeText(getText(), location.getX(), location.getY());
    }

}

