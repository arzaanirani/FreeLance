import java.util.*;
import java.util.concurrent.*;
import java.util.concurrent.locks.*;
import java.math.*; // for BigInteger

public class ConcurrencyDemo {

    // An inner class for a Runnable task that we use in the next examples. The task
    // simply first waits a random time and then terminates.
    private class SimpleWaiter implements Runnable {
        private int id;
        public SimpleWaiter(int id) {
            this.id = id;
        }
        public void run() {
            System.out.println("Starting waiter #" + id + ".");
            try {
                // Sleep between 2 and 4 seconds
                Thread.sleep(rng.nextInt(2000) + 2000);
            }
            catch(InterruptedException e) { }
            System.out.println("Finishing waiter #" + id + ".");
        }
    }
    
    // Demonstrate how to create and launch new background Threads.
    public void simpleThreadDemo(int n) {
        for(int i = 0; i < n; i++) {
            Thread t = new Thread(new SimpleWaiter(i));
            t.start();
        }
        System.out.println("All waiters created, returning from method.");
    }

    // The previous example implemented using an ExecutorService instead of explicitly
    // creating threads. See what happens if you call this with parameter n > 5.
    public void simpleExecutorDemo(int n) {
        ExecutorService es = Executors.newFixedThreadPool(5);
        for(int i = 0; i < n; i++) {
            es.submit(new SimpleWaiter(i));
        }
        System.out.println("All waiters submitted, returning from method.");
    }
    
    // A thread-safe version of Random that uses a mutex lock to ensure that at most
    // one thread at the time may be executing its nextInt method.
    private Random rng = new Random() {
        private Lock mutex = new ReentrantLock();
        public int nextInt() {
            mutex.lock();
            int result = super.nextInt();
            mutex.unlock();
            return result;
        }
    };
    
    // For the next problem, a Callable task that returns a result of type BigInteger.
    public class PrimeFinder implements Callable<BigInteger> {
        private int id, bits;
        public PrimeFinder(int id, int bits) {
            this.id = id;
            this.bits = bits;
        }
        public BigInteger call() {
            System.out.println("Starting PrimeFinder #" + id + ".");
            BigInteger result = BigInteger.probablePrime(bits, rng);
            System.out.println("Returning from PrimeFinder #" + id + ".");
            return result;
        }
    }
    
    // A method that creates a list of n probable large primes, and does not terminate
    // until n probable primes have been found and the list has been filled.
    public List<BigInteger> findPrimes(int n, int bits) throws InterruptedException {
        ArrayList<BigInteger> result = new ArrayList<BigInteger>();
        ArrayList<Future<BigInteger>> futures = new ArrayList<Future<BigInteger>>();
        ExecutorService es = Executors.newFixedThreadPool(5);

        for(int i = 0; i < n; i++) {
            futures.add(es.submit(new PrimeFinder(i, bits)));
        }
        
        for(Future<BigInteger> f: futures) {
            try {
                result.add(f.get()); // blocks until task is done
            } catch(ExecutionException e) { }
        }
        System.out.println(result);
        return result;
    }                
}