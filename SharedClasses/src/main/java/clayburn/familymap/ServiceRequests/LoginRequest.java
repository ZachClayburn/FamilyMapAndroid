package clayburn.familymap.ServiceRequests;


/**
 * A request to log onto the server
 */
public class LoginRequest implements ServiceRequest{

    /**
     * The user name to log in with
     */
    private String userName;

    /**
     * The password to log in with
     */
    private String password;

    /**
     * Constructs a Login request to be sent to the Server
     * @param userName The user name to log in with
     * @param password The password to log in with
     */
    public LoginRequest(String userName, String password) {
        this.userName = userName;
        this.password = password;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPassword() {
        return password;
    }

    public boolean hasNullValues(){
        return  userName == null ||
                password == null;
    }
}
