package bg.sofia.uni.fmi.mjt.uno.server.command;

import bg.sofia.uni.fmi.mjt.uno.server.exception.UserAlreadyExistsException;
import bg.sofia.uni.fmi.mjt.uno.server.exception.UserDoesNotExistException;
import bg.sofia.uni.fmi.mjt.uno.server.exception.UserNotLoggedException;
import bg.sofia.uni.fmi.mjt.uno.server.manager.Manager;
import bg.sofia.uni.fmi.mjt.uno.server.player.Player;

import java.io.IOException;
import java.nio.channels.SelectionKey;

public class LogOutCommand implements Command {
    private static final String MESSAGE = "Logged out!";

    @Override
    public String execute(Manager manager, SelectionKey key) throws UserNotLoggedException, IOException,
        UserDoesNotExistException, UserAlreadyExistsException {
        if (manager == null || key == null) {
            throw new IllegalArgumentException("Arguments cannot be null!");
        }

        Object player =  key.attachment();
        if (player == null) {
            throw new UserNotLoggedException("You have to be logged in order to logout!");
        }
        manager.logout((Player) player);
        key.attach(null);
        return MESSAGE;
    }
}
