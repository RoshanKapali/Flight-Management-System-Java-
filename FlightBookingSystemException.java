package bcu.cmp5332.bookingsystem.main;

/**
 * A custom exception for the Flight Booking System.
 * This exception is thrown when an error occurs, such as invalid commands
 * or other issues within the system.
 */
public class FlightBookingSystemException extends Exception {

    /**
     * Creates a new {@code FlightBookingSystemException} with a specific error message.
     *
     * @param message A descriptive error message explaining the issue.
     */
    public FlightBookingSystemException(String message) {
        super(message);
    }
}
