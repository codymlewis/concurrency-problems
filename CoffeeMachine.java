import java.util.Queue;
import java.util.LinkedList;
import java.util.Stack;
import java.util.PriorityQueue;

/**
 * CoffeeMachine - COMP2240A2
 * A coffee machine
 *
 * @author Cody Lewis (c3283349)
 */

public class CoffeeMachine {

    /**
     * A dispenser tap in the CoffeeMachine
     */
    public class Tap {
        private int id;

        /**
         * Input constructor
         *
         * @param id id number of the tap
         */
        public Tap(int id) {
            this.id = id;
        }

        /**
         * Get the id number of this tap
         *
         * @return this tap's id number
         */
        public int getID() {
            return id;
        }
    }

    private int numberTaps;
    private Stack<Tap> taps;
    private SyncTimer timer;
    private Queue<Client> line;
    private PriorityQueue<Integer> eventTimes;
    private boolean servingHot;
    private boolean changeHeat;

    /**
     * Default constructor
     */
    public CoffeeMachine() {
        numberTaps = 2;
        taps = new Stack<Tap>();
        eventTimes = new PriorityQueue<>();
        timer = new SyncTimer();
        for (int i = 1; i <= numberTaps; ++i) {
            taps.push(new Tap(i));
            eventTimes.offer(timer.getTime());
        }
        line = new LinkedList<>();
        servingHot = true;
        changeHeat = true;
    }

    /**
     * Start the timer
     */
    public void startTimer() {
        timer.start();
    }

    /**
     * Stop the timer
     */
    public void stopTimer() {
        timer.kill();
    }

    /**
     * Get the current time from the timer
     */
    public int getTime() {
        return timer.getTime();
    }

    /**
     * Add a client to the line
     *
     * @param client Client to add the the end of the line
     */
    public void addClient(Client client) {
        line.offer(client);
    }

    /**
     * Run through serving each of the clients
     */
    public synchronized void run() throws Exception {
        while (!eventTimes.isEmpty()) {
            int sleepTime = eventTimes.poll() - timer.getTime();
            Thread.sleep(sleepTime * SyncTimer.QUANTUM + SyncTimer.EPSILON);
            boolean toWait = true;
            while (toWait) {
                synchronized (taps) {
                    toWait = taps.isEmpty();
                }
                if (toWait) { this.wait(); }
            }
            Client curClient;
            // The line lock ensures that the clients are served in order
            synchronized(line) {
                curClient = line.peek();
                if (curClient != null) {
                    synchronized (curClient) {
                        if (changeHeat) {
                            servingHot = curClient.gettingHot();
                            changeHeat = false;
                        }
                        if (servingHot != curClient.gettingHot()) {
                            wait();
                        }
                        eventTimes.offer(timer.getTime() + curClient.getBrewTime());
                        curClient.notify();
                        line.poll();
                    }
                }
            }
        }
        System.out.format("(%d) DONE\n", timer.getTime());
    }

    /**
     * Get the next usable tap from the CoffeeMachine
     *
     * @return The next usable tap
     */
    public Tap nextTap() {
        synchronized (taps) {
            return taps.pop();
        }
    }

    /**
     * Stop using the tap of the CoffeeMachine
     *
     * @param tap The Tap to return
     */
    public synchronized void returnTap(Tap tap) {
        synchronized (taps) {
            taps.push(tap);
            if (taps.size() == numberTaps) {
                changeHeat = true;
            }
            notify();
        }
    }
}
