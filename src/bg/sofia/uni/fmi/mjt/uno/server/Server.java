package bg.sofia.uni.fmi.mjt.uno.server;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
//import java.nio.charset.StandardCharsets;
import java.util.Iterator;

public class Server {
    private static final int SERVER_PORT = 5000;
    private static final int BUFFER_SIZE = 1024;
    private static final String HOST = "localhost";

    private boolean runningFlag = true;

    private ByteBuffer buffer;
    private Selector selector;

    public Server() { }

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
            while (runningFlag) {
                try {
                    int readyChannels = selector.select();
                    if (readyChannels == 0) {
                        continue;
                    }
                    Iterator<SelectionKey> keyIterator = selector.selectedKeys().iterator();
                    iterate(keyIterator);
                } catch (IOException e) {
                    System.out.println("Error occurred while processing client request: " + e.getMessage());
                }
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

    public boolean read(SelectionKey key) throws IOException {
        SocketChannel clientChannel = (SocketChannel) key.channel();
        buffer.clear();
        int readBytes = clientChannel.read(buffer);
        if (readBytes < 0) {
            System.out.println("Client has closed the connection");
            clientChannel.close();
            return false;
        }
        buffer.flip();
        clientChannel.write(buffer);
        return true;
    }

    public void iterate(Iterator<SelectionKey> keyIterator) throws IOException {
        while (keyIterator.hasNext()) {
            SelectionKey key = keyIterator.next();
            if (key.isReadable()) {
                if (!read(key)) {
                    continue;
                }
            } else if (key.isAcceptable()) {
                accept(selector, key);
            }
            keyIterator.remove();
        }
    }


    // those two needs to be implemented with my logic
//    private String getClientInput(SocketChannel clientChannel) throws IOException {
//        buffer.clear();
//
//        int readBytes = clientChannel.read(buffer);
//        if (readBytes < 0) {
//            clientChannel.close();
//            return null;
//        }
//
//        buffer.flip();
//
//        byte[] clientInputBytes = new byte[buffer.remaining()];
//        buffer.get(clientInputBytes);
//
//        return new String(clientInputBytes, StandardCharsets.UTF_8);
//    }
//
//    private void writeClientOutput(SocketChannel clientChannel, String output) throws IOException {
//        buffer.clear();
//        buffer.put(output.getBytes());
//        buffer.flip();
//
//        clientChannel.write(buffer);
//    }
}
