package clayburn.familymap.database;

import clayburn.familymap.model.User;

import java.sql.*;

/**
 * UserDAO provides all database services for the User table
 * @author Zach Clayburn
 */
public class UserDAO extends Database.DataAccessObject {

    public UserDAO(Connection connection) {
        super(connection);
    }

    /**
     * Attempts to add the given Users to the database
     * @param users The Users to be added to the database
     * @throws Database.DatabaseException If there is an error getting the Users
     */
    public void addUsers(User[] users) throws Database.DatabaseException {

        final String update = "INSERT INTO users (username, password, email, firstName, lastName, gender, personID)" +
                "VALUES (?,?,?,?,?,?,?);";

        try(PreparedStatement statement = connection.prepareStatement(update)) {
            for(User u : users) {
                statement.setString(1, u.getUserName());
                statement.setString(2, u.getPassword());
                statement.setString(3, u.getEmail());
                statement.setString(4, u.getFirstName());
                statement.setString(5, u.getLastName());
                statement.setString(6, u.getGender());
                statement.setString(7, u.getPersonID());

                statement.executeUpdate();
            }
        }
        catch (SQLException e){
            throw new Database.DatabaseException(e.getMessage());
        }
    }

    public boolean userNameAvailable(String userName) throws Database.DatabaseException {

        final String query = "SELECT count(*) FROM users WHERE (userName = ?);";

        try(PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, userName);
            ResultSet resultSet = statement.executeQuery();

            int count = -1;
            if (resultSet.next()) {
                count = resultSet.getInt(1);
            }

            return count == 0;
        }
        catch (SQLException e){
            throw new Database.DatabaseException(e.getMessage());
        }
    }

    /**
     * Attempts to retrieve the user with the given user name
     * @param userName The user name of the User to be retrieved
     * @return The User with the given user name, if one exists, null otherwise
     * @throws Database.DatabaseException If there is an error getting the User
     */
    public User getUser(String userName) throws Database.DatabaseException {

        User user = null;
        final String statementString = "SELECT * FROM users WHERE username = ?";

        try (PreparedStatement statement = connection.prepareStatement(statementString)){
            statement.setString(1,userName);
            ResultSet set = statement.executeQuery();

            if(set.next()){
                user = User.newEmptyUser();
                user.setUserName(set.getString("username"));
                user.setPassword(set.getString("password"));
                user.setEmail(set.getString("email"));
                user.setFirstName(set.getString("firstName"));
                user.setLastName(set.getString("lastName"));
                user.setGender(set.getString("gender"));
                user.setPersonID(set.getString("personID"));
            }
        }
        catch (SQLException e){
            throw new Database.DatabaseException("Could not get User: " + userName,e);
        }


        return user;
    }

    protected void createTableHelper(Statement statement) throws SQLException {
        statement.executeUpdate("DROP TABLE IF EXISTS users;");
        statement.executeUpdate("CREATE TABLE users (" +
                "username TEXT PRIMARY KEY, " +
                "password TEXT, " +
                "email TEXT, " +
                "firstName TEXT," +
                "lastName TEXT, " +
                "gender TEXT, " +
                "personID TEXT, " +
                "CONSTRAINT genderCheck CHECK (gender = 'm' OR gender = 'f' )" +
                ");"
        );
    }
}
