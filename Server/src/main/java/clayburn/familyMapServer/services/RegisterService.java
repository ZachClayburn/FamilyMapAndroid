package clayburn.familyMapServer.services;

import clayburn.familymap.ServiceRequests.RegisterRequest;
import clayburn.familymap.ServiceResponses.RegisterResponse;
import clayburn.familyMapServer.database.*;
import clayburn.familymap.model.AuthToken;
import clayburn.familymap.model.Event;
import clayburn.familymap.model.Person;
import clayburn.familymap.model.User;

import java.util.UUID;

/**
 * RegisterService performs the registration service
 */
public class RegisterService {

    /**
     * Registers a new user into the server
     * @param request The request from the client
     * @return The response to the register request
     * @throws Database.DatabaseException If there is an error registering the user in the database
     */
    public RegisterResponse register(RegisterRequest request) throws Database.DatabaseException {

        Database db = null;
        boolean commit = false;
        RegisterResponse response = null;
        try {
            db = new Database();
            db.openConnection();
            UserDAO userDAO = db.getUserDAO();

            User user = request.getUser();

            if (!userDAO.userNameAvailable(user.getUserName())) {
                throw new Database.DatabaseException("Username already Taken!");
            }


            String token = UUID.randomUUID().toString();
            AuthToken authToken = new AuthToken(token,user.getUserName());
            db.getAuthTokenDAO().addAuthToken(authToken);

            user.setPersonID(UUID.randomUUID().toString());

            Person[] persons = FillService.generatePersons(user,4);
            Event[] events = FillService.generateEvents(persons);

            userDAO.addUsers(new User[] {user});

            db.getPersonDAO().addPersons(persons);
            db.getEventDAO().addEvents(events);

            response = new RegisterResponse(token,user.getUserName(),user.getPersonID());

            commit = true;

        }
        finally {
            if (db != null){
                db.closeConnection(commit);
            }
        }
        return response;
    }
}
