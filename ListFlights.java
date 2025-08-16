package bcu.cmp5332.bookingsystem.commands;

import bcu.cmp5332.bookingsystem.main.FlightBookingSystemException;
import bcu.cmp5332.bookingsystem.model.Flight;
import bcu.cmp5332.bookingsystem.model.FlightBookingSystem;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

/**
 * The {@code ListFlights} command retrieves and displays a list of upcoming flights
 * in the flight booking system. Only flights that have not yet departed are shown,
 * sorted by departure date.
 */
public class ListFlights implements Command {

    /**
     * Executes the command to list upcoming flights.
     * Filters flights that have a departure date after the system's current date.
     * The flights are displayed in order of departure date.
     *
     * @param flightBookingSystem The flight booking system instance.
     * @throws FlightBookingSystemException If an error occurs while retrieving flights.
     */
    @Override
    public void execute(FlightBookingSystem flightBookingSystem) throws FlightBookingSystemException {
        // Get the current system date
        LocalDate systemDate = flightBookingSystem.getSystemDate();

        // Filter only future flights (flights that haven't departed) and sort by departure date
        List<Flight> futureFlights = flightBookingSystem.getFlights()
            .stream()
            .filter(flight -> flight.getDepartureDate().isAfter(systemDate)) // Ensures only upcoming flights
            .sorted((f1, f2) -> f1.getDepartureDate().compareTo(f2.getDepartureDate())) // Sort by departure date
            .collect(Collectors.toList());

        // Display the filtered flights
        if (futureFlights.isEmpty()) {
            System.out.println("No upcoming flights available.");
        } else {
            System.out.println("Upcoming Flights:");
            for (Flight flight : futureFlights) {
                System.out.println("--------------------------------------------------");
                System.out.println(flight.getDetailsShort());
                System.out.println("Passengers: " + flight.getPassengers().size() + "/" + flight.getCapacity());
            }
            System.out.println("--------------------------------------------------");
            System.out.println(futureFlights.size() + " upcoming flight(s) listed.");
        }
    }
}
