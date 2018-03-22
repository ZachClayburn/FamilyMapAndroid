package clayburn.familymap.app.network;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import clayburn.familymap.ServiceRequests.LoginRequest;
import clayburn.familymap.ServiceResponses.ErrorResponse;
import clayburn.familymap.ServiceResponses.LoginResponse;
import clayburn.familymap.ServiceResponses.ServiceResponse;
import clayburn.familymap.database.Database;
import clayburn.familymap.model.User;

import static org.junit.Assert.*;

/**
 * Created by zach on 3/17/18.
 */
public class ProxyTest {

    private Proxy proxy = new Proxy("localhost",8080);
    private Database db = new Database();

    static String mUserName = "userName";
    static String mPassWord = "password";
    static String mEmail = "email";
    static String mFirstName = "firstName";
    static String mLastName = "lastName";
    static String mGender = "m";
    static String mPersonID = "RANDOMPERSONID";
    static User mUser = new User(mUserName,mPassWord,mEmail,mFirstName,mLastName,mGender,mPersonID);

    static String serverHost = "localhost";
    static int port = 8080;


    @Before
    public void setUp() throws Exception {
        Database.backUp();
        db.openConnection();
        db.createAllTables();
        db.getUserDAO().addUsers(mUser);
        db.closeConnection(true);
    }

    @After
    public void tearDown() throws Exception {
        Database.restore();
    }

    @Test
    public void login() throws Exception {
        Proxy proxy = new Proxy(serverHost,port);
        LoginRequest goodRequest = new LoginRequest(mUserName,mPassWord);
        LoginRequest badRequest = new LoginRequest("BAD","INFO");
        ServiceResponse goodResponse = proxy.login(goodRequest);
        ServiceResponse badResponse = proxy.login(badRequest);
        try {
            assertEquals(LoginResponse.class,goodResponse.getClass());
            LoginResponse loginResponse = (LoginResponse) goodResponse;
            assertEquals(mPersonID,loginResponse.getPersonID());

            assertEquals(ErrorResponse.class,badResponse.getClass());
        } catch (Exception e){
            fail(e.getMessage());
        }
    }

    @Test
    public void register() throws Exception {
        Proxy proxy = new Proxy(serverHost,port);

    }

    @Test
    public void getPersons() throws Exception {
        Proxy proxy = new Proxy(serverHost,port);

    }

    @Test
    public void getEvents() throws Exception {
    }

}