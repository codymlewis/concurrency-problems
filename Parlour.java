import java.util.Set;
import java.util.TreeSet;
import java.util.concurrent.Semaphore;

/**
 * Parlour - COMP2240
 * The ice cream parlour, including the line up logic
 *
 * @author Cody Lewis (c3283349)
 */

public class Parlour {
    private int availableSeats;
    private int totalSeats;
    private Semaphore seats;
    private Semaphore accessTime;
    private Semaphore accessCustomers;
    private Semaphore wait;
    private boolean waitMode;
    private Timer timer;
    private Set<Customer> customers;

    /**
     * Default constructor
     */
    public Parlour() {
        totalSeats = 4;
        availableSeats = totalSeats;
        seats = new Semaphore(totalSeats, true);
        accessCustomers = new Semaphore(1, true);
        accessTime = new Semaphore(1, true);
        wait = new Semaphore(0, true);
        customers = new TreeSet<>();
        timer = new Timer();
        waitMode = false;
    }

    /**
     * Start the timer
     */
    public void start() {
        timer.start();
    }

    /**
     * Check whether there are still things to do
     *
     * @return true if there are still customers that will eat else false
     */
    public boolean hasEvents() throws Exception {
        accessCustomers.acquire();
        boolean result = customers.isEmpty();
        accessCustomers.release();
        return !result;
    }

    /**
     * Stop the timer
     */
    public void stopTimer() {
        timer.kill();
    }

    /**
     * Add a thing to do
     *
     * @param customer The Customer involved in the event
     */
    public void addEvent(Customer customer) throws Exception {
        accessCustomers.acquire();
        customers.add(customer);
        accessCustomers.release();
    }

    /**
     * Like young Skywalker, the Customer will take a seat
     *
     * @param customer Customer to take a seat
     * @return Time when the customer will finish eating
     */
    public int takeSeat(Customer customer) throws Exception {
        if (waitMode) {
            wait.acquire();
        }
        seats.acquire();
        if (--availableSeats == 0) {
            waitMode = true;
        }
        accessTime.acquire();
        int curTime = timer.getTime();
        accessTime.release();
        return curTime;
    }

    /**
     * The Customer leaves the Parlour
     *
     * @param customer Customer that will leave
     * @return Time the Customer leaves
     */
    public int leave(Customer customer) throws Exception {
        accessCustomers.acquire();
        customers.remove(customer);
        accessCustomers.release();
        seats.release();
        if (++availableSeats == 4) {
            waitMode = false;
            for (int i = 0; i < totalSeats; ++i) {
                wait.release();
            }
        }
        accessTime.acquire();
        int curTime = timer.getTime();
        accessTime.release();
        return curTime;
    }
}
