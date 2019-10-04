/**
 * Client - COMP2240A2
 * A Client for a coffee machine
 *
 * @author Cody Lewis (c3283349)
 */

public class Client extends Thread implements Comparable<Client> {
    private String id;
    private int brewTime;
    private boolean isHot;
    private CoffeeMachine coffeeMachine;

    /**
     * Input constructor
     *
     * @param id ID of the client
     * @param brewTime time the client will take to brew a coffee
     * @param coffeeMachine CoffeeMachine that the Client will use
     */
    public Client(String id, int brewTime, CoffeeMachine coffeeMachine) {
        this.id = id;
        this.brewTime = brewTime;
        isHot = id.contains("H");
        this.coffeeMachine = coffeeMachine;
        coffeeMachine.addClient(this);
    }

    /**
     * Find whether the client wants a hot coffee
     *
     * @return true if they want a hot coffee else false
     */
    public boolean gettingHot() {
        return isHot;
    }

    /**
     * Get the id of the client
     *
     * @return this Client's id
     */
    public String getID() {
        return id;
    }

    /**
     * Get the time that this Client wants to brew for
     *
     * @return brewing time
     */
    public int getBrewTime() {
        return brewTime;
    }

    /**
     * Compare the id of this to another Client
     *
     * @param other The other Client
     * @return id of this compared to the id of other
     */
    public int compareTo(Client other) {
        return id.compareTo(other.getID());
    }

    /**
     * Wait in line, brew a coffee for some time, and leave.
     */
    public void run() {
        try {
            synchronized (this) {
                wait();
            }
            CoffeeMachine.Tap curTap = coffeeMachine.nextTap();
            synchronized (curTap) {
                System.out.format(
                    "(%d) %s uses dispenser %d (time: %d)\n",
                    coffeeMachine.getTime(),
                    id,
                    curTap.getID(),
                    brewTime
                );
                Thread.sleep(brewTime * SyncTimer.QUANTUM);
                coffeeMachine.returnTap(curTap);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
