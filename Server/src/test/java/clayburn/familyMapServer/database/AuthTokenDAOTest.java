package clayburn.familyMapServer.database;

import clayburn.model.AuthToken;
import clayburn.model.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;


class AuthTokenDAOTest {

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
    void addAuthTokens() {
        try {
            AuthTokenDAO dao = db.getAuthTokenDAO();
            AuthToken token1 = new AuthToken("0","zach");
            AuthToken token2 = new AuthToken("1","Meg");
            AuthToken token3 = new AuthToken("2","Ben");


            dao.addAuthToken(token1);
            dao.addAuthToken(token3);
            dao.addAuthToken(token2);

        }catch(Database.DatabaseException e){
            fail(e);
        }


    }

    @Test
    void checkUniqueness() {
        AuthToken  token1 = new AuthToken("0","zach");
        AuthToken  token2 = new AuthToken("0","Meg");
        try {
            AuthTokenDAO dao = db.getAuthTokenDAO();;
            dao.addAuthToken(token1);
            assertThrows(Database.DatabaseException.class,()->dao.addAuthToken(token2),
                    "[SQLITE_CONSTRAINT]  Abort due to constraint violation (column authToken is not unique)");
        }
        catch (Database.DatabaseException e){
            fail(e);
        }//[SQLITE_CONSTRAINT]  Abort due to constraint violation (column authToken is not unique)
    }

    @Test
    void checkAuthToken() {

        User user1 = new User("Zach","password","zachclabyrn@gmail.com","zach",
                        "clayburn","m","0");
        User user2 = new User("Meg","Secret","Germany87@gmail.com","Meg",
                "Clayburn","f","1");
        User[] users = {user1, user2};
        String token1 = "AuthToken1";
        String token2 = "AuthToken2";
        String token3 = "AuthToken3";
        AuthToken authToken1 = new AuthToken(token1,user1.getUserName());
        AuthToken authToken2 = new AuthToken(token2,user2.getUserName());


        try {
            AuthTokenDAO dao = db.getAuthTokenDAO();
            UserDAO userDAO = db.getUserDAO();
            userDAO.addUsers(users);
            dao.addAuthToken(authToken1);
            dao.addAuthToken(authToken2);
            User testUser = dao.getUserFromAuthToken(token1);
            assertEquals(user1,testUser);
            testUser = dao.getUserFromAuthToken(token2);
            assertEquals(user2,testUser);
            testUser = dao.getUserFromAuthToken(token3);
            assertNull(testUser);
        } catch(Database.DatabaseException e){
            fail(e);
        }
    }
}