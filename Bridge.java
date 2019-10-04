import java.util.concurrent.Semaphore;

/**
 * Bridge - COMP2240A2
 * The bridge that all farmers will cross
 *
 * @author Cody Lewis (c3283349)
 * @since 2019-09-10
 */
public class Bridge {
    private int neon;
    private int length;
    private Semaphore availabilty;

    /**
     * Default constructor
     */
    public Bridge() {
        neon = 0;
        length = 0;
        availabilty = new Semaphore(1, true);
    }

    /**
     * Input constructor
     *
     * @param length Amount of steps needed to cross the bridge
     */
    public Bridge(int length) {
        this();
        this.length = length;
    }

    /**
     * Get the length of the bridge
     *
     * @return Amount of steps needed to cross the bridge
     */
    public int getLength() {
        return length;
    }

    /**
     * Have the farmer wait for the bridge to be crossable
     *
     * @param farmer A Farmer that wants to cross the bridge
     */
    public void waitForBridge(Farmer farmer) throws Exception {
        availabilty.acquire();
    }

    /**
     * Increment the neon sign value and print it
     */
    public void incrementNeon() {
        System.out.format("NEON = %d\n", ++neon);
    }

    /**
     * Signal that a farmer has finished crossing the bridge
     */
    public void signalBridge() {
        availabilty.release();
    }
}
