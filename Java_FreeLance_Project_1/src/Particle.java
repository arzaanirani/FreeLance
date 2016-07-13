import java.util.Random;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Particle {
    private double x, y;
    private static Random rng;

    static {
        rng = new Random() {
            private Lock mutex = new ReentrantLock();

            @Override
            public double nextGaussian () {
                mutex.lock();
                double result = super.nextGaussian();
                mutex.unlock();
                return result;
            }
        };
    }

    public Particle () {
        x = rng.nextDouble() * 500;
        y = rng.nextDouble() * 500;
    }

    public void move () {
        x += rng.nextGaussian();
        y += rng.nextGaussian();
    }

    public double x () {
        return x;
    }

    public double y () {
        return y;
    }
}
