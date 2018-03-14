package clayburn.familymap.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class EventTest {

    @BeforeEach
    void setUp() {
        event1 = new Event(eventID1,descendant1,personID1,latitude1,longitude1,country1,city1,eventType1,year1);
        event2 = new Event(eventID2,descendant2,personID2,latitude2,longitude2,country2,city2,eventType2,year2);
    }

    Event event1;
    Event event2;

    static String eventID1 = "eventID1";
    static String descendant1 = "descendant1";
    static String personID1 = "personID1";
    static int latitude1 = 100;
    static int longitude1 = 100;
    static String country1 = "country1";
    static String city1 = "city1";
    static String eventType1 = "eventType1";
    static String year1 = "1991";

    static String eventID2 = "eventID2";
    static String descendant2 = "descendant2";
    static String personID2 = "personID2";
    static int latitude2 = 200;
    static int longitude2 = 200;
    static String country2 = "country2";
    static String city2 = "city2";
    static String eventType2 = "eventType2";
    static String year2 = "1992";

    @Test
    void gettersAndSetters() {

        assertEquals(event1.getEventID(),eventID1);
        assertEquals(event1.getDescendant(),descendant1);
        assertEquals(event1.getPersonID(),personID1);
        assertEquals(event1.getLatitude(),latitude1);
        assertEquals(event1.getLongitude(),longitude1);
        assertEquals(event1.getCountry(),country1);
        assertEquals(event1.getCity(),city1);
        assertEquals(event1.getEventType(),eventType1);
        assertEquals(event1.getYear(),year1);

        assertEquals(event2.getEventID(),eventID2);
        assertEquals(event2.getDescendant(),descendant2);
        assertEquals(event2.getPersonID(),personID2);
        assertEquals(event2.getLatitude(),latitude2);
        assertEquals(event2.getLongitude(),longitude2);
        assertEquals(event2.getCountry(),country2);
        assertEquals(event2.getCity(),city2);
        assertEquals(event2.getEventType(),eventType2);
        assertEquals(event2.getYear(),year2);

        event1.setEventID(eventID2);
        event1.setDescendant(descendant2);
        event1.setPersonID(personID2);
        event1.setLatitude(latitude2);
        event1.setLongitude(longitude2);
        event1.setCountry(country2);
        event1.setCity(city2);
        event1.setEventType(eventType2);
        event1.setYear(year2);

        assertEquals(event1.getEventID(),eventID2);
        assertEquals(event1.getDescendant(),descendant2);
        assertEquals(event1.getPersonID(),personID2);
        assertEquals(event1.getLatitude(),latitude2);
        assertEquals(event1.getLongitude(),longitude2);
        assertEquals(event1.getCountry(),country2);
        assertEquals(event1.getCity(),city2);
        assertEquals(event1.getEventType(),eventType2);
        assertEquals(event1.getYear(),year2);

    }

    @Test
    void newEmptyEvent() {

        event1 = Event.newEmptyEvent();

        assertNull(event1.getEventID());
        assertNull(event1.getDescendant());
        assertNull(event1.getPersonID());
        assertEquals(event1.getLatitude(),0);
        assertEquals(event1.getLongitude(),0);
        assertNull(event1.getCountry());
        assertNull(event1.getCity());
        assertNull(event1.getEventType());
        assertNull(event1.getYear());

    }

    @Test
    void testToString() {

        String string = "Event{eventID='" + eventID1 +
                "', descendant='" + descendant1 +
                "', person='" + personID1 +
                "', latitude=" + Double.toString(latitude1) +
                ", longitude=" + Double.toString(longitude1) +
                ", country='" + country1 +
                "', city='" + city1 +
                "', eventType='" + eventType1 +
                "', year='" + year1 + "'}";

        assertEquals(event1.toString(),string);

    }

    @Test
    void equals() {
        assertTrue(event1.equals(event1));
        assertFalse(event1.equals(event2));

        event1.setEventID(eventID2);
        event1.setDescendant(descendant2);
        event1.setPersonID(personID2);
        event1.setLatitude(latitude2);
        event1.setLongitude(longitude2);
        event1.setCountry(country2);
        event1.setCity(city2);
        event1.setEventType(eventType2);
        event1.setYear(year2);

        assertTrue(event1.equals(event2));

    }

    @Test
    void testHashCode() {
        assertNotEquals(event1.hashCode(),event2.hashCode());
        assertEquals(event1.hashCode(),event1.hashCode());

        event1.setEventID(eventID2);
        event1.setDescendant(descendant2);
        event1.setPersonID(personID2);
        event1.setLatitude(latitude2);
        event1.setLongitude(longitude2);
        event1.setCountry(country2);
        event1.setCity(city2);
        event1.setEventType(eventType2);
        event1.setYear(year2);

        assertEquals(event1.hashCode(),event2.hashCode());

    }
}