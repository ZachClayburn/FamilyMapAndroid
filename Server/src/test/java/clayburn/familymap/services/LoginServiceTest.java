package clayburn.familymap.services;

import clayburn.ServiceRequests.LoadRequest;
import clayburn.ServiceRequests.LoginRequest;
import clayburn.ServiceResponses.LoginResponse;
import clayburn.familymap.database.Database;
import clayburn.model.User;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

class LoginServiceTest {

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
    void login() {

        String username = "username";
        String password = "Pa$$W0rD";
        String personID = "PersonID";

        User user = new User(username,password,"","","","m",personID);

        User[] users = {user};

        try {
            Database db = new Database();
            db.openConnection();
            db.getUserDAO().addUsers(users);
            db.closeConnection(true);

            LoginRequest request = new LoginRequest(username,password);

            LoginResponse response = new LoginService().login(request);

            assertEquals(username,response.getUserName());
            assertEquals(personID,response.getPersonID());

            db.openConnection();
            User testUser = db.getAuthTokenDAO().getUserFromAuthToken(response.getAuthToken());
            db.closeConnection(true);
            assertEquals(testUser,user);

            LoginRequest badRequest1 = new LoginRequest(".",".");
            assertThrows(Database.DatabaseException.class,()->new LoginService().login(badRequest1));

            LoginRequest badRequest2 = new LoginRequest(username,".");
            assertThrows(Database.DatabaseException.class,()->new LoginService().login(badRequest2));

            LoginRequest badRequest3 = new LoginRequest("",password);
            assertThrows(Database.DatabaseException.class,()->new LoginService().login(badRequest3));

        } catch (Database.DatabaseException e) {
            fail(e);
        }
    }
}