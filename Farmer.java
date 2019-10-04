/**
 * Farmer - COMP2240A2
 * A Farmer Thread object
 *
 * @author Cody Lewis (c3283349)
 * @since 2019-10-01
 */

public class Farmer extends Thread {
    private boolean canCross;
    private String id;
    private Bridge bridge;
    private int distStepped;
    private boolean toWait;
    private String headingTo;
    private boolean blocked;

    /**
     * Construct a farmer with the input values
     *
     * @param id ID of the farmer
     * @param bridge the shared bridge for the Farmer to cross
     * @param headingTo Where the farmer is heading to
     */
    public Farmer(String id, Bridge bridge, String headingTo) {
        canCross = false;
        toWait = false;
        this.id = id;
        this.bridge = bridge;
        distStepped = 0;
        this.headingTo = headingTo;
        waiting();
    }

    /**
     * Print that the farmer is waiting to cross the bridge
     */
    public void waiting() {
        System.out.format(
            "%s: Waiting for bridge. Going towards %s\n",
            id,
            headingTo
        );
    }

    /**
     * Cross the bridge
     *
     * @param amount Amount of steps to cross the bridge by
     */
    public boolean cross(int amount) {
        distStepped += amount;
        if (distStepped < bridge.getLength()) {
            System.out.format(
                "%s: Crossing bridge Step %d.\n", id, distStepped
            );
            return false;
        } else {
            distStepped = 0;
            headingTo = headingTo == "North" ? "South" : "North";
            System.out.format("%s: Across the bridge.\n", id);
            return true;
        }
    }

    /**
     * Main running function for this Thread
     */
    public void run() {
        try {
            while (true) {
                waiting();
                bridge.waitForBridge(this);
                while (!cross(5));
                bridge.incrementNeon();
                bridge.signalBridge();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String toString() {
        return id;
    }
}
