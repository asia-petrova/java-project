package bg.sofia.uni.fmi.mjt.uno.server.command;

import bg.sofia.uni.fmi.mjt.uno.server.game.Status;
import bg.sofia.uni.fmi.mjt.uno.server.manager.Manager;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.SocketChannel;
import java.nio.charset.StandardCharsets;

public class ListGames implements Command {
    private Status status;

    public ListGames(String status) {
        this.status = Status.valueOf(status.replaceAll("--status=", "").trim().toUpperCase());
    }

    @Override
    public String execute(Manager manager, SelectionKey key) throws IOException {
        if (manager == null || key == null) {
            throw new IllegalArgumentException("Arguments cannot be null!");
        }

        return manager.getWithStatus(status);
    }
}
