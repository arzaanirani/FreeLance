package com.ldnel;

import javafx.scene.shape.Rectangle;

/**
 * Created by ldnel_000 on 2015-10-25.
 */
public class Location {
    /*
    A Helper class to represent an x,y location.
    Locations are expected to be relative to upper left (0,0).
    Location x and y values should not be negative
     */

    private double x; //x location,  0 is leftmost
    private double y; //y location, 0 is topmost

    public static double distanceBetween(Location loc1, Location loc2){
        //answer pythagoreon distance between loc1 and loc2
        return Math.sqrt((loc2.getX() - loc1.getX())*(loc2.getX() - loc1.getX()) +
                         (loc2.getY() - loc1.getY())*(loc2.getY() - loc1.getY())
        );
    }

    public Location(double anX, double aY){
        this.setX(anX);
        this.setY(aY);
    }
    public double getX(){return x;}
    public double getY(){return y;}
    public void setX(double anX){
        if( x >= 0) x = anX;
        else x = 0; //prevent negative values
    }
    public void setY(double aY){
        if(y >= 0) y = aY;
        else y = 0; //prevent negative values
    }
    public void moveDelta(double deltaX, double deltaY){
        //move the location by deletX,deltaY
        setX(x + deltaX);
        setY(y + deltaY);
    }
    public boolean isWithinRect(Rectangle rect){
        //Answer whether this location is withing rectangle rect
        //rect.x and rect.y are the upper,left corner of rect.
        if(x < rect.getX()) return false;
        if(x > rect.getX() + rect.getWidth()) return false;
        if(y < rect.getY()) return false;
        if(y > rect.getY() + rect.getHeight()) return false;
        return true;
    };
}
