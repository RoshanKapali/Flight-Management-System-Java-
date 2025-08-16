package bcu.cmp5332.bookingsystem.model;

/**
 * The {@code User} class represents a user in the flight booking system.
 * A user has a username, password, and a role, which can be either "admin" or "customer".
 */
public class User {

    private final String username;
    private final String password;
    private final String role; // Either "admin" or "customer"

    /**
     * Creates a new user with the given username, password, and role.
     *
     * @param username The unique name of the user.
     * @param password The user's password.
     * @param role The role of the user, either "admin" or "customer".
     */
    public User(String username, String password, String role) {
        this.username = username;
        this.password = password;
        this.role = role;
    }

    /**
     * Gets the username of the user.
     *
     * @return The username.
     */
    public String getUsername() {
        return username;
    }

    /**
     * Gets the password of the user.
     * 
     * @return The user's password.
     */
    public String getPassword() {
        return password;
    }

    /**
     * Gets the role of the user.
     *
     * @return The role as a string, either "admin" or "customer".
     */
    public String getRole() {
        return role;
    }

    /**
     * Checks if the user is an admin.
     *
     * @return {@code true} if the user's role is "admin", otherwise {@code false}.
     */
    public boolean isAdmin() {
        return "admin".equals(role);
    }

    /**
     * Checks if the user is a customer.
     *
     * @return {@code true} if the user's role is "customer", otherwise {@code false}.
     */
    public boolean isCustomer() {
        return "customer".equals(role);
    }
}
