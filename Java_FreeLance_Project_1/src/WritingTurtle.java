import java.io.PrintWriter;

public class WritingTurtle extends TurtleDecorator {

    private PrintWriter out;

    public WritingTurtle(Turtle t, PrintWriter p) {
        super(t);
        this.out = p;
    }

    @Override
    public void move (double dist) {
        out.println("Move " + dist);
        out.flush();
        super.move(dist);
    }

    @Override
    public void turn (double turnAngle) {
        out.println("Turn " + turnAngle);
        super.turn(turnAngle);
        out.flush();
    }
}
