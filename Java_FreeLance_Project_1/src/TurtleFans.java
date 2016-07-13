import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class TurtleFans extends JPanel {

    private BufferedImage img;

    public TurtleFans (Turtle turtle, int size, int n) {
        setPreferredSize(new Dimension(400, 400));
        img = new BufferedImage(400, 400, BufferedImage.TYPE_INT_RGB);
        turtle.setGraphics2D((Graphics2D) img.getGraphics());
        turtle.setPosition((int) (Math.random() * 400), (int) (Math.random() * 400));
        turtle.setColor(Color.RED);
        turtle.setPen(true);
        for (int i = 0; i < n; i++) {
            for (int d = 0; d < 4; d++) {
                turtle.move(d % 2 == 0 ? size : size / 5);
                turtle.turn(90);
            }
            System.out.println(360 / n);
            turtle.turn(360 / n);
        }
    }

    @Override
    public void paintComponent (Graphics g) {
        g.drawImage(img, 0, 0, null);
    }

    public static void main (String[] args) throws InterruptedException, IOException {
        JFrame frame = new JFrame();
        frame.setLayout(new FlowLayout(FlowLayout.CENTER));
        frame.setSize(1200, 1200);
        frame.add(new TurtleFans(new WritingTurtle(new EggActionTurtle(.25, 12), new PrintWriter(new FileWriter("output.txt"))), 70, 3));
        frame.pack();
        frame.setVisible(true);
    }
}
