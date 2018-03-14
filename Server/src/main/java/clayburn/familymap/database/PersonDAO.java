package clayburn.familymap.database;


import clayburn.familymap.model.Person;

import java.sql.*;
import java.util.ArrayList;

/**
 * PersonDAO provides all database services for the Person table
 * @author Zach Clayburn
 */
public class PersonDAO extends Database.DataAccessObject {

    public PersonDAO(Connection connection) {
        super(connection);
    }

    /**
     * Attempt to add the Person to the database
     * @param persons the Person to be added to the Database
     * @throws clayburn.familymap.database.Database.DatabaseException If there is an Error in loading the Persons
     */
    public void addPersons(Person[] persons) throws Database.DatabaseException {

        final String update = "INSERT INTO " +
                "persons (personID, descendant, firstName, lastName, gender, father, mother, spouse) " +
                "VALUES (?,?,?,?,?,?,?,?)";

        try(PreparedStatement statement = connection.prepareStatement(update)){
            for (Person person : persons) {
                statement.setString(1,person.getPersonID());
                statement.setString(2,person.getDescendant());
                statement.setString(3,person.getFirstName());
                statement.setString(4,person.getLastName());
                statement.setString(5,person.getGender());
                statement.setString(6,person.getFather());
                statement.setString(7,person.getMother());
                statement.setString(8,person.getSpouse());

                statement.executeUpdate();
            }
        }
        catch (SQLException e){
            throw new Database.DatabaseException(e);
        }
    }

    /**
     * Attempts to retrieve a specific Person out to the database
     * @param personID The ID of the person you are looking for
     * @param userName The Username of the of the user performing the request
     * @return The person corresponding to personID, or null if none exists
     * @throws Database.DatabaseException If there is an error in the Database
     */
    public Person getPerson(String personID, String userName) throws Database.DatabaseException {

        final String query = "SELECT * FROM persons WHERE personID = ? AND descendant = ?";

        try(PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setString(1,personID);
            statement.setString(2,userName);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                return extractPersonFromTable(resultSet);
            } else {
                return null;
            }
        }
        catch (SQLException e) {
            throw new Database.DatabaseException("Error getting the person from the database", e);
        }
    }

    /**
     * Get all of the Persons in the database
     * @param userName Username of the user performing the request
     * @return An array of all of the persons in the database
     * @throws Database.DatabaseException If there is an error in the Database
     */
    public Person[] getAllPersons(String userName) throws Database.DatabaseException {

        final String query = "SELECT * FROM persons WHERE descendant = ?";

        try(PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setString(1,userName);
            ResultSet resultSet = statement.executeQuery();

            ArrayList<Person> personArrayList = new ArrayList<>();
            while (resultSet.next()){
                personArrayList.add(extractPersonFromTable(resultSet));
            }
            return personArrayList.toArray(new Person[personArrayList.size()]);
        }
        catch (SQLException e) {
            throw new Database.DatabaseException("Error Getting the Persons from the database",e);
        }
    }

    /**
     * Removes all Persons belonging to the given user from the database
     * @param username The username of the user who's data is being removed
     * @throws Database.DatabaseException If there was an error removing the user's data
     */
    public void removeAllFromUser(String username) throws Database.DatabaseException{

        final String query = "DELETE FROM persons WHERE descendant = ?";

        try(PreparedStatement statement = connection.prepareStatement(query)){

            statement.setString(1,username);

            statement.executeUpdate();
        } catch (SQLException e) {
            throw new Database.DatabaseException("Error clearing data from the database",e);
        }
    }

    private Person extractPersonFromTable(ResultSet resultSet) throws SQLException {

        Person person = Person.newEmptyPerson();

        person.setPersonID(resultSet.getString("personID"));
        person.setDescendant(resultSet.getString("descendant"));
        person.setFirstName(resultSet.getString("firstName"));
        person.setLastName(resultSet.getString("lastName"));
        person.setGender(resultSet.getString("gender"));
        person.setFather(resultSet.getString("father"));
        person.setMother(resultSet.getString("mother"));
        person.setSpouse(resultSet.getString("spouse"));

        return person;
    }

    protected void createTableHelper(Statement statement) throws SQLException {
        statement.executeUpdate("DROP TABLE IF EXISTS persons;");
        statement.executeUpdate("CREATE TABLE persons(" +
                "personID TEXT PRIMARY KEY, " +
                "descendant TEXT, " +
                "firstName TEXT, " +
                "lastName TEXT, " +
                "gender TEXT, " +
                "father TEXT, " +
                "mother TEXT, " +
                "spouse TEXT, " +
                "CONSTRAINT genderCheck CHECK (gender = 'm' OR gender = 'f')" +
                ");"
        );
    }
}
