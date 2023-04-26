package config;

import com.sun.net.httpserver.HttpExchange;

import java.io.IOException;
import java.io.OutputStream;
import java.util.Map;

public class VerifyHttpHandler implements com.sun.net.httpserver.HttpHandler {
    @Override
    public void handle(HttpExchange exc) throws IOException {
        // need GET params here
        Map<String, String> params = CommonTools.queryToMap(exc.getRequestURI().getQuery());
        int response = MainApplication.verifyIdentityByOTP(params.get("email"),params.get("code"));
        String responseStr = response==0 ? "pass" : "fail";
        exc.sendResponseHeaders(200, responseStr.length());
        OutputStream out = exc.getResponseBody();
        out.write(responseStr.getBytes());
        out.close();
    }

}
