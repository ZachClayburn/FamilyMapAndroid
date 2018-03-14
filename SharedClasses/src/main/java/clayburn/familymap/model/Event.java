package clayburn.familymap.model;


import java.util.Objects;

/**
 * Event Represents an event in a {@link clayburn.model.Person}'s life
 */
public class Event {

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
    private String personID;

    /**
     * Latitude of event’s location
     */
    private double latitude;

    /**
     * Longitude of event’s location
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
     * Creates a new Event object representing a row from the event table
     * @param eventID Unique identifier for this event (non-empty string)
     * @param descendant Username of the User that this Event's corresponding Person belongs to
     * @param personID ID of this Event's corresponding Person
     * @param latitude Latitude of event’s location
     * @param longitude Longitude of event’s location
     * @param country Country in which event occurred
     * @param city City in which event occurred
     * @param eventType Type of event
     * @param year Year the event took place, Stored as a string
     * @throws NumberFormatException if the year is not actually an integer
     */
    public Event(String eventID,    String descendant,  String personID,
                 double latitude,   double longitude,   String country,
                 String city,       String eventType,   String year) throws NumberFormatException{
        this.eventID = eventID;
        this.descendant = descendant;
        this.personID = personID;
        this.latitude = latitude;
        this.longitude = longitude;
        this.country = country;
        this.city = city;
        this.eventType = eventType;
        if(year != null) {
            Integer.parseInt(year);
        }
        this.year = year;
    }

    /**
     * Create an event with null values, to be initialized with Setters
     */
    public static Event newEmptyEvent() {
        return new Event(null, null, null, 0, 0,
                null, null, null, null);
    }

    public String getEventID() {
        return eventID;
    }

    public void setEventID(String eventID) {
        this.eventID = eventID;
    }

    public String getDescendant() {
        return descendant;
    }

    public void setDescendant(String descendant) {
        this.descendant = descendant;
    }

    public String getPersonID() {
        return personID;
    }

    public void setPersonID(String personID) {
        this.personID = personID;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getEventType() {
        return eventType;
    }

    public void setEventType(String eventType) {
        this.eventType = eventType;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        Integer.parseInt(year);
        this.year = year;
    }

    /**
     * Creates a String out ot the Event
     * @return The String containing the Event's fields
     */
    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Event{");
        sb.append("eventID='").append(eventID).append('\'');
        sb.append(", descendant='").append(descendant).append('\'');
        sb.append(", person='").append(personID).append('\'');
        sb.append(", latitude=").append(latitude);
        sb.append(", longitude=").append(longitude);
        sb.append(", country='").append(country).append('\'');
        sb.append(", city='").append(city).append('\'');
        sb.append(", eventType='").append(eventType).append('\'');
        sb.append(", year='").append(year).append('\'');
        sb.append('}');
        return sb.toString();
    }

    /**
     * Compares the given Event object with another object o
     * @param o The object to be compared
     * @return False if o is null, not an Event or if it's fields don't match, true otherwise
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Event event = (Event) o;
        return Double.compare(event.latitude, latitude) == 0 &&
                Double.compare(event.longitude, longitude) == 0 &&
                Objects.equals(eventID, event.eventID) &&
                Objects.equals(descendant, event.descendant) &&
                Objects.equals(personID, event.personID) &&
                Objects.equals(country, event.country) &&
                Objects.equals(city, event.city) &&
                Objects.equals(eventType, event.eventType) &&
                Objects.equals(year, event.year);
    }

    /**
     * Generate a hash code for the given Event
     * @return The Event's hash code
     */
    @Override
    public int hashCode() {
        return Objects.hash(eventID, descendant, personID, latitude, longitude, country, city, eventType, year);
    }
}
