package config;

import com.google.zxing.WriterException;
import com.sun.net.httpserver.HttpExchange;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.util.HashMap;
import java.util.Map;

public class HttpHandler implements com.sun.net.httpserver.HttpHandler {
    @Override
    public void handle(HttpExchange exc) throws IOException {
        Map<String, String> params = queryToMap(exc.getRequestURI().getQuery());
        System.out.println("param A=" + params.get("email"));
//        String response = "This is the reponse";
//        exc.sendResponseHeaders(200, response.length());
        // need GET params here

        String fileAddr;
        try {
            fileAddr = MainApplication.get2FAbarcode(params.get("email"));
        } catch (WriterException e) {
            throw new RuntimeException(e);
        }
        File file = new File(fileAddr);
        exc.sendResponseHeaders(200, file.length());
        OutputStream out = exc.getResponseBody();
        Files.copy(file.toPath(), out);
        //out.write(response.getBytes());
        out.close();
    }

    public Map<String, String> queryToMap(String query) {
        if (query == null) {
            return null;
        }
        Map<String, String> result = new HashMap<>();
        for (String param : query.split("&")) {
            String[] entry = param.split("=");
            if (entry.length > 1) {
                result.put(entry[0], entry[1]);
            } else {
                result.put(entry[0], "");
            }
        }
        return result;
    }
}
