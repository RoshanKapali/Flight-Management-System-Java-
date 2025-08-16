package bcu.cmp5332.bookingsystem.commands;

import bcu.cmp5332.bookingsystem.model.FlightBookingSystem;
import bcu.cmp5332.bookingsystem.gui.LoginWindow;
import bcu.cmp5332.bookingsystem.main.FlightBookingSystemException;

/**
 * The {@code LoadGUI} command is responsible for launching the graphical user interface (GUI)
 * of the flight booking system. It initializes and displays the login window.
 */
public class LoadGUI implements Command {

    /**
     * Executes the command to load the GUI.
     * This method initializes and displays the login window for user authentication.
     *
     * @param flightBookingSystem The flight booking system instance.
     * @throws FlightBookingSystemException If an error occurs while launching the GUI.
     */
    @Override
    public void execute(FlightBookingSystem flightBookingSystem) throws FlightBookingSystemException {
        new LoginWindow(flightBookingSystem);
    }
}
