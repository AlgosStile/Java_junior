package ru.geekbrains.lesson5;

import ru.geekbrains.chat.server.Server;

import java.io.IOException;
import java.net.ServerSocket;

/**
 * Реализовал префикс личных сообщений – с помощью префикса "@", который реализован в broadcastMessage серверного
 * класса ClientManager.
 * Пересылка сообщений всем участникам чата – через тот же метод broadcastMessage, но без использования "@".
 * Обработка подключения и отключения клиентов – реализовано в конструкторе и методе removeClient класса ClientManager,
 * соответственно, но дублировать для каждого не стал!
 */
public class Program {

    public static void main(String[] args) {
        // Запуск сервера на порту 1400
        int port = 1400;
        try (ServerSocket serverSocket = new ServerSocket(port)) {
            Server server = new Server(serverSocket);
            System.out.println("Сервер запущен и слушает порт " + port);
            server.runServer();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
