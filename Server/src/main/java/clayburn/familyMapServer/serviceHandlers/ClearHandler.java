package clayburn.familyMapServer.serviceHandlers;


import clayburn.familymap.ServiceResponses.ClearResponse;
import clayburn.familymap.ServiceResponses.ErrorResponse;
import clayburn.familyMapServer.ObjectTransmitter;
import clayburn.familyMapServer.database.Database;
import clayburn.familyMapServer.services.ClearService;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.*;
import java.net.HttpURLConnection;

/**
 * ClearHandler parses and handles clear requests
 */
public class ClearHandler implements HttpHandler {

    /**
     * Handles the http request to clear the database
     * @param httpExchange The httpExchange between the client and server
     * @throws IOException Something went wrong in communication with the client
     */
    public void handle(HttpExchange httpExchange) throws IOException {

        System.out.println("Clear request from " + httpExchange.getRemoteAddress());

        ClearResponse response;

        if(httpExchange.getRequestMethod().toLowerCase().equals("post")){
            try {

                response = new ClearService().clear();
                httpExchange.sendResponseHeaders(HttpURLConnection.HTTP_OK,0);
                ObjectTransmitter.send(response,httpExchange.getResponseBody());
            }
            catch (Database.DatabaseException e){
                httpExchange.sendResponseHeaders(HttpURLConnection.HTTP_INTERNAL_ERROR,0);
                ObjectTransmitter.send(new ErrorResponse(e.getMessage()),httpExchange.getResponseBody());
            }
        } else {
            httpExchange.sendResponseHeaders(HttpURLConnection.HTTP_BAD_METHOD,0);
            ObjectTransmitter.send(new ErrorResponse("Wrong request type!"),httpExchange.getResponseBody());
        }
    }
}
