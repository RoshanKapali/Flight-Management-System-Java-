package bcu.cmp5332.bookingsystem.commands;

import bcu.cmp5332.bookingsystem.data.BookingDataManager;
import bcu.cmp5332.bookingsystem.main.FlightBookingSystemException;
import bcu.cmp5332.bookingsystem.model.Booking;
import bcu.cmp5332.bookingsystem.model.Customer;
import bcu.cmp5332.bookingsystem.model.Flight;
import bcu.cmp5332.bookingsystem.model.FlightBookingSystem;

import java.io.IOException;
import java.time.LocalDate;
import javax.swing.JOptionPane;

/**
 * The {@code AddBooking} command allows a customer to book an outbound flight with an optional return flight.
 * The booking is validated to ensure seat availability before being added to the system.
 */
public class AddBooking implements Command {

    private final int customerId;
    private final int outboundFlightId;
    private final Integer returnFlightId; // Return flight ID (nullable)

    /**
     * Creates a booking command for a customer.
     *
     * @param customerId The ID of the customer making the booking.
     * @param outboundFlightId The ID of the outbound flight.
     * @param returnFlightId The ID of the return flight (nullable if no return flight is booked).
     */
    public AddBooking(int customerId, int outboundFlightId, Integer returnFlightId) {
        this.customerId = customerId;
        this.outboundFlightId = outboundFlightId;
        this.returnFlightId = returnFlightId; // Can be null if no return flight
    }

    /**
     * Executes the add booking command.
     * The booking is validated and added to the system, and a confirmation is displayed.
     *
     * @param flightBookingSystem The flight booking system instance.
     * @throws FlightBookingSystemException If the customer or flight does not exist, or if there are no available seats.
     */
    @Override
    public void execute(FlightBookingSystem flightBookingSystem) throws FlightBookingSystemException {
        // Get customer and outbound flight
        Customer customer = flightBookingSystem.getCustomerByID(customerId);
        Flight outboundFlight = flightBookingSystem.getFlightByID(outboundFlightId);
        Flight returnFlight = (returnFlightId != null) ? flightBookingSystem.getFlightByID(returnFlightId) : null;

        // Validate customer and flights
        if (customer == null) {
            throw new FlightBookingSystemException("Customer with ID " + customerId + " not found.");
        }
        if (outboundFlight == null) {
            throw new FlightBookingSystemException("Outbound flight with ID " + outboundFlightId + " not found.");
        }
        if (returnFlightId != null && returnFlight == null) {
            throw new FlightBookingSystemException("Return flight with ID " + returnFlightId + " not found.");
        }

        // Check if flights have available seats
        if (outboundFlight.getPassengers().size() >= outboundFlight.getCapacity()) {
            throw new FlightBookingSystemException("Outbound flight #" + outboundFlightId + " is fully booked.");
        }
        if (returnFlight != null && returnFlight.getPassengers().size() >= returnFlight.getCapacity()) {
            throw new FlightBookingSystemException("Return flight #" + returnFlightId + " is fully booked.");
        }

        // Create a new booking with outbound and return flight
        LocalDate bookingDate = flightBookingSystem.getSystemDate();
        Booking booking = new Booking(customer, outboundFlight, returnFlight, bookingDate);

        // Add booking to customer
        customer.addBooking(booking);

        // Add customer to flight passenger lists
        outboundFlight.addPassenger(customer);
        if (returnFlight != null) {
            returnFlight.addPassenger(customer);
        }

        // Save booking data
        try {
            BookingDataManager bookingDataManager = new BookingDataManager();
            bookingDataManager.storeData(flightBookingSystem);
        } catch (IOException ex) {
            throw new FlightBookingSystemException("Failed to save booking data: " + ex.getMessage());
        }

        // Print confirmation in console
        System.out.println("Booking added successfully.");
        System.out.println("Customer: " + customer.getName());
        System.out.println("Outbound Flight: " + outboundFlight.getFlightNumber() + 
                           " from " + outboundFlight.getOrigin() + " to " + outboundFlight.getDestination());
        System.out.println("Price: $" + outboundFlight.getPrice());
        System.out.println("Departure Date: " + outboundFlight.getDepartureDate());

        if (returnFlight != null) {
            System.out.println("Return Flight: " + returnFlight.getFlightNumber() + 
                               " from " + returnFlight.getOrigin() + " to " + returnFlight.getDestination());
            System.out.println("Return Price: $" + returnFlight.getPrice());
            System.out.println("Return Date: " + returnFlight.getDepartureDate());
        }

        // Display confirmation in GUI
        String message = "Booking added successfully!\n\n" +
                         "Customer: " + customer.getName() + "\n" +
                         "Outbound Flight: " + outboundFlight.getFlightNumber() + " (" +
                         outboundFlight.getOrigin() + " → " + outboundFlight.getDestination() + ")\n" +
                         "Price: $" + outboundFlight.getPrice() + "\n" +
                         "Departure Date: " + outboundFlight.getDepartureDate() + "\n";

        if (returnFlight != null) {
            message += "\nReturn Flight: " + returnFlight.getFlightNumber() + " (" +
                       returnFlight.getOrigin() + " → " + returnFlight.getDestination() + ")\n" +
                       "Return Price: $" + returnFlight.getPrice() + "\n" +
                       "Return Date: " + returnFlight.getDepartureDate();
        }

        JOptionPane.showMessageDialog(null, message, "Booking Confirmation", JOptionPane.INFORMATION_MESSAGE);
    }
}
