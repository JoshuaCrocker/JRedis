package io.crocker.jredis.client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class Client {
    public Client() throws IOException {
        String input, response;

        Socket socket = new Socket("localhost", 5000);

        PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
        BufferedReader in = new BufferedReader(
                new InputStreamReader(socket.getInputStream())
        );

        BufferedReader usr = new BufferedReader(new InputStreamReader(System.in));

        do {
            System.out.print("jredis> ");
            input = usr.readLine();

            out.println(input);
            response = in.readLine();

            System.out.println("[server] " + response);
            System.out.println("\n");
        } while (!response.equals("Goodbye!"));

        out.close();
        in.close();
        usr.close();
        socket.close();
    }

    public static void main(String[] args) {
        try {
            new Client();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
