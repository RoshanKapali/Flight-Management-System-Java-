package bcu.cmp5332.bookingsystem.model;

import bcu.cmp5332.bookingsystem.main.FlightBookingSystemException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Represents a flight in the booking system. Each flight has a unique ID, flight number, 
 * origin, destination, departure date, capacity, price, and a list of passengers.
 */
public class Flight {

    private int id;
    private String flightNumber;
    private String origin;
    private String destination;
    private LocalDate departureDate;
    private int capacity;
    private double price;
    private final Set<Customer> passengers;

    /**
     * Creates a new flight with the specified details.
     *
     * @param id The unique flight ID.
     * @param flightNumber The flight number.
     * @param origin The departure location.
     * @param destination The arrival location.
     * @param departureDate The date of departure.
     * @param capacity The maximum number of passengers the flight can hold.
     * @param price The ticket price for the flight.
     */
    public Flight(int id, String flightNumber, String origin, String destination, LocalDate departureDate, int capacity, double price) {
        this.id = id;
        this.flightNumber = flightNumber;
        this.origin = origin;
        this.destination = destination;
        this.departureDate = departureDate;
        this.capacity = capacity;
        this.price = price;
        this.passengers = new HashSet<>();
    }

    /**
     * Gets the unique flight ID.
     *
     * @return The flight ID.
     */
    public int getId() {
        return id;
    }

    /**
     * Sets the unique flight ID.
     *
     * @param id The new flight ID.
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Gets the flight number.
     *
     * @return The flight number.
     */
    public String getFlightNumber() {
        return flightNumber;
    }

    /**
     * Sets the flight number.
     *
     * @param flightNumber The new flight number.
     */
    public void setFlightNumber(String flightNumber) {
        this.flightNumber = flightNumber;
    }

    /**
     * Gets the origin (departure location) of the flight.
     *
     * @return The origin of the flight.
     */
    public String getOrigin() {
        return origin;
    }

    /**
     * Sets the origin (departure location) of the flight.
     *
     * @param origin The new origin of the flight.
     */
    public void setOrigin(String origin) {
        this.origin = origin;
    }

    /**
     * Gets the destination (arrival location) of the flight.
     *
     * @return The destination of the flight.
     */
    public String getDestination() {
        return destination;
    }

    /**
     * Sets the destination (arrival location) of the flight.
     *
     * @param destination The new destination of the flight.
     */
    public void setDestination(String destination) {
        this.destination = destination;
    }

    /**
     * Gets the departure date of the flight.
     *
     * @return The departure date.
     */
    public LocalDate getDepartureDate() {
        return departureDate;
    }

    /**
     * Sets the departure date of the flight.
     *
     * @param departureDate The new departure date.
     */
    public void setDepartureDate(LocalDate departureDate) {
        this.departureDate = departureDate;
    }

    /**
     * Gets the total passenger capacity of the flight.
     *
     * @return The capacity of the flight.
     */
    public int getCapacity() {
        return capacity;
    }

    /**
     * Sets the total passenger capacity of the flight.
     *
     * @param capacity The new capacity of the flight.
     */
    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    /**
     * Gets the price of a ticket for this flight.
     *
     * @return The ticket price.
     */
    public double getPrice() {
        return price;
    }

    /**
     * Sets the price of a ticket for this flight.
     *
     * @param price The new ticket price.
     */
    public void setPrice(double price) {
        this.price = price;
    }

    /**
     * Gets a list of passengers currently booked on this flight.
     *
     * @return A list of passengers.
     */
    public List<Customer> getPassengers() {
        return new ArrayList<>(passengers);
    }

    /**
     * Returns a short summary of the flight details.
     *
     * @return A brief description of the flight.
     */
    public String getDetailsShort() {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        return "Flight #" + id + " - " + flightNumber + " - " + origin + " to " 
                + destination + " on " + departureDate.format(dtf);
    }

    /**
     * Returns a detailed description of the flight, including passenger details.
     *
     * @return A detailed summary of the flight.
     */
    public String getDetailsLong() {
        StringBuilder details = new StringBuilder();
        details.append("Flight Details:\n");
        details.append("ID: ").append(id).append("\n");
        details.append("Flight Number: ").append(flightNumber).append("\n");
        details.append("Origin: ").append(origin).append("\n");
        details.append("Destination: ").append(destination).append("\n");
        details.append("Departure Date: ").append(departureDate).append("\n");
        details.append("Capacity: ").append(capacity).append("\n");
        details.append("Price ($): ").append(price).append("\n");
        details.append("Passengers (").append(passengers.size()).append("/").append(capacity).append("):\n");
        for (Customer passenger : passengers) {
            details.append("- ").append(passenger.getDetailsShort()).append("\n");
        }
        return details.toString();
    }

    /**
     * Adds a passenger to the flight if there is available capacity.
     *
     * @param passenger The customer to be added to the flight.
     * @throws FlightBookingSystemException If the flight is already fully booked.
     */
    public void addPassenger(Customer passenger) throws FlightBookingSystemException {
        if (passengers.size() >= capacity) {
            throw new FlightBookingSystemException("Flight #" + id + " is fully booked. Cannot add passenger.");
        }
        passengers.add(passenger);
    }

    /**
     * Removes a passenger from the flight.
     *
     * @param passenger The customer to be removed from the flight.
     */
    public void removePassenger(Customer passenger) {
        passengers.remove(passenger);
    }
}
