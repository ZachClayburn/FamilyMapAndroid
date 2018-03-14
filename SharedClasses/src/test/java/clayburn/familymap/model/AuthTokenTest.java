package clayburn.familymap.model;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Created by zclaybur on 3/7/18.
 */
class AuthTokenTest {

    @BeforeEach
    void setUp(){
        authToken = new AuthToken(authToken1,userName1);
        testAuthToken = new AuthToken(authToken2,userName2);
    }

    @AfterEach
    void tearDown(){
        authToken = null;
    }

    AuthToken authToken;

    AuthToken testAuthToken;

    static String authToken1 = "randomAuthToken";
    static String userName1 = "SomeRandomUserName";
    static String authToken2 = "newAuthToken";
    static String userName2 = "userName2";


    @Test
    void getAuthToken() {
        assertEquals(authToken.getAuthToken(),authToken1);
    }

    @Test
    void setAuthToken() {
        authToken.setAuthToken(authToken2);
        assertEquals(authToken.getAuthToken(),authToken2);
    }

    @Test
    void getUserName() {
        assertEquals(authToken.getUserName(),userName1);
    }

    @Test
    void setUserName() {
        authToken.setUserName(userName2);
        assertEquals(authToken.getUserName(), userName2);
    }

    @Test
    void testToString() {
        String string = "AuthToken{authToken='" + authToken1 + "', userName='" + userName1 + "'}";
        assertEquals(authToken.toString(),string);
    }

    @Test
    void equals() {
        assertNotEquals(authToken,testAuthToken);
    }

    @Test
    void TestHashCode() {
        assertNotEquals(authToken.hashCode(),testAuthToken.hashCode());
    }

}