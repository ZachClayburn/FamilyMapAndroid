package clayburn.familymap.services;


import clayburn.familymap.ServiceRequests.LoadRequest;
import clayburn.familymap.ServiceResponses.LoadResponse;
import clayburn.familymap.database.Database;

/**
 * LoadService performs the load service
 */
public class LoadService {

    /**
     * Loads the given Persons, Events, and Users into the database
     * @param request A request from the client containing the Persons, Events, and Users to be loaded
     * @return An Load response to be sent to the user stating how many Persons, Events and Users were loaded
     * @throws clayburn.familymap.database.Database.DatabaseException If there is an error loading data
     */
    public LoadResponse load(LoadRequest request) throws Database.DatabaseException {

        Database db = null;
        boolean commit = false;
        try {
            db = new Database();
            db.openConnection();

            db.createAllTables();

            db.getUserDAO().addUsers(request.getUsers());
            db.getEventDAO().addEvents(request.getEvents());
            db.getPersonDAO().addPersons(request.getPersons());
            commit = true;
        }
        finally {
            if(db != null){
                db.closeConnection(commit);
            }
        }

        return new LoadResponse(request.getUsers().length,request.getPersons().length,request.getEvents().length);

    }
}
