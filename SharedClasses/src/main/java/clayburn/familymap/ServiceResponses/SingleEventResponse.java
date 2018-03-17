package clayburn.familymap.ServiceResponses;

import clayburn.familymap.model.Event;

public class SingleEventResponse implements ServiceResponse {

    /**
     * Unique identifier for this event (non-empty string)
     */
    private String eventID;

    /**
     * Username of the User that this Event's corresponding Person belongs to
     */
    private String descendant;

    /**
     * ID of this Event's corresponding Person
     */
    private String person;

    /**
     * Latitude of event's location
     */
    private double latitude;

    /**
     * Longitude of event's location
     */
    private double longitude;

    /**
     * Country in which event occurred
     */
    private String country;

    /**
     * City in which event occurred
     */
    private String city;

    /**
     * Type of event
     */
    private String eventType;

    /**
     * Year the event took place, Stored as a string
     */
    private String year;

    /**
     * Creates a SingleEventResponse out of an Event object to be serialized and sent to the client
     * @param event The Event that the client requested
     */
    public SingleEventResponse(Event event) {
        this.eventID = event.getEventID();
        this.descendant = event.getDescendant();
        this.person = event.getPersonID();
        this.latitude = event.getLatitude();
        this.longitude = event.getLongitude();
        this.country = event.getCountry();
        this.city = event.getCity();
        this.eventType = event.getEventType();
        this.year = event.getYear();
    }

    /**
     * Create an Event object out of the information in the response
     * @return The Event object that the client requested
     */
    public Event getEvent(){
        return new Event(eventID,descendant,person,latitude,longitude,country,city,eventType,year);
    }
}
