package bcu.cmp5332.bookingsystem.model;

import bcu.cmp5332.bookingsystem.main.FlightBookingSystemException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class FlightBookingSystemTest {

    private FlightBookingSystem fbs;
    private Flight flight1;
    private Flight flight2;
    private Customer passenger1;
    private Customer passenger2;

    @BeforeEach
    public void setUp() throws FlightBookingSystemException {
        fbs = new FlightBookingSystem();

        // Initialize flights
        flight1 = new Flight(101, "FL123", "New York", "Los Angeles", LocalDate.of(2025, 6, 10), 200, 350.0);
        flight2 = new Flight(102, "FL456", "Los Angeles", "Chicago", LocalDate.of(2025, 6, 15), 200, 250.0);

        // Initialize passengers (customers)
        passenger1 = new Customer(7, "Roshan", "111111111", "roshan@example.com");
        passenger2 = new Customer(8, "Sudip", "999999999", "sudip@example.com");

        // Add flights and customers
        fbs.addCustomer(passenger1);
        fbs.addCustomer(passenger2);
        fbs.addFlight(flight1);
        fbs.addFlight(flight2);
    }

    @Test
    public void testAddPassenger() throws FlightBookingSystemException {
        flight1.addPassenger(passenger1);
        assertEquals(1, flight1.getPassengers().size());  // FIXED: Check the size of the list
        assertTrue(flight1.getPassengers().contains(passenger1));
    }

    @Test
    public void testAddPassengerAtFullCapacity() throws FlightBookingSystemException {
        for (int i = 0; i < 200; i++) {
            Customer tempCustomer = new Customer(i, "Temp Customer " + i, "00000000" + i, "temp" + i + "@example.com");
            fbs.addCustomer(tempCustomer);
            flight1.addPassenger(tempCustomer);
        }
        assertThrows(FlightBookingSystemException.class, () -> flight1.addPassenger(passenger1));
    }

    @Test
    public void testGetCustomerByID() throws FlightBookingSystemException {
        Customer retrievedCustomer = fbs.getCustomerByID(7);
        assertNotNull(retrievedCustomer, "Retrieved customer should not be null.");
        assertEquals("Roshan", retrievedCustomer.getName());
    }

    @Test
    public void testGetFlightByID() throws FlightBookingSystemException {
        Flight retrievedFlight = fbs.getFlightByID(101);
        assertNotNull(retrievedFlight, "Retrieved flight should not be null.");
        assertEquals("FL123", retrievedFlight.getFlightNumber());
    }

    @Test
    public void testCustomerNotFound() {
        Exception exception = assertThrows(FlightBookingSystemException.class, () -> {
            fbs.getCustomerByID(99);
        });
        assertEquals("There is no customer with that ID.", exception.getMessage());
    }

    @Test
    public void testFlightNotFound() {
        Exception exception = assertThrows(FlightBookingSystemException.class, () -> {
            fbs.getFlightByID(999);
        });
        assertEquals("There is no flight with that ID.", exception.getMessage());
    }
}
