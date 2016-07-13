package com.ldnel;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

/**
 * Created by ldnel_000 on 2015-10-25.
 */
public interface Drawable {
    public final static Color defaultDrawableFillColor = Color.RED;
    public final static Color defaultDrawableStrokeColor = Color.BLACK;
    public static final Color highlightColor = Color.YELLOW;

    public void drawWith(GraphicsContext thePen);
    //public void drawInArea(GraphicsContext thePen, double topX, double topY, double width, double height);
}