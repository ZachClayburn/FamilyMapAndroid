package clayburn.familymap.database;

import clayburn.familymap.model.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class UserDAOTest {

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
    void addUser() {

        User[] users = {
                new User("bob","password","email@e.mail","bob","bobson",
                        "m","1"),
                new User("asdfl;kj","fjsl","g@m.e","sally","jane","f","2")
        };
        assertAll(()->db.getUserDAO().addUsers(users));
    }

    @Test
    void userNameAvailable() {
        try {
            UserDAO dao = db.getUserDAO();

            User[] users = {
                    new User("Zach", "password", "zachClayburn@gmail.com", "Zach",
                            "Clayburn", "M", "1"),
                    new User("Mabbot", "MahHubbyZach", "Germany87@gmail.com", "Meghan",
                            "Clayburn", "f", "2")
            };
            //assert dao.addUsers(users);
            dao.addUsers(users);

            assertTrue(dao.userNameAvailable("Jerry"));
            assertTrue(dao.userNameAvailable("zach"));
            assertFalse(dao.userNameAvailable("Zach"));
            assertFalse(dao.userNameAvailable("Mabbot"));

        }
        catch (Database.DatabaseException e){
            fail(e);
        }
    }

    @Test
    void getUser() {
        String userName1 = "NOMHusker";
        String userName2 = "girl";
        User user1 = new User(userName1,"NEBYU","zachclayburn@gmail.com",
                "Zach","Clayburn","m","1");
        User user2 = new User(userName2,"<3","g@i.rl","Jane","doe",
                "f","2");
        try {
            UserDAO dao = db.getUserDAO();
            dao.addUsers(new User[]{user1, user2});

            User testUser;
            testUser = dao.getUser(userName1);
            assertEquals(user1, testUser);
            assertNotEquals(user2, testUser);
            testUser = dao.getUser(userName2);
            assertEquals(user2, testUser);
            assertNotEquals(user1, testUser);
            assertNull(dao.getUser("not a valid username"));
        }
        catch (Database.DatabaseException e){
            fail(e);
        }
    }
}