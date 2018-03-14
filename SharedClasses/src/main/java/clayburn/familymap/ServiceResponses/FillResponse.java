package clayburn.familymap.ServiceResponses;


/**
 * A response to a fill request
 */
public class FillResponse implements ServiceResponse {

    /**
     * A message stating how many people and events were successfully added to the database
     */
    private String message;

    /**
     * Creates a new Fill response to be sent to the client
     * @param numPersons The number of Persons added for the fill request
     * @param numEvents The number of Events added for the fill request
     */
    public FillResponse(int numPersons, int numEvents) {
        this.message = "Successfully added " + numPersons + " persons and " + numEvents + " events to the database.";
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
