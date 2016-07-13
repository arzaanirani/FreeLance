import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

public class TurtleTree extends JPanel {

    private BufferedImage img;

    public TurtleTree(Turtle turtle, int size) {
        setPreferredSize(new Dimension(400, 400));
        img = new BufferedImage(400, 400, BufferedImage.TYPE_INT_RGB);
        turtle.setGraphics2D((Graphics2D) img.getGraphics());
        turtle.setColor(Color.RED);
        turtle.setPosition(200, 375);
        turtle.turn(-90);
        drawTree(turtle, size);
    }

    public void drawTree(Turtle turtle, double len) {
        if (len < 5)
            return;
        turtle.move(len);
        turtle.pushState();
        turtle.turn(45);
        drawTree(turtle, len / 2);
        turtle.popState();
        turtle.turn(-45);
        drawTree(turtle, len/2);
    }

    @Override
    public void paintComponent(Graphics g) {
        g.drawImage(img, 0, 0, null);
    }

    public static void main(String[] args) throws InterruptedException {
        JFrame frame = new JFrame();
        frame.setLayout(new FlowLayout(FlowLayout.CENTER));
        frame.setSize(1200, 1200);
        frame.add(new TurtleTree(new BasicTurtle(), 100));
        frame.add(new TurtleTree(new CurveTurtle(), 200));
        frame.add(new TurtleTree(new VeeringTurtle(), 150));
        frame.pack();
        frame.setVisible(true);
    }
}
