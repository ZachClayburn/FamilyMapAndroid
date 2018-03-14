package clayburn.familymap.database;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.security.PrivilegedActionException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class Database {

    static {
        try {
            final String driver = "org.sqlite.JDBC";
            Class.forName(driver);
        }
        catch(ClassNotFoundException e){
            e.printStackTrace();
        }
    }

    /**
     * Opens a connection to the database and begins a transaction
     * @throws DatabaseException When there is an error opening the connection
     */
    public void openConnection() throws DatabaseException {

        try {
            final String CONNECTION_URL = "jdbc:sqlite:Server/src/main/java/clayburn/familymap/familyMap.sqlite";
            connection = DriverManager.getConnection(CONNECTION_URL);

            connection.setAutoCommit(false);
            userDAO = new UserDAO(connection);
            authTokenDAO = new AuthTokenDAO(connection);
            personDAO = new PersonDAO(connection);
            eventDAO = new EventDAO(connection);
        }
        catch (SQLException e){
            throw new DatabaseException("Connection could not be opened",e);
        }
    }

    /**
     * Closes the connection to the database and commits or rolls back the transaction. Sets the connection
     * to null when it is closed.
     * @param commit If true, transaction is committed, if false, transaction is rolled back.
     * @throws DatabaseException If there was an error closing the connection
     */
    public void closeConnection(boolean commit) throws DatabaseException {

        if (connection == null) {
            return;
        }

        try{
            if (commit){
                connection.commit();
            }
            else{
                connection.rollback();
            }

            connection.close();
            connection = null;

            userDAO.connection = null;
            authTokenDAO.connection = null;
            personDAO.connection = null;
            eventDAO.connection = null;
        }
        catch (SQLException e){
            throw new DatabaseException("Connection could not be closed",e);
        }
    }

    /**
     * Drops all tables, if the exist, and creates new, empty ones;
     * @throws DatabaseException If there was an error creating the tables
     */
    public void createAllTables() throws DatabaseException{
        userDAO.createTable();
        authTokenDAO.createTable();
        personDAO.createTable();
        eventDAO.createTable();
    }

    /**
     * The Connection to the database used by the DAOs
     */
    private Connection connection = null;

    private UserDAO userDAO = null;
    private AuthTokenDAO authTokenDAO = null;
    private PersonDAO personDAO = null;
    private EventDAO eventDAO = null;

    public UserDAO getUserDAO() throws DatabaseException {
        if (userDAO == null){
            throw new DatabaseException("Database connection not opened");
        }
        return userDAO;
    }

    public AuthTokenDAO getAuthTokenDAO() throws DatabaseException {
        if (userDAO == null){
            throw new DatabaseException("Database connection not opened");
        }
        return authTokenDAO;
    }

    public PersonDAO getPersonDAO() throws DatabaseException {
        if (userDAO == null){
            throw new DatabaseException("Database connection not opened");
        }
        return personDAO;
    }

    public EventDAO getEventDAO() throws DatabaseException {
        if (userDAO == null){
            throw new DatabaseException("Database connection not opened");
        }
        return eventDAO;
    }


    /**
     * DataAccessObject is the abstract parent of all of the DAO objects
     * in the Family Map Server. It implements the basic sqlite functionality
     * like connecting and committing transactions
     * @author Zach Clayburn
     */
    abstract static class DataAccessObject {

        /**
         * A connection to the database;
         */
        protected Connection connection;

        public DataAccessObject(Connection connection){
            this.connection = connection;
        }

        /**
         * Deletes the table, if one exists, and creates a new, empty one
         * @throws DatabaseException if an error has occurred in the process
         */
        public void createTable() throws DatabaseException {


            try(Statement statement = connection.createStatement()) {
                createTableHelper(statement);
            }
            catch (SQLException e){
                throw new DatabaseException("Table Creation Failed",e);
            }
        }

        protected abstract void createTableHelper(Statement statement) throws SQLException;


    }

    static private final String mainPath = "Server/src/clayburn/familymap/familyMap.sqlite";
    static private final String backUpPath = "Server/src/clayburn/familymap/familyMapBackup.sqlite";

    public static void backUp() throws IOException {
        Files.copy(
                Paths.get(mainPath),
                Paths.get(backUpPath),
                StandardCopyOption.REPLACE_EXISTING,
                StandardCopyOption.COPY_ATTRIBUTES
        );
    }

    public static void restore() throws IOException {
        Files.copy(
                Paths.get(backUpPath),
                Paths.get(mainPath),
                StandardCopyOption.REPLACE_EXISTING,
                StandardCopyOption.COPY_ATTRIBUTES
        );

    }


    /**
     * An exception thrown by all DAOs when a database error occurs
     */
    public static class DatabaseException extends Exception{

        /**
         * Creates a DatabaseException caused by another Exception
         * @param message A description of the error
         * @param throwable The causing Exception
         */
        public DatabaseException(String message, Throwable throwable) {
            super(message, throwable);
        }

        /**
         * Creates a DatabaseException
         * @param message A description of the error
         */
        public DatabaseException(String message){
            super(message);
        }

        /**
         * Constructs a new exception with the specified cause and a detail
         * message of {@code (cause==null ? null : cause.toString())} (which
         * typically contains the class and detail message of {@code cause}).
         * This constructor is useful for exceptions that are little more than
         * wrappers for other throwables (for example, {@link
         * PrivilegedActionException}).
         *
         * @param cause the cause (which is saved for later retrieval by the
         *              {@link #getCause()} method).  (A {@code null} value is
         *              permitted, and indicates that the cause is nonexistent or
         *              unknown.)
         * @since 1.4
         */
        public DatabaseException(Throwable cause) {
            super(cause);
        }
    }
}
