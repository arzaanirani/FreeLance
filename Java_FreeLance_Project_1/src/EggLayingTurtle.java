import java.util.Random;

public class EggLayingTurtle extends BasicTurtle {

    private double prob, diameter;

    private static Random rng;

    static {
        rng = new Random();
    }

    public EggLayingTurtle (double prob, double diameter) {
        this.prob = prob;
        this.diameter = diameter;
    }

    @Override
    public void move (double dist) {
        super.move(dist);
        if (rng.nextDouble() < prob)
            g2.drawOval((int) (x - diameter / 2), (int) (y - diameter / 2), (int) (diameter), (int) (diameter));
    }
}
