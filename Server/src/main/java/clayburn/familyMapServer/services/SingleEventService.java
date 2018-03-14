package clayburn.familyMapServer.services;


import clayburn.familymap.ServiceResponses.SingleEventResponse;
import clayburn.familyMapServer.database.Database;
import clayburn.familymap.model.Event;
import clayburn.familymap.model.User;

/**
 * SingleEventService performs the get single person service
 */
public class SingleEventService {

    /**
     * Returns a single Event requested by the client
     * @param eventID The ID of the event requested from the client
     * @param authToken The AuthToken from the request
     * @return The response containing the requested Event
     * @throws Database.DatabaseException If there is an error in retrieving the Event
     */
    public SingleEventResponse getEvent(String eventID, String authToken) throws Database.DatabaseException{

        Database db = null;
        boolean commit = false;
        try {
            db = new Database();
            db.openConnection();

            User user = db.getAuthTokenDAO().getUserFromAuthToken(authToken);
            if(user == null) {
                throw new Database.DatabaseException("Bad Authorization Token");
            }

            Event event = db.getEventDAO().getEvent(eventID,user.getUserName());
            if(event == null) {
                throw new Database.DatabaseException("No Such event Exists");
            }

            commit = true;
            return new SingleEventResponse(event);

        }
        finally {
            if(db != null) {
                db.closeConnection(commit);
            }
        }
    }
}
