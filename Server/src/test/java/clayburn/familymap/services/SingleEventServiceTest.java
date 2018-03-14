package clayburn.familymap.services;

import clayburn.familymap.ServiceResponses.SingleEventResponse;
import clayburn.familymap.database.Database;
import clayburn.familymap.model.*;
import org.junit.jupiter.api.*;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

class SingleEventServiceTest {

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
    void getEvent() {
        String userName1 = "user1";
        String userName2 = "user2";

        String eventID1 = "1";
        String eventID2 = "2";
        String eventID3 = "3";
        String eventID4 = "4";
        String eventID5 = "5";

        String token1 = "auth1";
        String token2 = "auth2";
        String token3 = "auth3";

        User user1 = new User(userName1,"","","","","m","");
        User user2 = new User(userName2,"","","","","m","");

        User[] users = {user1,user2};

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

        Event[] events = {event1,event2,event3,event4,event5};

        Event[] user1Events = {event1,event2};
        Event[] user2Events = {event3,event4,event5};

        AuthToken authToken1 = new AuthToken(token1,userName1);
        AuthToken authToken2 = new AuthToken(token2,userName2);
        AuthToken authToken3 = new AuthToken(token3,userName2);

        try {
            Database db = new Database();
            db.openConnection();
            db.getEventDAO().addEvents(events);
            db.getUserDAO().addUsers(users);
            db.getAuthTokenDAO().addAuthToken(authToken1);
            db.getAuthTokenDAO().addAuthToken(authToken2);
            db.getAuthTokenDAO().addAuthToken(authToken3);
            db.closeConnection(true);

            SingleEventResponse response = new SingleEventService().getEvent(eventID1,token1);
            assertEquals(event1,response.getEvent());

            response = new SingleEventService().getEvent(eventID3,token2);
            assertEquals(event3,response.getEvent());

            response = new SingleEventService().getEvent(eventID4,token2);
            assertEquals(event4,response.getEvent());

            response = new SingleEventService().getEvent(eventID5,token3);
            assertEquals(event5,response.getEvent());

            assertThrows(Database.DatabaseException.class,()->new SingleEventService().getEvent(eventID5,token1));

        } catch (Database.DatabaseException e) {
            fail(e);
        }
    }
}