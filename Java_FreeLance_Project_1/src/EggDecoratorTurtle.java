import java.util.Random;

public class EggDecoratorTurtle extends TurtleDecorator {

    private double prob, diameter;

    private static Random rng;

    static {
        rng = new Random();
    }

    public EggDecoratorTurtle (double prob, double diameter) {
        super(new BasicTurtle());
        this.prob = prob;
        this.diameter = diameter;
    }

    @Override
    public void move (double dist) {
        turtle.move(dist);
        if (rng.nextDouble() < prob)
            turtle.getGraphics2D().drawOval((int) (turtle.getX() - diameter / 2), (int) (turtle.getY() - diameter / 2), (int) (diameter), (int) (diameter));
    }
}
