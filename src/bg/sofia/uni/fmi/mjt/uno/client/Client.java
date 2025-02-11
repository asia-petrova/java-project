package bg.sofia.uni.fmi.mjt.uno.client;

import bg.sofia.uni.fmi.mjt.uno.client.exceptions.InvalidCountOfParameters;
import bg.sofia.uni.fmi.mjt.uno.client.exceptions.NoSuchCommand;
import bg.sofia.uni.fmi.mjt.uno.client.validcommand.CheckCommand;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.Channels;
import java.nio.channels.SocketChannel;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

public class Client {
    private static final int SERVER_PORT = 5000;
    private ByteBuffer buffer;

    public static void main(String[] args) {
        try (SocketChannel socketChannel = SocketChannel.open();
             BufferedReader reader = new BufferedReader(Channels.newReader(socketChannel, StandardCharsets.UTF_8));
             PrintWriter writer = new PrintWriter(Channels.newWriter(socketChannel, StandardCharsets.UTF_8), true);
             Scanner scanner = new Scanner(System.in)) {

            socketChannel.connect(new InetSocketAddress("localhost", SERVER_PORT));
            System.out.println("Connected to the server.");

            while (true) {
                System.out.print("Enter message: ");
                String message = scanner.nextLine();

                try {
                    CheckCommand possibleCmd = CheckCommand.of(message);
                    if (!possibleCmd.checkString()) {
                        System.out.println(possibleCmd.getDescription());
                        continue;
                    }
                } catch (NoSuchCommand | InvalidCountOfParameters e) {
                    System.out.println(e.getMessage());
                    continue;
                }

                if ("quit".equals(message)) {
                    break;
                }

                System.out.println("Sending message <" + message + "> to the server...");
                writer.println(message); // Изпраща съобщение с нов ред
                writer.flush(); // Гарантира, че съобщението е изпратено

                // Четене на всички редове, докато не пристигне празен ред
                StringBuilder reply = new StringBuilder();
                String line;
                while ((line = reader.readLine()) != null) {
                    if (line.isEmpty()) { // Ако съобщението е приключило
                        break;
                    }
                    reply.append(line).append("\n"); // Добавяме към резултата
                }

                System.out.println("The server replied:\n" + reply);
            }
        } catch (IOException e) {
            throw new RuntimeException("There is a problem with the network communication", e);
        }

    }
}
