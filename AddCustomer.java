package bcu.cmp5332.bookingsystem.commands;

import java.io.IOException;
import javax.swing.JOptionPane;
import bcu.cmp5332.bookingsystem.data.CustomerDataManager;
import bcu.cmp5332.bookingsystem.main.FlightBookingSystemException;
import bcu.cmp5332.bookingsystem.model.Customer;
import bcu.cmp5332.bookingsystem.model.FlightBookingSystem;

/**
 * The {@code AddCustomer} command allows a new customer to be added to the flight booking system.
 * A unique ID is generated for the new customer, and their details are saved.
 */
public class AddCustomer implements Command {

    private final String name;
    private final String phone;
    private final String email; // Added to store customer email

    /**
     * Creates a command to add a new customer with the specified details.
     *
     * @param name  The name of the customer.
     * @param phone The phone number of the customer.
     * @param email The email address of the customer.
     */
    public AddCustomer(String name, String phone, String email) {
        this.name = name;
        this.phone = phone;
        this.email = email;
    }

    /**
     * Executes the add customer command.
     * A new customer is created, assigned a unique ID, added to the system, and saved to a file.
     * A confirmation message is displayed in both the console and GUI.
     *
     * @param flightBookingSystem The flight booking system instance.
     * @throws FlightBookingSystemException If an error occurs while adding or saving the customer.
     */
    @Override
    public void execute(FlightBookingSystem flightBookingSystem) throws FlightBookingSystemException {
        // Generate a unique ID for the new customer
        int maxId = 0;
        if (!flightBookingSystem.getCustomers().isEmpty()) {
            // Find the maximum ID among existing customers
            for (Customer customer : flightBookingSystem.getCustomers()) {
                if (customer.getId() > maxId) {
                    maxId = customer.getId();
                }
            }
        }
        int newId = maxId + 1;

        // Create a new Customer object
        Customer customer = new Customer(newId, name, phone, email);

        // Add the customer to the FlightBookingSystem
        flightBookingSystem.addCustomer(customer);

        // Save the updated customer data to customers.txt
        try {
            CustomerDataManager customerDataManager = new CustomerDataManager();
            customerDataManager.storeData(flightBookingSystem);
        } catch (IOException ex) {
            throw new FlightBookingSystemException("Failed to save customer data: " + ex.getMessage());
        }

        // Print success message in console
        System.out.println("Customer #" + newId + " added.");
        System.out.println("Customer Details:");
        System.out.println("Name: " + name);
        System.out.println("Phone: " + phone);
        System.out.println("Mail: " + email);

        // Print success message in GUI
        JOptionPane.showMessageDialog(
            null, 
            "Customer #" + newId + " added successfully!\n\n" +
            "Customer Details:\n" +
            "Name: " + name + "\n" +
            "Phone: " + phone + "\n" +
            "Mail: " + email,
            "Customer Added",
            JOptionPane.INFORMATION_MESSAGE
        );
    }
}
