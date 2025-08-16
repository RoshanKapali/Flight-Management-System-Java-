package bcu.cmp5332.bookingsystem.commands;

import bcu.cmp5332.bookingsystem.main.FlightBookingSystemException;
import bcu.cmp5332.bookingsystem.model.Flight;
import bcu.cmp5332.bookingsystem.model.FlightBookingSystem;

/**
 * The {@code ShowFlight} command displays detailed information about a specific flight.
 * The flight is identified using its unique ID.
 */
public class ShowFlight implements Command {

    private final int flightId;

    /**
     * Creates a command to display details of a flight with the specified ID.
     *
     * @param flightId The ID of the flight to be displayed.
     */
    public ShowFlight(int flightId) {
        this.flightId = flightId;
    }

    /**
     * Executes the show flight command.
     * Retrieves and prints the detailed information of the specified flight.
     *
     * @param flightBookingSystem The flight booking system instance.
     * @throws FlightBookingSystemException If the flight does not exist.
     */
    @Override
    public void execute(FlightBookingSystem flightBookingSystem) throws FlightBookingSystemException {
        // Get the flight by its ID
        Flight flight = flightBookingSystem.getFlightByID(flightId);

        // Check if the flight exists
        if (flight == null) {
            throw new FlightBookingSystemException("Flight with ID " + flightId + " not found.");
        }

        // Print detailed flight information
        System.out.println(flight.getDetailsLong());
    }
}
