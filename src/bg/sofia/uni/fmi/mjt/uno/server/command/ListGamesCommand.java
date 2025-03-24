package bg.sofia.uni.fmi.mjt.uno.server.command;

import bg.sofia.uni.fmi.mjt.uno.server.game.Status;
import bg.sofia.uni.fmi.mjt.uno.server.manager.Manager;

import java.io.IOException;
import java.nio.channels.SelectionKey;

public class ListGamesCommand implements Command {
    private static final int BUFFER_SIZE = 1024;

    private Status status;

    public ListGamesCommand(String status) {
        this.status = Status.valueOf(status.replaceAll("status\\s*=", "").trim().toUpperCase());
    }

    @Override
    public String execute(Manager manager, SelectionKey key) throws IOException {
        if (manager == null || key == null) {
            throw new IllegalArgumentException("Arguments cannot be null!");
        }

        return manager.getWithStatus(status);
    }
}
