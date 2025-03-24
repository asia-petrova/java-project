package bg.sofia.uni.fmi.mjt.uno.server.command;

import bg.sofia.uni.fmi.mjt.uno.server.exception.UserAlreadyExistsException;
import bg.sofia.uni.fmi.mjt.uno.server.exception.UserDoesNotExistException;
import bg.sofia.uni.fmi.mjt.uno.server.exception.WrongPasswordException;
import bg.sofia.uni.fmi.mjt.uno.server.manager.Manager;
import bg.sofia.uni.fmi.mjt.uno.server.player.Player;

import java.io.IOException;
import java.nio.channels.SelectionKey;

public class LogInCommand implements Command {
    private static final String MESSAGE = "Logged in!";
    private String username;
    private int password;

    public LogInCommand(String username, String password) {
        this.username = username.replaceFirst("username\\s*=", "").trim();
        this.password = password.replaceFirst("password\\s*=", "").trim().hashCode();
    }

    @Override
    public String execute(Manager manager, SelectionKey key) throws UserDoesNotExistException,
        WrongPasswordException, IOException, UserAlreadyExistsException {
        if (manager == null || key == null) {
            throw new IllegalArgumentException("Arguments cannot be null!");
        }

        if (key.attachment() != null) {
            throw new UserAlreadyExistsException("User already logged!");
        }

        Player loggedPlayer = manager.login(username, password, key);
        key.attach(loggedPlayer);
        return MESSAGE;
    }
}
