package bg.sofia.uni.fmi.mjt.uno.server.command;

import bg.sofia.uni.fmi.mjt.uno.server.exception.GameAlreadyExistsException;
import bg.sofia.uni.fmi.mjt.uno.server.exception.UserNotLoggedException;
import bg.sofia.uni.fmi.mjt.uno.server.manager.Manager;
import bg.sofia.uni.fmi.mjt.uno.server.player.Player;

import java.io.IOException;
import java.nio.channels.SelectionKey;

public class CreateGameCommand implements Command {
    private static final String MESSAGE = "Created!";
    private int numberOfPlayers;
    String gameId;

    CreateGameCommand(String numberOfPlayers, String gameId) {
        if (numberOfPlayers == null) {
            this.numberOfPlayers = 2;
        } else {
            this.numberOfPlayers = Integer.parseInt(numberOfPlayers.replaceFirst("players\\s*=", "").trim());
        }
        this.gameId = gameId.replaceFirst("game-id\\s*=", "").trim();
    }

    @Override
    public String execute(Manager manager, SelectionKey key)
        throws GameAlreadyExistsException, UserNotLoggedException, IOException {
        if (manager == null || key == null) {
            throw new IllegalArgumentException("Arguments cannot be null!");
        }

        Object player = key.attachment();
        if (player == null) {
            throw new UserNotLoggedException("User not logged in cannot create game");
        }

        manager.addGame(gameId, (Player) player, numberOfPlayers);
        return MESSAGE;
    }
}
