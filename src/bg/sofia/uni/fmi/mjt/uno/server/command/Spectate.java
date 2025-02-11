package bg.sofia.uni.fmi.mjt.uno.server.command;

import bg.sofia.uni.fmi.mjt.uno.server.exception.CanNotPlayThisCardException;
import bg.sofia.uni.fmi.mjt.uno.server.exception.GameDoesNotExistsException;
import bg.sofia.uni.fmi.mjt.uno.server.exception.UserNotLoggedException;
import bg.sofia.uni.fmi.mjt.uno.server.manager.Manager;
import bg.sofia.uni.fmi.mjt.uno.server.player.Player;

import java.io.IOException;
import java.nio.channels.SelectionKey;

public class Spectate implements Command {

    @Override
    public String execute(Manager manager, SelectionKey key)
        throws UserNotLoggedException, IOException, GameDoesNotExistsException, CanNotPlayThisCardException {
        if (manager == null || key == null) {
            throw new IllegalArgumentException("Arguments cannot be null!");
        }

        Object obj = key.attachment();
        if (obj == null) {
            throw new UserNotLoggedException("User not logged in cannot create game");
        }
        Player player = (Player) obj;
        if (!player.inGame()) {
            throw new GameDoesNotExistsException("Player should be in game to have a hand to show");
        }
        manager.getGame(player).spectate(player);
        return "spectate";
    }
}
