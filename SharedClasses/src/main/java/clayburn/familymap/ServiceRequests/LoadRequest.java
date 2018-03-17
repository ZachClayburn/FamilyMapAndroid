package clayburn.familymap.ServiceRequests;


import clayburn.familymap.model.*;

/**
 * A request to load data into the server's database
 */
public class LoadRequest implements ServiceRequest{

    /**
     * An array of users to be added
     */
    private User[] users;

    /**
     * An array of Persons to be added
     */
    private Person[] persons;

    /**
     * An Array of Events to be added
     */
    private Event[] events;

    /**
     * Creates a new load request to be sent to the server
     * @param users An array of Users to be added
     * @param persons An array of Persons to be added
     * @param events An array of Events to be added
     */
    public LoadRequest(User[] users, Person[] persons, Event[] events) {
        this.users = users;
        this.persons = persons;
        this.events = events;
    }

    public User[] getUsers() {
        return users;
    }

    public void setUsers(User[] users) {
        this.users = users;
    }

    public Person[] getPersons() {
        return persons;
    }

    public void setPersons(Person[] persons) {
        this.persons = persons;
    }

    public Event[] getEvents() {
        return events;
    }

    public void setEvents(Event[] events) {
        this.events = events;
    }
}
