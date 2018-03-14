package clayburn.familymap.services;

import clayburn.ServiceResponses.ClearResponse;
import clayburn.familymap.database.Database;
import clayburn.model.AuthToken;
import clayburn.model.Event;
import clayburn.model.Person;
import clayburn.model.User;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

class ClearServiceTest {

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

    @Test
    void clear(){

        String userName1 = "user1";
        String userName2 = "user2";

        String token1 = "auth1";
        String token2 = "auth2";
        String token3 = "auth3";

        String personID1 = "1";
        String personID2 = "2";
        String personID3 = "3";
        String personID4 = "4";
        String personID5 = "5";

        String eventID1 = "1";
        String eventID2 = "2";
        String eventID3 = "3";
        String eventID4 = "4";
        String eventID5 = "5";

        User user1 = new User(userName1,"","","","","m","");
        User user2 = new User(userName2,"","","","","m","");

        User[] users = {user1,user2};

        Person person1 = new Person(userName1,personID1,"","","m","","","");
        Person person2 = new Person(userName1,personID2,"","","m","","","");
        Person person3 = new Person(userName2,personID3,"","","m","","","");
        Person person4 = new Person(userName2,personID4,"","","m","","","");
        Person person5 = new Person(userName2,personID5,"","","m","","","");

        Event event1 = new Event(eventID1,userName1,"",0,0,"","",""
                ,"1");
        Event event2 = new Event(eventID2,userName1,"",0,0,"","",""
                ,"1");
        Event event3 = new Event(eventID3,userName2,"",0,0,"","",""
                ,"1");
        Event event4 = new Event(eventID4,userName2,"",0,0,"","",""
                ,"1");
        Event event5 = new Event(eventID5,userName2,"",0,0,"","",""
                ,"1");


        Person[] persons = {person1,person2,person3,person4,person5};

        Person[] user1Persons = {person1,person2};
        Person[] user2Persons = {person3,person4,person5};

        Event[] events = {event1,event2,event3,event4,event5};

        Event[] user1Events = {event1,event2};
        Event[] user2Events = {event3,event4,event5};

        AuthToken authToken1 = new AuthToken(token1,userName1);
        AuthToken authToken2 = new AuthToken(token2,userName2);
        AuthToken authToken3 = new AuthToken(token3,userName2);

        try {
            Database db = new Database();

            db.openConnection();

            db.getUserDAO().addUsers(users);
            db.getPersonDAO().addPersons(persons);
            db.getEventDAO().addEvents(events);
            db.getAuthTokenDAO().addAuthToken(authToken1);
            db.getAuthTokenDAO().addAuthToken(authToken2);
            db.getAuthTokenDAO().addAuthToken(authToken3);

            db.closeConnection(true);

            ClearResponse response = new ClearService().clear();
            assertEquals("Clear Successful!",response.getMessage());

            db.openConnection();

            User testUser;
            testUser = db.getAuthTokenDAO().getUserFromAuthToken(token1);
            assertNull(testUser);

            testUser = db.getAuthTokenDAO().getUserFromAuthToken(token2);
            assertNull(testUser);

            testUser = db.getAuthTokenDAO().getUserFromAuthToken(token3);
            assertNull(testUser);

            testUser = db.getUserDAO().getUser(userName1);
            assertNull(testUser);

            testUser = db.getUserDAO().getUser(userName2);
            assertNull(testUser);

            Event[] testEvents;
            testEvents = db.getEventDAO().getAllEvents(userName1);
            assertEquals(0,testEvents.length);

            testEvents = db.getEventDAO().getAllEvents(userName2);
            assertEquals(0,testEvents.length);

            Person[] testPersons;
            testPersons = db.getPersonDAO().getAllPersons(userName1);
            assertEquals(0,testPersons.length);

            testPersons = db.getPersonDAO().getAllPersons(userName2);
            assertEquals(0,testPersons.length);

            db.closeConnection(true);

        } catch(Database.DatabaseException e) {
            fail(e);
        }
    }

}