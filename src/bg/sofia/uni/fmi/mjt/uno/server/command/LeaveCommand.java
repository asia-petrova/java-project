package bg.sofia.uni.fmi.mjt.uno.server.command;

import bg.sofia.uni.fmi.mjt.uno.server.exception.GameDoesNotExistsException;

import bg.sofia.uni.fmi.mjt.uno.server.exception.UserNotLoggedException;
import bg.sofia.uni.fmi.mjt.uno.server.manager.Manager;
import bg.sofia.uni.fmi.mjt.uno.server.player.Player;

import java.nio.channels.SelectionKey;

public class LeaveCommand implements Command {
    private static final String MESSAGE = "Left game";

    @Override
    public String execute(Manager manager, SelectionKey key)
        throws UserNotLoggedException, GameDoesNotExistsException {
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
        manager.leaveGame(player, player.getCurrentGame());
        return MESSAGE;
    }
}
