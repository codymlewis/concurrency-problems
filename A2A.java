import java.util.stream.IntStream;

/**
 * A2A - COMP2240A2
 * Main class for part A of this assignment
 *
 * @author Cody Lewis (c3283349)
 * @since 2019-09-10
 */

public class A2A {
    public static void main(String args[]) {
        if (args.length != 2) {
            System.err.println("Error: Incorrect arguments supplied.");
            System.err.println("Usage: java A2A N=<integer> S=<integer>");
            System.exit(1);
        }
        Bridge bridge = new Bridge(15);
        IntStream.range(1, Integer.parseInt(args[0].substring(args[0].indexOf("=") + 1)) + 1)
            .boxed()
            .forEach(i -> (new Farmer(String.format("N_Farmer%d", i), bridge, "South")).start());
        IntStream.range(1, Integer.parseInt(args[1].substring(args[1].indexOf("=") + 1)) + 1)
            .boxed()
            .forEach(i -> (new Farmer(String.format("S_Farmer%d", i), bridge, "North")).start());
    }
}
