package dcom.assignment;

import java.io.*;
import java.net.*;

public class SocketClient {
    public static void main(String[] args) {
        try {
            Socket socket = new Socket("localhost", 1060); // Connect to server on port 1234

            // Create input and output streams
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);

            out.println("Hello from client!"); // Send message to server
            String response = in.readLine();   // Read response from server
            System.out.println("Server says: " + response);

            // Close resources
            in.close();
            out.close();
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
