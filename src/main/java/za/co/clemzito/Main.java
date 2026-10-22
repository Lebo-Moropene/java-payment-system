package za.co.clemzito;

/**
 * Entry point of the Payment System application.
 *
 * <p>Prints a welcome banner and a short sample list of payments to the
 * console when the application is executed.</p>
 */
public class Main {

    /**
     * Creates the application entry point.
     */
    public Main() {
    }

    /**
     * Runs the Payment System demo.
     *
     * @param args command line arguments supplied to the application; they are
     *             currently unused.
     */
    public static void main(String[] args){
        System.out.println("Welcome to the Payment System");

        for(int i = 1; i<= 5; i++){
            System.out.println("Payment number: " + i);
        }
    }
}
