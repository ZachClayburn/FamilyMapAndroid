package clayburn.familymap.database;


import clayburn.familymap.model.Event;

import java.sql.*;
import java.util.ArrayList;

/**
 * EventDAO provides all database services for the Event table
 * @author Zach Clayburn
 */
public class EventDAO extends Database.DataAccessObject {

    public EventDAO(Connection connection) {
        super(connection);
    }

    /**
     * Attempts to add the given Event to the table
     * @param events The Event to be added
     * @throws Database.DatabaseException If there is an error adding the events
     */
    public void addEvents(Event[] events) throws Database.DatabaseException {

        final String update = "INSERT INTO " +
                "events (eventID, descendant, person, latitude, longitude, country, city, eventType, year) " +
                "VALUES (?,?,?,?,?,?,?,?,?)";

        try(PreparedStatement statement = connection.prepareStatement(update)){
            for (Event event : events) {
                statement.setString(1,event.getEventID());
                statement.setString(2,event.getDescendant());
                statement.setString(3,event.getPersonID());
                statement.setDouble(4,event.getLatitude());
                statement.setDouble(5,event.getLongitude());
                statement.setString(6,event.getCountry());
                statement.setString(7,event.getCity());
                statement.setString(8,event.getEventType());
                statement.setString(9,event.getYear());

                statement.executeUpdate();
            }
        }
        catch (SQLException e){
            throw new Database.DatabaseException(e);
        }
    }

    /**
     * Attempts to retrieve the user linked to the given EventID
     * @param eventID The ID of the event that is to be retrieved
     * @param userName The username of the person performing the request
     * @return The event linked with the given ID if one exists, null otherwise
     * @throws Database.DatabaseException If there is an error retrieving the Event
     */
    public Event getEvent(String eventID, String userName)throws Database.DatabaseException{

        final String query = "SELECT * FROM events WHERE eventID = ? AND descendant = ?";

        try(PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setString(1, eventID);
            statement.setString(2, userName);

            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()){
                return extractEventFromTable(resultSet);
            }else {
                return null;
            }

        } catch (SQLException e) {
            throw new Database.DatabaseException(e);
        }
    }

    /**
     * Retrieves all of the Events in the database
     * @param userName The username of the person performing the request
     * @return An array containing all of the Events in the database
     * @throws Database.DatabaseException If there is an error getting the events
     */
    public  Event[] getAllEvents(String userName) throws Database.DatabaseException {
        final String query = "SELECT * FROM events WHERE descendant = ?";

        try(PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setString(1,userName);

            ResultSet resultSet = statement.executeQuery();
            ArrayList<Event> events = new ArrayList<>();

            while (resultSet.next()){
                events.add(extractEventFromTable(resultSet));
            }
            return events.toArray(new Event[events.size()]);

        } catch (SQLException e) {
            throw new Database.DatabaseException(e);
        }
    }

    /**
     * Removes all events belonging to the given user from the database
     * @param username The username of the user who's data is being removed
     * @throws Database.DatabaseException If there was an error removing the user's data
     */
    public void removeAllFromUser(String username) throws Database.DatabaseException{

        final String query = "DELETE FROM events WHERE descendant = ?";

        try(PreparedStatement statement = connection.prepareStatement(query)){

            statement.setString(1,username);

            statement.executeUpdate();
        } catch (SQLException e) {
            throw new Database.DatabaseException("Error clearing data from the database",e);
        }
    }

    private Event extractEventFromTable(ResultSet resultSet) throws SQLException {
        Event event = Event.newEmptyEvent();

        event.setEventID(resultSet.getString("eventID"));
        event.setDescendant(resultSet.getString("descendant"));
        event.setPersonID(resultSet.getString("person"));
        event.setLatitude(resultSet.getDouble("latitude"));
        event.setLongitude(resultSet.getDouble("longitude"));
        event.setCountry(resultSet.getString("country"));
        event.setCity(resultSet.getString("city"));
        event.setEventType(resultSet.getString("eventType"));
        event.setYear(resultSet.getString("year"));

        return event;
    }

    protected void createTableHelper(Statement statement) throws SQLException {
        statement.executeUpdate("DROP TABLE IF EXISTS events;");
        statement.executeUpdate("CREATE TABLE events(" +
                "eventID TEXT PRIMARY KEY, " +
                "descendant TEXT, " +
                "person TEXT, " +
                "latitude NUMERIC, " +
                "longitude NUMERIC, " +
                "country TEXT, " +
                "city TEXT, " +
                "eventType TEXT, " +
                "year TEXT" +
                ");"
        );
    }
}
