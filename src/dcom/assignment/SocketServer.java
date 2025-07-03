package dcom.assignment;


import java.io.*;
import java.net.*;

public class SocketServer {
    public static void main(String[] args) {
        // Start TCP and UDP servers in separate threads
        new Thread(() -> startTCPServer()).start();
        new Thread(() -> startUDPServer()).start();
    }

    public static void startTCPServer() {
        try (ServerSocket serverSocket = new ServerSocket(1060)) {
            System.out.println("[TCP] Server started on port 1060...");
            Socket clientSocket = serverSocket.accept();
            System.out.println("[TCP] Client connected!");

            BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);

            String message = in.readLine();
            System.out.println("[TCP] Received: " + message);

            out.println("Hello from TCP server!");

            in.close();
            out.close();
            clientSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void startUDPServer() {
        try (DatagramSocket udpSocket = new DatagramSocket(1060)) {
            System.out.println("[UDP] Server started on port 1060...");

            byte[] buffer = new byte[1024];
            DatagramPacket packet = new DatagramPacket(buffer, buffer.length);

            udpSocket.receive(packet);
            String received = new String(packet.getData(), 0, packet.getLength());
            System.out.println("[UDP] Received: " + received);

            String reply = "Hello from UDP server!";
            byte[] replyData = reply.getBytes();
            DatagramPacket response = new DatagramPacket(
                    replyData, replyData.length, packet.getAddress(), packet.getPort());
            udpSocket.send(response);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
