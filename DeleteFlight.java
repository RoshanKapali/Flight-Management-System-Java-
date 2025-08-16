package bcu.cmp5332.bookingsystem.commands;

import bcu.cmp5332.bookingsystem.data.FlightDataManager;
import bcu.cmp5332.bookingsystem.main.FlightBookingSystemException;
import bcu.cmp5332.bookingsystem.model.Flight;
import bcu.cmp5332.bookingsystem.model.FlightBookingSystem;

import java.io.IOException;
import javax.swing.JOptionPane;

/**
 * The {@code DeleteFlight} command is used to remove a flight from the flight booking system.
 * The flight can only be deleted if it has no passengers booked.
 */
public class DeleteFlight implements Command {

    private final int flightId;

    /**
     * Creates a command to delete a flight with the specified ID.
     *
     * @param flightId The ID of the flight to be deleted.
     */
    public DeleteFlight(int flightId) {
        this.flightId = flightId;
    }

    /**
     * Executes the delete flight command.
     * The flight is removed from the system if it exists and has no passengers booked.
     * The updated flight data is then saved to file.
     *
     * @param flightBookingSystem The flight booking system where the flight exists.
     * @throws FlightBookingSystemException If the flight does not exist or cannot be deleted.
     */
    @Override
    public void execute(FlightBookingSystem flightBookingSystem) throws FlightBookingSystemException {
        // Get the flight by ID
        Flight flight = flightBookingSystem.getFlightByID(flightId);

        // Check if the flight exists
        if (flight == null) {
            throw new FlightBookingSystemException("Flight with ID " + flightId + " not found.");
        }

        // Check if the flight has any bookings
        if (!flight.getPassengers().isEmpty()) {
            throw new FlightBookingSystemException("Flight #" + flightId + " has passengers and cannot be deleted.");
        }

        // Remove the flight from the system
        flightBookingSystem.getFlightsMap().remove(flightId); // Remove from the map

        // Save the updated flight data to flights.txt
        try {
            FlightDataManager flightDataManager = new FlightDataManager();
            flightDataManager.storeData(flightBookingSystem); // Save changes to file
        } catch (IOException ex) {
            throw new FlightBookingSystemException("Failed to save flight data: " + ex.getMessage());
        }

        // Print success message in console
        System.out.println("Flight #" + flightId + " deleted successfully.");
        System.out.println("Origin: " + flight.getOrigin());
        System.out.println("Destination: " + flight.getDestination());

        // Show success message in GUI
        JOptionPane.showMessageDialog(
            null, 
            "Flight #" + flight.getId() + " deleted successfully!\n" +
            "Origin: " + flight.getOrigin() + "\n" +
            "Destination: " + flight.getDestination(),
            "Flight Deleted",
            JOptionPane.INFORMATION_MESSAGE
        );
    }
}
