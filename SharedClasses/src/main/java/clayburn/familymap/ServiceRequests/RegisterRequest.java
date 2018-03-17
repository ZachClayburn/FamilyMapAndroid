package clayburn.familymap.ServiceRequests;


import clayburn.familymap.model.User;

/**
 * A request to register a new user
 */
public class RegisterRequest implements ServiceRequest {

    /**
     * The username to register
     */
    private String userName;

    /**
     * The password for the new user account
     */
    private String password;

    /**
     * The new User's email
     */
    private String email;

    /**
     * The new User's first name
     */
    private String firstName;

    /**
     * The new User's last name
     */
    private String lastName;

    /**
     * The new User's gender
     */
    private String gender;

    /**
     * Constructs a new RegisterRequest to send to the server
     * @param userName The username to register
     * @param password The password for the new user account
     * @param email The new User's email
     * @param firstName The new User's first name
     * @param lastName The new User's last name
     * @param gender The new User's gender
     */
    public RegisterRequest(String userName, String password, String email, String firstName, String lastName, String gender) {
        this.userName = userName;
        this.password = password;
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
        this.gender = gender;
    }

    /**
     * Checks if there are any null values
     * @return True if there are any null values, false otherwise
     */
    public boolean hasNullValues(){
        return  (userName == null ||
                password  == null ||
                email     == null ||
                firstName == null ||
                lastName  == null ||
                gender    == null );
    }

    /**
     * Get the User that the request includes
     * @return The User, with the userID field set as null
     */
    public User getUser(){
        return new User(userName,password,email,firstName,lastName,gender,null);
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getUserName() {
        return userName;
    }

    public String getPassword() {
        return password;
    }

    public String getEmail() {
        return email;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getGender() {
        return gender;
    }
}
