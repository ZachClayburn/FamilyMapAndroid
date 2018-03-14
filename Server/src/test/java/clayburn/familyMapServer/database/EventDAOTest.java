package clayburn.familyMapServer.database;

import clayburn.model.Event;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class EventDAOTest {

    private Database db = new Database();

    @BeforeEach
    void setUp() throws Database.DatabaseException {
        db.openConnection();
        db.createAllTables();
    }

    @AfterEach
    void tearDown() throws Database.DatabaseException {
        db.closeConnection(false);
    }

    @Test
    void addEvents() {
        final String eventID1 = "0";
        final String eventID2 = "1";

        Event event1 = new Event(eventID1,"0","0",12.5,-7.6,"USA",
                "Layton","Death","1989");
        Event event2 = new Event(eventID2,"1","1",13.5,-8.6,"Canada"
                ,"ColdPlace","Birth","2016");

        Event[] events = {
                event1,
                event2
        };

        Event[] duplicates = {
                event1
        };

        try {
            db.getEventDAO().addEvents(events);
            assertThrows(Database.DatabaseException.class,()->db.getEventDAO().addEvents(duplicates),
                    "[SQLITE_CONSTRAINT]  Abort due to constraint violation (column eventID is not unique)");
        } catch (Database.DatabaseException e) {
            fail(e);
        }
    }

    @Test
    void getEvent() {
        final String eventID1 = "0";
        final String eventID2 = "1";
        final String eventID3 = "2";
        final String eventID4 = "3";

        final String userName1 = "0";
        final String userName2 = "1";

        Event event1 = new Event(eventID1,userName1,"0",12.5,-7.6,"USA",
                "Layton","Death","1989");
        Event event2 = new Event(eventID2,userName1,"1",13.5,-8.6,"Canada"
                ,"ColdPlace","Birth","2016");
        Event event3 = new Event(eventID3,userName2,"1",11.5,-6.6,"Singapore"
                ,"Singapore","Became rich","2020");


        Event[] events = {
                event1,
                event2,
                event3
        };

        try {
            db.getEventDAO().addEvents(events);
            Event testEvent;

            testEvent = db.getEventDAO().getEvent(eventID1,userName1);
            assertEquals(testEvent,event1);

            testEvent = db.getEventDAO().getEvent(eventID2,userName1);
            assertEquals(testEvent,event2);

            testEvent = db.getEventDAO().getEvent(eventID3,userName1);
            assertNull(testEvent);

            testEvent = db.getEventDAO().getEvent(eventID3,userName2);
            assertEquals(testEvent,event3);

            testEvent = db.getEventDAO().getEvent(eventID3,userName1);
            assertNull(testEvent);

        } catch (Database.DatabaseException e) {
            fail(e);
        }
    }

    @Test
    void getAllEvents() {
        final String eventID1 = "0";
        final String eventID2 = "1";
        final String eventID3 = "2";
        final String eventID4 = "3";

        final String userName1 = "0";
        final String userName2 = "1";

        Event event1 = new Event(eventID1,userName1,"0",12.5,-7.6,"USA",
                "Layton","Death","1989");
        Event event2 = new Event(eventID2,userName1,"1",13.5,-8.6,"Canada"
                ,"ColdPlace","Birth","2016");
        Event event3 = new Event(eventID3,userName2,"1",11.5,-6.6,"Singapore"
                ,"Singapore","Became rich","2020");


        Event[] events = {
                event1,
                event2,
                event3
        };

        Event[] user1Events = {
                event1,
                event2
        };

        try {
            db.getEventDAO().addEvents(events);
            Event[] testEvents;

            testEvents = db.getEventDAO().getAllEvents(userName1);
            assertArrayEquals(testEvents, user1Events);

        } catch (Database.DatabaseException e) {
            fail(e);
        }
    }

    @Test
    void removeAllFromUser(){
        final String eventID1 = "0";
        final String eventID2 = "1";
        final String eventID3 = "2";
        final String eventID4 = "3";

        final String userName1 = "user0";
        final String userName2 = "user1";

        Event event1 = new Event(eventID1,userName1,"0",12.5,-7.6,"USA",
                "Layton","Death","1989");
        Event event2 = new Event(eventID2,userName1,"1",13.5,-8.6,"Canada"
                ,"ColdPlace","Birth","2016");
        Event event3 = new Event(eventID3,userName2,"1",11.5,-6.6,"Singapore"
                ,"Singapore","Became rich","2020");


        Event[] events = {
                event1,
                event2,
                event3
        };

        Event[] user2Events = {
                event3
        };

        try {
            db.getEventDAO().addEvents(events);

            db.getEventDAO().removeAllFromUser(userName1);

            Event[] testEvents;
            testEvents = db.getEventDAO().getAllEvents(userName1);
            assertEquals(testEvents.length,0);

            testEvents = db.getEventDAO().getAllEvents(userName2);
            assertArrayEquals(testEvents,user2Events);

        }catch (Database.DatabaseException e){
            fail(e);
        }
    }
}