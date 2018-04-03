package clayburn.familymap.model;

/**
 * Created by zachc_000 on 3/22/2018.
 */

import com.google.android.gms.maps.model.LatLng;

import java.util.HashMap;
import java.util.HashSet;
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
        mEventTypes = new HashSet<>();
    }

    /**
     * Get a reference to the global Model for holding data in the app.
     * @return The app's data holder model.
     */
    public static Model get(){
        if (sModel == null) {
            sModel = new Model();
        }
        return sModel;
    }

    private Map<String, Person> mPersons;
    private Map<String, Event> mEvents;
    private Map<String, Set<Event>> mPersonEvents;
    private Set<String> mEventTypes;
    private Map<String,Float> mEventColors;

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

            mEventTypes.add(event.getEventType());

            Set<Event> set = mPersonEvents.get(event.getPersonID());
            if (set == null) {
                set = new TreeSet<>(new Event.EventComparator());
                mPersonEvents.put(event.getPersonID(),set);
            }
            set.add(event);
        }

        float colorStep = 360F / mEventTypes.size();
        float currentColor = 0;
        mEventColors = new HashMap<>();
        for(String eventType : mEventTypes){
            mEventColors.put(eventType,currentColor);
            currentColor += colorStep;
        }
    }

    /**
     * Get the real name of the logged in user. Retrieves the first and last name of the currently
     * logged in user from the data retried from the server and concatanates them into a string
     * @return A string of the format [firstName lastName] of the Person object representing the
     * currently logged in user
     */
    public String getUsersRealName(){
        String firstName = mPersons.get(mUserPersonID).getFirstName();
        String lastName = mPersons.get(mUserPersonID).getLastName();

        return firstName + " " + lastName;
    }

    /**
     * Get the eventIDs used to acquire more data from the model
     * @return A set containing all the eventID properties of the Event objects saved in the Model
     */
    public Set<String> getEventIDSet(){
        return mEvents.keySet();
    }

    /**
     * Get the location of the Event with the given <code>eventID</code>
     * @param eventID The eventID of the Event who's location you desire
     * @return A LatLng with the coordinates of the given Event's location
     */
    public LatLng getEventPosition(String eventID){
        Event event = mEvents.get(eventID);
        return new LatLng(event.getLatitude(),event.getLongitude());
    }

    /**
     * Get a float representing the hue value of the events color.
     * @param eventID The eventID of the Event who's color you desire
     * @return A hue value float of the Event's color.
     */
    public float getEventColor(String eventID){
        return mEventColors.get(mEvents
                        .get(eventID)
                        .getEventType()
        );
    }

    public String getUserPersonID() {
        return mUserPersonID;
    }

    public void setUserPersonID(String userPersonID) {
        mUserPersonID = userPersonID;
    }

    public Set<String> getEventTypes() {
        return mEventTypes;
    }

    public Map<String, Float> getEventColors() {
        return mEventColors;
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
