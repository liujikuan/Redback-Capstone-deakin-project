import com.sun.net.httpserver.HttpServer;
import config.RegisterHttpHandler;
import config.VerifyHttpHandler;

import java.io.*;
import java.net.InetSocketAddress;

public class MyHttpServer {

    public static void main(String[] args) throws IOException {
        // set default port
        int port = 8080;
        if (args.length > 0) {
            try {
                port = Integer.parseInt(args[0]);
            } catch (NumberFormatException e) {
                System.err.println("Invalid port number: " + args[0]);
                System.exit(1);
            }
        }
        // create server on port 8000
        InetSocketAddress address = new InetSocketAddress(port);
        HttpServer server = HttpServer.create(address, 0);

        // bind handler
        server.createContext("/fa", new RegisterHttpHandler());
        server.setExecutor(null);

        server.createContext("/verify", new VerifyHttpHandler());
        server.setExecutor(null);
        server.start();
    }
}