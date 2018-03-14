package clayburn.familyMapServer.services;


import clayburn.familymap.ServiceResponses.SinglePersonResponse;
import clayburn.familyMapServer.database.Database;
import clayburn.familymap.model.Person;
import clayburn.familymap.model.User;

/**
 * SinglePersonService performs the get single person info service
 */
public class SinglePersonService {

    /**
     * Returns a single Person requested by the client
     * @param personID The personID of the Requested Person
     * @param authToken The auth token of the request
     * @return The Person requested by the client
     * @throws Database.DatabaseException If there is an error in relieving
     */
    public SinglePersonResponse getPerson(String personID, String authToken) throws Database.DatabaseException {

        Database db = null;
        boolean commit = false;
        try {
            db = new Database();
            db.openConnection();

            User user = db.getAuthTokenDAO().getUserFromAuthToken(authToken);
            if(user == null) {
                throw new Database.DatabaseException("Bad Authorization Token");
            }

            Person person = db.getPersonDAO().getPerson(personID, user.getUserName());
            if(person == null) {
                throw new Database.DatabaseException("No Such event Exists");
            }

            commit = true;
            return new SinglePersonResponse(person);
        }
        finally {
            if(db != null){
                db.closeConnection(commit);
            }
        }
    }
}
