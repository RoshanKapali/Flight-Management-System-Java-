package bcu.cmp5332.bookingsystem.commands;

import bcu.cmp5332.bookingsystem.main.FlightBookingSystemException;
import bcu.cmp5332.bookingsystem.model.Flight;
import bcu.cmp5332.bookingsystem.model.FlightBookingSystem;

import java.util.List;
import java.util.stream.Collectors;

/**
 * The {@code FlightSearch} command allows users to search for flights based on
 * multiple criteria such as flight number, origin, destination, departure date, and available seats.
 */
public class FlightSearch implements Command {

    private final String flightNumber;
    private final String origin;
    private final String destination;
    private final String departureDate;
    private final int availableSeats;

    /**
     * Creates a search query for flights based on user-defined criteria.
     *
     * @param flightNumber The flight number to search for (can be empty for wildcard search).
     * @param origin The departure location to filter by (can be empty for wildcard search).
     * @param destination The arrival location to filter by (can be empty for wildcard search).
     * @param departureDate The departure date to filter by (can be empty for wildcard search).
     * @param availableSeats The minimum number of available seats required (-1 to ignore this filter).
     */
    public FlightSearch(String flightNumber, String origin, String destination, String departureDate, int availableSeats) {
        this.flightNumber = flightNumber;
        this.origin = origin;
        this.destination = destination;
        this.departureDate = departureDate;
        this.availableSeats = availableSeats;
    }

    /**
     * Executes the flight search command.
     * Filters flights based on the provided search criteria and updates the system with the results.
     *
     * @param flightBookingSystem The flight booking system instance.
     * @throws FlightBookingSystemException If no flights match the search criteria.
     */
    @Override
    public void execute(FlightBookingSystem flightBookingSystem) throws FlightBookingSystemException {
        List<Flight> flights = flightBookingSystem.getFlights()
            .stream()
            .filter(flight -> (flightNumber.isEmpty() || flight.getFlightNumber().contains(flightNumber)) &&
                             (origin.isEmpty() || flight.getOrigin().contains(origin)) &&
                             (destination.isEmpty() || flight.getDestination().contains(destination)) &&
                             (departureDate.isEmpty() || flight.getDepartureDate().toString().contains(departureDate)) &&
                             (availableSeats == -1 || (flight.getCapacity() - flight.getPassengers().size()) >= availableSeats))
            .collect(Collectors.toList());

        if (flights.isEmpty()) {
            throw new FlightBookingSystemException("No flights found matching the search criteria.");
        }

        // Set the filtered list of flights
        flightBookingSystem.setFilteredFlights(flights);
    }
}
