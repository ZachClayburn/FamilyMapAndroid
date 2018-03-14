package clayburn.familymap.ServiceResponses;


/**
 * A response containing an error message
 */
public class ErrorResponse implements ServiceResponse {

    /**
     * Creates a new error response to be serialized and returned to the client
     * @param message A string describing the nature of the error
     */
    public ErrorResponse(String message){

        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    /**
     * A string describing the nature of the error
     */
    private String message;
}
