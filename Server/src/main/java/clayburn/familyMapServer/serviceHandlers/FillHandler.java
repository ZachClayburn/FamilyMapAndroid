package clayburn.familyMapServer.serviceHandlers;


import clayburn.familymap.ServiceResponses.ErrorResponse;
import clayburn.familymap.ServiceResponses.FillResponse;
import clayburn.familymap.ServiceResponses.ServiceResponse;
import clayburn.familyMapServer.ObjectTransmitter;
import clayburn.familyMapServer.database.Database;
import clayburn.familyMapServer.services.FillService;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.util.regex.Pattern;

/**
 * FillHandler parses and handles fill requests
 */
public class FillHandler implements HttpHandler {

    /**
     * Handles the http request to fill a user's family tree
     * @param httpExchange The exchange between the client and server
     * @throws IOException Something went wrong in communication with the client
     */
    public void handle(HttpExchange httpExchange) throws IOException {

        System.out.println("Fill request from " + httpExchange.getRemoteAddress());

        if(!httpExchange.getRequestMethod().toLowerCase().equals("post")){
            httpExchange.sendResponseHeaders(HttpURLConnection.HTTP_BAD_METHOD,-1);
            return;
        }
        try {

            String requestPath = httpExchange.getRequestURI().getPath();
            String[] requestComponents = Pattern.compile("/").split(requestPath);

            int generations;

            switch(requestComponents.length) {
                case 3: generations = 4; break;
                case 4: generations = Integer.parseInt(requestComponents[3]); break;
                default: {
                    httpExchange.sendResponseHeaders(HttpURLConnection.HTTP_BAD_REQUEST, 0);
                    ObjectTransmitter.send(
                            new ErrorResponse("Incorrect number of arguments"),
                            httpExchange.getResponseBody()
                    );
                    return;
                }
            }

            FillResponse response = new FillService().fill(requestComponents[2], generations);

            httpExchange.sendResponseHeaders(HttpURLConnection.HTTP_OK,0);
            ObjectTransmitter.send(response,httpExchange.getResponseBody());

        } catch(Database.DatabaseException e) {
            httpExchange.sendResponseHeaders(HttpURLConnection.HTTP_BAD_REQUEST, 0);
            ObjectTransmitter.send(
                    new ErrorResponse(e.getMessage()),
                    httpExchange.getResponseBody()
            );
        } catch(NumberFormatException e) {
            httpExchange.sendResponseHeaders(HttpURLConnection.HTTP_BAD_REQUEST, 0);
            ObjectTransmitter.send(
                    new ErrorResponse("Improperly formatted request"),
                    httpExchange.getResponseBody()
            );
        }
    }
}
