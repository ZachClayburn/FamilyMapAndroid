package clayburn.familymap.ServiceResponses;


/**
 * The response to a clear request
 */
public class ClearResponse implements ServiceResponse {

    /**
     * A message stating clear was successful
     */
    private String message;

    /**
     * Creates a new clear response
     * @param message A message stating clear was successful
     */
    public ClearResponse(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

}
