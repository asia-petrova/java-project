package bg.sofia.uni.fmi.mjt.uno.client;

import bg.sofia.uni.fmi.mjt.uno.client.output.CardsOutput;
import bg.sofia.uni.fmi.mjt.uno.client.validinput.CheckInput;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

public class Client {
    private static final int SERVER_PORT = 5000;
    private static final int BUFFER_SIZE = 1024;
    private static final String SERVER_HOST = "localhost";
    private static boolean running = true;
    private static CardsOutput output = new CardsOutput();

    private static ByteBuffer buffer = ByteBuffer.allocateDirect(BUFFER_SIZE);

    public static void main(String[] args) {
        try (SocketChannel socketChannel = SocketChannel.open();
             Scanner scanner = new Scanner(System.in)) {
            socketChannel.connect(new InetSocketAddress(SERVER_HOST, SERVER_PORT));

            System.out.println("Connected to the server.");

            iterate(scanner, socketChannel);

        } catch (IOException e) {
            throw new RuntimeException("There is a problem with the network communication", e);
        }
    }

    private static void readWriteToServet(String message, SocketChannel socketChannel) throws IOException {
        buffer.clear(); // switch to writing mode
        buffer.put(message.getBytes()); // buffer fill
        buffer.flip(); // switch to reading mode
        socketChannel.write(buffer); // buffer drain

        buffer.clear(); // switch to writing mode
        socketChannel.read(buffer); // buffer fill
        buffer.flip(); // switch to reading mode

        byte[] byteArray = new byte[buffer.remaining()];
        buffer.get(byteArray);
        String reply = new String(byteArray, StandardCharsets.UTF_8); // buffer drain

        output.print(reply);
    }

    private static void iterate(Scanner scanner, SocketChannel socketChannel) throws IOException {
        while (running) {
            System.out.print("Enter command: ");
            String message = scanner.nextLine(); // read a line from the console

            if ("quit".equals(message)) {
                return;
            }
            try {
                CheckInput check = CheckInput.of(message);
                if (check == null) {
                    System.out.println("No such command as: " + message);
                    continue;
                }
                if (!check.checkString()) {
                    System.out.println(check.getDescription());
                    continue;
                }
            } catch (Exception e) {
                System.out.println(e.getMessage());
                continue;
            }
            readWriteToServet(message, socketChannel);
        }
    }

}
