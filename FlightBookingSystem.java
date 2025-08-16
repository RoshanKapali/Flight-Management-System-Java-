package bcu.cmp5332.bookingsystem.model;

import bcu.cmp5332.bookingsystem.main.FlightBookingSystemException;
import java.time.LocalDate;
import java.util.*;

/**
 * The {@code FlightBookingSystem} class manages flights and customers in the system.
 * It provides methods for adding, retrieving, and managing flights and customers.
 */
public class FlightBookingSystem {

    private final Map<Integer, Customer> customers = new TreeMap<>();
    private final Map<Integer, Flight> flights = new TreeMap<>();
    private List<Flight> filteredFlights; // Stores filtered flights based on criteria

    /**
     * Gets the current system date.
     *
     * @return The current date as a {@code LocalDate} object.
     */
    public LocalDate getSystemDate() {
        return LocalDate.now(); // Returns the current date
    }

    /**
     * Retrieves a list of all flights in the system.
     *
     * @return An unmodifiable list of flights.
     */
    public List<Flight> getFlights() {
        List<Flight> out = new ArrayList<>(flights.values());
        return Collections.unmodifiableList(out);
    }

    /**
     * Retrieves a flight by its unique ID.
     *
     * @param id The flight ID.
     * @return The flight with the specified ID.
     * @throws FlightBookingSystemException If no flight with the given ID exists.
     */
    public Flight getFlightByID(int id) throws FlightBookingSystemException {
        if (!flights.containsKey(id)) {
            throw new FlightBookingSystemException("There is no flight with that ID.");
        }
        return flights.get(id);
    }

    /**
     * Retrieves a customer by their unique ID.
     *
     * @param id The customer ID.
     * @return The customer with the specified ID.
     * @throws FlightBookingSystemException If no customer with the given ID exists.
     */
    public Customer getCustomerByID(int id) throws FlightBookingSystemException {
        if (!customers.containsKey(id)) {
            throw new FlightBookingSystemException("There is no customer with that ID.");
        }
        return customers.get(id);
    }

    /**
     * Adds a new flight to the system.
     *
     * @param flight The flight to be added.
     * @throws FlightBookingSystemException If a flight with the same number and departure date already exists.
     * @throws IllegalArgumentException If a flight with the same ID already exists.
     */
    public void addFlight(Flight flight) throws FlightBookingSystemException {
        if (flights.containsKey(flight.getId())) {
            throw new IllegalArgumentException("Duplicate flight ID.");
        }
        for (Flight existing : flights.values()) {
            if (existing.getFlightNumber().equals(flight.getFlightNumber()) 
                && existing.getDepartureDate().isEqual(flight.getDepartureDate())) {
                throw new FlightBookingSystemException("There is a flight with the same "
                        + "number and departure date in the system");
            }
        }
        flights.put(flight.getId(), flight);
    }

    /**
     * Adds a new customer to the system.
     *
     * @param customer The customer to be added.
     */
    public void addCustomer(Customer customer) {
        customers.put(customer.getId(), customer);
    }

    /**
     * Retrieves a list of all customers in the system.
     *
     * @return A list of customers.
     */
    public List<Customer> getCustomers() {
        return new ArrayList<>(customers.values());
    }

    /**
     * Provides direct access to the map of customers.
     *
     * @return A map containing all customers indexed by their ID.
     */
    public Map<Integer, Customer> getCustomersMap() {
        return customers;
    }

    /**
     * Sets the list of filtered flights, usually based on search criteria.
     *
     * @param filteredFlights The list of filtered flights.
     */
    public void setFilteredFlights(List<Flight> filteredFlights) {
        this.filteredFlights = filteredFlights;
    }

    /**
     * Retrieves the currently filtered list of flights.
     *
     * @return A list of filtered flights.
     */
    public List<Flight> getFilteredFlights() {
        return filteredFlights;
    }

    /**
     * Provides direct access to the map of flights.
     *
     * @return A map containing all flights indexed by their ID.
     */
    public Map<Integer, Flight> getFlightsMap() {
        return flights;
    }
}
