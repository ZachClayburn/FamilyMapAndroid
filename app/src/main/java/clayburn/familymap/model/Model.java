package clayburn.familymap.model;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.PolylineOptions;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;
import java.util.function.Predicate;

import static clayburn.familymap.model.Model.LineName.*;

/**
 * The global data holder for the entire app.
 */
public class Model {

    private static final String TAG = "MODEL";

    public static final String LIFE_STORY_LINES = "Life Story Lines";
    public static final String FAMILY_TREE_LINES = "Family Trees Lines";
    public static final String SPOUSE_LINES = "Spouse Lines";

    private int mSpouseLineColor = 0XFFB71C1C;
    private int mFamilyTreeLineColor = 0XFF1A237E;
    private int mLifeStoryLineColor = 0XFF64DD17;

    private static Model sModel;

    private Model(){
        mPersons = new HashMap<>();
        mEvents = new HashMap<>();
        mPersonEvents = new HashMap<>();
        mEventTypes = new HashSet<>();
        initLineSettings();
        initMapSettings();
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

            Set<Event> set = mPersonEvents.computeIfAbsent(
                    event.getPersonID(),
                    k -> new TreeSet<>(new Event.EventComparator())
            );
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

    // Line Settings Methods------------------------------------------------------------------------

    private final int[] COLORS = {
            0xFFB71C1C,//Red
            0xFF2E7D32,//Green
            0xFF0D47A1,//Blue
            0xFFFFCA28 //Yellow
    };
    public enum LineName {
        lifeStoryLines,
        familyTreeLines,
        spouseLines
    }
    private Map<LineName, Boolean> mLineDrawn;
    private Map<LineName, Integer> mLineColorInds;

    private void initLineSettings() {
        //Set up default map options
        mLineDrawn = new HashMap<>();
        for (LineName name : LineName.values()) {
            mLineDrawn.put(name, true);
        }

        mLineColorInds = new HashMap<>();
        mLineColorInds.put(LineName.lifeStoryLines,0);
        mLineColorInds.put(LineName.familyTreeLines,1);
        mLineColorInds.put(spouseLines,2);
    }

    public int getLineColorSelection(LineName lineName){
        return mLineColorInds.get(lineName);
    }

    public void setLineColorSelection(LineName lineName, int selection){
        mLineColorInds.replace(lineName,selection);
    }

    public boolean isLineDrawn(LineName lineName){
        return mLineDrawn.get(lineName);
    }

    public void setLineDrawn(LineName lineName, boolean isDrawn){
        mLineDrawn.replace(lineName,isDrawn);
    }


    //Map Style Setting Methods---------------------------------------------------------------------

    private final int[] MAP_TYPES = {
            GoogleMap.MAP_TYPE_NORMAL,
            GoogleMap.MAP_TYPE_HYBRID,
            GoogleMap.MAP_TYPE_SATELLITE,
            GoogleMap.MAP_TYPE_TERRAIN
    };
    private int mMapTypeSelection;

    private void initMapSettings() {
        mMapTypeSelection = 0;
    }

    public int getCurrentMapType() {
        return MAP_TYPES[mMapTypeSelection];
    }

    public void setCurrentMapType(int selection) {
        mMapTypeSelection = selection;
    }

    public int getCurrentMapTypeIndex(){
        return mMapTypeSelection;
    }

    //Unorganized Mess------------------------------------------------------------------------------
    //TODO Fix this

    /**
     * Get the real name of the logged in user. Retrieves the first and last name of the currently
     * logged in user from the data retried from the server and concatanates them into a string
     * @return A string of the format [firstName lastName] of the Person object representing the
     * currently logged in user
     */
    public String getUsersRealName(){
        return getPersonFullName(mUserPersonID);
    }

    @NonNull
    public String getPersonFullName(String personID) {
        String firstName = mPersons.get(personID).getFirstName();
        String lastName = mPersons.get(personID).getLastName();

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
    public LatLng getEventLocation(String eventID){
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

    /**
     * Return the name of the Person who owns the given event
     * @param eventID The eventID of the Event you are checking
     * @return The first and last name of the person who the event belongs to formatted as a string
     */
    public String getEventPersonName(String eventID){
        Event event = mEvents.get(eventID);
        Person person = mPersons.get(event.getPersonID());
        return person.getFirstName() + " " + person.getLastName();
    }

    /**
     * Get the information of an event formatted as a string
     * @param eventID The eventID of the Event you want information on
     * @return A string containing the event type, event location and event year
     */
    public String getEventInfo(String eventID){
        Event event = mEvents.get(eventID);
        return event.getEventType() +
                ": " +
                event.getCity() +
                ", " +
                event.getCountry() +
                " (" +
                event.getYear() +
                ")";

    }

    public PolylineOptions getSpouseLine(String eventID){
        Log.d(TAG,"getSpouseLine(String) called");

        if (!mLineDrawn.get(spouseLines)){
            return null;
        }

        String personID = mEvents.get(eventID).getPersonID();
        String spouseID = mPersons.get(personID).getSpouse();
        if (spouseID == null) {
            return null;
        }

        Event event = mEvents.get(eventID);
        LatLng position = new LatLng(event.getLatitude(),event.getLongitude());
        int lineColor = COLORS[mLineColorInds.get(spouseLines)];

        PolylineOptions options = new PolylineOptions();
        options.add(position);
        options.color(lineColor);

        position = getFirstLifeEvent(spouseID);
        if (position == null) {
            return null;
        }
        options.add(position);
        return options;
    }

    public PolylineOptions[] getFamilyHistoryLines(String eventID){
        Log.d(TAG,"getFamilyHistoryLines(String) called");

        if (!mLineDrawn.get(familyTreeLines)){
            return null;
        }

        Event event = mEvents.get(eventID);
        LatLng position = new LatLng(event.getLatitude(),event.getLongitude());

        ArrayList<PolylineOptions> optionsArrayList = new ArrayList<>();

        recursiveFamilyHistoryHelper(optionsArrayList,event.getPersonID(),position,10F);

        return optionsArrayList.toArray(new PolylineOptions[optionsArrayList.size()]);
    }

    private void recursiveFamilyHistoryHelper(ArrayList<PolylineOptions> optionsArrayList,
                                              String personID,
                                              LatLng position,
                                              float width){
        PolylineOptions options;
        Person person = mPersons.get(personID);
        LatLng ancestorPosition;
        int lineColor = COLORS[mLineColorInds.get(familyTreeLines)];

        //Paternal Side
        if (person.getFather() != null) {
            options = new PolylineOptions();
            options.add(position);
            options.color(lineColor);
            options.width(width);
            ancestorPosition = getFirstLifeEvent(person.getFather());
            if (ancestorPosition != null) {
                options.add(ancestorPosition);
                optionsArrayList.add(options);
                recursiveFamilyHistoryHelper(optionsArrayList,person.getFather(),ancestorPosition,width/1.5F);
            }
        }

        //Maternal side
        if (person.getMother() != null) {
            options = new PolylineOptions();
            options.add(position);
            options.color(lineColor);
            options.width(width);
            ancestorPosition = getFirstLifeEvent(person.getMother());
            if (ancestorPosition != null) {
                options.add(ancestorPosition);
                optionsArrayList.add(options);
                recursiveFamilyHistoryHelper(optionsArrayList,person.getMother(),ancestorPosition,width/1.5F);
            }
        }
    }

    @Nullable
    private LatLng getFirstLifeEvent(String personID){

        Set<Event> personEvents = mPersonEvents.get(personID);
        for (Event event : personEvents) {
            //TODO Filter events
            LatLng position = new LatLng(event.getLatitude(),event.getLongitude());
            return position;
        }
        return null;
    }

    public PolylineOptions getLifeStoryLine(String eventID){
        Log.d(TAG,"getLifeStoryLine(String) called");

        if (!mLineDrawn.get(lifeStoryLines)){
            return null;
        }

        String personID = mEvents.get(eventID).getPersonID();
        int lineColor = COLORS[mLineColorInds.get(lifeStoryLines)];

        PolylineOptions options = new PolylineOptions();
        options.color(lineColor);

        for (Event event : mPersonEvents.get(personID)) {
            //TODO Add filtering
            options.add(
                    new LatLng(event.getLatitude(),event.getLongitude())
            );
        }

        return options;
    }

    public String getUserPersonID() {
        return mUserPersonID;
    }

    public void setUserPersonID(String userPersonID) {
        mUserPersonID = userPersonID;
    }

    public List<ExpandableListItem> getEventList(String peronID){

        List<ExpandableListItem> listItems = new ArrayList<>();
        Set<Event> events = mPersonEvents.get(peronID);

        for (Event event : events) {
            listItems.add(
                    new ListEvent(event.getEventID())
            );
        }

        return listItems;
    }

    public List<ExpandableListItem> getFamilyList(String personID){

        List<ExpandableListItem> listItems = new ArrayList<>();
        final Person person = mPersons.get(personID);
        String relationID;
        ListPerson listPerson;
        Predicate<Person> isParent;

        //Get Parents
        relationID = person.getFather();
        if (relationID != null) {
            listPerson = new ListPerson(relationID,ListPerson.PARENT);
            listItems.add(listPerson);
        }
        relationID = person.getMother();
        if (relationID != null) {
            listPerson = new ListPerson(relationID,ListPerson.PARENT);
            listItems.add(listPerson);
        }

        //Get Spouse
        relationID = person.getSpouse();
        if (relationID != null) {
            listPerson = new ListPerson(relationID,ListPerson.SPOUSE);
            listItems.add(listPerson);
        }

        //Get Children
        switch (person.getGender()){
            case "m": isParent = p -> personID.equals(p.getFather()); break;
            case "f": isParent = p -> personID.equals(p.getMother()); break;
            default:{
                isParent = p -> false;
                Log.e(TAG,"Person who's gender is neither m or f exists: " + personID);
            }break;
        }

        for (String id : mPersons.keySet()) {
            Person p = mPersons.get(id);
            if (p == null)
                continue;
            if (isParent.test(p)){
                listPerson = new ListPerson(id, ListPerson.CHILD);
                listItems.add(listPerson);
                break;
            }
        }

        return listItems;
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
