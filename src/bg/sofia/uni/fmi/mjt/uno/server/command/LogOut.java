package bg.sofia.uni.fmi.mjt.uno.server.command;

import bg.sofia.uni.fmi.mjt.uno.server.exception.UserNotLoggedException;
import bg.sofia.uni.fmi.mjt.uno.server.manager.Manager;
import bg.sofia.uni.fmi.mjt.uno.server.player.Player;

import java.nio.channels.SelectionKey;

public class LogOut implements Command {
    public LogOut() { }

    @Override
    public void execute(Manager manager, SelectionKey key) throws UserNotLoggedException {
        Object player =  key.attachment();
        if (player == null) {
            throw new UserNotLoggedException("You have to be logged in order to logout!");
        }
        key.attach(null);
    }
}
