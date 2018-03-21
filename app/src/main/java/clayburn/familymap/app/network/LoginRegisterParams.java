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

    public LoginRegisterParams(String serverHost, int serverPort,
                               String userName, String password) {
        mServerHost = serverHost;
        mServerPort = serverPort;
        mUserName = userName;
        mPassword = password;
    }

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
