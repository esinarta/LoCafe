package ca.bcit.locafe.data.model;

/**
 * Data class that captures user information for logged in users retrieved from LoginRepository
 */
public class LoggedInUser extends User{

    public LoggedInUser(String firstName, String lastName, String email) {
        super(firstName, lastName, email);
    }

    public String getDisplayName() {
        return firstName + lastName;
    }
}