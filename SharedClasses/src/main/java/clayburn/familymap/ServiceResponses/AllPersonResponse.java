package clayburn.familymap.ServiceResponses;


import clayburn.familymap.model.Person;

/**
 * A response to the request for all Persons
 */
public class AllPersonResponse implements ServiceResponse {

    /**
     * An array of Person objects
     */
    private Person[] persons;

    /**
     * Create an response for all of the persons to be serialized and sent to the client
     * @param persons an array of Person objects representing the entire person table
     */
    public AllPersonResponse(Person[] persons) {
        this.persons = persons;
    }

    public Person[] getData() {
        return persons;
    }

    public void setPersons(Person[] persons) {
        this.persons = persons;
    }
}
