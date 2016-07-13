import javax.swing.Timer;

public class ParticleFieldWithTimer extends ParticleField {

    public ParticleFieldWithTimer () {
        super();
        int delay = 1000 / 25;
        new Timer(delay, e -> repaint()).start();
    }
}
