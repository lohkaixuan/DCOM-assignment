package dcom.assignment;


import java.io.*;
import java.net.*;

public class SocketServer {
    public static void main(String[] args) {
        try {
            ServerSocket serverSocket = new ServerSocket(1234); // Listen on port 1234
            System.out.println("Server started. Waiting for client...");

            Socket socket = serverSocket.accept(); // Accept client connection
            System.out.println("Client connected.");

            // Create input and output streams
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);

            String message = in.readLine(); // Receive message from client
            System.out.println("Received: " + message);

            out.println("Hello from server!"); // Send response to client

            // Close resources
            in.close();
            out.close();
            socket.close();
            serverSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
