package dcom.assignment;

import java.io.*;
import java.net.*;

public class SocketClient {// File: CombinedClient.java

    public static void main(String[] args) {
        // Start TCP and UDP clients in separate threads
        new Thread(() -> startTCPClient()).start();
        new Thread(() -> startUDPClient()).start();
    }

    public static void startTCPClient() {
        try (Socket socket = new Socket("192.168.43.199", 1060)) {
            System.out.println("[TCP] Connected to server.");

            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));

            out.println("Hello from TCP client!");
            String response = in.readLine();
            System.out.println("[TCP] Server says: " + response);

            in.close();
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void startUDPClient() {
        try (DatagramSocket udpSocket = new DatagramSocket()) {
            InetAddress serverAddress = InetAddress.getByName("192.168.43.199");
            String message = "Hello from UDP client!";
            byte[] data = message.getBytes();

            DatagramPacket packet = new DatagramPacket(data, data.length, serverAddress, 1060);
            udpSocket.send(packet);
            System.out.println("[UDP] Sent message.");

            byte[] buffer = new byte[1024];
            DatagramPacket response = new DatagramPacket(buffer, buffer.length);
            udpSocket.receive(response);

            String reply = new String(response.getData(), 0, response.getLength());
            System.out.println("[UDP] Server says: " + reply);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
