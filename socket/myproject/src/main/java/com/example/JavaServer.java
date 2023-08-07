package com.example;

import java.io.*;
import java.net.*;
import java.util.List;
import java.util.ArrayList;
import com.fasterxml.jackson.databind.ObjectMapper; // Import the Jackson library for JSON parsing

public class JavaServer {
    public static void main(String[] args) throws Exception {
        int port = 12345;
        ServerSocket serverSocket = new ServerSocket(port);
        Socket clientSocket = null;
        System.out.println("Java receiver listening on port " + port);
        clientSocket = serverSocket.accept();
        System.out.println("Connected to Erlang sender.");

        while (true)
            try {

                BufferedReader reader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                String receivedJSON = reader.readLine();

                if (receivedJSON == null)
                    break;
                System.out.println("received: " + receivedJSON);

            } catch (IOException e) {
                e.printStackTrace();
            }

        if (clientSocket != null)
            clientSocket.close();
    }
}
