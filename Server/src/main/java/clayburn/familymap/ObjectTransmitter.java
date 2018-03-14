package clayburn.familymap;


import com.google.gson.Gson;
import com.google.gson.JsonIOException;
import com.google.gson.JsonSyntaxException;

import java.io.*;

/**
 * An object that Transmits objects to and from the client.
 * Currently implemented with Google Gson
 */
public class ObjectTransmitter {

    private static Gson gson = new Gson();

    /**
     * Receives data from the client in reader form of a Reader and builds an object out of it
     * @param <T> The Class of T
     * @param inputStream A reader made from the request stream of the http request
     * @param objectType The expected class of the received object
     * @return The constructed object
     * @throws TransmissionException If there was an error in creating the object
     * @throws IOException if there is an error with the input stream
     */
    public static <T> T receive(InputStream inputStream, Class<T> objectType) throws TransmissionException, IOException {
        try (Reader reader = new InputStreamReader(inputStream)){
            return gson.fromJson(reader, objectType);
        }
        catch (JsonIOException | JsonSyntaxException e){
            throw new TransmissionException(e);
        }
    }

    /**
     * Sends data of the supplied object through the writer to the client
     * @param objectToSend The object to be sent to the client
     * @param sendStream The Writer made out of http response output stream
     * @throws IOException if there is an error with the input stream
     */
    public static void send(Object objectToSend, OutputStream sendStream) throws IOException {
        try(Writer writer = new OutputStreamWriter(sendStream)) {
            gson.toJson(objectToSend, writer);
        }
    }

    /**
     * An exception class to wrap inner parsing and IO exception in the receiving process
     */
    public static class TransmissionException extends Exception {
        public TransmissionException() {
            super();
        }

        public TransmissionException(String s) {
            super(s);
        }

        public TransmissionException(String s, Throwable throwable) {
            super(s, throwable);
        }

        public TransmissionException(Throwable throwable) {
            super(throwable);
        }
    }
}
