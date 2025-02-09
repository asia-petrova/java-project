package bg.sofia.uni.fmi.mjt.uno.client;

import bg.sofia.uni.fmi.mjt.uno.client.exceptions.InvalidCountOfParameters;
import bg.sofia.uni.fmi.mjt.uno.client.exceptions.NoSuchCommand;
import bg.sofia.uni.fmi.mjt.uno.client.validcommand.CheckCommand;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.InetSocketAddress;
import java.nio.channels.Channels;
import java.nio.channels.SocketChannel;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

public class Client {
    private static final int SERVER_PORT = 5000;

    public static void main(String[] args) {
        try (SocketChannel socketChannel = SocketChannel.open();
             BufferedReader reader = new BufferedReader(Channels.newReader(socketChannel, StandardCharsets.UTF_8));
             PrintWriter writer = new PrintWriter(Channels.newWriter(socketChannel, StandardCharsets.UTF_8), true);
             Scanner scanner = new Scanner(System.in)) {

            socketChannel.connect(new InetSocketAddress("localhost", SERVER_PORT));

            System.out.println("Connected to the server.");

            while (true) {
                System.out.print("Enter message: ");
                String message = scanner.nextLine(); // read a line from the console
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

                writer.println(message);

                String reply = reader.readLine(); // read the response from the server
                System.out.println("The server replied <" + reply + ">");
            }
        } catch (IOException e) {
            throw new RuntimeException("There is a problem with the network communication", e);
        }
    }
}
