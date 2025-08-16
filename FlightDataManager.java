package bcu.cmp5332.bookingsystem.data;

import bcu.cmp5332.bookingsystem.main.FlightBookingSystemException;
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
 * Manages the loading and storing of flight data from a file.
 * The data is stored in a text file with values separated by "::".
 */
public class FlightDataManager implements DataManager {

    private final String RESOURCE = "./resources/data/flights.txt";
    private static final String SEPARATOR = "::"; // Ensures consistency in data formatting

    /**
     * Loads flight data from a file and populates the flight booking system.
     * This method reads each line from the file, extracts flight details, 
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
                if (properties.length < 7) {
                    throw new FlightBookingSystemException("Invalid flight data format on line " + line_idx);
                }

                try {
                    int id = Integer.parseInt(properties[0]);
                    String flightNumber = properties[1];
                    String origin = properties[2];
                    String destination = properties[3];
                    LocalDate departureDate = LocalDate.parse(properties[4]);
                    int capacity = Integer.parseInt(properties[5]);
                    double price = Double.parseDouble(properties[6]);

                    Flight flight = new Flight(id, flightNumber, origin, destination, departureDate, capacity, price);
                    fbs.addFlight(flight);
                } catch (NumberFormatException ex) {
                    throw new FlightBookingSystemException("Unable to parse flight id " + properties[0] + " on line " + line_idx
                        + "\nError: " + ex);
                } catch (DateTimeParseException ex) {
                    throw new FlightBookingSystemException("Invalid date format on line " + line_idx
                        + "\nError: " + ex);
                }
                line_idx++;
            }
        }
    }

    /**
     * Stores the current flight data into a file.
     * The flight details are written in a structured format using "::" as a separator.
     *
     * @param fbs The flight booking system containing the flight data.
     * @throws IOException If an error occurs while writing to the file.
     */
    @Override
    public void storeData(FlightBookingSystem fbs) throws IOException {
        try (PrintWriter out = new PrintWriter(new FileWriter(RESOURCE))) {
            for (Flight flight : fbs.getFlights()) {
                out.print(flight.getId() + SEPARATOR);
                out.print(flight.getFlightNumber() + SEPARATOR);
                out.print(flight.getOrigin() + SEPARATOR);
                out.print(flight.getDestination() + SEPARATOR);
                out.print(flight.getDepartureDate() + SEPARATOR);
                out.print(flight.getCapacity() + SEPARATOR);
                out.print(flight.getPrice() + SEPARATOR);
                out.println();
            }
        }
    }
}
