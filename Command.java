package bcu.cmp5332.bookingsystem.commands;

import bcu.cmp5332.bookingsystem.main.FlightBookingSystemException;
import bcu.cmp5332.bookingsystem.model.FlightBookingSystem;

/**
 * The {@code Command} interface represents an executable command
 * in the flight booking system.
 * Each command implements the {@code execute} method to perform
 * a specific action on the {@code FlightBookingSystem}.
 */
public interface Command {

    /**
     * A constant string containing the help message displaying available commands.
     */
    public static final String HELP_MESSAGE = "Commands:\n"
            + "\tlistflights                                               print all flights\n"
            + "\tlistcustomers                                             print all customers\n"
            + "\taddflight                                                 add a new flight (requires flight number, origin, destination, departure date, and capacity)\n"
            + "\taddcustomer <name> <phone> [email]                        add a new customer (email is optional)\n"
            + "\tshowflight [flight id]                                    show flight details\n"
            + "\tshowcustomer [customer id]                                show customer details\n"
            + "\taddbooking [customer id] [flight id]                      add a new booking\n"
            + "\tcancelbooking [customer id] [flight id]                   cancel a booking\n"
            + "\teditbooking [booking id] [Old flight id] [New flight id]  update a booking\n"
            + "\tdeleteflight [flight id]                                  delete a flight\n"
            + "\tdeletecustomer [customer id]                              delete a customer\n"
            + "\tloadgui                                                   loads the GUI version of the app\n"
            + "\thelp                                                      prints this help message\n"
            + "\texit                                                      exits the program";

    /**
     * Executes the command on the provided {@code FlightBookingSystem}.
     *
     * @param flightBookingSystem the flight booking system on which the command is executed.
     * @throws FlightBookingSystemException if an error occurs during command execution.
     */
    public void execute(FlightBookingSystem flightBookingSystem) throws FlightBookingSystemException;

}
