package io.crocker.jredis.server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {

    private static final int PORT = 5000;

    //    private ArrayList<E> pool = new ArrayList<E>();

    /**
     * Creates the server.
     *
     * @throws IOException
     */
    public Server() throws IOException {
        // Start up server
        String input;

        ServerSocket server = new ServerSocket(PORT);
        System.out.println("Server started on " + server.getInetAddress().toString() + ":" + PORT);

        Socket client = server.accept();
        System.out.println("Client accepted on " + client.getInetAddress().toString() + ":" + client.getPort());

        PrintWriter out = new PrintWriter(client.getOutputStream(), true);
        BufferedReader in = new BufferedReader(new InputStreamReader(client.getInputStream()));

        do {
            input = in.readLine();

            System.out.println("Command received: " + input);

            switch (input) {
                case Commands.EXIT:
                    out.println("Goodbye!");
                    break;
                default:
                    out.println("Unrecognised command: " + input);
                    break;
            }

            out.flush();
        } while (!input.equals(Commands.EXIT));

        System.out.println("Shutting server down");

        out.close();
        in.close();
        client.close();
        server.close();
    }

    public static void main(String[] args) {
        try {
            new Server();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
