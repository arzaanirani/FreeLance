public class VeeringTurtle extends BasicTurtle {

    @Override
    public void move(double dist) {
        this.turn(-3);
        super.move(dist);
    }
}
