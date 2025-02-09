package bg.sofia.uni.fmi.mjt.uno.server.command;

import bg.sofia.uni.fmi.mjt.uno.server.exception.GameAlreadyExistsException;
import bg.sofia.uni.fmi.mjt.uno.server.exception.UserNotLoggedException;
import bg.sofia.uni.fmi.mjt.uno.server.manager.Manager;
import bg.sofia.uni.fmi.mjt.uno.server.player.Player;

import java.nio.channels.SelectionKey;

public class CreateGame implements Command {
    private int numberOfPlayers;
    String gameId;

    CreateGame(String numberOfPlayers, String gameId) {
        this.numberOfPlayers =
            numberOfPlayers == null ? 2 : Integer.parseInt(numberOfPlayers.replaceFirst("--players=", ""));
        this.gameId = gameId.replaceFirst("--game-id=", "");
    }

    @Override
    public void execute(Manager manager, SelectionKey key) throws GameAlreadyExistsException, UserNotLoggedException {
        if (manager == null || key == null) {
            throw new IllegalArgumentException("Arguments cannot be null!");
        }

        Object player = key.attachment();
        if (player == null) {
            throw new UserNotLoggedException("User not logged in cannot create game");
        }

        manager.addGame(gameId, (Player) player, numberOfPlayers);
    }
}
