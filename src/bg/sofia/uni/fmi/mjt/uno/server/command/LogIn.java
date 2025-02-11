package bg.sofia.uni.fmi.mjt.uno.server.command;

import bg.sofia.uni.fmi.mjt.uno.server.exception.UserDoesNotExistException;
import bg.sofia.uni.fmi.mjt.uno.server.exception.WrongPasswordException;
import bg.sofia.uni.fmi.mjt.uno.server.manager.Manager;
import bg.sofia.uni.fmi.mjt.uno.server.player.Player;

import java.nio.channels.SelectionKey;

public class LogIn implements Command {
    private String username;
    private int password;

    public LogIn(String username, String password) {
        this.username = username.replaceFirst("--username=", "").trim();
        this.password = password.replaceFirst("--password=", "").hashCode();
    }

    @Override
    public String execute(Manager manager, SelectionKey key) throws UserDoesNotExistException,
        WrongPasswordException {
        if (manager == null || key == null) {
            throw new IllegalArgumentException("Arguments cannot be null!");
        }

        Player loggedPlayer = manager.login(username, password, key);
        key.attach(loggedPlayer);
        return loggedPlayer.toString();
    }
}
