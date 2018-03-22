package clayburn.familymap.model;

/**
 * Created by zachc_000 on 3/22/2018.
 */

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
    private Map<String, List<Event>> mPersonEvents;

    public static void setModel(Model model) {
        sModel = model;
    }

    public Map<String, Person> getPersons() {
        return mPersons;
    }

    public void setPersons(Map<String, Person> persons) {
        mPersons = persons;
    }

    public Map<String, Event> getEvents() {
        return mEvents;
    }

    public void setEvents(Map<String, Event> events) {
        mEvents = events;
    }

    public Map<String, List<Event>> getPersonEvents() {
        return mPersonEvents;
    }

    public void setPersonEvents(Map<String, List<Event>> personEvents) {
        mPersonEvents = personEvents;
    }
}
