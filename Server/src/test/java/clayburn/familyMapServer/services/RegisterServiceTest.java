package clayburn.familyMapServer.services;

import clayburn.ServiceRequests.LoginRequest;
import clayburn.ServiceRequests.RegisterRequest;
import clayburn.ServiceResponses.LoginResponse;
import clayburn.ServiceResponses.RegisterResponse;
import clayburn.familyMapServer.database.Database;
import clayburn.model.Event;
import clayburn.model.Person;
import clayburn.model.User;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

class RegisterServiceTest {

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
    void register() {

        String username = "username";
        String password = "Pa$$W0rD";

        User user = new User(username,password,"email","FN","LN","m",null);


        try {

            RegisterRequest request = new RegisterRequest(username,password,user.getEmail(),user.getFirstName(),
                    user.getLastName(),user.getGender());

            RegisterResponse response = new RegisterService().register(request);

            user.setPersonID(response.getPersonID());

            assertEquals(username,response.getUserName());

            Database db = new Database();
            db.openConnection();

            User testUser = db.getAuthTokenDAO().getUserFromAuthToken(response.getAuthToken());
            assertEquals(testUser,user);

            Person[] persons = db.getPersonDAO().getAllPersons(username);
            assertEquals(31,persons.length);

            Event[] events = db.getEventDAO().getAllEvents(username);
            assertEquals(124,events.length);

            db.closeConnection(true);


        } catch (Database.DatabaseException e) {
            fail(e);
        }
    }
}