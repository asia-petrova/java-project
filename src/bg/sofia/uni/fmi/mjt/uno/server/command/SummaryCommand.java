package bg.sofia.uni.fmi.mjt.uno.server.command;

import bg.sofia.uni.fmi.mjt.uno.server.exception.CanNotPlayThisCardException;
import bg.sofia.uni.fmi.mjt.uno.server.exception.GameDoesNotExistsException;
import bg.sofia.uni.fmi.mjt.uno.server.exception.NotRightTurnOfPlayerException;
import bg.sofia.uni.fmi.mjt.uno.server.manager.Manager;

import java.io.IOException;
import java.nio.channels.SelectionKey;

public class SummaryCommand implements Command {
    private String id;

    public SummaryCommand(String id) {
        this.id = id.replaceFirst("--game-id\\s*=", "");
    }

    @Override
    public String execute(Manager manager, SelectionKey key)
        throws IOException, GameDoesNotExistsException,
        NotRightTurnOfPlayerException, CanNotPlayThisCardException {
        if (manager == null || key == null) {
            throw new IllegalArgumentException("Arguments cannot be null!");
        }

        return manager.summary(id);
    }
}
