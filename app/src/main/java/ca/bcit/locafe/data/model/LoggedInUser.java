package ca.bcit.locafe.data.model;

/**
 * Data class that captures user information for logged in users retrieved from LoginRepository
 */
public class LoggedInUser extends User{

    public LoggedInUser(String userId, String displayName) {
        super(userId, displayName);
    }

    public String getUserId() {
        return userId;
    }

    public String getDisplayName() {
        return displayName;
    }
}