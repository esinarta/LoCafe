package ca.bcit.locafe.data.model;

public class User {
    protected String firstName;
    protected String lastName;
    protected String email;
    protected Business[] favourites;

    public User(String firstName, String lastName, String email) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getEmail() {
        return email;
    }

    public Business[] getFavourites() { return favourites; }

}
