package clayburn.familyMapServer.database;

import clayburn.familymap.model.AuthToken;
import clayburn.familymap.model.User;

import java.sql.*;

/**
 * AuthTokenDAO provides all database services for the AuthToken table
 * @author Zach Clayburn
 */
public class AuthTokenDAO extends Database.DataAccessObject {

    public AuthTokenDAO(Connection connection) {
        super(connection);
    }

    /**
     * Attempts to add an AuthToken to the database
     * @param authToken The AuthToken to be added
     * @throws Database.DatabaseException If there is an error in the database
     */
    public void addAuthToken(AuthToken authToken) throws Database.DatabaseException {

        final String statementString = "INSERT INTO authTokens (authToken, userName) VALUES (?,?)";

        try (PreparedStatement statement = connection.prepareStatement(statementString)){
            statement.setString(1,authToken.getAuthToken());
            statement.setString(2,authToken.getUserName());

            statement.execute();
        }
        catch(SQLException e){
            throw new Database.DatabaseException("An error occurred while adding authTokens",e);
        }

    }

    /**
     * Checks if a User is associated with the given token
     * @param token An auth token string that is sent from the client to the user
     * @return The User linked to the given auth token, or null if there is none
     * @throws Database.DatabaseException If an error occurs in the process
     */
    public User getUserFromAuthToken(String token) throws Database.DatabaseException {

        User user = null;
        final String query = "SELECT users.* " +
                "FROM users, authTokens " +
                "WHERE authTokens.authToken = ? AND authTokens.userName = users.username;";

        try(PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1,token);

            ResultSet r = statement.executeQuery();

            if (r.next()){
                user = User.newEmptyUser();
                user.setUserName(r.getString("userName"));
                user.setPassword(r.getString("password"));
                user.setEmail(r.getString("email"));
                user.setFirstName(r.getString("firstName"));
                user.setLastName(r.getString("lastName"));
                user.setGender(r.getString("gender"));
                user.setPersonID(r.getString("personID"));
            }
            return user;
        }
        catch (SQLException e){
            throw new Database.DatabaseException("An error occurred in CheckAuthToken",e);
        }


    }

    protected void createTableHelper(Statement statement) throws SQLException {
        statement.executeUpdate("DROP TABLE IF EXISTS authTokens;");
        statement.executeUpdate("CREATE TABLE authTokens(" +
                "authToken TEXT PRIMARY KEY, " +
                "userName TEXT" +
                ");"
        );
    }
}
