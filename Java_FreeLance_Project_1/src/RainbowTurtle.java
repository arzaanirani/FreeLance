import java.awt.*;

public class RainbowTurtle extends BasicTurtle {

    @Override
    public void move(double dist) {
        this.setColor(new Color((int) (Math.random() * 256), (int) (Math.random() * 256), (int) (Math.random() * 256)));
        super.move(dist);
    }
}
