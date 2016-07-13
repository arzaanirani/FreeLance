public class ParticleFieldWithThreads extends ParticleField {

    public ParticleFieldWithThreads () {
        super();
        new Thread(() -> {
            while (true) {
                repaint();
                try {
                    Thread.sleep(40);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }
}
