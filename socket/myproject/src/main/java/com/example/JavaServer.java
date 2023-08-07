package com.example;

import java.io.*;
import java.net.*;
import java.util.List;
import java.util.ArrayList;
import com.fasterxml.jackson.databind.ObjectMapper; // Import the Jackson library for JSON parsing

public class JavaServer {
    public static void main(String[] args) {
        int port = 12345;

        try (ServerSocket serverSocket = new ServerSocket(port)) {
            System.out.println("Java receiver listening on port " + port);

            Socket clientSocket = serverSocket.accept();
            System.out.println("Connected to Erlang sender.");

            BufferedReader reader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            String receivedJSON = reader.readLine();

            System.out.println("received: " + receivedJSON);

            ObjectMapper objectMapper = new ObjectMapper();
            List<Double> receivedFloats = objectMapper.readValue(receivedJSON, List.class);

            System.out.println("Received random floats from Erlang sender: " + receivedFloats);

            clientSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
