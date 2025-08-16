package bcu.cmp5332.bookingsystem.commands;

import bcu.cmp5332.bookingsystem.data.FlightDataManager;
import bcu.cmp5332.bookingsystem.main.FlightBookingSystemException;
import bcu.cmp5332.bookingsystem.model.Flight;
import bcu.cmp5332.bookingsystem.model.FlightBookingSystem;

import java.io.IOException;
import java.time.LocalDate;
import javax.swing.JOptionPane;

/**
 * The {@code AddFlight} command adds a new flight to the flight booking system.
 * The new flight is assigned an ID, stored in the system, and saved to a file.
 */
public class AddFlight implements Command {

    private final String flightNumber;
    private final String origin;
    private final String destination;
    private final LocalDate departureDate;
    private final int capacity; 
    private final double price;

    /**
     * Creates a command to add a new flight with the specified details.
     *
     * @param flightNumber The unique flight number.
     * @param origin The departure location of the flight.
     * @param destination The arrival location of the flight.
     * @param departureDate The date of departure.
     * @param capacity The maximum number of passengers allowed on the flight.
     * @param price The price of a single ticket for the flight.
     */
    public AddFlight(String flightNumber, String origin, String destination, LocalDate departureDate, int capacity, double price) {
        this.flightNumber = flightNumber;
        this.origin = origin;
        this.destination = destination;
        this.departureDate = departureDate;
        this.capacity = capacity;
        this.price = price;
    }

    /**
     * Executes the add flight command.
     * A new flight is created and added to the system, then saved to a file.
     * A confirmation message is displayed in both the console and GUI.
     *
     * @param flightBookingSystem The flight booking system instance.
     * @throws FlightBookingSystemException If an error occurs while adding or saving the flight.
     */
    @Override
    public void execute(FlightBookingSystem flightBookingSystem) throws FlightBookingSystemException {
        // Determine the next available flight ID
        int maxId = 0;
        if (!flightBookingSystem.getFlights().isEmpty()) {
            int lastIndex = flightBookingSystem.getFlights().size() - 1;
            maxId = flightBookingSystem.getFlights().get(lastIndex).getId();
        }

        // Create and add the new flight
        Flight flight = new Flight(++maxId, flightNumber, origin, destination, departureDate, capacity, price);
        flightBookingSystem.addFlight(flight);

        // Save the updated flight data to flights.txt
        try {
            FlightDataManager flightDataManager = new FlightDataManager();
            flightDataManager.storeData(flightBookingSystem);
        } catch (IOException ex) {
            throw new FlightBookingSystemException("Failed to save flight data: " + ex.getMessage());
        }

        // Display the flight added message in console
        System.out.println("Flight #" + flight.getId() + " added.");
        System.out.println("Origin: " + flight.getOrigin());
        System.out.println("Destination: " + flight.getDestination());
        System.out.println("Departure: " + flight.getDepartureDate());
        System.out.println("Capacity: " + flight.getCapacity());
        System.out.println("Price: $" + flight.getPrice());

        // Display the flight added message through GUI
        JOptionPane.showMessageDialog(
            null, 
            "Flight #" + flight.getId() + " added successfully!\n\n" +
            "Origin: " + flight.getOrigin() + "\n" +
            "Destination: " + flight.getDestination() + "\n" +
            "Departure: " + flight.getDepartureDate() + "\n" +
            "Capacity: " + flight.getCapacity() + "\n" +
            "Price: $" + flight.getPrice(),
            "Flight Added",
            JOptionPane.INFORMATION_MESSAGE
        );
    }
}
