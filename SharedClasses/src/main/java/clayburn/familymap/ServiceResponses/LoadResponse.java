package clayburn.familymap.ServiceResponses;


/**
 * A response to a load request
 */
public class LoadResponse implements ServiceResponse{

    String message;

    /**
     * Creates a new LoadResponse to be sent to the client
     * @param numUsers The number of Users loaded
     * @param numPersons The number of Persons loaded
     * @param numEvents The number of Events loaded
     */
    public LoadResponse(int numUsers, int numPersons, int numEvents) {
        this.message = "Successfully added " + numUsers + " users, " + numPersons +
                " persons, and " + numEvents + " events to the database.";
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
