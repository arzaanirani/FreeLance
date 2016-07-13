import java.util.concurrent.Semaphore;

public class LearningThreads {
    Semaphore maySleep = new Semaphore(1);
    boolean running = true;

    private class SleepyHead implements Runnable {
        private int id;

        public SleepyHead (int id) {
            this.id = id;
        }

        @Override
        public void run () {
            while (running) {
                try {
                    maySleep.acquire();
                    System.out.println("Thread id " + id + " has acquired a permit to sleep.");
                    Thread.sleep(2000);
                    System.out.println("Thread id " + id + " has woken up.");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            System.out.println("Thread id " + id + " has completed it's execution.");
        }
    }

    public LearningThreads () {
        for (int i=1; i<=5; i++)
            new Thread(new SleepyHead(i)).start();
    }

    @Override
    protected void finalize () throws Throwable {
        super.finalize();
        running = false;
    }
}
