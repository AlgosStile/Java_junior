package ru.geekbrains.chat.server;

import java.io.*;
import java.net.Socket;
import java.util.ArrayList;

public class ClientManager implements Runnable {

    private final Socket socket;
    private BufferedReader bufferedReader;
    private BufferedWriter bufferedWriter;
    private String name;

    public final static ArrayList<ClientManager> clients = new ArrayList<>();

    public ClientManager(Socket socket) {
        this.socket = socket;
        try {
            bufferedWriter = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
            bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            name = bufferedReader.readLine();
            clients.add(this);
            System.out.println(name + " подключился к чату.");
            broadcastMessage("Server: " + name + " подключился к чату.");
        }
        catch (IOException e){
            closeEverything(socket, bufferedReader, bufferedWriter);
        }


    }

    @Override
    public void run() {
        String massageFromClient;

        while (socket.isConnected()) {
            try {
                massageFromClient = bufferedReader.readLine();
                broadcastMessage(massageFromClient);
            }
            catch (IOException e){
                closeEverything(socket, bufferedReader, bufferedWriter);
                break;
            }
        }
    }

    private void broadcastMessage(String messageToSend) {
        try {
            if (messageToSend.startsWith("@")) {
                // *Личное сообщение
                String[] messageParts = messageToSend.split(" ", 2);
                String theUserToPM = messageParts[0].substring(1); // Игнорируем символ '@'
                String messageContent = messageParts[1];
                boolean userFound = false;
                for (ClientManager client: clients) {
                    if (client.name.equals(theUserToPM)) {
                        // *Отправляем личное сообщение только этому клиенту
                        client.bufferedWriter.write("Личное от " + name + ": " + messageContent);
                        client.bufferedWriter.newLine();
                        client.bufferedWriter.flush();
                        userFound = true;
                    }
                }
                if (!userFound) {
                    // *Если пользователь не найден, сообщаем отправителю
                    this.bufferedWriter.write("Server: Пользователь " + theUserToPM + " не найден.");
                    this.bufferedWriter.newLine();
                    this.bufferedWriter.flush();
                }
            } else {
                // *А тут внедряем общее сообщение всем пользователям
                for (ClientManager client : clients) {
                    if (!client.name.equals(name)) {
                        client.bufferedWriter.write(messageToSend);
                        client.bufferedWriter.newLine();
                        client.bufferedWriter.flush();
                    }
                }
            }
        } catch (IOException e) {
            closeEverything(socket, bufferedReader, bufferedWriter);
        }
    }



    private void closeEverything(Socket socket, BufferedReader bufferedReader, BufferedWriter bufferedWriter) {
        // Удаление клиента из коллекции
        removeClient();
        try {
            // Завершаем работу буфера на чтение данных
            if (bufferedReader != null) {
                bufferedReader.close();
            }
            // Завершаем работу буфера для записи данных
            if (bufferedWriter != null) {
                bufferedWriter.close();
            }
            // Закрытие соединения с клиентским сокетом
            if (socket != null) {
                socket.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void removeClient(){
        clients.remove(this);
        System.out.println(name + " покинул чат.");
        broadcastMessage("Server: " + name + " покинул чат.");
    }

}
