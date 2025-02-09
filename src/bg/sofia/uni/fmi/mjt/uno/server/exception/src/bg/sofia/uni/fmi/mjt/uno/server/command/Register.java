package bg.sofia.uni.fmi.mjt.uno.server.command;

import bg.sofia.uni.fmi.mjt.uno.server.exception.UserAlreadyExistsException;
import bg.sofia.uni.fmi.mjt.uno.server.manager.Manager;

import java.nio.channels.SelectionKey;

public class Register implements Command {
    private String username;
    private int password;

    public Register(String username, String password) {
        this.username = username.replaceFirst("--username=", "").trim();
        this.password = password.replaceFirst("--password=", "").hashCode();
    }

    @Override
    public void execute(Manager manager, SelectionKey key) throws UserAlreadyExistsException {
        if (manager == null || key == null) {
            throw new IllegalArgumentException("Arguments cannot be null!");
        }
        manager.registerUser(username, password);
    }
}
