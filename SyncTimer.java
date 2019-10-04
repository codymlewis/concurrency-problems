/**
 * SyncTimer - COMP2240
 * Timer that uses monitors
 *
 * @author Cody Lewis (c3283349)
 */

public class SyncTimer extends Thread {
    public static final int QUANTUM = 50;
    public static final int EPSILON = 1;
    private int time;
    private boolean alive;

    /**
     * Default constructor
     */
    public SyncTimer() {
        time = 0;
        alive = true;
    }

    /**
     * Get the current time
     *
     * @return current time in quantum units
     */
    public synchronized int getTime() {
        return time;
    }

    /**
     * Kill this timer
     */
    public synchronized void kill() {
        alive = false;
    }

    /**
     * Sleep for a quantum then increment the time
     */
    public void run() {
        try {
            while(alive) {
                Thread.sleep(QUANTUM);
                time++;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
