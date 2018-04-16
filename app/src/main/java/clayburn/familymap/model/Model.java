package clayburn.familymap.model;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;
import java.util.function.BiPredicate;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import clayburn.familymap.app.ui.FilterActivity;

import static clayburn.familymap.model.Model.LineName.*;

/**
 * The global data holder for the entire app.
 */
public class Model {

    private static final String TAG = "MODEL";

    private static Model sModel;

    /**
     * Reset all information, Only to be used at logout
     */
    public static void reset() {
        sModel = new Model();
    }


    private Model(){
        mPersons = new HashMap<>();
        mEvents = new HashMap<>();
        mPersonEvents = new HashMap<>();
        mEventTypes = new HashSet<>();
        initLineSettings();
        initMapSettings();
        initFilterSettings();
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
            mEventTypeFilters.put(eventType,true);
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

    //Filter Setting methods------------------------------------------------------------------------

    private Set<String> mEventTypes;
    private Map<String, Boolean> mEventTypeFilters;
    private boolean mMaleFilter;
    private boolean mFemaleFilter;

    private void initFilterSettings(){
        mEventTypes = new HashSet<>();
        mEventTypeFilters = new HashMap<>();
        mMaleFilter = true;
        mFemaleFilter = true;
    }

    public Set<String> getEventTypes() {
        return mEventTypes;
    }

    public void setEventFilter(String eventType, Boolean isDrawn){
        mEventTypeFilters.replace(eventType,isDrawn);
    }

    public void setGenderFilter(boolean isMale, boolean isDrawn){
        if (isMale) {
            mMaleFilter = isDrawn;
        } else {
            mFemaleFilter = isDrawn;
        }
    }

    public boolean isEventDrawn(String eventType){
        return mEventTypeFilters.get(eventType) != null && mEventTypeFilters.get(eventType);
    }

    public boolean isEventDrawn(boolean isMale){
        if (isMale) {
            return mMaleFilter;
        } else {
            return mFemaleFilter;
        }
    }

    private boolean isEventFiltered(Event event){
        if (!mEventTypeFilters.get(event.getEventType())){
            return true;
        }
        Person person = mPersons.get(event.getPersonID());
        return isMale(person.getPersonID()) ?
                !mMaleFilter : !mFemaleFilter;
    }
    //TODO Add Mother's side filter
    //TODO Add Father's side filter

    //Line and Marker Methods-----------------------------------------------------------------------

    /**
     * Get the MarkerOptions to draw a marker for the Event with the given <code>eventID</code>
     * @param eventID The eventID of the Event who's location you desire
     * @return A MarkerOptions for the given Event's location, or null if the Event is filtered
     */
    @Nullable
    public MarkerOptions getMarkerOption(String eventID){

        Event event = mEvents.get(eventID);
        if (isEventFiltered(event)){
            return null;
        }

        float color = mEventColors.get(event.getEventType());
        BitmapDescriptor icon = BitmapDescriptorFactory.defaultMarker(color);

        LatLng location = new LatLng(event.getLatitude(),event.getLongitude());

        return new MarkerOptions()
                .position(location)
                .icon(icon);
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
            if (!isEventFiltered(event)) {
                options.add(
                        new LatLng(event.getLatitude(), event.getLongitude())
                );
            }
        }

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
            if (!isEventFiltered(event)) {
                return new LatLng(event.getLatitude(), event.getLongitude());
            }
        }
        return null;
    }

    //Person Activity Methods-----------------------------------------------------------------------

    public List<ExpandableListItem> getEventList(String peronID){

        List<ExpandableListItem> listItems = new ArrayList<>();
        Set<Event> events = mPersonEvents.get(peronID);

        for (Event event : events) {
            if (!isEventFiltered(event)) {
                listItems.add(new ListEvent(event.getEventID()));
            }
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
        if (isEventDrawn(true)) {
            relationID = person.getFather();
            if (relationID != null) {
                listPerson = new ListPerson(relationID,ListPerson.PARENT);
                listItems.add(listPerson);
            }
        }
        if (isEventDrawn(false)) {
            relationID = person.getMother();
            if (relationID != null) {
                listPerson = new ListPerson(relationID,ListPerson.PARENT);
                listItems.add(listPerson);
            }
        }

        //Get Spouse
        relationID = person.getSpouse();
        if (relationID != null) {
            if (isEventDrawn(isMale(relationID))) {
                listPerson = new ListPerson(relationID,ListPerson.SPOUSE);
                listItems.add(listPerson);
            }
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

        mPersons.values().stream()
                .filter(isParent)
                .map(Person::getPersonID)
                .filter(pid -> isEventDrawn(isMale(pid)))
                .findFirst()
                .ifPresent(id -> listItems.add(new ListPerson(id, ListPerson.CHILD)));


        return listItems;
    }

    //Search Activity Methods-----------------------------------------------------------------------

    public List<ExpandingGroup> search(String searchString){

        List<ExpandingGroup> list = new ArrayList<>();

        List<ExpandableListItem> contentList;
        ExpandingGroup group;

        contentList = mEvents.values().stream()
                .filter(event -> eventSearchHelper(event, searchString))
                .map(event -> new ListEvent(event.getEventID()))
                .collect(Collectors.toList());
        if (!contentList.isEmpty()) {
            group = new ExpandingGroup(ExpandingGroup.EVENT_GROUP_TITLE, contentList);
            list.add(group);
        }

        contentList = mPersons.values().stream()
                .filter(person -> personSearchHelper(person,searchString))
                .map(person -> new ListPerson(person.getPersonID(),ListPerson.NO_RELATION))
                .collect(Collectors.toList());
        if (!contentList.isEmpty()) {
            group = new ExpandingGroup(ExpandingGroup.PERSON_GROUP_TITLE, contentList);
            list.add(group);
        }

        return list;
    }

    private boolean eventSearchHelper(Event event, String searchString){
        String s = searchString.toLowerCase();
        return !isEventFiltered(event) && (
                event.getCity().toLowerCase().contains(s) ||
                event.getCountry().toLowerCase().contains(s) ||
                event.getYear().toLowerCase().contains(s) ||
                event.getEventType().toLowerCase().contains(s) ||
                getPersonName(getEventPersonID(event.getEventID())).toLowerCase().contains(s));
    }

    private boolean personSearchHelper(Person person, String searchString){
        String s = searchString.toLowerCase();

        return person.getFirstName().toLowerCase().contains(s) ||
                person.getLastName().toLowerCase().contains(s);
    }

    //Unorganized Mess------------------------------------------------------------------------------
    //Low Priority Fix this

    @NonNull
    public String getPersonName(String personID) {
        Person person = mPersons.get(personID);
        return person.getFirstName() + " " + person.getLastName();
    }

    /**
     * Get the eventIDs used to acquire more data from the model
     * @return A set containing all the eventID properties of the Event objects saved in the Model
     */
    public Iterable<String> getEventIDIterable(){
        return mEvents.keySet();
    }

    public String getEventPersonID(String eventID){
        return mEvents.get(eventID).getPersonID();
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

    public String getUserPersonID() {
        return mUserPersonID;
    }

    public void setUserPersonID(String userPersonID) {
        mUserPersonID = userPersonID;
    }

    public boolean isMale(String personID){
        return mPersons.get(personID).getGender().equals("m");
    }

    public String getAuthToken() {
        return mAuthToken;
    }

    public void setAuthToken(String authToken) {
        this.mAuthToken = authToken;
    }
}
