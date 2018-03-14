package clayburn.familyMapServer.services;


import clayburn.familymap.ServiceRequests.LoginRequest;
import clayburn.familymap.ServiceResponses.LoginResponse;
import clayburn.familyMapServer.database.AuthTokenDAO;
import clayburn.familyMapServer.database.Database;
import clayburn.familyMapServer.database.UserDAO;
import clayburn.familymap.model.AuthToken;
import clayburn.familymap.model.User;

import java.util.UUID;

/**
 * LoginService performs the login service
 */
public class LoginService {

    /**
     * Logs a user in, providing them with an auth token to be used in login required tasks
     * @param request The request sent from the client
     * @return The LoginResponse that will be returned to the client
     * @throws Database.DatabaseException If there is a database Error
     */
    public LoginResponse login(LoginRequest request)throws Database.DatabaseException {

        Database db = null;
        boolean commit = false;
        AuthToken token;
        User user;
        try {
            db = new Database();
            db.openConnection();
            UserDAO userDAO = db.getUserDAO();
            user = userDAO.getUser(request.getUserName());
            if (user == null || !user.getPassword().equals(request.getPassword())){
                throw new Database.DatabaseException("UserName and Password do not Match");
            }

            AuthTokenDAO authTokenDAO = db.getAuthTokenDAO();
            token = new AuthToken(UUID.randomUUID().toString(),user.getUserName());
            authTokenDAO.addAuthToken(token);

            commit = true;

        }
        finally {
            if (db != null) {
                db.closeConnection(commit);
            }
        }
        return new LoginResponse(token.getAuthToken(),user.getUserName(),user.getPersonID());
    }
}
