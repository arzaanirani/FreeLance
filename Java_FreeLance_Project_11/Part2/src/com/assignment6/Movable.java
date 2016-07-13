package com.assignment6;

public interface Movable {

    //Direction
    public final static double RIGHT = 1.0;
    public final static double LEFT = -1.0;
    public final static double UPWARDS = -1.0;
    public final static double DOWNWARDS = 1.0;

    public void advanceInArea(double topLeftX,double topLeftY,double width, double height);
}