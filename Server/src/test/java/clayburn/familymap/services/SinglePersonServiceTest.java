package clayburn.familymap.services;

import clayburn.ServiceResponses.SingleEventResponse;
import clayburn.ServiceResponses.SinglePersonResponse;
import clayburn.familymap.database.Database;
import clayburn.model.AuthToken;
import clayburn.model.Event;
import clayburn.model.Person;
import clayburn.model.User;
import org.junit.jupiter.api.*;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

class SinglePersonServiceTest {

    @BeforeAll
    static void backUp() throws IOException {

        Database.backUp();

    }

    @AfterAll
    static void restore() throws IOException {

        Database.restore();

    }


    @BeforeEach
    void setUp() throws Database.DatabaseException {

        Database db = new Database();
        db.openConnection();
        db.createAllTables();
        db.closeConnection(true);

    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void getPerson() {
        String userName1 = "user1";
        String userName2 = "user2";

        String personID1 = "1";
        String personID2 = "2";
        String personID3 = "3";
        String personID4 = "4";
        String personID5 = "5";

        String token1 = "auth1";
        String token2 = "auth2";
        String token3 = "auth3";

        User user1 = new User(userName1,"","","","","m","");
        User user2 = new User(userName2,"","","","","m","");

        User[] users = {user1,user2};

        Person person1 = new Person(userName1,personID1,"","","m","","","");
        Person person2 = new Person(userName1,personID2,"","","m","","","");
        Person person3 = new Person(userName2,personID3,"","","m","","","");
        Person person4 = new Person(userName2,personID4,"","","m","","","");
        Person person5 = new Person(userName2,personID5,"","","m","","","");

        Person[] persons = {person1,person2,person3,person4,person5};

        AuthToken authToken1 = new AuthToken(token1,userName1);
        AuthToken authToken2 = new AuthToken(token2,userName2);
        AuthToken authToken3 = new AuthToken(token3,userName2);

        try {
            Database db = new Database();
            db.openConnection();
            db.getPersonDAO().addPersons(persons);
            db.getUserDAO().addUsers(users);
            db.getAuthTokenDAO().addAuthToken(authToken1);
            db.getAuthTokenDAO().addAuthToken(authToken2);
            db.getAuthTokenDAO().addAuthToken(authToken3);;
            db.closeConnection(true);

            SinglePersonResponse response = new SinglePersonService().getPerson(personID1,token1);
            assertEquals(person1,response.getPerson());

            response = new SinglePersonService().getPerson(personID3,token2);
            assertEquals(person3,response.getPerson());

            response = new SinglePersonService().getPerson(personID4,token2);
            assertEquals(person4,response.getPerson());

            response = new SinglePersonService().getPerson(personID5,token3);
            assertEquals(person5,response.getPerson());

            assertThrows(Database.DatabaseException.class,()->new SinglePersonService().getPerson(personID5,token1));

        } catch (Database.DatabaseException e) {
            fail(e);
        }
    }
}