package bg.sofia.uni.fmi.mjt.uno.server;

import bg.sofia.uni.fmi.mjt.uno.server.command.Command;
import bg.sofia.uni.fmi.mjt.uno.server.exception.ProblemWithFileException;
import bg.sofia.uni.fmi.mjt.uno.server.logger.Logger;
import bg.sofia.uni.fmi.mjt.uno.server.manager.Manager;
import bg.sofia.uni.fmi.mjt.uno.server.manager.SystemManager;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Date;
import java.util.Iterator;

public class Server {
    private static final int SERVER_PORT = 5000;
    private static final int BUFFER_SIZE = 1024;
    private static final String HOST = "localhost";
    private static final String STOP = "SERVER_STOP";

    private Logger logger;
    private Manager gamesManager;

    private boolean runningFlag = true;

    private ByteBuffer buffer;
    private Selector selector;

    public Server() {
        logger = new Logger();
        gamesManager = new SystemManager();
    }

    public static void main(String[] args) {
        Server server = new Server();
        server.start();
        server.stop();
    }

    public void start() {
        try (ServerSocketChannel serverSocketChannel = ServerSocketChannel.open()) {
            selector = Selector.open();
            configureServerSocketChannel(serverSocketChannel, selector);
            this.buffer = ByteBuffer.allocate(BUFFER_SIZE);
            try {
                iterate();
            } catch (IOException e) {
                System.out.println("Error occurred while processing client request: " + e.getMessage());
            }

        } catch (IOException e) {
            throw new UncheckedIOException("failed to start server", e);
        }
    }

    public void stop() {
        this.runningFlag = false;
        if (selector.isOpen()) {
            selector.wakeup();
        }
        try {
            gamesManager.saveInFile();
        } catch (ProblemWithFileException e) {
            logger.logProblem(new Date() + ": " + Arrays.toString(e.getStackTrace()));
        }

    }

    private void configureServerSocketChannel(ServerSocketChannel channel, Selector selector) throws IOException {
        channel.bind(new InetSocketAddress(HOST, SERVER_PORT));
        channel.configureBlocking(false);
        channel.register(selector, SelectionKey.OP_ACCEPT);
    }

    private void accept(Selector selector, SelectionKey key) throws IOException {
        ServerSocketChannel sockChannel = (ServerSocketChannel) key.channel();
        SocketChannel accept = sockChannel.accept();

        accept.configureBlocking(false);
        accept.register(selector, SelectionKey.OP_READ);
    }

    public String read(SocketChannel clientChannel) throws IOException {
        buffer.clear();
        int readBytes = clientChannel.read(buffer);
        if (readBytes < 0) {
            System.out.println("Client has closed the connection");
            //key.cancel();
            clientChannel.close();
            return null;
        }
        buffer.flip();
        byte[] clientInputBytes = new byte[buffer.remaining()];
        buffer.get(clientInputBytes);
        clientChannel.write(buffer);
        return new String(clientInputBytes, StandardCharsets.UTF_8);
    }

    public void iterate() throws IOException {
        while (runningFlag) {
            int readyChannels = selector.select(1000);
            if (readyChannels == 0) {
                System.out.println("No more clients connected");
                continue;
            }
            Iterator<SelectionKey> keyIterator = selector.selectedKeys().iterator();
            while (keyIterator.hasNext()) {
                SelectionKey key = keyIterator.next();
                if (key.isReadable()) {
                    SocketChannel sc = (SocketChannel) key.channel();
                    String input = read(sc);
                    if (input == null) {
                        continue;
                    }
                    if (input.equals(STOP)) {
                        runningFlag = false;
                        return;
                    }
                    executeCommand(key, input, sc);
                } else if (key.isAcceptable()) {
                    accept(selector, key);
                } else if (key.isWritable()) {
                    System.out.println("Key is writable, sending data...");
                }
                keyIterator.remove();
            }
        }
    }

    private void executeCommand(SelectionKey key, String command, SocketChannel sc) {
        String messageForClient = "";
        try {
            logger.logMessage(new Date() + ": " + command);
            messageForClient = Command.of(command).execute(gamesManager, key);
        } catch (IOException e) {
            logger.logProblem(new Date() + ": " + "\n\t>>" + Arrays.toString(e.getStackTrace()));
            messageForClient = "There is a problem with the sever! Please try again later!";
        } catch (Exception e) {
            logger.logProblem(new Date() + ": " + e.getMessage() + "\n\t>>" + Arrays.toString(e.getStackTrace()));
            messageForClient = e.getMessage();
        }

        try {
            sendMessage(messageForClient, key, sc);
            System.out.println(messageForClient);
        } catch (IOException e) {
            logger.logProblem(new Date() + ": " + Arrays.toString(e.getStackTrace()));
        }
    }

    private void sendMessage(String message, SelectionKey key, SocketChannel sc) throws IOException {
        message += "\n";
        ByteBuffer buffer = ByteBuffer.wrap(message.getBytes(StandardCharsets.UTF_8)); // Автоматично създава буфер с данните

        while (buffer.hasRemaining()) { // Проверява дали има още данни за изпращане
            int bytesWritten = sc.write(buffer);
            System.out.println("Bytes written: " + bytesWritten);
        }

        System.out.println("Message fully sent: " + message);
    }


}
