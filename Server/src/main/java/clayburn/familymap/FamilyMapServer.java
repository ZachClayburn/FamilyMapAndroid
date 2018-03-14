package clayburn.familymap;


import clayburn.familymap.serviceHandlers.*;
import com.sun.net.httpserver.*;

import java.io.IOException;
import java.net.InetSocketAddress;

/**
 * The main class for the family map Server
 */
public class FamilyMapServer {

    /**
     * Runs the FM Server
     * @param args The port to run the server on
     */
    public static void main(String[] args){
        new FamilyMapServer().run(Integer.parseInt(args[0]));
    }

    /**
     * The maximum number of waiting connections
     */
    private static final int MAX_WAITING_CONNECTIONS = 12;

    /**
     * The server that will handle HTTP requests
     */
    private HttpServer server;

    /**
     * Start the Server
     * @param portNumber The port to run the server on
     */
    public void run(int portNumber){
        try {
            server = HttpServer.create(new InetSocketAddress(portNumber),MAX_WAITING_CONNECTIONS);
        }
        catch (IOException e){
            e.printStackTrace();
            return;
        }

        server.setExecutor(null);

        System.out.println("Creating Contexts");

        server.createContext("/",new DefaultHandler());
        server.createContext("/user/register", new RegisterHandler());
        server.createContext("/clear", new ClearHandler());
        server.createContext("/fill/", new FillHandler());
        server.createContext("/user/login",new LoginHandler());
        server.createContext("/load", new LoadHandler());
        server.createContext("/person", new DataRequestHandler());
        server.createContext("/event", new DataRequestHandler());

        System.out.println("Starting Server on port " + portNumber);

        server.start();
    }

}
