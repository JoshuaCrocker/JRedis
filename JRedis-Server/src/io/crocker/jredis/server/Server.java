package io.crocker.jredis.server;

import io.crocker.jredis.server.command.Command;
import io.crocker.jredis.server.data.HashMapDataStore;
import io.crocker.jredis.server.command.CommandProcessor;

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
     * @throws IOException Socket errors
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

        HashMapDataStore store = new HashMapDataStore();
        CommandProcessor processor = new CommandProcessor(store);

        do {
            input = in.readLine();
            String[] splitString = input.split(" +");

            System.out.println("Command received: " + input);

            switch (splitString[0]) {
                case Command.EXIT:
                    out.println("Goodbye!");
                    break;
                case Command.GET:
                case Command.SET:
                case Command.DELETE:
                case Command.FLUSH:
                case Command.LIST:
                case Command.INCREMENT:
                case Command.DECREMENT:
                case Command.ADD:
                    String response = processor.process(splitString);
                    out.print(
                            response.replace("\n", "{new_line}")
                    );
                    out.println();
                    break;
                default:
                    out.println("Unrecognised command: " + input);
                    break;
            }

            out.flush();
        } while (!input.equals(Command.EXIT));

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
