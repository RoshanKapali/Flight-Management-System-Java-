package bcu.cmp5332.bookingsystem.main;

import bcu.cmp5332.bookingsystem.commands.LoadGUI;
import bcu.cmp5332.bookingsystem.commands.ShowCustomer;
import bcu.cmp5332.bookingsystem.commands.ShowFlight;
import bcu.cmp5332.bookingsystem.commands.ListFlights;
import bcu.cmp5332.bookingsystem.commands.AddFlight;
import bcu.cmp5332.bookingsystem.commands.CancelBooking;
import bcu.cmp5332.bookingsystem.commands.AddBooking;
import bcu.cmp5332.bookingsystem.commands.AddCustomer;
import bcu.cmp5332.bookingsystem.commands.DeleteFlight;
import bcu.cmp5332.bookingsystem.commands.DeleteCustomer;
import bcu.cmp5332.bookingsystem.commands.Command;
import bcu.cmp5332.bookingsystem.commands.EditBooking;
import bcu.cmp5332.bookingsystem.commands.Help;
import bcu.cmp5332.bookingsystem.commands.ListCustomers;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;

/**
 * The {@code CommandParser} class is responsible for interpreting user input
 * and converting it into executable commands for the flight booking system.
 * It reads input, validates commands, and returns the appropriate command object.
 */
public class CommandParser {

    /**
     * Parses the given command-line input and returns the corresponding {@code Command} object.
     *
     * @param line The user input command.
     * @return A {@code Command} object that can be executed.
     * @throws IOException If an input error occurs.
     * @throws FlightBookingSystemException If the command is invalid or contains errors.
     */
    public static Command parse(String line) throws IOException, FlightBookingSystemException {
        try {
            String[] parts = line.trim().split("\\s+");
            String cmd = parts[0];

            BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

            switch (cmd.toLowerCase()) {
                case "addflight":
                    return handleAddFlight(reader);

                case "addcustomer":
                    return handleAddCustomer(parts);

                case "loadgui":
                    return new LoadGUI();

                case "listflights":
                    return new ListFlights();

                case "listcustomers":
                    return new ListCustomers();

                case "help":
                    return new Help();
                    
                case "showflight":
                case "showcustomer":
                    if (parts.length == 2) {
                        int id = Integer.parseInt(parts[1]);
                        return cmd.equals("showflight") ? new ShowFlight(id) : new ShowCustomer(id);
                    }
                    break;

                case "addbooking":
                    if (parts.length == 3 || parts.length == 4) { 
                        int customerId = Integer.parseInt(parts[1]);
                        int outboundFlightId = Integer.parseInt(parts[2]);
                        Integer returnFlightId = (parts.length == 4) ? Integer.parseInt(parts[3]) : null;
                        return new AddBooking(customerId, outboundFlightId, returnFlightId);
                    }
                    break;

                case "cancelbooking":
                    if (parts.length == 3) {
                        int customerId = Integer.parseInt(parts[1]);
                        int flightId = Integer.parseInt(parts[2]);
                        return new CancelBooking(customerId, flightId);
                    }
                    break;

                case "editbooking":
                    if (parts.length == 4) {
                        int customerId = Integer.parseInt(parts[1]);
                        int oldFlightId = Integer.parseInt(parts[2]);
                        int newFlightId = Integer.parseInt(parts[3]);
                        return new EditBooking(customerId, oldFlightId, newFlightId);
                    }
                    break;

                case "deleteflight":
                    if (parts.length == 2) {
                        int flightId = Integer.parseInt(parts[1]);
                        return new DeleteFlight(flightId);
                    }
                    break;

                case "deletecustomer":
                    if (parts.length == 2) {
                        int customerId = Integer.parseInt(parts[1]);
                        return new DeleteCustomer(customerId);
                    }
                    break;
            }

        } catch (NumberFormatException ex) {
            throw new FlightBookingSystemException("Invalid number format: " + ex.getMessage());
        }

        throw new FlightBookingSystemException("Invalid command.");
    }

    /**
     * Handles the process of adding a new flight by prompting the user for details.
     *
     * @param reader A {@code BufferedReader} to read user input.
     * @return An {@code AddFlight} command with the collected details.
     * @throws IOException If an input error occurs.
     * @throws FlightBookingSystemException If input data is invalid.
     */
    private static Command handleAddFlight(BufferedReader reader) throws IOException, FlightBookingSystemException {
        System.out.print("Flight Number: ");
        String flightNumber = reader.readLine();
        System.out.print("Origin: ");
        String origin = reader.readLine();
        System.out.print("Destination: ");
        String destination = reader.readLine();

        LocalDate departureDate = parseDateWithAttempts(reader);

        System.out.print("Capacity: ");
        int capacity = Integer.parseInt(reader.readLine());

        System.out.print("Price: "); 
        double price = Double.parseDouble(reader.readLine());

        return new AddFlight(flightNumber, origin, destination, departureDate, capacity, price);
    }

    /**
     * Handles the process of adding a new customer using provided command arguments.
     *
     * @param parts An array containing command arguments.
     * @return An {@code AddCustomer} command with the collected details.
     * @throws FlightBookingSystemException If input data is invalid or missing.
     */
    private static Command handleAddCustomer(String[] parts) throws FlightBookingSystemException {
        if (parts.length < 3) {
            throw new FlightBookingSystemException("Invalid command. Usage: addcustomer <name> <phone> [email]");
        }
        String name = parts[1];
        String phone = parts[2];
        String email = (parts.length > 3) ? parts[3] : ""; // Optional email

        return new AddCustomer(name, phone, email);
    }

    /**
     * Reads and validates a date from user input, allowing multiple attempts.
     *
     * @param br A {@code BufferedReader} to read user input.
     * @param attempts The number of times the user can retry entering a valid date.
     * @return A valid {@code LocalDate} object.
     * @throws IOException If an input error occurs.
     * @throws FlightBookingSystemException If the user fails to enter a valid date.
     */
    private static LocalDate parseDateWithAttempts(BufferedReader br, int attempts) throws IOException, FlightBookingSystemException {
        if (attempts < 1) {
            throw new IllegalArgumentException("Number of attempts should be higher than 0");
        }
        while (attempts > 0) {
            attempts--;
            System.out.print("Departure Date (YYYY-MM-DD format): ");
            try {
                return LocalDate.parse(br.readLine());
            } catch (DateTimeParseException dtpe) {
                System.out.println("Date must be in YYYY-MM-DD format. " + attempts + " attempts remaining...");
            }
        }
        
        throw new FlightBookingSystemException("Incorrect departure date provided. Cannot create flight.");
    }

    /**
     * A simpler version of {@code parseDateWithAttempts} that defaults to 3 attempts.
     *
     * @param br A {@code BufferedReader} to read user input.
     * @return A valid {@code LocalDate} object.
     * @throws IOException If an input error occurs.
     * @throws FlightBookingSystemException If the user fails to enter a valid date.
     */
    private static LocalDate parseDateWithAttempts(BufferedReader br) throws IOException, FlightBookingSystemException {
        return parseDateWithAttempts(br, 3);
    }
}
