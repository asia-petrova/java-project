package bg.sofia.uni.fmi.mjt.uno.server.command;

import bg.sofia.uni.fmi.mjt.uno.server.exception.GameAlreadyFullException;
import bg.sofia.uni.fmi.mjt.uno.server.exception.GameDoesNotExistsException;
import bg.sofia.uni.fmi.mjt.uno.server.exception.UserAlreadyExistsException;
import bg.sofia.uni.fmi.mjt.uno.server.exception.UserNotLoggedException;
import bg.sofia.uni.fmi.mjt.uno.server.manager.Manager;
import bg.sofia.uni.fmi.mjt.uno.server.player.Player;

import java.nio.channels.SelectionKey;

public class Join implements Command {
    String gameId;
    String displayName;

    public Join(String gameId, String displayName) {
        this.gameId = gameId.replaceFirst("--game-id=", "").trim();
        this.displayName = displayName == null ? "" : displayName.replaceFirst("--display-name=", "").trim();
    }

    @Override
    public void execute(Manager manager, SelectionKey key) throws UserNotLoggedException, UserAlreadyExistsException,
        GameAlreadyFullException, GameDoesNotExistsException {
        Object obj = key.attachment();
        if (obj == null) {
            throw new UserNotLoggedException("User not logged in cannot create game");
        }
        Player player = (Player) obj;
        if (player.inGame()) {
            throw new UserAlreadyExistsException("User already exists in game");
        }
        manager.joinGame(player, gameId, displayName);
    }
}
