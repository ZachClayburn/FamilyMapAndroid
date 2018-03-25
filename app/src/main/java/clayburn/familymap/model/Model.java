package clayburn.familymap.model;

/**
 * Created by zachc_000 on 3/22/2018.
 */

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

/**
 * The global data holder for the entire app.
 */
public class Model {

    private static Model sModel;

    private Model(){

        mPersons = new HashMap<>();
        mEvents = new HashMap<>();
        mPersonEvents = new HashMap<>();
    }

    /**
     * Get a reference to the global Model for holding data in the app.
     * @return The app's data holder model.
     */
    public static Model getModel(){
        if (sModel == null) {
            sModel = new Model();
        }
        return sModel;
    }

    private Map<String, Person> mPersons;
    private Map<String, Event> mEvents;
    private Map<String, Set<Event>> mPersonEvents;
    private String mAuthToken;
    private String mUserPersonID;

    /**
     * Processes the information from the server and populates the Model with the information in the
     * form needed for the app.
     * @param persons All of the Persons belonging to the user
     * @param events All of the Events linked to Persons belonging to the user
     */
    public void populateModel(Person[] persons, Event[] events){
        //TODO Finish this method

        for (Person person : persons) {
            mPersons.put(person.getPersonID(),person);
        }

        for (Event event : events) {
            mEvents.put(event.getEventID(),event);

            Set<Event> set = mPersonEvents.get(event.getPersonID());

            if (set == null) {
                set = new TreeSet<>(new Event.EventComparator());
            }
            set.add(event);
        }
    }

    public String getUsersRealName(){
        String firstName = mPersons.get(mUserPersonID).getFirstName();
        String lastName = mPersons.get(mUserPersonID).getLastName();

        return firstName + " " + lastName;
    }

    public String getUserPersonID() {
        return mUserPersonID;
    }

    public void setUserPersonID(String userPersonID) {
        mUserPersonID = userPersonID;
    }

    public String getAuthToken() {
        return mAuthToken;
    }

    public void setAuthToken(String authToken) {
        this.mAuthToken = authToken;
    }

    public Map<String, Person> getPersons() {
        return mPersons;
    }

    public Map<String, Event> getEvents() {
        return mEvents;
    }

    public Map<String, Set<Event>> getPersonEvents() {
        return mPersonEvents;
    }
}
