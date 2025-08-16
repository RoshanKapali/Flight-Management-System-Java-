package bcu.cmp5332.bookingsystem.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

public class CustomerTest {

    private Customer customer;
    private Flight flight1;
    private Flight flight2;
    private Booking booking1;
    private Booking booking2;

    @BeforeEach
    void setUp() {
        customer = new Customer(1, "Alice Karki", "987654321", "alice@gmail.com");
        flight1 = new Flight(101, "FL123", "New York", "Los Angeles", LocalDate.of(2025, 6, 10), 200, 350.0);
        flight2 = new Flight(102, "FL456", "Los Angeles", "Chicago", LocalDate.of(2025, 6, 15), 200, 250.0);
        booking1 = new Booking(customer, flight1, flight1, LocalDate.of(2025, 5, 1));
        booking2 = new Booking(customer, flight2, flight1, LocalDate.of(2025, 5, 2));

        customer.addBooking(booking1);
        customer.addBooking(booking2);
    }

    @Test
    void testCustomerDetails() {
        assertEquals(1, customer.getId());
        assertEquals("Alice Karki", customer.getName());
        assertEquals("987654321", customer.getPhone());
        assertEquals("alice@gmail.com", customer.getEmail());
    }

    @Test
    void testAddBooking() {
        assertEquals(2, customer.getBookings().size());
        assertTrue(customer.getBookings().contains(booking1));
        assertTrue(customer.getBookings().contains(booking2));
    }

    @Test
    void testGetDetailsShort() {
        String expectedShortDetails = "Customer #1 - Alice Karki - 987654321 - alice@gmail.com";
        assertEquals(expectedShortDetails, customer.getDetailsShort());
    }

    @Test
    void testGetDetailsLong() {
        String details = customer.getDetailsLong();
        assertTrue(details.contains("Alice Karki"));
        assertTrue(details.contains("Flight: FL123"));
        assertTrue(details.contains("Flight: FL456"));
    }
}
