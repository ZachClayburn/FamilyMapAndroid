package clayburn.familymap.services;


import clayburn.familymap.ServiceResponses.AllPersonResponse;
import clayburn.familymap.database.Database;
import clayburn.familymap.model.Person;
import clayburn.familymap.model.User;

/**
 * AllPersonService performs the get all people service
 */
public class AllPersonService {

    /**
     * Fetches and returns all Persons stored in the database
     * @return An response containing all the persons in the database
     * @param authToken The auth token from the client sending the request
     * @throws Database.DatabaseException If there was an error retrieving the Persons
     */
    public AllPersonResponse getAllPersons(String authToken) throws Database.DatabaseException {

        Database db = null;
        boolean commit = false;
        try {
            db = new Database();
            db.openConnection();

            User user = db.getAuthTokenDAO().getUserFromAuthToken(authToken);
            if(user == null) {
                throw new Database.DatabaseException("Bad Authorization Token");
            }

            Person[] events = db.getPersonDAO().getAllPersons(user.getUserName());
            commit = true;

            return new AllPersonResponse(events);
        }finally {
            if(db != null) {
                db.closeConnection(commit);
            }
        }
    }
}
