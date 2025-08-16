package bcu.cmp5332.bookingsystem.data;

import bcu.cmp5332.bookingsystem.main.FlightBookingSystemException;
import bcu.cmp5332.bookingsystem.model.FlightBookingSystem;
import java.io.IOException;

/**
 * The {@code DataManager} interface defines methods for loading and storing 
 * data in the flight booking system. Implementing classes should handle 
 * data persistence for different system components.
 */
public interface DataManager {
    
    /**
     * Separator used to structure data in stored files.
     */
    public static final String SEPARATOR = "::";
    
    /**
     * Loads data into the flight booking system from an external source.
     *
     * @param fbs The flight booking system instance where data should be loaded.
     * @throws IOException If an I/O error occurs while reading the data.
     * @throws FlightBookingSystemException If the data is in an invalid format or contains errors.
     */
    public void loadData(FlightBookingSystem fbs) throws IOException, FlightBookingSystemException;

    /**
     * Stores the current flight booking system data to an external source.
     *
     * @param fbs The flight booking system instance containing the data to be stored.
     * @throws IOException If an I/O error occurs while writing the data.
     */
    public void storeData(FlightBookingSystem fbs) throws IOException;
}
