import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

// This code is responsible for displaying the model/shape
public class DisplayPanel extends JPanel {
    private int[][] facesX;
    private int[][] facesY;
    private int width;
    private int height;
    
    public DisplayPanel(int[][] fX, int[][] fY) {
        facesX = fX;
        facesY = fY;
        setPreferredSize(new Dimension(800, 800));
    }
    
    // Display the image
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        for (int i=0; i<facesX.length; i++) {
            g.setColor(Color.gray);
            g.fillPolygon(facesX[i], facesY[i], 3);
            g.setColor(Color.black);
            g.drawPolygon(facesX[i], facesY[i], 3);
        }
    }
}
