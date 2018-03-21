package clayburn.familymap.app.network;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import clayburn.familymap.database.Database;

import static org.junit.Assert.*;

/**
 * Created by zach on 3/17/18.
 */
public class ProxyTest {

    private Proxy proxy = new Proxy("localhost",8080);
    private Database db = new Database();

    @Before
    public void setUp() throws Exception {
        Database.backUp();
        db.openConnection();
        db.createAllTables();
        db.closeConnection(true);
    }

    @After
    public void tearDown() throws Exception {
        Database.restore();
    }

    @Test
    public void login() throws Exception {
        db.openConnection();
//        db.getUserDAO().addUsers();
    }

    @Test
    public void register() throws Exception {
    }

    @Test
    public void getPersons() throws Exception {
    }

    @Test
    public void getEvents() throws Exception {
    }

}