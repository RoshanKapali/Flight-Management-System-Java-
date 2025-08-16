package bcu.cmp5332.bookingsystem.commands;

import bcu.cmp5332.bookingsystem.main.FlightBookingSystemException;
import bcu.cmp5332.bookingsystem.model.Customer;
import bcu.cmp5332.bookingsystem.model.FlightBookingSystem;

/**
 * The {@code ShowCustomer} command displays detailed information about a specific customer.
 * The customer is identified using their unique ID.
 */
public class ShowCustomer implements Command {

    private final int customerId;

    /**
     * Creates a command to display details of a customer with the specified ID.
     *
     * @param customerId The ID of the customer to be displayed.
     */
    public ShowCustomer(int customerId) {
        this.customerId = customerId;
    }

    /**
     * Executes the show customer command.
     * Retrieves and prints the detailed information of the specified customer.
     *
     * @param flightBookingSystem The flight booking system instance.
     * @throws FlightBookingSystemException If the customer does not exist.
     */
    @Override
    public void execute(FlightBookingSystem flightBookingSystem) throws FlightBookingSystemException {
        // Get the customer by ID
        Customer customer = flightBookingSystem.getCustomerByID(customerId);

        // Check if the customer exists
        if (customer == null) {
            throw new FlightBookingSystemException("Customer with ID " + customerId + " not found.");
        }

        // Print detailed customer information
        System.out.println(customer.getDetailsLong());
    }
}
