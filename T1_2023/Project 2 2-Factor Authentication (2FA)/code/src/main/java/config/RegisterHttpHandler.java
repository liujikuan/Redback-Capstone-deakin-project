package config;

import com.google.zxing.WriterException;
import com.sun.net.httpserver.HttpExchange;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.util.Map;

/**
 *
 */
public class RegisterHttpHandler implements com.sun.net.httpserver.HttpHandler {
    @Override
    public void handle(HttpExchange exc) throws IOException {
        Map<String, String> params = CommonTools.queryToMap(exc.getRequestURI().getQuery());
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

}
