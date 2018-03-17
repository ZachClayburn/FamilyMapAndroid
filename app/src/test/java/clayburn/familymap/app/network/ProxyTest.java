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
    @Before
    public void setUp() throws Exception {
        Database.backUp();
    }

    @After
    public void tearDown() throws Exception {
        Database.restore();
    }

    @Test
    public void login() throws Exception {
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