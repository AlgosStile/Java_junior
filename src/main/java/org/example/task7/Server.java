package org.example.task7;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class Server {
    private static List<ClientHandler> clients = new ArrayList<>();

    public static void main(String[] args) {
        try {
            ServerSocket serverSocket = new ServerSocket(30777);
            System.out.println("Сервер запущен. Ожидание подключений...");

            while (true) {
                Socket clientSocket = serverSocket.accept();
                System.out.println("Новое подключение: " + clientSocket);

                ClientHandler clientHandler = new ClientHandler(clientSocket);
                clients.add(clientHandler);
                clientHandler.start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void broadcastMessage(String message, ClientHandler sender) {
        for (ClientHandler client : clients) {
            if (client != sender) {
                client.sendMessage(message);
            }
        }
    }

    public static void sendPrivateMessage(String message, String recipient, ClientHandler sender) {
        for (ClientHandler client : clients) {
            if (client.getUsername().equalsIgnoreCase(recipient)) {
                client.sendMessage("[ЛИЧНОЕ СООБЩЕНИЕ] " + sender.getUsername() + ": " + message);
                sender.sendMessage("[ЛИЧНОЕ СООБЩЕНИЕ] Вы -> " + client.getUsername() + ": " + message);
                return;
            }
        }
        sender.sendMessage("Пользователь '" + recipient + "' не найден.");
    }

    public static void removeClient(ClientHandler clientHandler) {
        clients.remove(clientHandler);
    }
}