public class IntegerTurtle extends TurtleDecorator {

    public IntegerTurtle (Turtle turtle) {
        super(turtle);
    }

    @Override
    public void move (double dist) {
        super.move(dist);
        turtle.setPosition(Math.round(turtle.getX()), Math.round(turtle.getY()));
    }

    @Override
    public void turn (double turnAngle) {
        super.turn(turnAngle);
        turtle.setPosition(Math.round(turtle.getX()), Math.round(turtle.getY()));
    }
}
