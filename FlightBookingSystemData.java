package bcu.cmp5332.bookingsystem.data;

import bcu.cmp5332.bookingsystem.main.FlightBookingSystemException;
import bcu.cmp5332.bookingsystem.model.FlightBookingSystem;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Manages the loading and storing of data for the flight booking system.
 * This class utilizes multiple {@code DataManager} implementations to handle
 * different types of data such as flights, customers, and bookings.
 */
public class FlightBookingSystemData {

    private static final List<DataManager> dataManagers = new ArrayList<>();

    // Static initializer block to ensure data managers are added only once
    static {
        dataManagers.add(new FlightDataManager());
        dataManagers.add(new CustomerDataManager());
        dataManagers.add(new BookingDataManager()); 
    }

    /**
     * Loads the flight booking system data from storage.
     * This method initializes a new {@code FlightBookingSystem} instance and
     * populates it using the registered data managers.
     *
     * @return A fully loaded instance of {@code FlightBookingSystem}.
     * @throws FlightBookingSystemException If an error occurs while loading the data.
     * @throws IOException If an I/O error occurs during file reading.
     */
    public static FlightBookingSystem load() throws FlightBookingSystemException, IOException {
        FlightBookingSystem fbs = new FlightBookingSystem();
        for (DataManager dm : dataManagers) {
            dm.loadData(fbs);
        }
        return fbs;
    }

    /**
     * Stores the flight booking system data to storage.
     * This method iterates through the registered data managers and saves
     * all relevant data to files or other storage mediums.
     *
     * @param fbs The flight booking system instance containing data to be stored.
     * @throws IOException If an I/O error occurs during file writing.
     */
    public static void store(FlightBookingSystem fbs) throws IOException {
        for (DataManager dm : dataManagers) {
            dm.storeData(fbs);
        }
    }
}
