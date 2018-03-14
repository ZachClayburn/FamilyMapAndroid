package clayburn.familymap.serviceHandlers;


import clayburn.familymap.ServiceRequests.LoadRequest;
import clayburn.familymap.ServiceResponses.ErrorResponse;
import clayburn.familymap.ServiceResponses.LoadResponse;
import clayburn.familymap.ObjectTransmitter;
import clayburn.familymap.database.Database;
import clayburn.familymap.services.LoadService;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.IOException;
import java.net.HttpURLConnection;

/**
 * LoadHandler parses and handles load requests
 */
public class LoadHandler implements HttpHandler {

    /**
     * Handles the http request to load data into the database
     * @param httpExchange The exchange between the client and server
     * @throws IOException Something went wrong in communication with the client
     */
    public void handle(HttpExchange httpExchange) throws IOException {

        System.out.println("Load Request from " + httpExchange.getRemoteAddress());

        if (httpExchange.getRequestMethod().toLowerCase().equals("get")){
            httpExchange.sendResponseHeaders(HttpURLConnection.HTTP_BAD_METHOD,-1);
            return;
        }
        try {
            LoadRequest request = ObjectTransmitter.receive(httpExchange.getRequestBody(),LoadRequest.class);

            LoadResponse response = new LoadService().load(request);

            httpExchange.sendResponseHeaders(HttpURLConnection.HTTP_OK,0);
            ObjectTransmitter.send(response,httpExchange.getResponseBody());
        } catch (ObjectTransmitter.TransmissionException | Database.DatabaseException e) {
            httpExchange.sendResponseHeaders(HttpURLConnection.HTTP_INTERNAL_ERROR,0);
            ObjectTransmitter.send(new ErrorResponse(e.getMessage()),httpExchange.getResponseBody());
        }
    }
}
