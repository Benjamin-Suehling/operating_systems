import java.util.concurrent.atomic.AtomicIntegerArray;

/**
 * Implementation of the filter algorithm,
 * that is the extension of Peterson's algorithm
 * to work more than 2 processes
 *
 * @author  Benjamin Sühling
 * @since   2021-12-23
 */
public class Filter extends Thread {

    private static int k;

    private static AtomicIntegerArray level;
    private static AtomicIntegerArray lastToEnter;

    private final int id;

    private static int cycles;
    private static int threads;
    private static int threadsFinished;

    public Filter(int id) {
        this.id = id;
        level = new AtomicIntegerArray(threads);
        for (int i = 0; i > level.length(); i++) {
            level.set(i, -1);
        }
        lastToEnter = new AtomicIntegerArray(threads - 1);
    }

    @Override
    public void run() {
        for (int i = 0; i < Filter.cycles; i++) {
            lock();
            // critical section:
            k++;
            unlock();
        }
        finish();
    }

    private void lock() {
        for (int l = 0; l < threads - 1; l++) {
            level.set(id, l);
            lastToEnter.set(l, id);
            while (lastToEnter.get(l) == id && existsThreadWithAtLeastSameLevel(id)) {
                // do nothing
            }
        }
    }

    private boolean existsThreadWithAtLeastSameLevel(int i) {
        int threadLevel = level.get(i);
        for (int j = 0; j < threads; j++) {
            if (level.get(j) >= threadLevel && i != j) {
                return true;
            }
        }
        return false;
    }

    private void unlock() {
        level.set(id, -1);
    }

    private void finish() {
        threadsFinished++;
        if (threadsFinished == threads) {
            System.out.println("Expected: " + threads * cycles);
            System.out.println("Result:   " + k);
        }
    }

    public static void main(String[] args) {
        if (args.length < 2) {
            if (args.length < 1) {
                cycles = 10000;
            } else {
                cycles = Integer.parseInt(args[0]);
            }
            threads = 10;
        } else {
            threads = Integer.parseInt(args[1]);
        }
        for (int i = 0; i < threads; i++) {
            new Filter(i).start();
        }
    }
}
