package clayburn.familymap.model;


import java.util.Objects;

/**
 * User represents a user that has been registered on the Server
 * @author Zach Clayburn
 */
public class User {

    /**
     * User's unique user name
     */
    private String userName;

    /**
     * User's password
     */
    private String password;

    /**
     * User's email address
     */
    private String email;

    /**
     * User's first name
     */
    private String firstName;

    /**
     * User's last name
     */
    private String lastName;

    /**
     * User's gender(either m or f)
     */
    private String gender;

    /**
     * The ID of the Person created to represent this User
     */
    private String personID;

    /**
     * Creates a new User object representing a row from the user table
     * @param userName User's unique user name
     * @param password User's password
     * @param email User's email address
     * @param firstName User's first name
     * @param lastName User's last name
     * @param gender User's gender(either m or f)
     * @param personID The ID of the Person created to represent this User
     */
    public User(String userName, String password,
                String email, String firstName,
                String lastName, String gender, String personID) {
        this.userName = userName;
        this.password = password;
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
        this.gender = gender == null ? null : gender.toLowerCase();
        this.personID = personID;
    }

    /**
     * Creates a User with all null values to be initialized by setters
     */
    public static User newEmptyUser() {
        return new User(null,null,null,
                null,null,null,null);
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender.toLowerCase();
    }

    public String getPersonID() {
        return personID;
    }

    public void setPersonID(String personID) {
        this.personID = personID;
    }

    /**
     * Test if the given User and o are equal by comparing each field
     * @param o The object to compare to the User
     * @return True if equal,False if not
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(userName, user.userName) &&
                Objects.equals(password, user.password) &&
                Objects.equals(email, user.email) &&
                Objects.equals(firstName, user.firstName) &&
                Objects.equals(lastName, user.lastName) &&
                Objects.equals(gender, user.gender) &&
                Objects.equals(personID, user.personID);
    }

    /**
     * Generate a hash code for the given User
     * @return The User's hash code
     */
    @Override
    public int hashCode() {
        return Objects.hash(userName, password, email, firstName, lastName, gender, personID);
    }

    /**
     * Creates a String representing the User.
     * String is in the form User{userName='userName', password='password',
     * email='email', firstName='firstName', lastName='lastName', gender='m', personID='personID'}
     * @return A string representing the User
     */
    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("User{");
        sb.append("userName='").append(userName).append('\'');
        sb.append(", password='").append(password).append('\'');
        sb.append(", email='").append(email).append('\'');
        sb.append(", firstName='").append(firstName).append('\'');
        sb.append(", lastName='").append(lastName).append('\'');
        sb.append(", gender='").append(gender).append('\'');
        sb.append(", personID='").append(personID).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
