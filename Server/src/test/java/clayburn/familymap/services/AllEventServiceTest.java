package clayburn.familymap.services;

import clayburn.familymap.ServiceResponses.AllEventResponse;
import clayburn.familymap.database.Database;
import clayburn.familymap.model.AuthToken;
import clayburn.familymap.model.Event;
import clayburn.familymap.model.User;
import org.junit.jupiter.api.*;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.fail;

class AllEventServiceTest {

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
    void getAllEvents() {
        String userName1 = "user1";
        String userName2 = "user2";

        String token1 = "auth1";
        String token2 = "auth2";
        String token3 = "auth3";

        User user1 = new User(userName1,"","","","","m","");
        User user2 = new User(userName2,"","","","","m","");

        User[] users = {user1,user2};

        Event event1 = new Event("0",userName1,"",0,0,"","",""
                ,"1");
        Event event2 = new Event("1",userName1,"",0,0,"","",""
                ,"1");
        Event event3 = new Event("2",userName2,"",0,0,"","",""
                ,"1");
        Event event4 = new Event("3",userName2,"",0,0,"","",""
                ,"1");
        Event event5 = new Event("4",userName2,"",0,0,"","",""
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

            AllEventResponse response = new AllEventService().getAllEvents(token1);
            assertArrayEquals(user1Events,response.getData());

            response = new AllEventService().getAllEvents(token2);
            assertArrayEquals(user2Events,response.getData());

            response = new AllEventService().getAllEvents(token3);
            assertArrayEquals(user2Events,response.getData());

        } catch (Database.DatabaseException e) {
            fail(e);
        }
    }

}