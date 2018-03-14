package clayburn.familyMapServer.serviceHandlers;


import clayburn.familymap.ServiceResponses.*;
import clayburn.familyMapServer.ObjectTransmitter;
import clayburn.familyMapServer.database.Database;
import clayburn.familyMapServer.services.AllEventService;
import clayburn.familyMapServer.services.AllPersonService;
import clayburn.familyMapServer.services.SingleEventService;
import clayburn.familyMapServer.services.SinglePersonService;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.util.List;
import java.util.regex.Pattern;

/**
 * DataRequestHandler parses and handles requests for all Event data
 */
public class DataRequestHandler implements HttpHandler{

    /**
     * Handles the http request to return data from the server, either Person or Event
     * @param httpExchange The exchange between the client and server
     * @throws IOException Something went wrong in communication with the client
     */
    public void handle(HttpExchange httpExchange) throws IOException {

        System.out.println("Data request from " + httpExchange.getRemoteAddress());

        if(!httpExchange.getRequestMethod().toLowerCase().equals("get")){
            httpExchange.sendResponseHeaders(HttpURLConnection.HTTP_BAD_METHOD,-1);
            return;
        }
        try {
            List<String> authTokenHeader = httpExchange.getRequestHeaders().get("Authorization");
            String authToken = authTokenHeader.get(0);
            if(authToken.equals("")) {
                httpExchange.sendResponseHeaders(HttpURLConnection.HTTP_UNAUTHORIZED,0);
                ObjectTransmitter.send(new ErrorResponse("No Auth Token"),httpExchange.getResponseBody());
                return;
            }

            String requestPath = httpExchange.getRequestURI().getPath();
            String[] requestComponents = Pattern.compile("/").split(requestPath);

            ServiceResponse response;

            switch(requestComponents.length){
                case 2: response = allRequest(httpExchange, authToken, requestComponents);
                break;
                case 3: response = singleRequest(httpExchange, authToken, requestComponents);
                break;
                default:{
                    httpExchange.sendResponseHeaders(HttpURLConnection.HTTP_BAD_REQUEST,0);
                    ObjectTransmitter.send(
                            new ErrorResponse("Incorrect number of arguments"),
                            httpExchange.getResponseBody()
                    );
                    return;
                }
            }

            httpExchange.sendResponseHeaders(HttpURLConnection.HTTP_OK,0);
            ObjectTransmitter.send(response,httpExchange.getResponseBody());

        } catch(Database.DatabaseException e) {
            httpExchange.sendResponseHeaders(HttpURLConnection.HTTP_INTERNAL_ERROR,0);
            ObjectTransmitter.send(new ErrorResponse(e.getMessage()), httpExchange.getResponseBody());
        }
    }

    private ServiceResponse allRequest(HttpExchange httpExchange, String authToken, String[] requestComponents)
            throws IOException, Database.DatabaseException{
        String requestedDataName = requestComponents[1];
        switch (requestedDataName.toLowerCase()) {
            case "person":
                return new AllPersonService().getAllPersons(authToken);
            case "event":
                return new AllEventService().getAllEvents(authToken);
            default:
                throw new Database.DatabaseException("Data type of " + requestedDataName + " unavailable");
        }
    }

    private ServiceResponse singleRequest(HttpExchange httpExchange, String authToken, String[] requestComponents)
            throws IOException, Database.DatabaseException{
        String requestedDataName = requestComponents[1];
        String dataID = requestComponents[2];
        switch (requestedDataName.toLowerCase()) {
            case "person":
                return new SinglePersonService().getPerson(dataID, authToken);
            case "event":
                return new SingleEventService().getEvent(dataID, authToken);
            default:
                throw new Database.DatabaseException("Data type of " + requestedDataName + " unavailable");
        }
    }
}
