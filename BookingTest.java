package bcu.cmp5332.bookingsystem.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

public class BookingTest {

    private Customer customer;
    private Flight outboundFlight;
    private Flight returnFlight;
    private Booking booking;

    @BeforeEach
    void setUp() {
        customer = new Customer(1, "John Doe", "123456789", "john@example.com");
        outboundFlight = new Flight(101, "FL1001", "New York", "London", LocalDate.of(2025, 5, 15), 200, 500.0);
        returnFlight = new Flight(102, "FL1002", "London", "New York", LocalDate.of(2025, 5, 30), 200, 500.0);
        booking = new Booking(customer, outboundFlight, returnFlight, LocalDate.of(2025, 4, 10));
    }

    @Test
    void testBookingCreation() {
        assertEquals(customer, booking.getCustomer());
        assertEquals(outboundFlight, booking.getOutboundFlight());
        assertEquals(returnFlight, booking.getReturnFlight());
        assertEquals(LocalDate.of(2025, 4, 10), booking.getBookingDate());
    }

    @Test
    void testGetTotalPrice() {
        double expectedTotalPrice = outboundFlight.getPrice() + returnFlight.getPrice() + booking.getRebookFee() + booking.getCancellationFee();
        assertEquals(expectedTotalPrice, booking.getTotalPrice());
    }

    @Test
    void testCancellationFee() {
        assertEquals(50.0, booking.getCancellationFee());
    }

    @Test
    void testRebookFee() {
        assertEquals(30.0, booking.getRebookFee());
    }

    @Test
    void testBookingDetails() {
        String expectedDetails = "Booking Details:\n" +
                "Customer: John Doe\n" +
                "Flight: FL1001 from New York to London\n" +
                "Departure Date: 2025-05-15\n" +
                "Booking Date: 2025-04-10";
        
        assertTrue(booking.getDetails().contains("John Doe"));
        assertTrue(booking.getDetails().contains("FL1001"));
        assertTrue(booking.getDetails().contains("New York"));
    }
}
