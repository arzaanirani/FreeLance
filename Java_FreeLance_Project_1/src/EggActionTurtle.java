import java.util.Random;

public class EggActionTurtle extends ActionTurtle {

    private static Random rng;

    static {
        rng = new Random();
    }

    public EggActionTurtle (double prob, double diameter) {
        addAction(new Action() {
            @Override
            public void action (Turtle turtle) {
                if (rng.nextDouble() < prob)
                turtle.getGraphics2D().drawOval((int) (turtle.getX() - diameter / 2), (int) (turtle.getY() - diameter / 2), (int) (diameter), (int) (diameter));
            }
        });
    }

    @Override
    public void move (double dist) {
        super.move(dist);
    }
}
