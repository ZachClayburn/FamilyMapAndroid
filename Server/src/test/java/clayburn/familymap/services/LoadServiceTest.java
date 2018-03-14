package clayburn.familymap.services;

import clayburn.ServiceRequests.LoadRequest;
import clayburn.ServiceResponses.LoadResponse;
import clayburn.familymap.database.Database;
import clayburn.model.Event;
import clayburn.model.Person;
import clayburn.model.User;
import org.junit.jupiter.api.*;

import java.io.IOException;

class LoadServiceTest {

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
    void load() {

        String userName1 = "userName1";
        String userName2 = "userName2";

        String personID1 = "Person1";
        String personID2 = "Person2";
        String personID3 = "Person3";
        String personID4 = "Person4";
        String personID5 = "Person5";
        String personID6 = "Person6";

        String fName1 = "fName1";
        String lName1 = "lName1";
        String fName2 = "fName2";
        String lName2 = "lName2";

        User user1 = new User(userName1,"password","em@i.l",fName1,lName1,"m", personID1);
        User user2 = new User(userName2,"password","em@i.l",fName2,lName2,"f", personID2);

        Person person1 = new Person(userName1,personID1,fName1,lName1,"m",personID3,personID4,null);
        Person person2 = new Person(userName2,personID2,fName2,lName2,"f",personID5,personID6,null);
        Person person3 = new Person(userName1,personID3,"fName","lName","m",
                null,null,personID4);
        Person person4 = new Person(userName1,personID4,"fName","lName","f",
                null,null,personID3);
        Person person5 = new Person(userName2,personID5,"fName","lName","m",
                null,null,personID6);
        Person person6 = new Person(userName2,personID6,"fName","lName","f",
                null,null,personID5);

        Event event1 = new Event("0",userName1,personID1,1,1,"country","city",
                "birth","1");
        Event event2 = new Event("1",userName2,personID2,2,2,"country","city",
                "birth","2");
        Event event3 = new Event("2",userName1,personID3,3,3,"country","city",
                "birth","3");
        Event event4 = new Event("3",userName1,personID4,4,4,"country","city",
                "birth","4");
        Event event5 = new Event("4",userName2,personID5,5,5,"country","city",
                "birth","5");
        Event event6 = new Event("5",userName2,personID6,6,6,"country","city",
                "birth","6");

        User[] users = {user1,user2};
        Person[] persons = {person1,person2,person3,person4,person5,person6};
        Event[] events = {event1,event2,event3,event4,event5,event6};

        LoadRequest request = new LoadRequest(users,persons,events);

        try {
            LoadResponse response = new LoadService().load(request);

            String string = "Successfully added " + users.length + " users, " + persons.length +
                    " persons, and " + events.length + " events to the database.";

            assertEquals(response.getMessage(),string);

            Database db = new Database();
            db.openConnection();
            assertEquals(db.getUserDAO().getUser(userName1),user1);

            Person[] user1Persons = {person1,person3,person4};
            assertArrayEquals(db.getPersonDAO().getAllPersons(userName1),user1Persons);

            Person[] user2Persons = {person2,person5,person6};
            assertArrayEquals(db.getPersonDAO().getAllPersons(userName2),user2Persons);

            Event[] user1Events = {event1,event3,event4};
            assertArrayEquals(db.getEventDAO().getAllEvents(userName1),user1Events);

            Event[] user2events = {event2,event5,event6};
            assertArrayEquals(db.getEventDAO().getAllEvents(userName2),user2events);

            db.closeConnection(true);
        } catch (Database.DatabaseException e) {
            fail(e);
        }
    }
}