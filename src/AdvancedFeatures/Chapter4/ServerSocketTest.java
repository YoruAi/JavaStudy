package AdvancedFeatures.Chapter4;

import java.io.*;
import java.net.*;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

public class ServerSocketTest {
    public static void main(String[] args) {
        try (ServerSocket serverSocket = new ServerSocket(8189)) {
            System.out.println("Listening port 8189.");
            int count = 0;
            while (true) {
                Socket incoming = serverSocket.accept();
                count++;
                System.out.println("Client No." + count + " " + incoming.getInetAddress() + " Connected.");
                // 多线程
                Runnable r = new ThreadedHandler(incoming, count);
                Thread t = new Thread(r);
                t.start();
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}

class ThreadedHandler implements Runnable {
    private final Socket incoming;
    private final int id;

    public ThreadedHandler(Socket incoming, int id) {
        this.incoming = incoming;
        this.id = id;
    }

    @Override
    public void run() {
        try (var in = new Scanner(incoming.getInputStream(), StandardCharsets.UTF_8);
             var out = new PrintWriter(new OutputStreamWriter(incoming.getOutputStream(),
                     StandardCharsets.UTF_8), true)) {
            out.println("Hello No." + id + " client! Enter BYE to exit.");

            boolean done = false;
            while (!done && in.hasNextLine()) {
                String line = in.nextLine();
                System.out.println("Server Receive from Client No." + id + ": " + line);
                out.println("Echo: " + line);
                if (line.trim().equals("BYE"))
                    done = true;
            }

            System.out.println("Client No." + id + " disconnected.");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}