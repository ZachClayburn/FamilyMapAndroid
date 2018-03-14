package clayburn.familyMapServer.services;


import clayburn.familymap.ServiceResponses.AllEventResponse;
import clayburn.familyMapServer.database.Database;
import clayburn.familymap.model.Event;
import clayburn.familymap.model.User;

/**
 * AllEventService performs the get all person data service
 */
public class AllEventService {

    /**
     * Fetches and returns all Events stored in the database
     * @param authToken The authToken from the client
     * @return An response containing all of the Events in the database
     * @throws Database.DatabaseException If there was an error retrieving the requests
     */
    public AllEventResponse getAllEvents(String authToken) throws Database.DatabaseException{

        Database db = null;
        boolean commit = false;
        try {
            db = new Database();
            db.openConnection();

            User user = db.getAuthTokenDAO().getUserFromAuthToken(authToken);
            if(user == null) {
                throw new Database.DatabaseException("Bad Authorization Token");
            }

            Event[] events = db.getEventDAO().getAllEvents(user.getUserName());
            commit = true;

            return new AllEventResponse(events);
        }finally {
            if(db != null) {
                db.closeConnection(commit);
            }
        }
    }
}
