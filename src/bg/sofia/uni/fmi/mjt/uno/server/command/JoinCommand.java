package bg.sofia.uni.fmi.mjt.uno.server.command;

import bg.sofia.uni.fmi.mjt.uno.server.exception.GameAlreadyFullException;
import bg.sofia.uni.fmi.mjt.uno.server.exception.GameDoesNotExistsException;
import bg.sofia.uni.fmi.mjt.uno.server.exception.UserAlreadyExistsException;
import bg.sofia.uni.fmi.mjt.uno.server.exception.UserNotLoggedException;
import bg.sofia.uni.fmi.mjt.uno.server.manager.Manager;
import bg.sofia.uni.fmi.mjt.uno.server.player.Player;

import java.io.IOException;
import java.nio.channels.SelectionKey;

public class JoinCommand implements Command {
    private static final String MESSAGE = "Joined!";

    String gameId;
    String displayName;

    public JoinCommand(String gameId, String displayName) {
        this.gameId = gameId.replaceFirst("game-id\\s*=", "").trim();
        this.displayName = displayName == null ? "" : displayName.replaceFirst("display-name\\s*=", "").trim();
    }

    @Override
    public String execute(Manager manager, SelectionKey key) throws UserNotLoggedException, UserAlreadyExistsException,
        GameAlreadyFullException, GameDoesNotExistsException, IOException {
        if (manager == null || key == null) {
            throw new IllegalArgumentException("Arguments cannot be null!");
        }

        Object obj = key.attachment();
        if (obj == null) {
            throw new UserNotLoggedException("User not logged in cannot create game");
        }
        Player player = (Player) obj;
        if (player.inGame()) {
            try {
                if (manager.getGame(player).end() || manager.getGame(player).inGame(player) == null) {
                    manager.getGame(player).leaveGame(player);
                } else {
                    throw new UserAlreadyExistsException("User already exists in game");
                }
            } catch (GameDoesNotExistsException e) {
                player.leaveGame();
            }
        }
        manager.joinGame(player, gameId, displayName);
        return player.getDisplayName() + " in game: " + gameId + "! " + MESSAGE;
    }
}
