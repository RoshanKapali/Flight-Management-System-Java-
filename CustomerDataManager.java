package bcu.cmp5332.bookingsystem.data;

import bcu.cmp5332.bookingsystem.main.FlightBookingSystemException;
import bcu.cmp5332.bookingsystem.model.Customer;
import bcu.cmp5332.bookingsystem.model.FlightBookingSystem;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;

/**
 * Manages the loading and storing of customer data from a file.
 * The data is stored in a text file with values separated by "::".
 */
public class CustomerDataManager implements DataManager {

    private final String RESOURCE = "./resources/data/customers.txt";
    private static final String SEPARATOR = "::"; // Ensures consistency in data formatting

    /**
     * Loads customer data from a file and populates the flight booking system.
     * This method reads each line from the file, extracts customer details, 
     * and adds them to the system.
     *
     * @param fbs The flight booking system to populate.
     * @throws IOException If an error occurs while reading the file.
     * @throws FlightBookingSystemException If the data is in an invalid format.
     */
    @Override
    public void loadData(FlightBookingSystem fbs) throws IOException, FlightBookingSystemException {
        try (Scanner sc = new Scanner(new File(RESOURCE))) {
            int line_idx = 1;
            while (sc.hasNextLine()) {
                String line = sc.nextLine().trim(); // Trim the line to remove leading/trailing whitespace
                if (line.isEmpty()) {
                    continue; // Skip empty lines
                }

                String[] properties = line.split(SEPARATOR, -1);
                if (properties.length < 4) {
                    throw new FlightBookingSystemException("Invalid customer data format on line " + line_idx);
                }

                try {
                    int id = Integer.parseInt(properties[0]);
                    String name = properties[1];
                    String phone = properties[2];
                    String email = properties[3]; // Email is now properly separated

                    Customer customer = new Customer(id, name, phone, email);
                    fbs.addCustomer(customer);
                } catch (NumberFormatException ex) {
                    throw new FlightBookingSystemException("Unable to parse customer id " + properties[0] + " on line " + line_idx
                        + "\nError: " + ex);
                }
                line_idx++;
            }
        }
    }

    /**
     * Stores the current customer data into a file.
     * The customer details are written in a structured format using "::" as a separator.
     *
     * @param fbs The flight booking system containing the customer data.
     * @throws IOException If an error occurs while writing to the file.
     */
    @Override
    public void storeData(FlightBookingSystem fbs) throws IOException {
        try (PrintWriter out = new PrintWriter(new FileWriter(RESOURCE))) {
            for (Customer customer : fbs.getCustomers()) {
                out.print(customer.getId() + SEPARATOR);
                out.print(customer.getName() + SEPARATOR);
                out.print(customer.getPhone() + SEPARATOR);
                out.print(customer.getEmail() + SEPARATOR); // Email is now properly separated
                out.println();
            }
        }
    }
}
