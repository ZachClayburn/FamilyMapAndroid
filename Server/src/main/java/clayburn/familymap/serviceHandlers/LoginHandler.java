package clayburn.familymap.serviceHandlers;


import clayburn.familymap.ServiceRequests.LoginRequest;
import clayburn.familymap.ServiceResponses.ErrorResponse;
import clayburn.familymap.ServiceResponses.LoginResponse;
import clayburn.familymap.ObjectTransmitter;
import clayburn.familymap.database.Database;
import clayburn.familymap.services.LoginService;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.IOException;
import java.net.HttpURLConnection;

/**
 * LoginHandler parses and handles login requests
 */
public class LoginHandler implements HttpHandler {

    /**
     * Handles the http request to login to the server
     * @param httpExchange The exchange between the client and server
     * @throws IOException Something went wrong in communication with the client
     */
    public void handle(HttpExchange httpExchange) throws IOException {

        System.out.println("Login request from " + httpExchange.getRemoteAddress());

        if(httpExchange.getRequestMethod().toLowerCase().equals("get")){
            httpExchange.sendResponseHeaders(HttpURLConnection.HTTP_BAD_METHOD,-1);
            return;
        }
        try {
            LoginRequest request = ObjectTransmitter.receive(httpExchange.getRequestBody(),LoginRequest.class);
            if(request.hasNullValues()){
                httpExchange.sendResponseHeaders(HttpURLConnection.HTTP_BAD_REQUEST,0);
                ObjectTransmitter.send(new ErrorResponse("Missing value in request"),httpExchange.getResponseBody());
                return;
            }

            LoginResponse response = new LoginService().login(request);

            httpExchange.sendResponseHeaders(HttpURLConnection.HTTP_OK,0);
            ObjectTransmitter.send(response,httpExchange.getResponseBody());
        }
        catch (Database.DatabaseException | ObjectTransmitter.TransmissionException e){
            httpExchange.sendResponseHeaders(HttpURLConnection.HTTP_INTERNAL_ERROR,0);
            ObjectTransmitter.send(new ErrorResponse(e.getMessage()),httpExchange.getResponseBody());
        }

    }
}

