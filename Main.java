package bcu.cmp5332.bookingsystem.main;

import bcu.cmp5332.bookingsystem.data.FlightBookingSystemData;
import bcu.cmp5332.bookingsystem.commands.Command;
import bcu.cmp5332.bookingsystem.model.FlightBookingSystem;

import java.io.*;

/**
 * The {@code Main} class serves as the entry point for the Flight Booking System.
 * It initializes the system, processes user commands, and saves data upon exit.
 */
public class Main {

    /**
     * The main method initializes the flight booking system, processes user input commands,
     * and manages the execution of commands until the user exits.
     *
     * @param args command-line arguments (not used).
     * @throws IOException if an I/O error occurs while reading input.
     * @throws FlightBookingSystemException if an error occurs in the flight booking system.
     */
    public static void main(String[] args) throws IOException, FlightBookingSystemException {
        
        // Load the flight booking system data
        FlightBookingSystem fbs = FlightBookingSystemData.load();

        // Create a BufferedReader for user input
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        // Display system startup message
        System.out.println("----- FLIGHT BOOKING SYSTEM -----");
        System.out.println("Enter 'help' to see a list of available commands.");

        // Command processing loop
        while (true) {
            System.out.print("> ");
            String line = br.readLine();
            
            // Exit condition
            if (line.equals("exit")) {
                break;
            }

            try {
                // Parse and execute the command
                Command command = CommandParser.parse(line);
                command.execute(fbs);
            } catch (FlightBookingSystemException ex) {
                // Handle exceptions related to the flight booking system
                System.out.println(ex.getMessage());
            }
        }

        // Save flight booking system data before exiting
        FlightBookingSystemData.store(fbs);
        
        // Terminate the program
        System.exit(0);
    }
}
