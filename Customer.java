/**
 * Customer - COMP2240
 * An ice cream parlour customer
 *
 * @author Cody Lewis (c3283349)
 */

public class Customer extends Thread implements Comparable<Customer> {
    private String id;
    private int arrivalTime;
    private int eatingTime;
    private int finishEatingTime;
    private Parlour parlour;
    private String stats;

    /**
     * Input Constructor
     *
     * @param arrivalTime Time that the customer arrives at the parlour
     * @param id id of the customer
     * @param eatingTime Amount of time that the customer takes to eat
     * @param parlour The ice cream parlour that the customer will eat at
     */
    public Customer(int arrivalTime, String id, int eatingTime, Parlour parlour) throws Exception {
        this.id = id;
        this.arrivalTime = arrivalTime;
        this.eatingTime = eatingTime;
        this.parlour = parlour;
        this.stats = String.format("%s\t\t%d\t", id, arrivalTime);
        parlour.addEvent(this);
    }

    /**
     * Arrive at the parlour
     */
    public void arrive() throws Exception {
        this.stats += String.format("%d\t", parlour.takeSeat(this));
    }

    /**
     * Start eating at the parlour
     */
    public void startEating(int time) {
        this.stats += String.format("%d\t", time);
    }

    /**
     * Leave the parlour
     */
    public int leave() throws Exception {
        return parlour.leave(this);
    }

    /**
     * Get the summary of what the customer has done
     */
    public String summary() {
        return stats;
    }

    /**
     * Get the id of this
     *
     * @return this Customer's id
     */
    public String getID() { return id; }

    /**
     * Arrive at the parlour, eat, then leave
     */
    public void run() {
        try {
            Thread.sleep(arrivalTime * Timer.QUANTUM + Timer.EPSILON);
            arrive();
            Thread.sleep(eatingTime * Timer.QUANTUM + Timer.EPSILON);
            this.stats += leave();
        } catch (Exception e) {
            System.out.format("Error: %s", e.getMessage());
        }
    }

    /**
     * Compare the id of this customer to another
     */
    public int compareTo(Customer other) {
        return id.compareTo(other.getID());
    }
}
