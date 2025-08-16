package bcu.cmp5332.bookingsystem.commands;

import bcu.cmp5332.bookingsystem.data.CustomerDataManager;
import bcu.cmp5332.bookingsystem.main.FlightBookingSystemException;
import bcu.cmp5332.bookingsystem.model.Customer;
import bcu.cmp5332.bookingsystem.model.FlightBookingSystem;

import java.io.IOException;
import javax.swing.JOptionPane;

/**
 * The {@code DeleteCustomer} command removes a customer from the flight booking system.
 * A customer can only be deleted if they have no active bookings.
 */
public class DeleteCustomer implements Command {

    private final int customerId;

    /**
     * Creates a command to delete a customer with the specified ID.
     *
     * @param customerId The ID of the customer to be deleted.
     */
    public DeleteCustomer(int customerId) {
        this.customerId = customerId;
    }

    /**
     * Executes the delete customer command.
     * The customer is removed from the system if they exist and have no active bookings.
     * The updated customer data is then saved to file.
     *
     * @param flightBookingSystem The flight booking system where the customer exists.
     * @throws FlightBookingSystemException If the customer does not exist or cannot be deleted.
     */
    @Override
    public void execute(FlightBookingSystem flightBookingSystem) throws FlightBookingSystemException {
        // Get the customer by ID
        Customer customer = flightBookingSystem.getCustomerByID(customerId);

        // Check if the customer exists
        if (customer == null) {
            throw new FlightBookingSystemException("Customer with ID " + customerId + " not found.");
        }

        // Check if the customer has any bookings
        if (!customer.getBookings().isEmpty()) {
            throw new FlightBookingSystemException("Customer #" + customerId + " has active bookings and cannot be deleted.");
        }

        // Remove the customer from the system
        flightBookingSystem.getCustomersMap().remove(customerId); 

        // Save the updated customer data to customers.txt
        try {
            CustomerDataManager customerDataManager = new CustomerDataManager();
            customerDataManager.storeData(flightBookingSystem); 
        } catch (IOException ex) {
            throw new FlightBookingSystemException("Failed to save customer data: " + ex.getMessage());
        }

        // Print success message in console
        System.out.println("Customer #" + customerId + " deleted successfully.");

        // Show success message in GUI
        JOptionPane.showMessageDialog(
            null, 
            "Customer #" + customerId + " deleted successfully!",
            "Customer Deleted",
            JOptionPane.INFORMATION_MESSAGE
        );
    }
}
