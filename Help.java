package bcu.cmp5332.bookingsystem.commands;

import bcu.cmp5332.bookingsystem.model.FlightBookingSystem;

/**
 * The {@code Help} command displays a list of available commands
 * that users can execute in the flight booking system.
 */
public class Help implements Command {

    /**
     * Executes the help command.
     * Prints a predefined help message containing all available commands.
     *
     * @param flightBookingSystem The flight booking system instance.
     */
    @Override
    public void execute(FlightBookingSystem flightBookingSystem) {
        System.out.println(Command.HELP_MESSAGE);
    }
}
