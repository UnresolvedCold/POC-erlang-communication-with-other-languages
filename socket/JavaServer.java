import java.io.*;
import java.net.*;

public class JavaServer {
    public static void main(String[] args) {
        int port = 12345;

        try (ServerSocket serverSocket = new ServerSocket(port)) {
            System.out.println("Java server listening on port " + port);

            while (true) {
                Socket clientSocket = serverSocket.accept();
                System.out.println("Connected to Erlang client.");

                BufferedReader reader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                PrintWriter writer = new PrintWriter(clientSocket.getOutputStream(), true);

                String message = reader.readLine();
                System.out.println("Message received from Erlang: " + message);

                writer.println("Hello, World!");

                clientSocket.close();
                System.out.println("Response sent to Erlang client.");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
