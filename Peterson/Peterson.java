import java.util.concurrent.atomic.AtomicIntegerArray;

/**
 * Implementation of Peterson's algorithm for mutual exclusion
 *
 * @author  Benjamin Sühling
 * @since   2021-12-23
 */
public class Peterson extends Thread {

    private static int k;

    private static AtomicIntegerArray flag;
    private static volatile int turn;

    private final int id;

    private static int cycles;
    private static int threadsFinished;

    public Peterson(int id) {
        this.id = id;
        flag = new AtomicIntegerArray(2);
    }

    @Override
    public void run() {
        for (int i = 0; i < cycles; i++) {
            lock();
            // critical section:
            k++;
            unlock();
        }
        finish();
    }

    private void lock() {
        int other = 1 - id;
        flag.set(id, 1);
        turn = other;
        while (flag.get(other) == 1 && turn == other) {
            // do nothing
        }
    }

    private void unlock() {
        flag.set(id, 0);
    }

    private void finish() {
        Peterson.threadsFinished++;
        if (Peterson.threadsFinished == 2) {
            System.out.println("Expected: " + 2 * cycles);
            System.out.println("Result:   " + k);
        }
    }

    public static void main(String[] args) {
        if (args.length < 1) {
            Peterson.cycles = 10000;
        } else {
            Peterson.cycles = Integer.parseInt(args[0]);
        }
        for (int i = 0; i < 2; i++) {
            new Peterson(i).start();
        }
    }
}
