/**
 * Timer - COMP2240
 * Keep time in a separate Thread
 *
 * @author Cody Lewis (c3283349)
 */

public class Timer extends Thread {
    private int time;
    private boolean alive;
    /**
     * Amount of time (ms) for a single unit of sleep
     */
    public static final int QUANTUM = 100;

    /**
     * Default constructor
     */
    public Timer() {
        time = 0;
        alive = true;
    }

    /**
     * Get the current time in quantums from this Thread starting
     *
     * @return time in quantums from this starting
     */
    public int getTime() {
        return time;
    }

    /**
     * Kill this timer
     */
    public void kill() {
        alive = false;
    }

    /**
     * Sleep for a quantum then iterate the time
     */
    public void run() {
        try {
            while(alive) {
                Thread.sleep(Timer.QUANTUM);
                time++;
            }
        } catch (Exception e) {
            System.out.format("Error: %s", e.getMessage());
        }
    }
}
