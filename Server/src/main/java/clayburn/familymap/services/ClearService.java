package clayburn.familymap.services;


import clayburn.familymap.ServiceResponses.ClearResponse;
import clayburn.familymap.database.*;

/**
 * ClearService performs the clear service
 */
public class ClearService {

    /**
     * Clears the database
     * @throws Database.DatabaseException When there was an error creating the tables
     */
    public ClearResponse clear() throws Database.DatabaseException {

        Database db = null;
        boolean commit = false;
        try {
            db = new Database();
            db.openConnection();
            db.createAllTables();
            commit = true;
        }
        finally {
            if (db != null) {
                db.closeConnection(commit);
            }
        }
        return new ClearResponse("Clear Successful!");
    }
}
