import java.util.Collection;
import java.util.LinkedList;
import java.util.Scanner;
import java.io.File;

/**
 * A2B - COMP2240
 * Main class for part B of this assignment
 *
 * @author Cody Lewis (c3283349)
 */

public class A2B {
    public static void main(String[] args) {
        if (args.length < 1) {
            System.err.println("Error: Incorrect arguments supplied.");
            System.err.println("Usage: java A2B <inputfile>");
            System.exit(1);
        }
        A2B runner = new A2B();
        try {
            runner.run(args[0]);
        } catch (Exception e) {
            System.err.format("Error: %s", e.getMessage());
        }
    }

    /**
     * Create a parlour, some customers and run until they all leave
     *
     * @param filename name of the file contiain the data of each of the customers
     */
    public void run(String filename) throws Exception {
        System.out.println("Customer\tarrives\tSeats\tLeaves");
        Parlour parlour = new Parlour();
        Collection<Customer> customers = parseFile(filename, parlour);
        parlour.start();
        customers.stream().forEach(c -> c.start());
        while (parlour.hasEvents());
        customers.stream().forEach(c -> System.out.println(c.summary()));
        parlour.stopTimer();
    }

    /**
     * Parse the customer data file
     *
     * @param filename name of the file contiain the data of each of the customers
     * @param parlour Ice cream parlour that the customers will use
     * @return Linked list of the customers
     */
    private Collection<Customer> parseFile(String filename, Parlour parlour) {
        LinkedList<Customer> result = new LinkedList<>();
        try (Scanner fstream = new Scanner(new File(filename))) {
            String line;
            while (!(line = fstream.nextLine()).equals("END")) {
                Scanner sstream = new Scanner(line);
                result.add(
                    new Customer(sstream.nextInt(), sstream.next(), sstream.nextInt(), parlour)
                );
            }
        } catch (Exception e) {
            System.err.format("Error: %s", e.getMessage());
        }
        return result;
    }
}
