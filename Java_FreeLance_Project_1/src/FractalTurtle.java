public class FractalTurtle extends TurtleDecorator {

    private final int N = 100;

    public FractalTurtle (Turtle turtle) {
        super(turtle);
    }

    @Override
    public void move (double dist) {
        double delta = dist / N;
        for (int i = 0; i < N; i++) {
            double d = delta * Math.pow(2, -i);
            super.move(d);
            turn(i % 2 == 0 ? 10 : -10);
        }
    }
}
