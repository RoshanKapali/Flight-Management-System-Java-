package bcu.cmp5332.bookingsystem.model;

import java.time.LocalDate;

/**
 * Represents a flight booking for a customer. A booking consists of an outbound flight, 
 * an optional return flight, a booking date, and associated fees.
 */
public class Booking {

    private Customer customer;
    private Flight outboundFlight;
    private Flight returnFlight;
    private LocalDate bookingDate;
    private double cancellationFee;
    private double rebookFee;

    /**
     * Creates a new booking with the given customer, flights, and booking date.
     * Default fees for cancellation and rebooking are set automatically.
     *
     * @param customer The customer who made the booking.
     * @param outboundFlight The outbound flight associated with this booking.
     * @param returnFlight The return flight (if applicable), otherwise null.
     * @param bookingDate The date when the booking was made.
     */
    public Booking(Customer customer, Flight outboundFlight, Flight returnFlight, LocalDate bookingDate) {
        this.customer = customer;
        this.outboundFlight = outboundFlight;
        this.returnFlight = returnFlight;
        this.bookingDate = bookingDate;
        this.cancellationFee = 50.0; // Default cancellation fee
        this.rebookFee = 30.0; // Default rebooking fee
    }

    /**
     * Sets the customer for this booking.
     *
     * @param customer The customer who owns this booking.
     */
    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    /**
     * Gets the outbound flight for this booking.
     *
     * @return The outbound flight.
     */
    public Flight getOutboundFlight() {
        return outboundFlight;
    }

    /**
     * Sets the outbound flight for this booking.
     *
     * @param outboundFlight The new outbound flight.
     */
    public void setOutboundFlight(Flight outboundFlight) {
        this.outboundFlight = outboundFlight;
    }

    /**
     * Gets the return flight for this booking.
     *
     * @return The return flight, or null if none was booked.
     */
    public Flight getReturnFlight() {
        return returnFlight;
    }

    /**
     * Sets the return flight for this booking.
     *
     * @param returnFlight The new return flight (can be null if no return flight is needed).
     */
    public void setReturnFlight(Flight returnFlight) {
        this.returnFlight = returnFlight;
    }

    /**
     * Gets the customer associated with this booking.
     *
     * @return The customer who made the booking.
     */
    public Customer getCustomer() {
        return customer;
    }

    /**
     * Gets the date when the booking was made.
     *
     * @return The booking date.
     */
    public LocalDate getBookingDate() {
        return bookingDate;
    }

    /**
     * Sets the booking date.
     *
     * @param bookingDate The date when the booking was made.
     */
    public void setBookingDate(LocalDate bookingDate) {
        this.bookingDate = bookingDate;
    }

    /**
     * Gets the cancellation fee for this booking.
     *
     * @return The cancellation fee amount.
     */
    public double getCancellationFee() {
        return cancellationFee;
    }

    /**
     * Gets the rebooking fee for this booking.
     *
     * @return The rebooking fee amount.
     */
    public double getRebookFee() {
        return rebookFee;
    }

    /**
     * Calculates the total price of the booking, including flight prices and any associated fees.
     *
     * @return The total cost of the booking.
     */
    public double getTotalPrice() {
        double totalPrice = outboundFlight.getPrice() + cancellationFee + rebookFee;
        if (returnFlight != null) {
            totalPrice += returnFlight.getPrice(); // Add return flight price if it exists
        }
        return totalPrice;
    }

    /**
     * Returns a formatted string containing the details of the booking.
     *
     * @return A string with booking information including flights, dates, and total price.
     */
    public String getDetails() {
        String details = "Booking Details:\n" +
                         "Customer: " + customer.getName() + "\n" +
                         "Outbound Flight: " + outboundFlight.getFlightNumber() + 
                         " (" + outboundFlight.getOrigin() + " → " + outboundFlight.getDestination() + ")\n" +
                         "Departure Date: " + outboundFlight.getDepartureDate() + "\n" +
                         "Booking Date: " + bookingDate + "\n";

        if (returnFlight != null) {
            details += "Return Flight: " + returnFlight.getFlightNumber() + 
                       " (" + returnFlight.getOrigin() + " → " + returnFlight.getDestination() + ")\n" +
                       "Return Date: " + returnFlight.getDepartureDate() + "\n";
        }

        details += "Total Price: $" + getTotalPrice(); // ✅ Show total price

        return details;
    }
}
