package clayburn.familymap.app.network;

/**
 * Created by zachc_000 on 3/21/2018.
 */

/**
 * A data holder class for params detailing sever communications
 */
public class LoginRegisterParams {

    private String mServerHost;
    private int mServerPort;
    private String mUserName;
    private String mPassword;
    private String mFirstName;
    private String mLastName;
    private String mEmail;
    private String mGender;

    /**
     * Creates a LoginRegisterParams with only the information for login
     * @param serverHost The server's host name
     * @param serverPort The server's port
     * @param userName The user's username
     * @param password The user's password
     */
    public LoginRegisterParams(String serverHost, int serverPort,
                               String userName, String password) {
        mServerHost = serverHost;
        mServerPort = serverPort;
        mUserName = userName;
        mPassword = password;
    }

    /**
     * Creates a LoginRegisterParams with all the information needed for registration
     * @param serverHost The server's host name
     * @param serverPort The server's port
     * @param userName The user's username
     * @param password The user's password
     * @param firstName The user's first name
     * @param lastName The user's last name
     * @param email The user's email address
     * @param gender The user's gender
     */
    public LoginRegisterParams(String serverHost, int serverPort, String userName,
                               String password, String firstName, String lastName,
                               String email, String gender) {
        mServerHost = serverHost;
        mServerPort = serverPort;
        mUserName = userName;
        mPassword = password;
        mFirstName = firstName;
        mLastName = lastName;
        mEmail = email;
        mGender = gender;
    }

    public String getServerHost() {
        return mServerHost;
    }

    public int getServerPort() {
        return mServerPort;
    }

    public String getUserName() {
        return mUserName;
    }

    public String getPassword() {
        return mPassword;
    }

    public String getFirstName() {
        return mFirstName;
    }

    public String getLastName() {
        return mLastName;
    }

    public String getEmail() {
        return mEmail;
    }

    public String getGender() {
        return mGender;
    }
}
