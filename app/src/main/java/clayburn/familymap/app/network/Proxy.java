package clayburn.familymap.app.network;

import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import clayburn.familymap.ObjectTransmitter;
import clayburn.familymap.ServiceRequests.LoginRequest;
import clayburn.familymap.ServiceRequests.RegisterRequest;
import clayburn.familymap.ServiceRequests.ServiceRequest;
import clayburn.familymap.ServiceResponses.AllEventResponse;
import clayburn.familymap.ServiceResponses.AllPersonResponse;
import clayburn.familymap.ServiceResponses.ErrorResponse;
import clayburn.familymap.ServiceResponses.LoginResponse;
import clayburn.familymap.ServiceResponses.RegisterResponse;
import clayburn.familymap.ServiceResponses.ServiceResponse;

/**
 * Created by zach on 3/16/18.
 */

public class Proxy {

    private String hostName;
    private int port;

    /**
     * Creates a Proxy object to connect to and communicate with a server at the given
     * <code>hostName</code> on the given <code>port</code>. A connection is not opened until one
     * of the service methods is called.
     * @param hostName
     * @param port
     */
    public Proxy(String hostName, int port) {
        this.hostName = hostName;
        this.port = port;
    }

    /**
     * Attempts to log into the server and retrieve an authorization token to be used in further
     * transactions with the server. If there is an error on the sever side, it will report the
     * message that the sever provided.
     * @param request The request containing the login information
     * @return A response containing the auth token to be used in further transactions with the
     * server, or an error message if there was one.
     */
    public ServiceResponse login(LoginRequest request){
        return loginRegisterHelper(request, "/login", LoginResponse.class);
    }

    /**
     * Attempts to register a new user into the server and retrieve an authorization token to be
     * used in further transactions with the server. If there is an error on the sever side, it will
     * report the message that the sever provided.
     * @param request The request containing the register information
     * @return A response containing the auth token to be used in further transactions with the
     * server, or an error message if there was one.
     */
    public ServiceResponse register(RegisterRequest request){
        return loginRegisterHelper(request, "/register", RegisterResponse.class);
    }

    private ServiceResponse loginRegisterHelper(ServiceRequest request,
                                                String file,
                                                Class<?extends ServiceResponse> responseClass){
        try {
            URL url = new URL("http://",hostName,port,file);

            HttpURLConnection connection = (HttpURLConnection) url.openConnection();

            connection.setRequestMethod("POST");
            connection.setDoOutput(true);
            connection.setDoInput(true);

            OutputStream outputStream = connection.getOutputStream();
            ObjectTransmitter.send(request,outputStream);

            ServiceResponse response;

            if(connection.getResponseCode() == HttpURLConnection.HTTP_OK){
                response = ObjectTransmitter.receive(
                        connection.getInputStream(),
                        responseClass
                );
            } else {
                response = ObjectTransmitter.receive(
                        connection.getInputStream(),
                        ErrorResponse.class
                );
            }

            return response;

        } catch (IOException | ObjectTransmitter.TransmissionException e) {
            e.printStackTrace();
            return new ErrorResponse("Client Error: " + e.getLocalizedMessage());
        }
    }

    /**
     * Attempts to retrieve all of the current User's Persons from the database. If there is an
     * error on the sever side, it will report the message that the sever provided.
     * @param address A string containing the address of the server to contact, as well as the port
     *                number.
     * @param authToken The auth token acquired from either login or registration.
     * @return A response containing all of the Persons associated with the user, or an error
     * message if there was one.
     */
    public ServiceResponse getPersons(String address, String authToken){
        //TODO Add getPersons function
        return null;
    }

    /**
     * Attempts to retrieve all of the current User's Events from the database. If there is an
     * error on the sever side, it will report the message that the sever provided.
     * @param address A string containing the address of the server to contact, as well as the port
     *                number.
     * @param authToken The auth token acquired from either login or registration.
     * @return A response containing all of the Events associated with the user, or an error
     * message if there was one.
     */
    public ServiceResponse getEvents(String address, String authToken){
        //TODO Add getEvents function
        return null;
    }
}
