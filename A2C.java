import java.util.Scanner;
import java.util.Collection;
import java.util.LinkedList;
import java.io.File;

/**
 * A2C - COMP2240
 * Main class for part C of this assignment
 *
 * @author Cody Lewis (c3283349)
 */

public class A2C {
    public static void main(String[] args) {
        if (args.length < 1) {
            System.err.println("Error: Incorrect arguments supplied.");
            System.err.println("Usage: java A2C <inputfile>");
            System.exit(1);
        }
        A2C runner = new A2C();
        runner.run(args[0]);
    }

    /**
     * Create a parlour, some customers and run until they all leave
     *
     * @param filename name of the file contiain the data of each of the customers
     */
    public void run(String filename) {
        try {
            CoffeeMachine coffeeMachine = new CoffeeMachine();
            parseFile(filename, coffeeMachine).stream().forEach(c -> c.start());
            coffeeMachine.startTimer();
            coffeeMachine.run();
            coffeeMachine.stopTimer();
        } catch (Exception e) {
            System.err.format("Error: %s", e.getMessage());
        }
    }

    /**
     * Parse the Client data file
     *
     * @param filename Name of the Client data file
     * @param coffeeMachine CoffeeMachine that the clients will use
     * @return Collection containing each of the clients
     */
    private Collection<Client> parseFile(String filename, CoffeeMachine coffeeMachine) {
        LinkedList<Client> result = new LinkedList<>();
        try (Scanner fstream = new Scanner(new File(filename))) {
            int clientCount = fstream.nextInt();
            for (int i = 0; i < clientCount; ++i) {
                result.add(new Client(fstream.next(), fstream.nextInt(), coffeeMachine));
            }
        } catch (Exception e) {
            System.err.format("Error: %s", e.getMessage());
        }
        return result;
    }
}
