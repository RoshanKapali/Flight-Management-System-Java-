package bcu.cmp5332.bookingsystem.data;

import bcu.cmp5332.bookingsystem.main.FlightBookingSystemException;
import bcu.cmp5332.bookingsystem.model.Booking;
import bcu.cmp5332.bookingsystem.model.Customer;
import bcu.cmp5332.bookingsystem.model.Flight;
import bcu.cmp5332.bookingsystem.model.FlightBookingSystem;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.Scanner;

/**
 * Manages the loading and storing of booking data from a file.
 * The data is stored in a text file with values separated by "::".
 */
public class BookingDataManager implements DataManager {

    private final String RESOURCE = "./resources/data/bookings.txt";
    private static final String SEPARATOR = "::";

    /**
     * Loads booking data from a file and populates the flight booking system.
     * This method reads each line from the file, parses the booking details, 
     * and links them to customers and flights.
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
                String line = sc.nextLine().trim();
                if (line.isEmpty()) {
                    continue; // Skip empty lines
                }

                String[] properties = line.split(SEPARATOR, -1);
                if (properties.length < 4) {
                    throw new FlightBookingSystemException("Invalid booking data format on line " + line_idx);
                }

                try {
                    int customerId = Integer.parseInt(properties[0]);
                    int outboundFlightId = Integer.parseInt(properties[1]);
                    LocalDate bookingDate = LocalDate.parse(properties[2]);

                    // Check for return flight (handle NULL value)
                    Flight returnFlight = null;
                    if (!properties[3].equalsIgnoreCase("NULL") && !properties[3].isEmpty()) {
                        int returnFlightId = Integer.parseInt(properties[3]);
                        returnFlight = fbs.getFlightByID(returnFlightId);
                    }

                    // Find the customer and outbound flight
                    Customer customer = fbs.getCustomerByID(customerId);
                    Flight outboundFlight = fbs.getFlightByID(outboundFlightId);

                    if (customer == null || outboundFlight == null) {
                        throw new FlightBookingSystemException("Invalid customer or flight ID on line " + line_idx);
                    }

                    // Create a new booking with return flight handling
                    Booking booking = new Booking(customer, outboundFlight, returnFlight, bookingDate);

                    // Add the booking to the customer and flight
                    customer.addBooking(booking);
                    outboundFlight.addPassenger(customer);
                    if (returnFlight != null) {
                        returnFlight.addPassenger(customer);
                    }

                } catch (NumberFormatException ex) {
                    throw new FlightBookingSystemException("Error parsing booking data on line " + line_idx + 
                        "\nError: " + ex);
                } catch (DateTimeParseException ex) {
                    throw new FlightBookingSystemException("Invalid date format on line " + line_idx + 
                        "\nError: " + ex);
                }
                line_idx++;
            }
        }
    }

    /**
     * Stores the current bookings from the system into a file.
     * The booking details are written in a structured format using "::" as a separator.
     *
     * @param fbs The flight booking system containing the booking data.
     * @throws IOException If an error occurs while writing to the file.
     */
    @Override
    public void storeData(FlightBookingSystem fbs) throws IOException {
        try (PrintWriter out = new PrintWriter(new FileWriter(RESOURCE))) {
            for (Customer customer : fbs.getCustomers()) {
                for (Booking booking : customer.getBookings()) {
                    // Store outbound flight
                    out.print(booking.getCustomer().getId() + SEPARATOR);
                    out.print(booking.getOutboundFlight().getId() + SEPARATOR);
                    out.print(booking.getBookingDate() + SEPARATOR);

                    // Store return flight if available
                    if (booking.getReturnFlight() != null) {
                        out.print(booking.getReturnFlight().getId() + SEPARATOR);
                    } else {
                        out.print("NULL" + SEPARATOR);
                    }
                    
                    out.println();
                }
            }
        }
    }
}
