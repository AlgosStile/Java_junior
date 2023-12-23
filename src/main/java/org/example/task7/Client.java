package org.example.task7;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class Client {
    public static void main(String[] args) {
        try {
            Socket socket = new Socket("localhost", 30777);
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
            Scanner in = new Scanner(socket.getInputStream());

            System.out.println(in.nextLine());

            Scanner scanner = new Scanner(System.in);
            String username = scanner.nextLine();
            out.println(username);

            System.out.println(in.nextLine());

            Thread receivingThread = new Thread(() -> {
                while (true) {
                    String message = in.nextLine();
                    System.out.println(message);
                }
            });
            receivingThread.start();

            String input;
            do {
                input = scanner.nextLine();
                out.println(input);
            } while (!input.equalsIgnoreCase("exit"));

            socket.close();
            receivingThread.join();

        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }
}
