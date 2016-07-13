public class SubdividedMotionTurtle extends TurtleDecorator {

    private final int n;

    public SubdividedMotionTurtle (Turtle turtle, int n) {
        super(turtle);
        this.n = n;
    }

    @Override
    public void move (double dist) {
        for (int i = 0; i < n; i++) {
            super.move(dist / n);
        }
    }

    @Override
    public void turn (double turnAngle) {
        for (int i = 0; i < n; i++) {
            super.move(turnAngle / n);
        }
    }
}
