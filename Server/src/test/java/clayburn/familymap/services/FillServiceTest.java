package clayburn.familymap.services;

import clayburn.ServiceResponses.FillResponse;
import clayburn.familymap.database.Database;
import clayburn.model.Event;
import clayburn.model.Person;
import clayburn.model.User;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

class FillServiceTest {

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
    void fill(){

        String username = "username";
        String password = "Pa$$W0rD";
        String personID = "PersonID";

        User user = new User(username,password,"email","FN","LN","m",personID);

        try{
            Database db = new Database();

            db.openConnection();
            db.getUserDAO().addUsers(new User[]{user});
            db.closeConnection(true);

            FillResponse response = new FillService().fill(username,4);

            String string = "Successfully added " + 31 + " persons and " + 124 + " events to the database.";
            assertEquals(string,response.getMessage());

            db.openConnection();

            Person[] persons;
            persons = db.getPersonDAO().getAllPersons(username);

            assertEquals(personID, persons[0].getPersonID());
            assertEquals(31, persons.length);
            for(Person person : persons) {
                assertEquals(username, person.getDescendant());
            }

            Event[] events;
            events = db.getEventDAO().getAllEvents(username);
            assertEquals(124, events.length);
            for(Event event : events) {
                assertEquals(username, event.getDescendant());
            }

            db.closeConnection(true);

            response = new FillService().fill(username,0);

            string = "Successfully added " + 1 + " persons and " + 4 + " events to the database.";
            assertEquals(string,response.getMessage());

            db.openConnection();

            persons = db.getPersonDAO().getAllPersons(username);

            assertEquals(personID, persons[0].getPersonID());
            assertEquals(1, persons.length);
            for(Person person : persons) {
                assertEquals(username, person.getDescendant());
            }

            events = db.getEventDAO().getAllEvents(username);
            assertEquals(4, events.length);
            for(Event event : events) {
                assertEquals(username, event.getDescendant());
            }

            db.closeConnection(true);

        }
        catch(Database.DatabaseException e){
            fail(e);
        }
    }
}