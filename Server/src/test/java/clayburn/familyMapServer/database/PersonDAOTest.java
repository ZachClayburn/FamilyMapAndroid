package clayburn.familyMapServer.database;

import clayburn.model.Person;
import clayburn.model.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PersonDAOTest {

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
    void addPersons() {

        final String personID1 = "0";
        final String personID2 = "1";
        final String personID3 = "2";
        final String personID4 = "3";

        final String descendantID1 = "0";
        final String descendantID2 = "1";

        Person person1 = new Person(descendantID1,personID1,"John","Doe","M",
                personID3,personID4,personID2);
        Person person2 = new Person(descendantID1,personID2,"Jane","Doe","F",
                "","",personID1);
        Person person3 = new Person(descendantID2,personID3,"James","Doe","M",
                null,null,null);

        Person[] persons = {
                person1,
                person2,
                person3
        };

        Person[] duplicate = {person1};

        try {
            db.getPersonDAO().addPersons(persons);
            assertThrows(Database.DatabaseException.class,()->db.getPersonDAO().addPersons(duplicate),
                    "[SQLITE_CONSTRAINT]  Abort due to constraint violation (column personID is not unique)");
        } catch (Database.DatabaseException e) {
            fail(e);
        }

    }

    @Test
    void getPerson() {
        final String personID1 = "0";
        final String personID2 = "1";
        final String personID3 = "2";
        final String personID4 = "3";

        final String descendantID1 = "0";
        final String descendantID2 = "1";

        Person person1 = new Person(descendantID1,personID1,"John","Doe","M",
                personID3,personID4,personID2);
        Person person2 = new Person(descendantID1,personID2,"Jane","Doe","F",
                "","",personID1);
        Person person3 = new Person(descendantID2,personID3,"James","Doe","M",
                null,null,null);

        Person[] persons = {
                person1,
                person2,
                person3
        };

        try{
            db.getPersonDAO().addPersons(persons);
            Person testPerson;

            testPerson = db.getPersonDAO().getPerson(personID1,descendantID1);
            assertEquals(testPerson,person1);

            testPerson = db.getPersonDAO().getPerson(personID2,descendantID1);
            assertEquals(testPerson,person2);

            testPerson = db.getPersonDAO().getPerson(personID3,descendantID1);
            assertNull(testPerson);

            testPerson = db.getPersonDAO().getPerson(personID3,descendantID2);
            assertEquals(testPerson,person3);

            testPerson = db.getPersonDAO().getPerson(personID4,descendantID1);
            assertNull(testPerson);

        } catch (Database.DatabaseException e) {
            fail(e);
        }

    }

    @Test
    void getAllPersons() {
        final String personID1 = "0";
        final String personID2 = "1";
        final String personID3 = "2";
        final String personID4 = "3";

        final String descendantID1 = "user0";
        final String descendantID2 = "user1";

        Person person1 = new Person(descendantID1,personID1,"John","Doe","M",
                personID3,personID4,personID2);
        Person person2 = new Person(descendantID1,personID2,"Jane","Doe","F",
                "","",personID1);
        Person person3 = new Person(descendantID2,personID3,"James","Doe","M",
                null,null,null);

        Person[] persons = {
                person1,
                person2,
                person3
        };

        Person[] descendant1Persons = {
                person1,
                person2
        };

        try {
            db.getPersonDAO().addPersons(persons);
            Person[] testPersons = db.getPersonDAO().getAllPersons(descendantID1);
            assertArrayEquals(descendant1Persons,testPersons);
        }
        catch (Database.DatabaseException e){
            fail(e);
        }

    }

    @Test
    void removeAllFromUser() {
        final String personID1 = "0";
        final String personID2 = "1";
        final String personID3 = "2";
        final String personID4 = "3";

        final String descendantID1 = "user0";
        final String descendantID2 = "user1";

        Person person1 = new Person(descendantID1,personID1,"John","Doe","M",
                personID3,personID4,personID2);
        Person person2 = new Person(descendantID1,personID2,"Jane","Doe","F",
                "","",personID1);
        Person person3 = new Person(descendantID2,personID3,"James","Doe","M",
                null,null,null);

        Person[] persons = {
                person1,
                person2,
                person3
        };

        Person[] descendant1Persons = {
                person1,
                person2
        };

        try {
            db.getPersonDAO().addPersons(persons);

            db.getPersonDAO().removeAllFromUser(descendantID1);

            Person[] testPersons;
            testPersons = db.getPersonDAO().getAllPersons(descendantID1);

            assertEquals(testPersons.length,0);
        } catch (Database.DatabaseException e) {
            fail(e);
        }


    }
}