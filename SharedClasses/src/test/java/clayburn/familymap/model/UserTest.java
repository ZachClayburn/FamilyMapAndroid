package clayburn.familymap.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class UserTest {

    @BeforeEach
    void setUp() {
        user1 = new User(userName1,password1,email1,firstName1,lastName1,gender1,personID1);
        user2 = new User(userName2,password2,email2,firstName2,lastName2,gender2,personID2);
    }

    User user1;
    User user2;

    static final String userName1 = "UserName1";
    static final String password1 = "Password";
    static final String email1 = "User1@email.com";
    static final String firstName1 = "firstName1";
    static final String lastName1 = "lastName1";
    static final String gender1 = "m";
    static final String personID1 = "personID1";

    static final String userName2 = "UserName2";
    static final String password2 = "Password";
    static final String email2 = "User2@email.com";
    static final String firstName2 = "firstName2";
    static final String lastName2 = "lastName2";
    static final String gender2 = "f";
    static final String personID2 = "personID2";


    @Test
    void gettersAndSetters(){
        assertEquals(user1.getUserName(),userName1);
        assertEquals(user1.getPassword(),password1);
        assertEquals(user1.getEmail(),email1);
        assertEquals(user1.getFirstName(),firstName1);
        assertEquals(user1.getLastName(),lastName1);
        assertEquals(user1.getGender(),gender1);
        assertEquals(user1.getPersonID(),personID1);

        user1.setUserName(userName2);
        user1.setPassword(password2);
        user1.setEmail(email2);
        user1.setFirstName(firstName2);
        user1.setLastName(lastName2);
        user1.setGender(gender2);
        user1.setPersonID(personID2);

        assertEquals(user1.getUserName(),userName2);
        assertEquals(user1.getPassword(),password2);
        assertEquals(user1.getEmail(),email2);
        assertEquals(user1.getFirstName(),firstName2);
        assertEquals(user1.getLastName(),lastName2);
        assertEquals(user1.getGender(),gender2);
        assertEquals(user1.getPersonID(),personID2);

    }

    @Test
    void newEmptyUser() {
        User nullUser = User.newEmptyUser();

        assertEquals(nullUser.getUserName(),null);
        assertEquals(nullUser.getPassword(),null);
        assertEquals(nullUser.getEmail(),null);
        assertEquals(nullUser.getFirstName(),null);
        assertEquals(nullUser.getLastName(),null);
        assertEquals(nullUser.getGender(),null);
        assertEquals(nullUser.getPersonID(),null);

    }

    @Test
    void equals() {
        assertFalse(user1.equals(user2));
        assertTrue(user1.equals(user1));

        user1.setUserName(userName2);
        user1.setPassword(password2);
        user1.setEmail(email2);
        user1.setFirstName(firstName2);
        user1.setLastName(lastName2);
        user1.setGender(gender2);
        user1.setPersonID(personID2);

        assertTrue(user1.equals(user2));
    }

    @Test
    void testHashCode() {
        assertEquals(user1.hashCode(),user1.hashCode());
        assertNotEquals(user1.hashCode(),user2.hashCode());

        user1.setUserName(userName2);
        user1.setPassword(password2);
        user1.setEmail(email2);
        user1.setFirstName(firstName2);
        user1.setLastName(lastName2);
        user1.setGender(gender2);
        user1.setPersonID(personID2);

        assertEquals(user1.hashCode(),user2.hashCode());

    }

    @Test
    void testToString() {
        String string = "User{userName='" + userName1 + "', password='" + password1 + "', email='" + email1 +
                "', firstName='" + firstName1 + "', lastName='" + lastName1 + "', gender='" + gender1 +
                "', personID='" + personID1 + "'}";
        assertEquals(string, user1.toString());
    }
}