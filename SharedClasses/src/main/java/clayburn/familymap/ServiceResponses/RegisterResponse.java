package clayburn.familymap.ServiceResponses;


/**
 * The response to a register request
 */
public class RegisterResponse implements ServiceResponse {

    /**
     * The auth token for the current user's session
     */
    private String authToken;

    /**
     * The current user's userName
     */
    private String userName;

    /**
     * The PersonID string representing the user's corresponding Person
     */
    private String personID;

    /**
     * Creates a RegisterResponse to be sent to the client
     * @param authToken The auth token for the current user's session
     * @param userName The current user's userName
     * @param personID The PersonID string representing the user's corresponding Person
     */
    public RegisterResponse(String authToken, String userName, String personID) {
        this.authToken = authToken;
        this.userName = userName;
        this.personID = personID;
    }

    public String getAuthToken() {
        return authToken;
    }

    public void setAuthToken(String authToken) {
        this.authToken = authToken;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPersonID() {
        return personID;
    }

    public void setPersonID(String personID) {
        this.personID = personID;
    }
}
