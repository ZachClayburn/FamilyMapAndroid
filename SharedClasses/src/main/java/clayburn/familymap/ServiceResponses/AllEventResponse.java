package clayburn.familymap.ServiceResponses;

import clayburn.familymap.model.Event;

public class AllEventResponse implements ServiceResponse {

    /**
     * An array of all of the Events in the database
     */
    private Event[] data;

    /**
     * Creates a response containing all of the Events in the database
     * @param data An array of all of the Events in the database
     */
    public AllEventResponse(Event[] data) {
        this.data = data;
    }

    public Event[] getData() {
        return data;
    }

    public void setData(Event[] data) {
        this.data = data;
    }
}
