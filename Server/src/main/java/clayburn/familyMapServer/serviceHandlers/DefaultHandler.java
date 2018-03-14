package clayburn.familyMapServer.serviceHandlers;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.*;
import java.net.HttpURLConnection;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * Handles the requests for different files to display the web page
 */
public class DefaultHandler implements HttpHandler{

    private static String sitePath ="ServerTestPage";

    public void handle(HttpExchange httpExchange) throws IOException {

        System.out.println("Default request from" + httpExchange.getRemoteAddress());

        if (httpExchange.getRequestMethod().toLowerCase().equals("get")) {
            try {
                String requestURL = httpExchange.getRequestURI().getPath();
                if (requestURL.equals("/") || requestURL.length() == 0) {
                    requestURL = "/index.html";
                }

                Path filePath = Paths.get(sitePath, requestURL);

                if (Files.exists(filePath) && Files.isReadable(filePath)) {
                    httpExchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, 0);
                } else {
                    httpExchange.sendResponseHeaders(HttpURLConnection.HTTP_NOT_FOUND, 0);
                    filePath = Paths.get(sitePath, "HTML", "404.html");
                }

                OutputStream responseBody = httpExchange.getResponseBody();
                Files.copy(filePath, responseBody);
                responseBody.close();

            } catch (IOException e) {
                httpExchange.sendResponseHeaders(HttpURLConnection.HTTP_INTERNAL_ERROR,-1);
            }
        }else {
            httpExchange.sendResponseHeaders(HttpURLConnection.HTTP_BAD_METHOD,-1);
        }
    }
}
