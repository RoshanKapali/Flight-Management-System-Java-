package bcu.cmp5332.bookingsystem.commands;

import java.io.IOException;
import javax.swing.JOptionPane;
import bcu.cmp5332.bookingsystem.data.BookingDataManager;
import bcu.cmp5332.bookingsystem.main.FlightBookingSystemException;
import bcu.cmp5332.bookingsystem.model.Booking;
import bcu.cmp5332.bookingsystem.model.Customer;
import bcu.cmp5332.bookingsystem.model.Flight;
import bcu.cmp5332.bookingsystem.model.FlightBookingSystem;

/**
 * The {@code EditBooking} command allows a customer to modify their existing booking.
 * A customer can change their outbound or return flight, provided that seats are available.
 * A fixed rebooking fee is applied for the change.
 */
public class EditBooking implements Command {

    private final int customerId;
    private final int oldFlightId;
    private final int newFlightId;
    private static final double REBOOKING_FEE = 30.0; // Fixed rebooking fee

    /**
     * Creates a command to modify a booking for a specific customer.
     *
     * @param customerId The ID of the customer whose booking is being modified.
     * @param oldFlightId The ID of the flight the customer wishes to change.
     * @param newFlightId The ID of the new flight the customer wants to book.
     */
    public EditBooking(int customerId, int oldFlightId, int newFlightId) {
        this.customerId = customerId;
        this.oldFlightId = oldFlightId;
        this.newFlightId = newFlightId;
    }

    /**
     * Executes the command to edit a booking.
     * The existing booking is updated with the new flight details, and the rebooking fee is applied.
     * The system data is then saved, and a confirmation is displayed.
     *
     * @param flightBookingSystem The flight booking system instance.
     * @throws FlightBookingSystemException If the customer, flight, or booking is invalid or unavailable.
     */
    @Override
    public void execute(FlightBookingSystem flightBookingSystem) throws FlightBookingSystemException {
        // Get the customer by ID
        Customer customer = flightBookingSystem.getCustomerByID(customerId);
        if (customer == null) {
            throw new FlightBookingSystemException("Customer with ID " + customerId + " not found.");
        }

        // Find the booking to edit
        Booking bookingToEdit = null;
        for (Booking booking : customer.getBookings()) {
            if (booking.getOutboundFlight().getId() == oldFlightId
                    || (booking.getReturnFlight() != null && booking.getReturnFlight().getId() == oldFlightId)) {
                bookingToEdit = booking;
                break;
            }
        }

        // Check if the booking exists
        if (bookingToEdit == null) {
            throw new FlightBookingSystemException(
                    "Customer #" + customerId + " does not have a booking for Flight #" + oldFlightId);
        }

        // Get outbound and return flights
        Flight oldFlight = (bookingToEdit.getOutboundFlight().getId() == oldFlightId)
                ? bookingToEdit.getOutboundFlight()
                : bookingToEdit.getReturnFlight();
        Flight newFlight = flightBookingSystem.getFlightByID(newFlightId);

        if (oldFlight == null || newFlight == null) {
            throw new FlightBookingSystemException("Invalid flight IDs. Please check outbound and return flights.");
        }

        // Check if the new flight has available seats
        if (newFlight.getPassengers().size() >= newFlight.getCapacity()) {
            throw new FlightBookingSystemException("Flight #" + newFlightId + " is fully booked.");
        }

        // Apply the rebooking fee
        double totalRebookingCost = newFlight.getPrice() + REBOOKING_FEE;

        // Determine whether to update outbound or return flight
        if (bookingToEdit.getOutboundFlight().getId() == oldFlightId) {
            bookingToEdit.setOutboundFlight(newFlight);
        } else if (bookingToEdit.getReturnFlight() != null && bookingToEdit.getReturnFlight().getId() == oldFlightId) {
            bookingToEdit.setReturnFlight(newFlight);
        }

        // Remove the customer from the old flight and add them to the new flight
        oldFlight.getPassengers().remove(customer);
        newFlight.addPassenger(customer);

        // Save the updated booking data
        try {
            BookingDataManager bookingDataManager = new BookingDataManager();
            bookingDataManager.storeData(flightBookingSystem);
        } catch (IOException ex) {
            throw new FlightBookingSystemException("Failed to save booking data: " + ex.getMessage());
        }

        // Print success message in console
        System.out.println("Booking updated successfully.");
        System.out.println("Customer: " + customer.getName());
        System.out.println("Old Flight: " + oldFlight.getFlightNumber() + " (" + oldFlight.getOrigin() + " → "
                + oldFlight.getDestination() + ")");
        System.out.println("New Flight: " + newFlight.getFlightNumber() + " (" + newFlight.getOrigin() + " → "
                + newFlight.getDestination() + ")");
        System.out.println("A rebooking fee of $" + REBOOKING_FEE + " was applied.");
        System.out.println("Total Cost After Rebooking: $" + totalRebookingCost);

        // Print success message with rebooking fee details in GUI
        JOptionPane.showMessageDialog(null,
                "Booking updated successfully!\n\n" + "Customer: " + customer.getName() + "\n" + "Old Flight: "
                        + oldFlight.getFlightNumber() + " (" + oldFlight.getOrigin() + " → "
                        + oldFlight.getDestination() + ")\n" + "New Flight: " + newFlight.getFlightNumber() + " (" 
                        + newFlight.getOrigin() + " → " + newFlight.getDestination() + ")\n" + "Rebooking Fee: $"
                        + REBOOKING_FEE + "\n" + "Total Cost After Rebooking: $" + totalRebookingCost,
                "Rebooking Confirmation", JOptionPane.INFORMATION_MESSAGE);
    }
}
