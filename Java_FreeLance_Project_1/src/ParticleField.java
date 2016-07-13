import javax.swing.JPanel;
import java.awt.Dimension;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.List;

public class ParticleField extends JPanel {

    private List<Particle> particles;

    public ParticleField () {
        setPreferredSize(new Dimension(500, 500));
        particles = new ArrayList<>();
        for (int i = 0; i < 100; i++) {
            particles.add(new Particle());
        }
    }

    @Override
    protected void paintComponent (Graphics g) {
        super.paintComponent(g);
        for (Particle p : particles) {
            g.drawRect((int)p.x(), (int)p.y(), 3, 3);
        }
    }
}
