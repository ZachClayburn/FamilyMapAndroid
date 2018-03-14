package clayburn.familymap.serviceHandlers;

import clayburn.familymap.ServiceRequests.RegisterRequest;
import clayburn.familymap.ServiceResponses.ErrorResponse;
import clayburn.familymap.ServiceResponses.RegisterResponse;
import clayburn.familymap.ObjectTransmitter;
import clayburn.familymap.database.Database;
import clayburn.familymap.services.RegisterService;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.*;
import java.net.HttpURLConnection;

/**
 * RegisterHandler parses and handles register requests
 */
public class RegisterHandler implements HttpHandler {

    /**
     * Handles the http request to register a new user in the server
     * @param httpExchange The exchange between the client and server
     * @throws IOException Something went wrong in communication with the client
     */
    public void handle(HttpExchange httpExchange) throws IOException {

        System.out.println("Register request from " + httpExchange.getRemoteAddress());

        if(!httpExchange.getRequestMethod().toLowerCase().equals("post")){
            httpExchange.sendResponseHeaders(HttpURLConnection.HTTP_BAD_METHOD,-1);
            return;
        }
        try{
            RegisterRequest request = ObjectTransmitter.receive(httpExchange.getRequestBody(),RegisterRequest.class);
            if(request.hasNullValues()){
                httpExchange.sendResponseHeaders(HttpURLConnection.HTTP_BAD_REQUEST,0);
                ObjectTransmitter.send(new ErrorResponse("Missing value in request"),httpExchange.getResponseBody());
                return;
            }

            RegisterResponse response = new RegisterService().register(request);

            httpExchange.sendResponseHeaders(HttpURLConnection.HTTP_OK,0);
            ObjectTransmitter.send(response,httpExchange.getResponseBody());

        }
        catch(Database.DatabaseException | ObjectTransmitter.TransmissionException e){

            httpExchange.sendResponseHeaders(HttpURLConnection.HTTP_INTERNAL_ERROR,0);
            ObjectTransmitter.send(new ErrorResponse(e.getMessage()),httpExchange.getResponseBody());
        }
    }
}
