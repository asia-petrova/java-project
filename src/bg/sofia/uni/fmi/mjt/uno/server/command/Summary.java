package bg.sofia.uni.fmi.mjt.uno.server.command;

import bg.sofia.uni.fmi.mjt.uno.server.exception.CanNotPlayThisCardException;
import bg.sofia.uni.fmi.mjt.uno.server.exception.GameDoesNotExistsException;
import bg.sofia.uni.fmi.mjt.uno.server.exception.NotInGameException;
import bg.sofia.uni.fmi.mjt.uno.server.exception.NotRightTurnOfPlayerException;
import bg.sofia.uni.fmi.mjt.uno.server.manager.Manager;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.SocketChannel;
import java.nio.charset.StandardCharsets;

public class Summary implements Command {
    private String id;

    public Summary(String id) {
        this.id = id.replaceFirst("--game-id=", "");
    }

    @Override
    public String execute(Manager manager, SelectionKey key)
        throws IOException, GameDoesNotExistsException,
        NotRightTurnOfPlayerException, CanNotPlayThisCardException, NotInGameException {
        if (manager == null || key == null) {
            throw new IllegalArgumentException("Arguments cannot be null!");
        }

        return manager.summary(id);
    }
}
