package bcu.cmp5332.bookingsystem.model;

import bcu.cmp5332.bookingsystem.main.FlightBookingSystemException;
import java.util.ArrayList;
import java.util.List;

/**
 * Represents a customer in the flight booking system. A customer has a unique ID, 
 * name, contact details, and a list of bookings.
 */
public class Customer {

    private int id;
    private String name;
    private String phone;
    private String email;
    private final List<Booking> bookings = new ArrayList<>();

    /**
     * Creates a new customer with the given details.
     *
     * @param id The unique identifier for the customer.
     * @param name The name of the customer.
     * @param phone The phone number of the customer.
     * @param email The email address of the customer.
     */
    public Customer(int id, String name, String phone, String email) {
        this.id = id;
        this.name = name;
        this.phone = phone;
        this.email = email;
    }

    /**
     * Gets the customer's ID.
     *
     * @return The customer ID.
     */
    public int getId() {
        return id;
    }

    /**
     * Sets the customer's ID.
     *
     * @param id The new customer ID.
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Gets the customer's name.
     *
     * @return The name of the customer.
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the customer's name.
     *
     * @param name The new name of the customer.
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Gets the customer's phone number.
     *
     * @return The phone number of the customer.
     */
    public String getPhone() {
        return phone;
    }

    /**
     * Sets the customer's phone number.
     *
     * @param phone The new phone number of the customer.
     */
    public void setPhone(String phone) {
        this.phone = phone;
    }

    /**
     * Gets the customer's email address.
     *
     * @return The email of the customer.
     */
    public String getEmail() {
        return email;
    }

    /**
     * Sets the customer's email address.
     *
     * @param email The new email of the customer.
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Gets a list of all bookings associated with this customer.
     *
     * @return A list of the customer's bookings.
     */
    public List<Booking> getBookings() {
        return new ArrayList<>(bookings); // Return a copy to prevent external modification
    }

    /**
     * Adds a booking for the customer if it is not already in the list.
     *
     * @param booking The booking to add.
     */
    public void addBooking(Booking booking) {
        if (!bookings.contains(booking)) {
            bookings.add(booking);
        }
    }

    /**
     * Removes a booking from the customer's list of bookings.
     *
     * @param booking The booking to remove.
     */
    public void removeBooking(Booking booking) {
        bookings.remove(booking);
    }

    /**
     * Provides a short summary of the customer, including their ID, name, 
     * phone number, and email.
     *
     * @return A brief customer summary.
     */
    public String getDetailsShort() {
        return "Customer #" + id + " - " + name + " - " + phone + " - " + email;
    }

    /**
     * Provides a detailed summary of the customer, including their ID, contact details, 
     * and any associated bookings.
     *
     * @return A detailed customer summary.
     */
    public String getDetailsLong() {
        StringBuilder details = new StringBuilder();
        details.append("Customer Details:\n");
        details.append("ID: ").append(id).append("\n");
        details.append("Name: ").append(name).append("\n");
        details.append("Phone: ").append(phone).append("\n");
        details.append("Email: ").append(email).append("\n");

        details.append("Bookings:\n");
        if (bookings.isEmpty()) {
            details.append("No bookings available.\n");
        } else {
            for (Booking booking : bookings) {
                details.append("- ").append(booking.getDetails()).append("\n");
            }
        }
        return details.toString();
    }
}
