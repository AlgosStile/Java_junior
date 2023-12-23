package org.example.task7;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class ClientHandler extends Thread {
    private Socket clientSocket;
    private PrintWriter out;
    private Scanner in;
    private String username;

    public ClientHandler(Socket clientSocket) {
        this.clientSocket = clientSocket;
    }

    @Override
    public void run() {
        try {
            out = new PrintWriter(clientSocket.getOutputStream(), true);
            in = new Scanner(clientSocket.getInputStream());

            out.println("Введите ваше имя:");
            username = in.nextLine();
            out.println("Добро пожаловать, " + username + "!");

            Server.broadcastMessage(username + " присоединился к чату.", this);

            String input;
            do {
                input = in.nextLine();
                if (input.startsWith("@")) {
                    int spaceIndex = input.indexOf(" ");
                    if (spaceIndex >= 0) {
                        String recipient = input.substring(1, spaceIndex);
                        String message = input.substring(spaceIndex + 1);
                        Server.sendPrivateMessage(message, recipient, this);
                    } else {
                        out.println("Неправильный формат личного сообщения. Используйте @<пользователь> <сообщение>");
                    }
                } else {
                    Server.broadcastMessage(username + ": " + input, this);
                }
            } while (!input.equalsIgnoreCase("exit"));

            Server.removeClient(this);
            clientSocket.close();
            Server.broadcastMessage(username + " покинул чат.", this);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void sendMessage(String message) {
        out.println(message);
    }

    public String getUsername() {
        return username;
    }
}
