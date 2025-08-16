package bcu.cmp5332.bookingsystem.commands;

import bcu.cmp5332.bookingsystem.main.FlightBookingSystemException;
import bcu.cmp5332.bookingsystem.model.Customer;
import bcu.cmp5332.bookingsystem.model.FlightBookingSystem;
import java.util.List;

/**
 * The {@code ListCustomers} command retrieves and displays a list of all customers
 * registered in the flight booking system.
 */
public class ListCustomers implements Command {

    /**
     * Executes the command to list all customers in the system.
     * Retrieves the list of customers and prints their short details.
     *
     * @param flightBookingSystem The flight booking system instance.
     * @throws FlightBookingSystemException If an error occurs while retrieving customers.
     */
    @Override
    public void execute(FlightBookingSystem flightBookingSystem) throws FlightBookingSystemException {
        // Get the list of customers from the FlightBookingSystem
        List<Customer> customers = flightBookingSystem.getCustomers();

        // Check if there are any customers
        if (customers.isEmpty()) {
            System.out.println("No customers found.");
        } else {
            // Print details of each customer
            for (Customer customer : customers) {
                System.out.println(customer.getDetailsShort());
            }
            System.out.println(customers.size() + " customer(s) found.");
        }
    }
}
