package bcu.cmp5332.bookingsystem.commands;

import java.io.IOException;
import javax.swing.JOptionPane;
import bcu.cmp5332.bookingsystem.data.BookingDataManager;
import bcu.cmp5332.bookingsystem.main.FlightBookingSystemException;
import bcu.cmp5332.bookingsystem.model.Booking;
import bcu.cmp5332.bookingsystem.model.Customer;
import bcu.cmp5332.bookingsystem.model.Flight;
import bcu.cmp5332.bookingsystem.model.FlightBookingSystem;
import bcu.cmp5332.bookingsystem.gui.MainWindow;

/**
 * The {@code CancelBooking} command allows a customer to cancel their flight booking.
 * A fixed cancellation fee is deducted from the refund amount for each canceled flight.
 */
public class CancelBooking implements Command {

    private final int customerId;
    private final int flightId;
    private static final double CANCELLATION_FEE = 50.0; // Fixed cancellation fee

    /**
     * Creates a command to cancel a booking for a specific customer and flight.
     *
     * @param customerId The ID of the customer whose booking is being canceled.
     * @param flightId The ID of the flight being canceled.
     */
    public CancelBooking(int customerId, int flightId) {
        this.customerId = customerId;
        this.flightId = flightId;
    }

    /**
     * Executes the booking cancellation process.
     * The customer's booking is located, and the corresponding flight(s) are updated.
     * A refund amount is calculated after deducting the cancellation fee.
     * The cancellation is then saved, and the system's GUI is updated if applicable.
     *
     * @param flightBookingSystem The flight booking system instance.
     * @throws FlightBookingSystemException If the customer or flight does not exist or the booking is not found.
     */
    @Override
    public void execute(FlightBookingSystem flightBookingSystem) throws FlightBookingSystemException {
        // Get the customer by ID
        Customer customer = flightBookingSystem.getCustomerByID(customerId);
        if (customer == null) {
            throw new FlightBookingSystemException("Customer with ID " + customerId + " not found.");
        }

        // Find the booking to cancel
        Booking bookingToCancel = null;
        for (Booking booking : customer.getBookings()) {
            if (booking.getOutboundFlight().getId() == flightId || 
               (booking.getReturnFlight() != null && booking.getReturnFlight().getId() == flightId)) {
                bookingToCancel = booking;
                break;
            }
        }

        // Check if the booking exists
        if (bookingToCancel == null) {
            throw new FlightBookingSystemException("Customer #" + customerId + " does not have a booking for Flight #" + flightId);
        }

        // Get outbound and return flights
        Flight outboundFlight = bookingToCancel.getOutboundFlight();
        Flight returnFlight = bookingToCancel.getReturnFlight();

        // Calculate total refund
        double totalRefund = outboundFlight.getPrice() - CANCELLATION_FEE;
        if (returnFlight != null) {
            totalRefund += returnFlight.getPrice() - CANCELLATION_FEE; // Deduct cancellation fee for return flight too
        }
        if (totalRefund < 0) {
            totalRefund = 0; // Ensure refund is not negative
        }

        // Remove the booking from the customer
        customer.getBookings().remove(bookingToCancel);
        
        // Remove the customer from both flights' passenger lists
        outboundFlight.getPassengers().remove(customer);
        if (returnFlight != null) {
            returnFlight.getPassengers().remove(customer);
        }

        // Save the updated booking data
        try {
            BookingDataManager bookingDataManager = new BookingDataManager();
            bookingDataManager.storeData(flightBookingSystem);
        } catch (IOException ex) {
            throw new FlightBookingSystemException("Failed to save booking data: " + ex.getMessage());
        }

        // Print success message in console
        System.out.println("Booking canceled successfully.\n");
        System.out.println("Customer: " + customer.getName());
        System.out.println("Outbound Flight: " + outboundFlight.getFlightNumber() + 
                           " (" + outboundFlight.getOrigin() + " → " + outboundFlight.getDestination() + ")");
        System.out.println("Cancellation Fee: $" + CANCELLATION_FEE);
        if (returnFlight != null) {
            System.out.println("Return Flight: " + returnFlight.getFlightNumber() + 
                               " (" + returnFlight.getOrigin() + " → " + returnFlight.getDestination() + ")");
            System.out.println("Additional Cancellation Fee: $" + CANCELLATION_FEE);
        }
        System.out.println("Total Refund Amount: $" + totalRefund);

        // Display confirmation in GUI
        String message = "Booking canceled successfully!\n\n" +
                         "Customer: " + customer.getName() + "\n" +
                         "Outbound Flight: " + outboundFlight.getFlightNumber() + " (" +
                         outboundFlight.getOrigin() + " → " + outboundFlight.getDestination() + ")\n" +
                         "Cancellation Fee: $" + CANCELLATION_FEE + "\n";

        if (returnFlight != null) {
            message += "\nReturn Flight: " + returnFlight.getFlightNumber() + " (" +
                       returnFlight.getOrigin() + " → " + returnFlight.getDestination() + ")\n" +
                       "Additional Cancellation Fee: $" + CANCELLATION_FEE + "\n";
        }

        message += "Total Refund Amount: $" + totalRefund;

        JOptionPane.showMessageDialog(
            null, 
            message,
            "Cancellation Confirmation",
            JOptionPane.INFORMATION_MESSAGE
        );

        // Refresh the GUI so the canceled booking does not appear
        MainWindow mainWindow = MainWindow.getInstance();
        if (mainWindow != null) {
            mainWindow.displayCustomers(); // Refresh customer details to reflect cancellation
            mainWindow.displayFlights(flightBookingSystem.getFlights()); // Refresh flights list
        }
    }
}
